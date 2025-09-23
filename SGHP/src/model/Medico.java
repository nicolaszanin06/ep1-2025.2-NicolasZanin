package model;

public class Medico extends Pessoa {
    private String crm;
    private String especialidade;
    private int custoConsulta;

    public Medico(String nome, int idade, String telefone, String cpf, String crm, String especialidade, int custoConsulta) {
        super(nome, idade, telefone, cpf);
        this.crm = crm;
        this.especialidade = especialidade;
        this.custoConsulta = custoConsulta;

    
    }

}
