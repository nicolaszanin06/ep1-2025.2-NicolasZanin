package view;

import java.util.Scanner;
import service.GerenciadorHospitalar;

public class MenuMedico {
    private Scanner sc;
    private GerenciadorHospitalar gh;

    public MenuMedico(Scanner sc, GerenciadorHospitalar gh) throws InterruptedException {
        this.sc = sc;
        this.gh = gh;
        }

    public void exibirMenu() throws InterruptedException {
       boolean rodando = true;
        while (rodando) {
            System.out.println("");
            System.out.println("------ Menu Médicos ------");
            System.out.println("1. Cadastrar Médico");
            System.out.println("2. Listar Médicos");
            System.out.println("3. Remover Médico");
            System.out.println("4. Voltar ao Menu Principal");
            System.out.println("--------------------------");
            int op = util.Utilitario.lerInt(sc, "> Escolha uma opção: ");

            switch (op) {
                case 1:
                    break;
                case 2:
                    System.out.println("Listando médicos...");
                    break;
                case 3:
                    break;
                case 4:
                    rodando = false;
                    break;
                default:
                    System.out.println("Opção inválida!");
                    break;
            }
        }
    }

}
