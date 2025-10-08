package view;

import java.util.List;
import java.util.Scanner;

import model.Paciente;
import model.PacienteEspecial;
import model.enums.PlanoDeSaude;
import service.GerenciadorHospitalar;
import util.Utilitario;

public class MenuPaciente {

    private final Scanner sc;
    private final GerenciadorHospitalar gh;

    public MenuPaciente(Scanner sc, GerenciadorHospitalar gh) {
        this.sc = sc;
        this.gh = gh;
    }

    public void exibirMenu() {
        boolean rodando = true;
        while (rodando) {
            System.out.println("""
                    
                    ------ Menu Pacientes ------
                    1. Cadastrar Paciente
                    2. Listar Pacientes
                    3. Remover Paciente
                    4. Voltar
                    ----------------------------""");

            int op = Utilitario.lerInt(sc, "> Escolha uma opção: ");
            switch (op) {
                case 1 -> submenuCadastro();
                case 2 -> listarPacientes();
                case 3 -> removerPaciente();
                case 4 -> rodando = false;
                default -> System.out.println("Opção inválida!");
            }
        }
    }

    private void submenuCadastro() {
        System.out.println("""
            
                ------ Cadastrar Paciente ------
                1. Paciente Comum
                2. Paciente Convênio
                3. Voltar
                -------------------------------""");
        int tipo = Utilitario.lerInt(sc, "> Escolha uma opção: ");
        switch (tipo) {
            case 1 -> cadastrarPacienteComum();
            case 2 -> cadastrarPacienteEspecial();
            case 3 -> {} // apenas volta
            default -> System.out.println("Opção inválida!");
        }
    }

    private void cadastrarPacienteComum() {
        System.out.println("\n--- Cadastrar Paciente Comum ---");
        String nome = Utilitario.lerString(sc, "> Nome: ");
        int idade = Utilitario.lerInt(sc, "> Idade: ");
        String cpf = Utilitario.lerString(sc, "> CPF: ");

        if (!Utilitario.cpfValidador(cpf)) {
            System.out.println("CPF inválido. Verifique o número de dígitos.");
            return;
        }
        gh.cadastrarPacienteValidador(new Paciente(nome, idade, cpf));
    }

    private void cadastrarPacienteEspecial() {
        System.out.println("\n--- Cadastrar Paciente Convênio ---");
        String nome = Utilitario.lerString(sc, "> Nome: ");
        int idade = Utilitario.lerInt(sc, "> Idade: ");
        String cpf = Utilitario.lerString(sc, "> CPF: ");

        if (!Utilitario.cpfValidador(cpf)) {
            System.out.println("CPF inválido. Verifique o número de dígitos.");
            return;
        }

        System.out.println("--- Planos de Saúde ---");
        for (PlanoDeSaude plano : PlanoDeSaude.values()) {
            System.out.printf("%d. %s (Desconto: %.0f%%)%n",
                    plano.getCodigo(), plano.getNome(), plano.getDesconto() * 100);
        }

        int codigo = Utilitario.lerInt(sc, "> Código do plano: ");
        try {
            PlanoDeSaude plano = PlanoDeSaude.getPorCodigo(codigo);
            gh.cadastrarPacienteValidador(new PacienteEspecial(nome, idade, cpf, plano));
        } catch (IllegalArgumentException e) {
            System.out.println("Código de plano inválido. Cadastro cancelado.");
        }
    }

    private void listarPacientes() {
        System.out.println("\n--- Lista de Pacientes ---");
        List<Paciente> lista = gh.getPacientes();
        if (lista.isEmpty()) System.out.println("Nenhum paciente cadastrado.");
        else lista.forEach(System.out::println);
    }

    private void removerPaciente() {
        System.out.println("\n--- Remover Paciente ---");
        String cpf = Utilitario.lerString(sc, "> CPF do paciente: ");
        if (!gh.removerPaciente(cpf)) System.out.println("Paciente não encontrado.");
    }
}
