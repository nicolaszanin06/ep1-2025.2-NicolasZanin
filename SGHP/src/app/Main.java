package app;

import java.nio.file.Path;
import java.util.Scanner;

import service.GerenciadorHospitalar;
import view.Menu;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        GerenciadorHospitalar gh = new GerenciadorHospitalar();
        var repoPac = new repository.PacienteRepository();
        var repoMed = new repository.MedicoRepository();
        var repoCon = new repository.ConsultaRepository();

        {
            System.out.println("Bem-vindo(a) ao Sistema de Gerenciamento Hospitalar!");
            System.out.print("Carregando o menu");
            // Animação de Loading
            Thread.sleep(700);
            System.out.print(".");
            Thread.sleep(700);
            System.out.print(".");
            Thread.sleep(700);
            System.out.println(".");

            // Carregar arquivos
            try {
                var pacientes = repoPac.carregar(Path.of("data/pacientes.csv"));
                for (var p : pacientes) {
                    gh.cadastrarPacienteValidador(p);
                }

                var medicos = repoMed.carregar(Path.of("data/medicos.csv"));
                for (var m : medicos) {
                    gh.cadastrarMedicoValidador(m);
                }

                var consultas = repoCon.carregar(
                        Path.of("data/consultas.csv"),
                        cpf -> gh.buscarPacientePorCPF(cpf),
                        crm -> gh.buscarMedicoPorCRM(crm));
                gh.getConsultas().addAll(consultas);

            } catch (Exception e) {
                System.out.println("Aviso ao carregar dados: " + e.getMessage());
            }

            Thread.sleep(1000);
            Menu menu = new Menu(sc, gh);
            menu.exibirMenu();

        }
        sc.close();

        // Salvar arquivos
        try {
            repoPac.salvar(gh.getPacientes(), Path.of("data/pacientes.csv"));
            repoMed.salvar(gh.getMedicos(), Path.of("data/medicos.csv"));
            repoCon.salvar(gh.getConsultas(), Path.of("data/consultas.csv"));
        } catch (Exception e) {
            System.out.println("Erro ao salvar dados: " + e.getMessage());
        }

    }
}
