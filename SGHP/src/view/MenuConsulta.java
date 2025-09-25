package view;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;
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
                agendarConsulta();
                break;
            case 2:
                listarConsultas();
                break;
            case 3:
                concluirConsulta();
                break;
            case 4:
                cancelarConsulta();
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
        //Código para Agendar Consulta
        private void agendarConsulta() {
            System.out.println("--- Agendar Consulta ---");
            System.out.print("> Insira o CPF do paciente: ");
            sc.nextLine();
            String cpfPaciente = sc.nextLine();
            Paciente paciente = gh.buscarPacientePorCPF(cpfPaciente);
            if (paciente == null) {
                System.out.println("Paciente não encontrado com o CPF: " + cpfPaciente);
                return;
            }
            System.out.println("> Insira o CRM do médico: ");
            String crmMedico = sc.nextLine();
            Medico medico = gh.buscarMedicoPorCRM(crmMedico);
            if (medico == null) {
                System.out.println("Médico não encontrado com o CRM: " + crmMedico);
                return;
            }

            LocalDateTime dataHora = null;
            boolean dataValida = false;
            while (!dataValida) {
                System.out.println("> Insira a data e hora da consulta (dd-MM-yyyy HH:mm): ");
                String dataHoraStr = sc.nextLine();
             try {
                    dataHora = LocalDateTime.parse(dataHoraStr, java.time.format.DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));
                    dataValida = true;
                } catch (DateTimeParseException e) {
                    System.out.println("Formato de data e hora inválido. Use DD-MM-YYYY HH:MM.");
                }
            }
            System.out.println("> Insira o local da consulta: ");
            String local = sc.nextLine();
            System.out.println("> Insira o motivo da consulta: ");
            String motivo = sc.nextLine();
            Consulta novConsulta = new Consulta(paciente, medico, dataHora, local, motivo);
            gh.agendarConsulta(novConsulta);
            System.out.println("Consulta agendada com sucesso para " + paciente.getNome() + " com o Dr. " + medico.getNome() + " em " + dataHora.format(java.time.format.DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")));
             }

        //Listar Consultas

        private void listarConsultas() {
            System.out.println("--- Listar Consultas ---");
            List<Consulta> consultas = gh.getConsultas();
            if (consultas.isEmpty()) {
                System.out.println("Nenhuma consulta agendada no sistema.");
                return;
            }
            for (Consulta c : consultas) {
                System.out.println(c.toString());
            }
        }

        //Concluir Consulta
        
        private void concluirConsulta() {
            System.out.println("--- Concluir Consulta ---");
            System.out.print("> Insira o ID da consulta a ser concluída: ");
            int idConsulta = sc.nextInt();
            sc.nextLine(); 
            Consulta consulta = gh.getConsultaByIndex(idConsulta);
            if (consulta == null) {
                System.out.println("Consulta não encontrada com o ID: " + idConsulta);
                return;
            }
            if (consulta.getStatus() != model.enums.StatusConsulta.AGENDADA) {
                System.out.println("A consulta deve estar agendada para ser concluída.");
                return;
            }
            System.out.print("> Insira o diagnóstico: ");
            String diagnostico = sc.nextLine();
            System.out.print("> Insira as prescrições (separadas por vírgula): ");
            String prescricoesStr = sc.nextLine();
            List<String> prescricoes = List.of(prescricoesStr.split(","));
            consulta.realizarConsulta(diagnostico, prescricoes);
            System.out.println("Consulta concluída com sucesso!");

        }

        private void cancelarConsulta() {
            System.out.println("--- Cancelar Consulta ---");
            System.out.print("> Insira o ID da consulta a ser cancelada: ");
            int idConsulta = sc.nextInt();
            sc.nextLine(); 
            Consulta consulta = gh.getConsultaByIndex(idConsulta);
            if (consulta == null) {
                System.out.println("Consulta não encontrada com o ID: " + idConsulta);
                return;
            }
            if (consulta.getStatus() != model.enums.StatusConsulta.AGENDADA) {
                System.out.println("A consulta deve estar agendada para ser cancelada.");
                return;
            }
            consulta.cancelarConsulta();
        System.out.println("Consulta cancelada com sucesso!");
    }
}