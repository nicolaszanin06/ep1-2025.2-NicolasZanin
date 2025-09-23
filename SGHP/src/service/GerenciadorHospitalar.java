package service;

import java.util.List;
import model.Paciente;
import model.Medico;
import model.Consulta;
import model.PacienteEspecial;

public class GerenciadorHospitalar {
    private List<Paciente> pacientes;
    private List<Medico> medicos;
    private List<Consulta> consultas;
    private List<PacienteEspecial> pacientesEspeciais;

    public GerenciadorHospitalar(List<Paciente> pacientes, List<Medico> medicos, List<Consulta> consultas, List<PacienteEspecial> pacientesEspeciais) {
        this.pacientes = pacientes;
        this.medicos = medicos;
        this.consultas = consultas;
        this.pacientesEspeciais = pacientesEspeciais;
    }

    public void cadastrarPaciente(Paciente paciente) {
        pacientes.add(paciente);
        System.out.println("Paciente cadastrado com sucesso: " + paciente.getNome());
    }

    public void cadastrarMedico(Medico medico) {
        medicos.add(medico);
        System.out.println("Médico cadastrado com sucesso: " + medico.getNome());
    }
    public void cadastrarConsulta(Consulta consulta) {
        consultas.add(consulta);
        System.out.println("Consulta cadastrada com sucesso para o paciente: " + consulta.getPaciente().getNome());
    }
    public void cadastrarPacienteEspecial(PacienteEspecial pacienteEspecial) {
        pacientesEspeciais.add(pacienteEspecial);
        System.out.println("Paciente especial cadastrado com sucesso: " + pacienteEspecial.getNome());
    }
    
}
