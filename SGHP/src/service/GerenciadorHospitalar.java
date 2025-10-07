package service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import model.Paciente;
import model.enums.StatusConsulta;
import model.Medico;
import model.Consulta;
import model.Internacao;

public class GerenciadorHospitalar {
    private final List<Paciente> pacientes = new ArrayList<>();
    private final List<Medico> medicos = new ArrayList<>();
    private final List<Consulta> consultas = new ArrayList<>();
    private final List<Internacao> internacoes = new ArrayList<>();

    // Métodos Pacientes

    public boolean cadastrarPacienteValidador(Paciente paciente) {
        if (buscarPacientePorCPF(paciente.getCpf()) != null) {
            System.out.println("Erro: Já existe paciente com CPF " + paciente.getCpf());
            return false;
        }
        pacientes.add(paciente);
        System.out.println("Paciente cadastrado com sucesso: " + paciente.getNome());
        return true;
    }

    public Paciente buscarPacientePorCPF(String cpf) {
        for (Paciente p : pacientes) {
            if (p.getCpf().equals(cpf)) {
                return p;
            }
        }
        return null;
    }

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
            cadastrarPacienteValidador(novoPaciente);
            return true;
        }
        System.out.println("Erro: Já existe um paciente com o CPF " + cpf);
        return false;
    }

    // Métodos Médicos

    public boolean cadastrarMedicoValidador(Medico medico) {
        if (buscarMedicoPorCRM(medico.getCrm()) != null) {
            System.out.println("Erro: Já existe médico com CRM " + medico.getCrm());
            return false;
        }
        medicos.add(medico);
        System.out.println("Médico cadastrado com sucesso: " + medico.getNome());
        return true;
    }

    public Medico buscarMedicoPorCRM(String crm) {
        for (Medico m : medicos) {
            if (m.getCrm().equals(crm)) {
                return m;
            }
        }
        return null;
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

    public boolean criarMedico(String nome, int idade, String cpf, String crm, String especialidade,
            int custoConsulta) {
        if (buscarMedicoPorCRM(crm) == null) {
            Medico novoMedico = new Medico(nome, idade, cpf, crm, especialidade, custoConsulta);
            cadastrarMedicoValidador(novoMedico);
            return true;
        }
        System.out.println("Erro: Já existe um médico com o CRM " + crm);
        return false;
    }

    // Métodos Consultas
    public void agendarConsulta(Consulta consulta) {
        for (Consulta c : consultas) {
            boolean mesmoHorario = c.getDataHora().equals(consulta.getDataHora());
            if (mesmoHorario && c.getMedico().equals(consulta.getMedico())) {
                System.out.println(
                        "ERRO: O médico " + consulta.getMedico().getNome() + " já possui uma consulta neste horário.");
                return;
            }
            if (mesmoHorario && c.getLocal().equalsIgnoreCase(consulta.getLocal())) {
                System.out.println(
                        "ERRO: O local '" + consulta.getLocal() + "' já está ocupado neste horário.");
                return;
            }
        }
        consultas.add(consulta);
        System.out.println("Consulta agendada com sucesso para o paciente " + consulta.getPaciente().getNome() +
                " com o médico " + consulta.getMedico().getNome() + " em " + consulta.getDataHora());
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

    private Consulta getConsultaByIndex(int index) {
        if (index >= 0 && index < consultas.size()) {
            return consultas.get(index);
        }
        return null;
    }

    public Consulta buscarConsultaPorId(int id) {
        for (Consulta c : consultas) {
            if (c.getId() == id)
                return c;
        }
        return null;
    }

    // Métodos Internações
    public void podeInternar(int numeroQuarto) {
        for (Internacao i : internacoes) {
            if (i.getNumeroQuarto() == numeroQuarto && i.estaAtiva()) {
                System.out.println("ERRO: O quarto " + numeroQuarto + " já está ocupado.");
                return;
            }
        }
        System.out.println("O quarto " + numeroQuarto + " está disponível para internação.");
    }

    public boolean quartoDisponivel(int numeroQuarto) {
        for (Internacao i : internacoes) {
            if (i.getNumeroQuarto() == numeroQuarto && i.estaAtiva()) {
                return false;
            }
        }
        return true;
    }

    public boolean criarInternacao(Paciente p, Medico m, LocalDateTime entrada, int quarto, double custoDiario) {
        if (!quartoDisponivel(quarto))
            return false;
        Internacao nova = new Internacao(p, m, entrada, null, quarto, custoDiario);
        internacoes.add(nova);
        return true;
    }

    public boolean darAlta(int id, java.time.LocalDateTime dataAlta) {
        Internacao internacao = buscarInternacaoPorId(id);
        if (internacao != null && internacao.estaAtiva()) {
            internacao.setDataAlta(dataAlta);
            System.out.println("Alta registrada para a internação ID " + id);
            return true;
        }
        System.out.println("Erro: Internação com ID " + id + " não encontrada ou já finalizada/cancelada.");
        return false;
    }

    public boolean cancelarInternacao(int id) {
        Internacao i = buscarInternacaoPorId(id);
        if (i == null)
            return false;
        if (!i.estaAtiva())
            return false;
        i.cancelarInternacao();
        return true;

    }

    public Internacao buscarInternacaoPorId(int id) {
        for (Internacao i : internacoes) {
            if (i.getId() == id)
                return i;
        }
        return null;
    }

    // Métodos para listar pacientes, médicos, consultas e internações
    public List<Paciente> getPacientes() {
        return pacientes;
    }

    public List<Medico> getMedicos() {
        return medicos;
    }

    public List<Consulta> getConsultas() {
        return consultas;
    }

    public List<Internacao> getInternacoes() {
        return internacoes;
    }

}