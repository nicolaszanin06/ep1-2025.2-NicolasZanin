package view;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import model.Paciente;
import model.PacienteEspecial;
import model.enums.PlanoDeSaude;
import service.GerenciadorHospitalar;

public class MenuPaciente {
    private Scanner sc;
    private GerenciadorHospitalar gh;

    public MenuPaciente(Scanner sc, GerenciadorHospitalar gh) throws InterruptedException {
        this.sc = sc;
        this.gh = gh;
    }
    
    public void exibirMenu() throws InterruptedException {    
        int opcao;
        do {
            System.out.println("");
            System.out.println("------ Menu Pacientes ------");
            System.out.println("1. Cadastrar Paciente");
            System.out.println("2. Listar Pacientes");
            System.out.println("3. Remover Paciente");
            System.out.println("4. Voltar ao Menu Principal");
            System.out.println("----------------------------");
            System.out.print("> Escolha uma opção: ");

            opcao = lerOpcaoMenu();
            switch (opcao) {
                case 1:
                    System.out.println();
                    System.out.println("------ Cadastrar Paciente ------");
                    System.out.println("1. Paciente Comum");
                    System.out.println("2. Paciente Convênio");
                    System.out.println("3. Voltar");
                    System.out.println("-------------------------------");
                    System.out.print("> Escolha uma opção: ");
                    int tipoPaciente = sc.nextInt();
                    switch (tipoPaciente) {
                        case 1:
                            cadastrarPacienteComum();
                            break;
                        case 2:
                            cadastrarPacienteEspecial();
                            break;
                        case 3:
                            new MenuPaciente(sc, gh);
                            break;
                        default:
                            System.out.println("Opção inválida!");
                            break;
                    }
                    break;
                case 2:
                    listarPacientes();
                    break;
                case 3:
                    removerPaciente();
                    break;
                case 4:
                    System.out.println("Voltando ao menu principal...");
                    new Menu(sc, gh);
                    break;
                default:
                    System.out.println("Opção inválida!");
                    break;
            }
        } while (opcao != 4);
    }

    private int lerOpcaoMenu() {
        while (true) {
            try {
                int opcao = sc.nextInt();
                sc.nextLine();
                return opcao;
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Por favor, digite um número.");
                sc.nextLine();
                System.out.print("> Escolha uma opção: ");
            }
        }
    }

    private void cadastrarPacienteComum() {
        System.out.println("\n--- Cadastrar Paciente Comum ---");
        sc.nextLine();
        System.out.print("> Nome: ");
        String nome = sc.nextLine();
        System.out.print("> Idade: ");
        int idade = sc.nextInt();
        System.out.print("> CPF: ");
        sc.nextLine();
        String cpf = sc.nextLine();
        Paciente novoPaciente = new Paciente(nome, idade, cpf);
        gh.cadastrarPaciente(novoPaciente);
    }

    private void cadastrarPacienteEspecial() {
        System.out.println("\n--- Cadastrar Paciente Convênio ---");
        sc.nextLine();
        System.out.print("> Nome: ");
        String nome = sc.nextLine();
        System.out.print("> Idade: ");
        int idade = sc.nextInt();
        System.out.print("> CPF: ");
        sc.nextLine();
        String cpf = sc.nextLine();

        System.out.println("--- Planos de Saúdes válidos ---");
        for (PlanoDeSaude plano : PlanoDeSaude.values()) {
            System.out.println(
                    plano.getCodigo() + ". " + plano.getNome() + " (Desconto: " + (plano.getDesconto() * 100) + "%)");
        }
        System.out.print("> Escolha o código do plano de saúde: ");
        int codigoPlano = sc.nextInt();
        PlanoDeSaude planoEscolhido = PlanoDeSaude.getPorCodigo(codigoPlano);

        if (planoEscolhido == null) {
            System.out.println("Código de plano inválido. Cadastro cancelado.");
        }
        PacienteEspecial novoPacienteEspecial = new PacienteEspecial(nome, idade, cpf, planoEscolhido);
        gh.cadastrarPaciente(novoPacienteEspecial);

    }

    private void listarPacientes() {
        System.out.println("--- Lista de Pacientes ---");
        List<model.Paciente> pacientes = gh.getPacientes();
        if (pacientes.isEmpty()) {
            System.out.println("Nenhum paciente cadastrado.");
        } else {
            for (model.Paciente p : pacientes) {
                System.out.println(p);
            }
        }
    }

    private void removerPaciente() {
        System.out.println("\n--- Remover Paciente ---");
        System.out.print("> Informe o CPF do paciente a ser removido: ");
        String cpf = sc.nextLine();
        boolean removido = gh.removerPaciente(cpf);
        if (removido) {
        } else {
            System.out.println("Paciente não encontrado.");
        }
    }
}
