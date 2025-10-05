package view;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

import model.Internacao;
import model.Medico;
import model.Paciente;
import service.GerenciadorHospitalar;
import util.Utilitario;

public class MenuInternacao {
    private final Scanner sc;
    private final GerenciadorHospitalar gh;

    public MenuInternacao(Scanner sc, GerenciadorHospitalar gh) {
        this.sc = sc;
        this.gh = gh;
    }

    public void exibirMenu() {
        boolean rodando = true;
        while (rodando) {
            System.out.println("\n--- Menu de Internação ---");
            System.out.println("1. Internar Paciente");
            System.out.println("2. Listar Pacientes Internados");
            System.out.println("3. Alta de Paciente");
            System.out.println("4. Cancelar Internação");
            System.out.println("5. Voltar ao Menu Principal");
            System.out.println("--------------------------");
            int op = Utilitario.lerInt(sc, "> Escolha uma opção: ");

            switch (op) {
                case 1:
                    cadastrarInternacao();
                    break;
                case 2:
                    listarInternacoes();
                    break;
                case 3:
                    darAlta();
                    break;
                case 4:
                    cancelarInternacao();
                    break;
                case 5:
                    rodando = false;
                    break;
                default:
                    System.out.println("Opção inválida! Tente novamente.");
            }
        }
    }

    private void cadastrarInternacao() {
        System.out.println("--- Cadastrar Internação ---");
        String cpf = Utilitario.lerString(sc, "> CPF do paciente: ");
        Paciente p = gh.buscarPacientePorCPF(cpf);
        if (p == null) {
            System.out.println("Paciente não encontrado.");
            return;
        }

        String crm = Utilitario.lerString(sc, "> CRM do médico: ");
        Medico m = gh.buscarMedicoPorCRM(crm);
        if (m == null) {
            System.out.println("Médico não encontrado.");
            return;
        }

        LocalDateTime entrada = Utilitario.lerDataHora(sc, "> Data/hora de entrada (dd-MM-yyyy HH:mm): ");
        int quarto = Utilitario.lerInt(sc, "> Número do quarto: ");
        double custo = Utilitario.lerDouble(sc, "> Custo diário: ");

        if (gh.criarInternacao(p, m, entrada, quarto, custo)) {
            System.out.println("Internação registrada.");
        } else {
            System.out.println("Não foi possível registrar a internação (quarto ocupado?).");
        }
    }

    private void listarInternacoes() {
        System.out.println("--- Listar Internações ---");
        List<Internacao> lista = gh.getInternacoes();
        if (lista.isEmpty()) {
            System.out.println("Nenhuma internação registrada.");
            return;
        }
        for (Internacao i : lista) {
            System.out.println("ID = " + i.getId() +
                    " | Paciente = " + i.getPaciente().getNome() +
                    " | Médico = " + i.getMedico().getNome() +
                    " | Quarto = " + i.getNumeroQuarto() +
                    " | Ativa = " + i.estaAtiva() +
                    " | Entrada = " + i.getDataInternacao() +
                    " | Alta = " + i.getDataAlta());
        }
    }

    private void darAlta() {
        System.out.println("--- Dar Alta ---");
        int id = Utilitario.lerInt(sc, "ID da internação: ");
        sc.nextLine();
        LocalDateTime alta = Utilitario.lerDataHora(sc, "> Data/hora da alta (dd-MM-yyyy HH:mm): ");
        if (gh.darAlta(id, alta)) {
            System.out.println("Alta registrada.");
        } else {
            System.out.println("Não foi possível registrar a alta (ID inválido ou já finalizada/cancelada).");
        }
    }

    private void cancelarInternacao() {
        System.out.println("--- Cancelar Internação ---");
        int id = Utilitario.lerInt(sc, "ID da internação: ");
        if (gh.cancelarInternacao(id)) {
            System.out.println("Internação cancelada.");
        } else {
            System.out.println("Não foi possível cancelar a internação (ID inválido ou já finalizada/cancelada).");
        }
    }
}
