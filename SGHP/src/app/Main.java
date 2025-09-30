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

            // Carrega Pacientes
            try {
                var pacientes = repoPac.carregar(Path.of("data/pacientes.csv"));
                pacientes.forEach(gh::cadastrarPacienteValidador);
            } catch (Exception e) {
                System.out.println("Aviso: não foi possível carregar pacientes: " + e.getMessage());
            }

            Thread.sleep(1000);
            Menu menu = new Menu(sc, gh);
            menu.exibirMenu();

        }
        sc.close();

        // Salva Pacientes
        try {
            repoPac.salvar(gh.getPacientes(), Path.of("data/pacientes.csv"));
        } catch (Exception e) {
            System.out.println("Erro: não foi possível salvar pacientes: " + e.getMessage());
        }
    }
}
