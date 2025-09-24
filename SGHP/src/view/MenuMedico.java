package view;

import java.util.Scanner;
import service.GerenciadorHospitalar;

public class MenuMedico {
    private Scanner sc;
    private GerenciadorHospitalar gh;

    public MenuMedico(Scanner sc, GerenciadorHospitalar gh) {
        this.sc = sc;
        this.gh = gh;
    }
}
