package view;

import java.util.Scanner;
import service.GerenciadorHospitalar;
public class MenuConsulta {
    Scanner sc = new Scanner(System.in);
    
    
    public MenuConsulta() {
        System.out.println("------ Menu Consultas ------");
        System.out.println("1. Agendar Consulta");
        System.out.println("2. Listar Consultas");
        System.out.println("3. Cancelar Consulta");
        System.out.println("4. Voltar ao Menu Principal");
        System.out.print("Escolha uma opção: ");
        int opcao = sc.nextInt();
        switch (opcao) {
            case 1:
            //implementar agendamento de consulta
                System.out.println("Agendando consulta...");
                break;
            case 2:
                new Menu();
                break;
            default:
                System.out.println("Opção inválida!");
                break;
            }
        
        }
    }

