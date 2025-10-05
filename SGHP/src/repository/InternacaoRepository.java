package repository;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import model.Internacao;
import model.Medico;
import model.Paciente;

public class InternacaoRepository {

    private static final String SEP = ";";
    private static final String HEADER = "id;cpfPaciente;crmMedico;dataInternacao;dataAlta;quarto;custoDiario;cancelada";
    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    /** Salva a lista de internações em CSV. */
    public void salvar(List<Internacao> internacoes, Path arquivo) throws IOException {
        if (arquivo.getParent() != null) {
            Files.createDirectories(arquivo.getParent());
        }

        try (BufferedWriter bw = Files.newBufferedWriter(arquivo)) {
            bw.write(HEADER);
            bw.newLine();

            for (Internacao i : internacoes) {
                String dataAltaStr = (i.getDataAlta() == null) ? "" : i.getDataAlta().format(FMT);
                String linha = String.join(SEP,
                        String.valueOf(i.getId()),
                        i.getPaciente().getCpf(),
                        i.getMedico().getCrm(),
                        i.getDataInternacao().format(FMT),
                        dataAltaStr,
                        String.valueOf(i.getNumeroQuarto()),
                        String.valueOf(i.getCustoDiario()),
                        String.valueOf(i.isCancelada()));
                bw.write(linha);
                bw.newLine();
            }
        }
    }

    /** Carrega internações do CSV. */
    public List<Internacao> carregar(
            Path arquivo,
            Function<String, Paciente> findPaciente,
            Function<String, Medico> findMedico) throws IOException {

        List<Internacao> lista = new ArrayList<>();
        if (!Files.exists(arquivo))
            return lista;

        try (BufferedReader br = Files.newBufferedReader(arquivo)) {
            String linha;
            boolean primeira = true;

            while ((linha = br.readLine()) != null) {
                linha = linha.trim();
                if (linha.isEmpty())
                    continue;

                if (primeira && linha.equalsIgnoreCase(HEADER)) {
                    primeira = false;
                    continue;
                }
                primeira = false;

                String[] p = linha.split(SEP, -1);
                if (p.length < 8)
                    continue;

                int idLido = parseIntSafe(p[0], 0);
                String cpf = p[1];
                String crm = p[2];
                LocalDateTime dataInternacao = parseDateTimeSafe(p[3], FMT, null);
                LocalDateTime dataAlta = p[4].isBlank() ? null : parseDateTimeSafe(p[4], FMT, null);
                int quarto = parseIntSafe(p[5], 0);
                double custoDiario = parseDoubleSafe(p[6], 0.0);
                boolean cancelada = Boolean.parseBoolean(p[7]);

                Paciente paciente = findPaciente.apply(cpf);
                Medico medico = findMedico.apply(crm);
                if (paciente == null || medico == null || dataInternacao == null)
                    continue;

                Internacao i = new Internacao(paciente, medico, dataInternacao, dataAlta, quarto, custoDiario);
                if (cancelada)
                    i.cancelarInternacao();
                lista.add(i);
            }
        }
        return lista;
    }

    // --- Helpers ---
    private static int parseIntSafe(String s, int def) {
        try {
            return Integer.parseInt(s);
        } catch (Exception e) {
            return def;
        }
    }

    private static double parseDoubleSafe(String s, double def) {
        try {
            return Double.parseDouble(s);
        } catch (Exception e) {
            return def;
        }
    }

    private static LocalDateTime parseDateTimeSafe(String s, DateTimeFormatter fmt, LocalDateTime def) {
        try {
            return LocalDateTime.parse(s, fmt);
        } catch (Exception e) {
            return def;
        }
    }
}
