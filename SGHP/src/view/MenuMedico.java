package view;

import java.util.Scanner;
import service.GerenciadorHospitalar;

public class MenuMedico {
    private Scanner sc;
    private GerenciadorHospitalar gh;

    public MenuMedico(Scanner sc, GerenciadorHospitalar gh) throws InterruptedException {
        this.sc = sc;
        this.gh = gh;

        System.out.println("");
        System.out.println("------ Menu Médicos ------");
        System.out.println("1. Cadastrar Médico");
        System.out.println("2. Listar Médicos");
        System.out.println("3. Remover Médico");
        System.out.println("4. Voltar ao Menu Principal");
        System.out.println("--------------------------");
        System.out.print("> Escolha uma opção: ");
        int opcao = sc.nextInt();

        switch (opcao) {
            case 1:
                break;
            case 2:
                System.out.println("Listando médicos...");
                break;
            case 3:
                break;
            case 4:
                new Menu(sc, gh);
                break;
            default:
                System.out.println("Opção inválida!");
                break;
        }
    }

}
