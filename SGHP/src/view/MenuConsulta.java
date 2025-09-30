package view;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

import model.Consulta;
import model.Medico;
import model.Paciente;
import model.enums.StatusConsulta;
import service.GerenciadorHospitalar;
import util.Utilitario;

public class MenuConsulta {
    private Scanner sc;
    private GerenciadorHospitalar gh;

    public MenuConsulta(Scanner sc, GerenciadorHospitalar gh) throws InterruptedException {
        this.sc = sc;
        this.gh = gh;
    }

    public void exibirMenu() throws InterruptedException {
        boolean rodando = true;
        while (rodando) {
            System.out.println("");
            System.out.println("------ Menu Consultas ------");
            System.out.println("1. Agendar Consulta");
            System.out.println("2. Listar Consultas");
            System.out.println("3. Detalhar Consulta");
            System.out.println("4. Concluir Consulta");
            System.out.println("5. Cancelar Consulta");
            System.out.println("6. Voltar ao Menu Principal");
            System.out.println("----------------------------");
            int op = util.Utilitario.lerInt(sc, "> Escolha uma opção: ");

            switch (op) {
                case 1:
                    agendarConsulta();
                    break;
                case 2:
                    listarConsultas();
                    break;
                case 3:
                    detalharConsulta();
                    break;
                case 4:
                    concluirConsulta();
                    break;
                case 5:
                    cancelarConsulta();
                    break;
                case 6:
                    rodando = false;
                    break;
                default:
                    System.out.println("Opção inválida!");
                    break;
            }
        }

    }

    // Código para Agendar Consulta
    private void agendarConsulta() {
        System.out.println("--- Agendar Consulta ---");
        String cpfPaciente = util.Utilitario.lerString(sc, "> Digite o CPF do paciente: ");
        Paciente paciente = gh.buscarPacientePorCPF(cpfPaciente);
        if (paciente == null) {
            System.out.println("Paciente não encontrado com o CPF: " + cpfPaciente);
            return;
        }
        String crmMedico = util.Utilitario.lerString(sc, "> Digite o CRM do paciente: ");
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
                dataHora = LocalDateTime.parse(dataHoraStr,
                        java.time.format.DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));
                dataValida = true;
            } catch (DateTimeParseException e) {
                System.out.println("Formato de data e hora inválido. Use DD-MM-YYYY HH:MM.");
            }
        }
        System.out.println("> Insira o local da consulta: ");
        String local = sc.nextLine();
        System.out.println("> Insira o motivo da consulta: ");
        String motivo = sc.nextLine();

        Consulta novaConsulta = new Consulta(paciente, medico, dataHora, local, motivo);
        gh.agendarConsulta(novaConsulta);
        paciente.adicionarConsulta(novaConsulta);

        System.out.println("Consulta agendada com sucesso para " + paciente.getNome() + " com o Dr. " + medico.getNome()
                + " em " + dataHora.format(java.time.format.DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")));
    }

    // Listar Consultas

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

    // Métodos do Menu

     private void concluirConsulta() {
        int id = Utilitario.lerInt(sc, "ID da consulta: ");
        Consulta c = gh.buscarConsultaPorId(id);
        if (c == null) {
            System.out.println("Consulta não encontrada.");
            return;
        }
        if (c.getStatus() != StatusConsulta.AGENDADA) {
            System.out.println("Transição inválida: status atual é " + c.getStatus());
            return;
        }

        sc.nextLine();
        System.out.print("Diagnóstico: ");
        String diag = sc.nextLine();
        System.out.print("Prescrições (separe por vírgula): ");
        String linha = sc.nextLine();
        List<String> prescr = java.util.Arrays.stream(linha.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .toList();

        c.realizarConsulta(diag, new java.util.ArrayList<>(prescr));
        System.out.println("Consulta " + c.getId() + " marcada como REALIZADA.");
    }

    private void cancelarConsulta() {
        int id = Utilitario.lerInt(sc, "ID da consulta: ");
        Consulta c = gh.buscarConsultaPorId(id);
        if (c == null) {
            System.out.println("Consulta não encontrada.");
            return;
        }
        if (c.getStatus() == StatusConsulta.AGENDADA) {
            c.setStatus(StatusConsulta.CANCELADA);
            System.out.println("Consulta " + c.getId() + " CANCELADA.");
        } else {
            System.out.println("Transição inválida: status atual é " + c.getStatus());
        }
    }

    private void detalharConsulta() {
        int id = Utilitario.lerInt(sc, "ID da consulta: ");
        Consulta c = gh.buscarConsultaPorId(id);
        if (c == null) {
            System.out.println("Consulta não encontrada.");
            return;
        }
        System.out.println(c);
    }
}