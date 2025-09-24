package view;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

import model.Consulta;
import model.Medico;
import model.Paciente;
import service.GerenciadorHospitalar;
public class MenuConsulta {
    Scanner sc = new Scanner(System.in);
    private GerenciadorHospitalar gr;
    
    public MenuConsulta() throws InterruptedException {
        System.out.println("");
        System.out.println("------ Menu Consultas ------");
        System.out.println("1. Agendar Consulta");
        System.out.println("2. Listar Consultas");
        System.out.println("3. Cancelar Consulta");
        System.out.println("4. Voltar ao Menu Principal");
        System.out.println("----------------------------");
        System.out.print("> Escolha uma opção: ");
        int opcao = sc.nextInt();

        switch (opcao) {
            case 1:
                agendarNovaConsulta();
                break;
            case 2:
            //implementar listagem de consultas
                System.out.println("Listando consultas...");
                break;
            case 3:
            //implementar cancelamento de consulta
                System.out.println("Consulta cancelada com sucesso!");
                break;
            case 4:
            //implementar retorno ao menu principal
                System.out.println("Voltando ao menu principal...");
                new Menu();
                break;
            default:
                System.out.println("Opção inválida!");
                break;
            }
        
        }


// Código interno do menu

    private void agendarNovaConsulta() {
        sc.nextLine();
        System.out.println("");
        System.out.println("--- Agendar Nova Consulta ---");
        System.out.println(">Insira o CPF do paciente: ");
            String cpfPaciente = sc.nextLine();
        System.out.println(">Insira o CRM do médico: ");
            String crmMedico = sc.nextLine();
        System.out.println(">Insira a data e hora da consulta (dd/MM/yyyy HH:mm): ");
            String dataHoraStr = sc.nextLine();
        System.out.println(">Insira o local da consulta: ");
            String local = sc.nextLine();
        System.out.println(">Insira o motivo da consulta: ");
            String motivo = sc.nextLine();
        sc.close();
        // Verificar se o paciente existe

        Paciente paciente = gr.buscarPacientePorCPF(cpfPaciente);
        if (paciente == null) {
            System.out.println("Paciente não encontrado com o CPF: " + cpfPaciente);
            return;
        }
        Medico medico = gr.buscarMedicoPorCRM(crmMedico);
        if (medico == null) {
            System.out.println("Médico não encontrado com o CRM: " + crmMedico);
            return;
        }
        // Coleta de dados
        LocalDateTime dataHora = null;
        boolean dataValida = false;
        while (!dataValida) {
         try {
                dataHora = LocalDateTime.parse(dataHoraStr, java.time.format.DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));
                dataValida = true;
            } catch (DateTimeParseException e) {
                System.out.println("Formato de data e hora inválido. Use DD-MM-YYYY HH:MM.");
            }
        }

        Consulta consulta = new Consulta(paciente, medico, dataHora, local, motivo);
        gr.agendarConsulta(consulta);
        
            
        throw new UnsupportedOperationException("Unimplemented method 'agendarNovaConsulta'");
    }
    }

