package model;

import java.util.ArrayList;
import java.util.List;

public class Paciente extends Pessoa {

    private List<Consulta> historicoConsultas;

    public Paciente(String nome, int idade, String cpf) {
        super(nome, idade, cpf);
        this.historicoConsultas = new ArrayList<>();
    }
    
    public List<Consulta> getConsultas() {
        return historicoConsultas;
    }
    public void adicionarConsulta(Consulta consulta) {
        this.historicoConsultas.add(consulta);
    }
    
    @Override
    public String toString() {
        return "Paciente:" + super.toString();
    }
}
