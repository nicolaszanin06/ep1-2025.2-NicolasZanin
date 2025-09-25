package view;

import java.util.Scanner;

import service.GerenciadorHospitalar;

public class MenuPaciente {
    private Scanner sc;
    private GerenciadorHospitalar gh;
    
    public MenuPaciente(Scanner sc, GerenciadorHospitalar gh) throws InterruptedException {
        this.sc = sc;
        this.gh = gh;
        
        System.out.println("");
        System.out.println("------ Menu Pacientes ------");
        System.out.println("1. Cadastrar Paciente");
        System.out.println("2. Listar Pacientes");
        System.out.println("3. Remover Paciente");
        System.out.println("4. Voltar ao Menu Principal");
        System.out.println("----------------------------");
        System.out.print("> Escolha uma opção: ");
        int opcao = sc.nextInt();

        switch (opcao) {
            case 1:
                gh.cadastrarMedico(null);
                break;
            case 2:
                System.out.println("Listando pacientes...");
                break;
            case 3:
                System.out.println("Removendo paciente...");
                break;
            case 4:
                System.out.println("Voltando ao menu principal...");
                new Menu(sc, gh);
                break;
            default:
                System.out.println("Opção inválida!");
                break;
        }
    }
}
