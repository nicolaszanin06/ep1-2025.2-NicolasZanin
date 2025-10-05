package view;

import java.util.Scanner;
import service.GerenciadorHospitalar;

public class MenuMedico {
    private Scanner sc;
    private GerenciadorHospitalar gh;

    public MenuMedico(Scanner sc, GerenciadorHospitalar gh) throws InterruptedException {
        this.sc = sc;
        this.gh = gh;
    }

    public void exibirMenu() throws InterruptedException {
        boolean rodando = true;
        while (rodando) {
            System.out.println("");
            System.out.println("------ Menu Médicos ------");
            System.out.println("1. Cadastrar Médico");
            System.out.println("2. Listar Médicos");
            System.out.println("3. Remover Médico");
            System.out.println("4. Voltar ao Menu Principal");
            System.out.println("--------------------------");
            int op = util.Utilitario.lerInt(sc, "> Escolha uma opção: ");

            switch (op) {
                case 1:
                    cadastrarMedico();
                    break;
                case 2:
                    listarMedicos();
                    break;
                case 3:
                    removerMedico();
                    break;
                case 4:
                    rodando = false;
                    break;
                default:
                    System.out.println("Opção inválida!");
                    break;
            }
        }
    }

    private void cadastrarMedico() {
        System.out.println("\n--- Cadastrar Médico ---");
        String nome = util.Utilitario.lerString(sc, "Nome: ");
        int idade = util.Utilitario.lerInt(sc, "Idade: ");
        String cpf = util.Utilitario.lerString(sc, "CPF: ");
        String crm = util.Utilitario.lerString(sc, "CRM: ");
        String esp = util.Utilitario.lerString(sc, "Especialidade: ");
        double custo = util.Utilitario.lerDouble(sc, "Custo da Consulta: ");
        gh.cadastrarMedicoValidador(new model.Medico(nome, idade, cpf, crm, esp, custo));
        System.out.println("Médico cadastrado.");
    }

    private void listarMedicos() {
        System.out.println("\n--- Lista de Médicos ---");
        for (model.Medico m : gh.getMedicos()) {
            System.out.println(m);
        }
    }

    private void removerMedico() {
        System.out.println("\n--- Remover Médico ---");
        String crm = util.Utilitario.lerString(sc, "> Digite o CRM do médico a ser removido: ");
        boolean removido = gh.removerMedico(crm);
        if (removido) {
            System.out.println("Médico removido com sucesso.");
        } else {
            System.out.println("Médico com CRM " + crm + " não encontrado.");
        }
    }
}
