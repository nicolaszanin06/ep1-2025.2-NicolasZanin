package app;

import java.util.Scanner;

import service.GerenciadorHospitalar;
import util.DataCarrega;
import view.Menu;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        GerenciadorHospitalar gh = new GerenciadorHospitalar();

        {
            System.out.println("Bem-vindo(a) ao Sistema de Gerenciamento Hospitalar!");
            System.out.print("Carregando o menu");
            // Animação de Loading
            Thread.sleep(700);
            System.out.print(".");
            Thread.sleep(700);
            System.out.print(".");
            Thread.sleep(700);
            System.out.println(".");

            // Carregar arquivos
            DataCarrega.loadAll(gh);

            Thread.sleep(1000);
            Menu menu = new Menu(sc, gh);
            menu.exibirMenu();

        }

        DataCarrega.saveAll(gh);

        sc.close();

    }

}
