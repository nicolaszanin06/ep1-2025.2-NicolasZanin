import java.util.Scanner;

import service.GerenciadorHospitalar;
import view.Menu;

public class Main {
    public static void main(String[] args) throws Exception {

        Scanner sc = new Scanner(System.in);
        GerenciadorHospitalar gh = new GerenciadorHospitalar();
        {
        System.out.println("Bem-vindo(a) ao sistema de Gerenciamento Hospitalar!");
        System.out.print("Carregando o menu. \nAguarde");
        //Animação de Loading
        Thread.sleep(700);
        System.out.print(".");
        Thread.sleep(700);
        System.out.print(".");
        Thread.sleep(700);
        System.out.println(".");

        Thread.sleep(1500);
        System.out.println("");
        new Menu(sc, gh);
        
        sc.close();
            }
        }
    }

