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
        int v = sc.nextInt();
        sc.nextLine();
        return v;
    }

    public static double lerDouble(Scanner sc, String mensagem) {
        System.out.print(mensagem);
        while (!sc.hasNextDouble()) {
            System.out.print("Entrada inválida. " + mensagem);
            sc.next();
        }

        double v = sc.nextDouble();
        sc.nextLine();
        return v;
    }

    public static String lerString(Scanner sc, String mensagem) {
        System.out.print(mensagem);
        return sc.nextLine();
    }

    public static java.time.LocalDateTime lerDataHora(Scanner sc, String mensagem) {
        var fmt = java.time.format.DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        while (true) {
            System.out.print(mensagem);
            String s = sc.nextLine();
            try {
                return java.time.LocalDateTime.parse(s, fmt);
            } catch (java.time.format.DateTimeParseException e) {
                System.out.println("Formato inválido. Use dd-MM-yyyy HH:mm.");
            }
        }
    }

    //Validador
    public static boolean cpfValidador(String cpf) {
        return cpf != null && cpf.matches("\\d{11}");
    }
}
