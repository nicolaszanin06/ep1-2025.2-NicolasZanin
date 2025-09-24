package view;

import java.util.Scanner;
import service.GerenciadorHospitalar;

public class MenuMedico {
    private Scanner sc;
    private GerenciadorHospitalar gh;

    public MenuMedico(Scanner sc, GerenciadorHospitalar gh) {
        this.sc = sc;
        this.gh = gh;

        System.out.println("");
        System.out.println("------ Menu Médicos ------");
        System.out.println("1. Cadastrar Médico");
        System.out.println("2. Listar Médicos");
        System.out.println("3. Voltar ao Menu Principal");
        System.out.println("--------------------------");
    }
}
