package model;

import model.enums.PlanoDeSaude;

public class PacienteEspecial extends Paciente {
    private PlanoDeSaude planoSaude;

    public PacienteEspecial(String nome, int idade, String cpf, PlanoDeSaude planoSaude) {
        super(nome, idade, cpf);
        this.planoSaude = planoSaude;
    }
    public PlanoDeSaude getPlanoSaude() {
        return planoSaude;
    }
    @Override
    public String toString() {
        return "Paciente Especial: " + super.toString() + ", Plano de Saúde: " + planoSaude;
    }
    public double custoDaConsulta(Medico medico) {
        return (medico.getCustoConsulta() * (1 - planoSaude.getDesconto())); //Sobreescrita do código vindo do Paciente
    }

}
