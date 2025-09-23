package model;

public class PacienteEspecial extends Paciente {
    private String planoSaude;

    public PacienteEspecial(String nome, int idade, String telefone, String cpf, String planoSaude) {
        super(nome, idade, cpf);
        this.planoSaude = planoSaude;
    }
    public String getPlanoSaude() {
        return planoSaude;
    }
    public void setPlanoSaude(String planoSaude) {
        this.planoSaude = planoSaude;
    }
    @Override
    public String toString() {
        return "Paciente Especial: " + super.toString() + ", Plano de Saúde: " + planoSaude;
    }

}
