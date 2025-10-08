package app;

import java.util.Scanner;

import service.GerenciadorHospitalar;
import util.DataCarrega;
import view.Menu;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        GerenciadorHospitalar gh = new GerenciadorHospitalar();
        System.out.println("Data dir: " + util.DataArquivos.dir().toAbsolutePath());
        {
            System.out.println("Bem-vindo(a) ao Sistema de Gerenciamento Hospitalar!");
            System.out.print("Carregando o menu");
            // Animação de Loading
            Thread.sleep(500);
            System.out.print(".");
            Thread.sleep(500);
            System.out.print(".");
            Thread.sleep(500);
            System.out.println(".");
            Thread.sleep(200);

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
