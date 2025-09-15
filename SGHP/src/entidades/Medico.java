package entidades;

public class Medico extends Pessoa {
    public String crm;
    public String especialidade;
    public int custoConsulta;

    public Medico(String nome, int idade, String telefone, String cpf, String crm, String especialidade, int custoConsulta) {
        super(nome, idade, telefone, cpf);
        this.crm = crm;
        this.especialidade = especialidade;
        this.custoConsulta = custoConsulta;
    }

}
