package view;

import java.util.Scanner;
import service.GerenciadorHospitalar;
import util.Utilitario;

public class Menu {

    private final Scanner sc;
    private final GerenciadorHospitalar gh;
    private final MenuPaciente menuPaciente;
    private final MenuMedico menuMedico;
    private final MenuConsulta menuConsulta;
    private final MenuInternacao menuInternacao;
    private final MenuRelatorios menuRelatorios;

    public Menu(Scanner sc, GerenciadorHospitalar gh) throws InterruptedException {
        this.sc = sc;
        this.gh = gh;
        menuPaciente = new MenuPaciente(sc, gh);
        menuMedico = new MenuMedico(sc, gh);
        menuConsulta = new MenuConsulta(sc, gh);
        menuInternacao = new MenuInternacao(sc, gh);
        menuRelatorios = new MenuRelatorios(sc, gh);
    }

    public void exibirMenu() throws InterruptedException {
        boolean rodando = true;
        while (rodando) {
            System.out.println("""
                    \n------ Menu Principal ------
                    1. Gerenciar Pacientes
                    2. Gerenciar Médicos
                    3. Gerenciar Consultas
                    4. Gerenciar Internações
                    5. Relatórios
                    6. Sair
                    ---------------------------""");

            int op = Utilitario.lerInt(sc, "> Escolha uma opção: ");
            switch (op) {
                case 1 -> menuPaciente.exibirMenu();
                case 2 -> menuMedico.exibirMenu();
                case 3 -> menuConsulta.exibirMenu();
                case 4 -> menuInternacao.exibirMenu();
                case 5 -> menuRelatorios.exibirMenu();
                case 6 -> {
                    rodando = false;
                    System.out.println("Salvando alterações e saindo...");
                }
                default -> {
                    System.out.println("Opção inválida! Tente novamente.");
                    Thread.sleep(500);
                }
            }
        }
    }
}
