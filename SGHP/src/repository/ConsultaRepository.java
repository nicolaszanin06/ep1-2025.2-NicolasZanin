package repository;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import model.Consulta;
import model.Medico;
import model.Paciente;
import model.enums.StatusConsulta;

public class ConsultaRepository {

    private static final String SEP = ";";
    private static final String HEADER = "id;cpfPaciente;crmMedico;dataHora;local;motivo;status;diagnostico;prescricoes";
    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    public void salvar(List<Consulta> consultas, Path arquivo) throws IOException {
        if (arquivo.getParent() != null) {
            Files.createDirectories(arquivo.getParent());
        }

        try (BufferedWriter bw = Files.newBufferedWriter(arquivo)) {
            bw.write(HEADER);
            bw.newLine();

            for (Consulta c : consultas) {
                String prescricoesStr = (c.getPrescricoes() == null || c.getPrescricoes().isEmpty())
                        ? ""
                        : String.join("|", c.getPrescricoes());

                String linha = String.join(SEP,
                        String.valueOf(c.getId()),
                        safe(c.getPaciente().getCpf()),
                        safe(c.getMedico().getCrm()),
                        c.getDataHora().format(FMT),
                        escape(c.getLocal()),
                        escape(c.getMotivo()),
                        c.getStatus().name(),
                        escape(c.getDiagnostico()),
                        escape(prescricoesStr)
                );
                bw.write(linha);
                bw.newLine();
            }
        }
    }

    public List<Consulta> carregar(
            Path arquivo,
            Function<String, Paciente> findPaciente,
            Function<String, Medico> findMedico
    ) throws IOException {

        List<Consulta> lista = new ArrayList<>();
        if (!Files.exists(arquivo)) {
            return lista;
        }

        int maxIdLido = 0;

        try (BufferedReader br = Files.newBufferedReader(arquivo)) {
            String linha;
            boolean primeira = true;

            while ((linha = br.readLine()) != null) {
                linha = linha.trim();
                if (linha.isEmpty()) continue;

                if (primeira && linha.equalsIgnoreCase(HEADER)) {
                    primeira = false;
                    continue;
                }
                primeira = false;

                String[] p = linha.split(SEP, -1);
                if (p.length < 9) continue;

                int idLido = parseIntSafe(p[0], 0);
                String cpf = p[1];
                String crm = p[2];
                LocalDateTime dataHora = parseDateTimeSafe(p[3], FMT, null);
                String local = unescape(p[4]);
                String motivo = unescape(p[5]);
                StatusConsulta status = parseStatusSafe(p[6]);
                String diagnostico = unescape(p[7]);
                String prescricoesStr = unescape(p[8]);

                Paciente paciente = findPaciente.apply(cpf);
                Medico medico = findMedico.apply(crm);
                if (paciente == null || medico == null || dataHora == null) {
                    continue;
                }

                Consulta c = new Consulta(paciente, medico, dataHora, local, motivo);
                c.setStatus(status);
                c.setDiagnostico(diagnostico);
                if (!prescricoesStr.isEmpty()) {
                    List<String> prescricoes = Arrays.stream(prescricoesStr.split("\\|"))
                            .map(String::trim)
                            .filter(s -> !s.isEmpty())
                            .toList();
                    c.setPrescricoes(new ArrayList<>(prescricoes));
                }

                lista.add(c);
                if (idLido > maxIdLido) maxIdLido = idLido;
            }
        }

        return lista;
    }

    // --- Helpers ---

    private static int parseIntSafe(String s, int def) {
        try { return Integer.parseInt(s); } catch (Exception e) { return def; }
    }

    private static LocalDateTime parseDateTimeSafe(String s, DateTimeFormatter fmt, LocalDateTime def) {
        try { return LocalDateTime.parse(s, fmt); } catch (Exception e) { return def; }
    }

    private static StatusConsulta parseStatusSafe(String s) {
        try { return StatusConsulta.valueOf(s); } catch (Exception e) { return StatusConsulta.AGENDADA; }
    }

    private static String safe(String s) { return s == null ? "" : s; }

    private static String escape(String s) {
        if (s == null) return "";
        return s.replace(";", ",");
    }

    private static String unescape(String s) {
        return s;
    }
}
