package model;

public class Medico extends Pessoa {
    private String crm;
    private String especialidade;
    private int custoConsulta;

    public Medico(String nome, int idade, String cpf, String crm, String especialidade, int custoConsulta) {
        super(nome, idade, cpf);
        this.crm = crm;
        this.especialidade = especialidade;
        this.custoConsulta = custoConsulta;
    }

    public String getCrm() {
        return crm;
    }
    public void setCrm(String crm) {
        this.crm = crm;
    }
    public String getEspecialidade() {
        return especialidade;
    }
    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }
    public int getCustoConsulta() {
        return custoConsulta;
    }
    public void setCustoConsulta(int custoConsulta) {
        this.custoConsulta = custoConsulta;
    }
    public String toString() {
        return "Médico: " + super.toString() + ", CRM: " + crm + ", Especialidade: " + especialidade + ", Custo da Consulta: R$" + custoConsulta;
    }
}
