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
        return super.toString() + ", Plano de Saúde: " + planoSaude;
    }

    @Override
    public double custoDaConsulta(Medico medico) {
        double preco = medico.getCustoConsulta() * (1 - planoSaude.getDesconto());
        if (getIdade() >= 60) {
            preco *= 0.9;
        }
        return preco;
    }

}
