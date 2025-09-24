package service;

import java.util.List;
import model.Paciente;
import model.Medico;
import model.Consulta;

public class GerenciadorHospitalar {
    private List<Paciente> pacientes;
    private List<Medico> medicos;
    private List<Consulta> consultas;


    public GerenciadorHospitalar(List<Paciente> pacientes, List<Medico> medicos, List<Consulta> consultas) {
        this.pacientes = pacientes;
        this.medicos = medicos;
        this.consultas = consultas;
    }

    public void cadastrarPaciente(Paciente paciente) {
        pacientes.add(paciente);
        System.out.println("Paciente cadastrado com sucesso: " + paciente.getNome());
    }

    public void cadastrarMedico(Medico medico) {
        medicos.add(medico);
        System.out.println("Médico cadastrado com sucesso: " + medico.getNome());
    }
    public void agendarConsulta(Consulta consulta) {
        for (Consulta c : consultas) {
            if (c.getMedico().equals(consulta.getMedico()) && c.getDataHora().equals(consulta.getDataHora())) {
                System.out.println("ERRO: O médico " + consulta.getMedico().getNome() + " já possui uma consulta neste horário.");
                return;
            }
        }
        consultas.add(consulta);
        System.out.println("Consulta agendada com sucesso para o paciente " + consulta.getPaciente().getNome() +
                " com o médico " + consulta.getMedico().getNome() + " em " + consulta.getDataHora());
    }

    public Paciente buscarPacientePorCPF(String cpf) {
                for (Paciente p : pacientes) {
                    if (p.getCpf().equals(cpf)) {
                        return p;
                    }
                }
                return null;
            }
    public Medico buscarMedicoPorCRM(String crm) {
                for (Medico m : medicos) {
                    if (m.getCrm().equals(crm)) {
                        return m;
                    }
                }
                return null;
            }
        }
        

