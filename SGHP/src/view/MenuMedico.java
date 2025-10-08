package view;

import java.util.Scanner;
import model.Medico;
import service.GerenciadorHospitalar;
import util.Utilitario;

public class MenuMedico {

    private final Scanner sc;
    private final GerenciadorHospitalar gh;

    public MenuMedico(Scanner sc, GerenciadorHospitalar gh) {
        this.sc = sc;
        this.gh = gh;
    }

    public void exibirMenu() {
        boolean rodando = true;
        while (rodando) {
            System.out.println("""
                    
                    ------ Menu Médicos ------
                    1. Cadastrar Médico
                    2. Listar Médicos
                    3. Remover Médico
                    4. Voltar
                    --------------------------""");

            int op = Utilitario.lerInt(sc, "> Escolha uma opção: ");
            switch (op) {
                case 1 -> cadastrarMedico();
                case 2 -> listarMedicos();
                case 3 -> removerMedico();
                case 4 -> rodando = false;
                default -> System.out.println("Opção inválida!");
            }
        }
    }
    
    private void cadastrarMedico() {
        System.out.println("\n--- Cadastrar Médico ---");
        String nome = Utilitario.lerString(sc, "> Nome: ");
        int idade = Utilitario.lerInt(sc, "> Idade: ");
        String cpf = Utilitario.lerString(sc, "> CPF: ");
        String crm = Utilitario.lerString(sc, "> CRM: ");
        String esp = Utilitario.lerString(sc, "> Especialidade: ");
        double custo = Utilitario.lerDouble(sc, "> Custo da Consulta: ");

        gh.cadastrarMedicoValidador(new Medico(nome, idade, cpf, crm, esp, custo));
        System.out.println("Médico cadastrado com sucesso.");
    }

    private void listarMedicos() {
        System.out.println("\n--- Lista de Médicos ---");
        var medicos = gh.getMedicos();
        if (medicos.isEmpty()) {
            System.out.println("Nenhum médico cadastrado.");
            return;
        }
        medicos.forEach(System.out::println);
    }

    private void removerMedico() {
        System.out.println("\n--- Remover Médico ---");
        String crm = Utilitario.lerString(sc, "> CRM do médico: ");
        if (gh.removerMedico(crm))
            System.out.println("Médico removido com sucesso.");
        else
            System.out.println("Médico com CRM " + crm + " não encontrado.");
    }
}
