package util;

import java.util.Scanner;

public class Utilitario {
    private Utilitario() {
    }

    public static int lerInt(Scanner sc, String mensagem) {
        System.out.print(mensagem);
        while (!sc.hasNextInt()) {
            System.out.print("Entrada inválida. " + mensagem);
            sc.next();
        }
        return sc.nextInt();
    }

    public static double lerDouble(Scanner sc, String mensagem) {
        System.out.print(mensagem);
        while (!sc.hasNextDouble()) {
            System.out.print("Entrada inválida. " + mensagem);
            sc.next();
        }
        return sc.nextDouble();
    }

    public static String lerString(Scanner sc, String mensagem) {
        System.out.print(mensagem);
        return sc.nextLine();
    }
}
