package view;

import java.util.Scanner;

public class Menu {
    Scanner sc = new Scanner(System.in);

    public Menu() throws InterruptedException {
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
                new MenuPaciente();
                break;
            case 2:
                new MenuMedico();
                break;
            case 3:
                new MenuConsulta();
                break;
            case 4:
                System.out.println("Salvando alterações e saindo...");
                break;
            default:
                System.out.println("");
                System.out.println("Opção inválida!");
                Thread.sleep(1000);
                new Menu();

        }
    }
}

