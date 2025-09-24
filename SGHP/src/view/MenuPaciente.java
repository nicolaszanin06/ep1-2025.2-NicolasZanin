package view;

import java.util.Scanner;

import service.GerenciadorHospitalar;

public class MenuPaciente {
    private Scanner sc;
    private GerenciadorHospitalar gh;
    
    public MenuPaciente(Scanner sc, GerenciadorHospitalar gh) {
        this.sc = sc;
        this.gh = gh;
    }
}
