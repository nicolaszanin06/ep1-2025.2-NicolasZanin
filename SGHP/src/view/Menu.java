package view;

import java.util.Scanner;
import service.GerenciadorHospitalar;

public class Menu {
    private Scanner sc = new Scanner(System.in);
    private GerenciadorHospitalar gh;

    public Menu(Scanner sc, GerenciadorHospitalar gh) throws InterruptedException {
        this.sc = sc;
        this.gh = gh;
    }

    public void exibirMenu() throws InterruptedException {
        int opcao;
        do {
            System.out.println("");
            System.out.println("------ Menu Principal ------");
            System.out.println("1. Gerenciar Pacientes");
            System.out.println("2. Gerenciar Médicos");
            System.out.println("3. Gerenciar Consultas");
            System.out.println("4. Sair");
            System.out.println("---------------------------");
            System.out.print("> Escolha uma opção: ");
            opcao = sc.nextInt();
            switch (opcao) {
                case 1:
                    MenuPaciente menuPaciente = new MenuPaciente(sc, gh);
                    menuPaciente.exibirMenu();
                    break;
                case 2:
                    MenuMedico menuMedico = new MenuMedico(sc, gh);
                    menuMedico.exibirMenu();
                    break;
                case 3:
                    MenuConsulta menuConsulta = new MenuConsulta(sc, gh);
                    menuConsulta.exibirMenu();
                    break;
                case 4:
                    System.out.println("Salvando alterações e saindo...");
                    break;
                default:
                    System.out.println("Opção inválida!");
                    Thread.sleep(1000);
            }
        } while (opcao != 4);
    }
}
