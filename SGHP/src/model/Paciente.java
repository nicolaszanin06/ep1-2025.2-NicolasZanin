package model;

import java.util.List;
import model.Consulta;

public class Paciente extends Pessoa {
    public Paciente(String nome, int idade, String cpf) {
        super(nome, idade, cpf);
    }
    
    @Override
    public String toString() {
        return "Paciente:" + super.toString();
    }
}
