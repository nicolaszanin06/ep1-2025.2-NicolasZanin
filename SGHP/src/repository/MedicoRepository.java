package repository;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import model.Medico;

public class MedicoRepository {

    private static final String SEP = ";";
    private static final String HEADER = "nome;idade;cpf;crm;especialidade;custoConsulta";

    public void salvar(List<Medico> medicos, Path arquivo) throws IOException {
        if (arquivo.getParent() != null) {
            Files.createDirectories(arquivo.getParent());
        }

        try (BufferedWriter bw = Files.newBufferedWriter(arquivo)) {
            bw.write(HEADER);
            bw.newLine();

            for (Medico m : medicos) {
                String linha = String.join(SEP,
                        escape(m.getNome()),
                        String.valueOf(m.getIdade()),
                        m.getCpf(),
                        m.getCrm(),
                        escape(m.getEspecialidade()),
                        String.valueOf(m.getCustoConsulta()));
                bw.write(linha);
                bw.newLine();
            }
        }
    }

    public List<Medico> carregar(Path arquivo) throws IOException {
        List<Medico> lista = new ArrayList<>();
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

                String[] partes = linha.split(SEP, -1);
                if (partes.length < 6)
                    continue; // linha malformada

                String nome = unescape(partes[0]);
                int idade = parseIntSafe(partes[1], 0);
                String cpf = partes[2];
                String crm = partes[3];
                String especialidade = unescape(partes[4]);
                double custoConsulta = parseDoubleSafe(partes[5], 0.0);

                lista.add(new Medico(nome, idade, cpf, crm, especialidade, custoConsulta));
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

    private static String escape(String s) {
        if (s == null)
            return "";
        return s.replace(";", ",");
    }

    private static String unescape(String s) {
        return s;
    }
}
