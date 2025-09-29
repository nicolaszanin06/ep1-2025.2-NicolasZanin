package view;

import java.util.Scanner;
import service.GerenciadorHospitalar;

public class Menu {
    private Scanner sc = new Scanner(System.in);
    private GerenciadorHospitalar gh;
    private MenuPaciente menuPaciente;
    private MenuMedico menuMedico;
    private MenuConsulta menuConsulta;

    public Menu(Scanner sc, GerenciadorHospitalar gh) throws InterruptedException {
        this.sc = sc;
        this.gh = gh;
        this.menuPaciente = new MenuPaciente(sc, gh);
        this.menuMedico = new MenuMedico(sc, gh);
        this.menuConsulta = new MenuConsulta(sc, gh);
    }

    public void exibirMenu() throws InterruptedException {
        boolean rodando = true;
        while (rodando) {
            System.out.println("");
            System.out.println("------ Menu Principal ------");
            System.out.println("1. Gerenciar Pacientes");
            System.out.println("2. Gerenciar Médicos");
            System.out.println("3. Gerenciar Consultas");
            System.out.println("4. Sair");
            System.out.println("---------------------------");
            int op = util.Utilitario.lerInt(sc, "> Escolha uma opção: ");

            switch (op) {
                case 1:
                    menuPaciente.exibirMenu();
                    break;
                case 2:
                    menuMedico.exibirMenu();
                    break;
                case 3:
                    menuConsulta.exibirMenu();
                    break;
                case 4:
                    rodando = false;
                    System.out.println("Salvando alterações e saindo...");
                    break;
                default:
                    System.out.println("Opção inválida!");
                    Thread.sleep(1000);
            }
        }
    }
}
