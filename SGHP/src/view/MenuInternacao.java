package view;

import java.time.LocalDateTime;
import java.util.Scanner;
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
            System.out.println("""
                    
                    ------ Menu de Internações ------
                    1. Internar Paciente
                    2. Listar Internações
                    3. Dar Alta
                    4. Cancelar Internação
                    5. Voltar
                    ---------------------------------""");

            int op = Utilitario.lerInt(sc, "> Escolha uma opção: ");
            switch (op) {
                case 1 -> cadastrarInternacao();
                case 2 -> listarInternacoes();
                case 3 -> darAlta();
                case 4 -> cancelarInternacao();
                case 5 -> rodando = false;
                default -> System.out.println("Opção inválida! Tente novamente.");
            }
        }
    }

    private void cadastrarInternacao() {
        System.out.println("\n--- Cadastrar Internação ---");
        Paciente p = buscarPaciente();
        if (p == null) return;
        Medico m = buscarMedico();
        if (m == null) return;

        LocalDateTime entrada = Utilitario.lerDataHora(sc, "> Data/hora de entrada (dd-MM-yyyy HH:mm): ");
        int quarto = Utilitario.lerInt(sc, "> Número do quarto: ");
        double custo = Utilitario.lerDouble(sc, "> Custo diário: ");

        if (gh.criarInternacao(p, m, entrada, quarto, custo))
            System.out.println("Internação registrada.");
        else
            System.out.println("Quarto " + quarto + " já está ocupado.");
    }

    private void listarInternacoes() {
        System.out.println("\n--- Lista de Internações ---");
        var lista = gh.getInternacoes();
        if (lista.isEmpty()) {
            System.out.println("Nenhuma internação registrada.");
            return;
        }
        lista.forEach(i -> System.out.printf(
                "ID = %d, Paciente = %s, Médico(a) = %s, Quarto = %d,Ativa = %s, Entrada = %s, Alta = %s%n",
                i.getId(),
                i.getPaciente().getNome(),
                i.getMedico().getNome(),
                i.getNumeroQuarto(),
                i.estaAtiva() ? "Sim" : "Não",
                i.getDataInternacao(),
                i.getDataAlta() != null ? i.getDataAlta() : "-"
        ));
    }

    private void darAlta() {
        System.out.println("\n--- Dar Alta ---");
        int id = Utilitario.lerInt(sc, "> ID da internação: ");
        LocalDateTime alta = Utilitario.lerDataHora(sc, "> Data/hora da alta (dd-MM-yyyy HH:mm): ");
        if (gh.darAlta(id, alta))
            System.out.println("Alta registrada.");
        else
            System.out.println("Internação não encontrada ou já finalizada/cancelada.");
    }

    private void cancelarInternacao() {
        System.out.println("\n--- Cancelar Internação ---");
        int id = Utilitario.lerInt(sc, "> ID da internação: ");
        if (gh.cancelarInternacao(id))
            System.out.println("Internação cancelada.");
        else
            System.out.println("Não foi possível cancelar a internação (ID inválido ou já finalizada/cancelada).");
    }

    // Helpers
    private Paciente buscarPaciente() {
        String cpf = Utilitario.lerString(sc, "> CPF do paciente: ");
        Paciente p = gh.buscarPacientePorCPF(cpf);
        if (p == null) System.out.println("Paciente não encontrado.");
        return p;
    }

    private Medico buscarMedico() {
        String crm = Utilitario.lerString(sc, "> CRM do médico: ");
        Medico m = gh.buscarMedicoPorCRM(crm);
        if (m == null) System.out.println("Médico não encontrado.");
        return m;
    }
}
