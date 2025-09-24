package view;

import java.util.Scanner;

import service.GerenciadorHospitalar;

public class MenuPaciente {
    private Scanner sc;
    private GerenciadorHospitalar gh;

    public MenuPaciente(Scanner sc, GerenciadorHospitalar gh) {
        this.sc = sc;
        this.gh = gh;

        System.out.println("");
        System.out.println("------ Menu Pacientes ------");
        System.out.println("1. Cadastrar Paciente");
        System.out.println("2. Listar Pacientes");
        System.out.println("3. Voltar ao Menu Principal");
        System.out.println("----------------------------");
    }
}
