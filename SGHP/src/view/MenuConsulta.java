package view;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import model.Consulta;
import model.Medico;
import model.Paciente;
import model.enums.StatusConsulta;
import service.GerenciadorHospitalar;
import util.Utilitario;

public class MenuConsulta {

    private final Scanner sc;
    private final GerenciadorHospitalar gh;

    public MenuConsulta(Scanner sc, GerenciadorHospitalar gh) {
        this.sc = sc;
        this.gh = gh;
    }

    public void exibirMenu() {
        boolean rodando = true;
        while (rodando) {
            System.out.println("""                   
                    ------ Menu Consultas ------
                    1. Agendar Consulta
                    2. Listar Consultas
                    3. Detalhar Consulta
                    4. Concluir Consulta
                    5. Cancelar Consulta
                    6. Voltar
                    ----------------------------""");

            int op = Utilitario.lerInt(sc, "> Escolha uma opção: ");
            switch (op) {
                case 1 -> agendarConsulta();
                case 2 -> listarConsultas();
                case 3 -> detalharConsulta();
                case 4 -> concluirConsulta();
                case 5 -> cancelarConsulta();
                case 6 -> rodando = false;
                default -> System.out.println("Opção inválida!");
            }
        }
    }

    // 1) Agendar
    private void agendarConsulta() {
        System.out.println("--- Agendar Consulta ---");

        String cpfPaciente = Utilitario.lerString(sc, "> CPF do paciente: ");
        Paciente paciente = gh.buscarPacientePorCPF(cpfPaciente);
        if (paciente == null) {
            System.out.println("Paciente não encontrado: " + cpfPaciente);
            return;
        }

        String crmMedico = Utilitario.lerString(sc, "> CRM do médico: ");
        Medico medico = gh.buscarMedicoPorCRM(crmMedico);
        if (medico == null) {
            System.out.println("Médico não encontrado: " + crmMedico);
            return;
        }

        LocalDateTime dataHora;
        while (true) {
            dataHora = Utilitario.lerDataHora(sc, "> Data e hora (dd-MM-yyyy HH:mm): ");
            if (dataHora.isBefore(LocalDateTime.now())) {
                System.out.println("A data/hora não pode ser no passado.");
            } else {
                break;
            }
        }

        String local = Utilitario.lerString(sc, "> Local da consulta: ");
        String motivo = Utilitario.lerString(sc, "> Motivo da consulta: ");

        Consulta nova = new Consulta(paciente, medico, dataHora, local, motivo);
        gh.agendarConsulta(nova);
        paciente.adicionarConsulta(nova);
        System.out.println("Consulta agendada para " + paciente.getNome() +
                " com Dr(a). " + medico.getNome() +
                " em " + dataHora.format(java.time.format.DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")));
    }

    // 2) Listar
    private void listarConsultas() {
        System.out.println("--- Listar Consultas ---");
        List<Consulta> consultas = gh.getConsultas();
        if (consultas.isEmpty()) {
            System.out.println("Nenhuma consulta no sistema.");
            return;
        }
        consultas.forEach(c -> System.out.println(c.toString()));
    }

    // 3) Detalhar
    private void detalharConsulta() {
        int id = Utilitario.lerInt(sc, "ID da consulta: ");
        Consulta c = gh.buscarConsultaPorId(id);
        if (c == null) {
            System.out.println("Consulta não encontrada.");
            return;
        }
        System.out.println(c);
    }

    // 4) Concluir
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

        String diag = Utilitario.lerString(sc, "Diagnóstico: ");
        String linha = Utilitario.lerString(sc, "Prescrições (separe por vírgula): ");
        List<String> prescr = Arrays.stream(linha.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .toList();

        c.realizarConsulta(diag, new java.util.ArrayList<>(prescr));
        System.out.println("Consulta " + c.getId() + " marcada como REALIZADA.");
    }

    // 5) Cancelar
    private void cancelarConsulta() {
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
        c.setStatus(StatusConsulta.CANCELADA);
        System.out.println("Consulta " + c.getId() + " cancelada.");
    }
}
