package model;

public class PacienteEspecial extends Paciente {
    public String planoSaude;

    public PacienteEspecial(String nome, int idade, String telefone, String cpf, String planoSaude) {
        super(nome, idade, telefone, cpf);
        this.planoSaude = planoSaude;
    }

}
