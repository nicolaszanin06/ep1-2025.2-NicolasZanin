package entidades;

public abstract class Pessoa {
    public String nome;
    public int idade;
    public String telefone;
    public String cpf;

    public Pessoa(String nome, int idade, String telefone, String cpf) {
        this.nome = nome;
        this.idade = idade;
        this.telefone = telefone;
        this.cpf = cpf;
    }



}
