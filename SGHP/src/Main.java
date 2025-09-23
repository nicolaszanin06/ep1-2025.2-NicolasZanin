import view.Menu;

public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println("Bem-vindo(a) ao sistema de Gerenciamento Hospitalar!");
        System.out.print("Carregando o menu. \nAguarde");
        System.out.print(".");
        //Animação de Loading
        Thread.sleep(800);
        System.out.print(".");
        Thread.sleep(800);
        System.out.print(".");
        Thread.sleep(800);
        System.out.print("");

        Thread.sleep(2000);
        new Menu();
        
        }
    }

