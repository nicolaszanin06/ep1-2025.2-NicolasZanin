package service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import model.Paciente;
import model.enums.StatusConsulta;
import model.Medico;
import model.Consulta;
import model.Internacao;

public class GerenciadorHospitalar {
    private List<Paciente> pacientes;
    private List<Medico> medicos;
    private List<Consulta> consultas;
    private List<Internacao> internacoes;

    public GerenciadorHospitalar() {
        this.pacientes = new java.util.ArrayList<>();
        this.medicos = new java.util.ArrayList<>();
        this.consultas = new java.util.ArrayList<>();
        this.internacoes = new ArrayList<>();
    }

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
                System.out.println(
                        "ERRO: O médico " + consulta.getMedico().getNome() + " já possui uma consulta neste horário.");
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

    // Métodos relacionados a consultas -----------------------------------------
    public Consulta getConsultaByIndex(int index) {
        if (index >= 0 && index < consultas.size()) {
            return consultas.get(index);
        }
        return null;
    }

    public boolean realizarConsulta(int index, String diagnostico, List<String> prescricoes) {
        Consulta consulta = getConsultaByIndex(index);
        if (consulta != null && consulta.getStatus() == StatusConsulta.AGENDADA) {
            consulta.setDiagnostico(diagnostico);
            consulta.setPrescricoes(prescricoes);
            consulta.setStatus(StatusConsulta.REALIZADA);
            System.out.println("Consulta de " + consulta.getPaciente().getNome() + " realizada com sucesso.");
            return true;
        }
        System.out.println("Erro: Consulta não encontrada ou não pode ser realizada (status: "
                + (consulta != null ? consulta.getStatus() : "N/A") + ").");
        return false;
    }

    public boolean cancelarConsulta(int index) {
        Consulta consulta = getConsultaByIndex(index);
        if (consulta != null && consulta.getStatus() == StatusConsulta.AGENDADA) {
            consulta.setStatus(StatusConsulta.CANCELADA);
            System.out.println("Consulta de " + consulta.getPaciente().getNome() + " cancelada com sucesso.");
            return true;
        }
        System.out.println("Erro: Consulta não encontrada ou não pode ser cancelada (status: "
                + (consulta != null ? consulta.getStatus() : "N/A") + ").");
        return false;
    }

    // Métodos para listar pacientes, médicos e consultas
    // -----------------------------------------
    public List<Paciente> getPacientes() {
        return pacientes;
    }

    public List<Medico> getMedicos() {
        return medicos;
    }

    public List<Consulta> getConsultas() {
        return consultas;
    }

    // Métodos relacionados a Pacientes -----------------------------------------
    public boolean atualizarPaciente(String cpf, String novoNome, int novaIdade) {
        Paciente paciente = buscarPacientePorCPF(cpf);
        if (paciente != null) {
            paciente.setNome(novoNome);
            paciente.setIdade(novaIdade);
            System.out.println("Paciente atualizado com sucesso: " + paciente.getNome());
            return true;
        }
        System.out.println("Erro: Paciente com CPF " + cpf + " não encontrado.");
        return false;
    }

    public boolean removerPaciente(String cpf) {
        Paciente paciente = buscarPacientePorCPF(cpf);
        if (paciente != null) {
            pacientes.remove(paciente);
            System.out.println("Paciente removido com sucesso: " + paciente.getNome());
            return true;
        }
        System.out.println("Erro: Paciente com CPF " + cpf + " não encontrado.");
        return false;
    }

    public boolean criarPaciente(String nome, int idade, String cpf) {
        if (buscarPacientePorCPF(cpf) == null) {
            Paciente novoPaciente = new Paciente(nome, idade, cpf);
            cadastrarPaciente(novoPaciente);
            return true;
        }
        System.out.println("Erro: Já existe um paciente com o CPF " + cpf);
        return false;
    }

    // Métodos relacionados a Médicos -----------------------------------------
    public boolean criarMedico(String nome, int idade, String cpf, String crm, String especialidade,
            int custoConsulta) {
        if (buscarMedicoPorCRM(crm) == null) {
            Medico novoMedico = new Medico(nome, idade, cpf, crm, especialidade, custoConsulta);
            cadastrarMedico(novoMedico);
            return true;
        }
        System.out.println("Erro: Já existe um médico com o CRM " + crm);
        return false;
    }

    public boolean atualizarMedico(String crm, String novoNome, int novaIdade, String novaEspecialidade,
            int novoCustoConsulta) {
        Medico medico = buscarMedicoPorCRM(crm);
        if (medico != null) {
            medico.setNome(novoNome);
            medico.setIdade(novaIdade);
            medico.setEspecialidade(novaEspecialidade);
            medico.setCustoConsulta(novoCustoConsulta);
            System.out.println("Médico atualizado com sucesso: " + medico.getNome());
            return true;
        }
        System.out.println("Erro: Médico com CRM " + crm + " não encontrado.");
        return false;
    }

    public boolean removerMedico(String crm) {
        Medico medico = buscarMedicoPorCRM(crm);
        if (medico != null) {
            medicos.remove(medico);
            System.out.println("Médico removido com sucesso: " + medico.getNome());
            return true;
        }
        System.out.println("Erro: Médico com CRM " + crm + " não encontrado.");
        return false;
    }

    // Métodos relacionados a Internações
    public void podeInternar(int numeroQuarto) {
        for (Internacao i : internacoes) {
            if (i.getNumeroQuarto() == numeroQuarto && i.estaAtiva()) {
                System.out.println("ERRO: O quarto " + numeroQuarto + " já está ocupado.");
                return;
            }
        }
        System.out.println("O quarto " + numeroQuarto + " está disponível para internação.");
    }
}
