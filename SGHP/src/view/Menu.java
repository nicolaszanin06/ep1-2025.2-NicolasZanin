package view;

import java.util.Scanner;
import service.GerenciadorHospitalar;

public class Menu {
    private Scanner sc = new Scanner(System.in);
    private GerenciadorHospitalar gh;

    public Menu(Scanner sc, GerenciadorHospitalar gh) throws InterruptedException {
        this.sc = sc;
        this.gh = gh;

        System.out.println("");
        System.out.println("------ Menu Principal ------");
        System.out.println("1. Gerenciar Pacientes");
        System.out.println("2. Gerenciar Médicos");
        System.out.println("3. Gerenciar Consultas");
        System.out.println("4. Sair");
         System.out.println("---------------------------");
        System.out.print("> Escolha uma opção: ");
        int opcao = sc.nextInt();
        switch (opcao) {
            case 1:
                new MenuPaciente(sc, gh);
                break;
            case 2:
                new MenuMedico(sc, gh);
                break;
            case 3:
                new MenuConsulta(sc, gh);
                break;
            case 4:
                System.out.println("Salvando alterações e saindo...");
                break;
            default:
                System.out.println("");
                System.out.println("Opção inválida!");
                Thread.sleep(1000);
                new Menu(sc, gh);

        }
    }
}

