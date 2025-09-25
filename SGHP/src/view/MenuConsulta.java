package view;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

import model.Consulta;
import model.Medico;
import model.Paciente;
import service.GerenciadorHospitalar;
public class MenuConsulta {
    private Scanner sc;
    private GerenciadorHospitalar gh;
    
    public MenuConsulta(Scanner sc, GerenciadorHospitalar gh) throws InterruptedException {
        this.sc = sc;
        this.gh = gh;

        System.out.println("");
        System.out.println("------ Menu Consultas ------");
        System.out.println("1. Agendar Consulta");
        System.out.println("2. Listar Consultas");
        System.out.println("3. Concluir Consulta");
        System.out.println("4. Cancelar Consulta");
        System.out.println("5. Voltar ao Menu Principal");
        System.out.println("----------------------------");
        System.out.print("> Escolha uma opção: ");
        int opcao = sc.nextInt();

        switch (opcao) {
            case 1:
                agendarNovaConsulta();
                break;
            case 2:
                listarConsultas();
                System.out.println("Listando consultas...");
                break;
            case 3:
                realizarConsulta();
                System.out.println("Consulta concluída com sucesso!");
                break;
            case 4:
                cancelarConsulta();
                System.out.println("Consulta cancelada com sucesso!");
                break;
            case 5:
                System.out.println("Voltando ao menu principal...");
                new Menu(sc, gh);
                break;
            default:
                System.out.println("Opção inválida!");
                break;
            }
        
        }


// Código interno do menu
    //Código para agendar nova consulta
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

        Paciente paciente = gh.buscarPacientePorCPF(cpfPaciente);
        if (paciente == null) {
            System.out.println("Paciente não encontrado com o CPF: " + cpfPaciente);
            return;
        }
        Medico medico = gh.buscarMedicoPorCRM(crmMedico);
        if (medico == null) {
            System.out.println("Médico não encontrado com o CRM: " + crmMedico);
            return;
        }
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
        gh.agendarConsulta(consulta);
        
            
        throw new UnsupportedOperationException("Unimplemented method 'agendarNovaConsulta'");
    }

    private void realizarConsulta() {
    }
    private void listarConsultas() {
    }
    private void cancelarConsulta() {
    }
}