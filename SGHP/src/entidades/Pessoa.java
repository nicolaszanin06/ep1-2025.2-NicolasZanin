package entidades;

public abstract class Pessoa {
    private String nome;
    private int idade;
    private String telefone;
    private String cpf;

    public Pessoa(String nome, int idade, String telefone, String cpf) {
        this.nome = nome;
        this.idade = idade;
        this.telefone = telefone;
        this.cpf = cpf;
    }



}
