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
    public double custoDaConsulta(Medico medico) {
        return medico.getCustoConsulta(); // Paciente comum nn tem desconto
    }
    @Override
    public String toString() {
        return super.toString();
    }
}
