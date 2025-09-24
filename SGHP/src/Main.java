import view.Menu;

public class Main {
    public static void main(String[] args) throws Exception {

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
        new Menu();
        
        }
    }

