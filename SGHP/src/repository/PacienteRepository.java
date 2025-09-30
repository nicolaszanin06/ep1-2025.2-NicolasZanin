package repository;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import model.Paciente;
import model.PacienteEspecial;
import model.enums.PlanoDeSaude;

public class PacienteRepository {

    private static final String SEP = ";";
    private static final String HEADER = "tipo;nome;idade;cpf;planoCodigo";

    /**
     * Salva a lista de pacientes em CSV no caminho informado.
     * Formato:
     * tipo;nome;idade;cpf;planoCodigo
     * COMUM;Maria;30;12345678901;-
     * ESPECIAL;João;65;10987654321;4
     */
    public void salvar(List<Paciente> pacientes, Path arquivo) throws IOException {
        if (arquivo.getParent() != null) {
            Files.createDirectories(arquivo.getParent());
        }

        try (BufferedWriter bw = Files.newBufferedWriter(arquivo)) {
            bw.write(HEADER);
            bw.newLine();

            for (Paciente p : pacientes) {
                if (p instanceof PacienteEspecial pe) {
                    String linha = String.join(SEP, "ESPECIAL", escape(p.getNome()), String.valueOf(p.getIdade()),
                            p.getCpf(), String.valueOf(pe.getPlanoSaude().getCodigo()));
                    bw.write(linha);
                } else {
                    String linha = String.join(SEP, "COMUM", escape(p.getNome()), String.valueOf(p.getIdade()),
                            p.getCpf(), "-");
                    bw.write(linha);
                }
                bw.newLine();
            }
        }
    }

    public List<Paciente> carregar(Path arquivo) throws IOException {
        List<Paciente> lista = new ArrayList<>();
        if (!Files.exists(arquivo)) {
            return lista;
        }

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
                String[] partes = linha.split(";", -1);
                if (partes.length < 5)
                    continue;
                String tipo = partes[0];
                String nome = unescape(partes[1]);
                int idade = parseIntSafe(partes[2], 0);
                String cpf = partes[3];
                String planoCodigo = partes[4];

                if ("ESPECIAL".equalsIgnoreCase(tipo)) {
                    try {
                        int cod = Integer.parseInt(planoCodigo);
                        PlanoDeSaude plano = PlanoDeSaude.getPorCodigo(cod);
                        lista.add(new PacienteEspecial(nome, idade, cpf, plano));
                    } catch (Exception e) {
                        lista.add(new Paciente(nome, idade, cpf));
                    }
                } else {
                    lista.add(new Paciente(nome, idade, cpf));
                }
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

    private static String escape(String s) {
        if (s == null)
            return "";
        return s.replace(";", ",");
    }

    private static String unescape(String s) {
        return s;
    }
}
