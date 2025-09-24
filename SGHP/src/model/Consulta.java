package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import model.enums.StatusConsulta;


public class Consulta {
    private Paciente paciente;
    private Medico medico;
    private LocalDateTime dataHora;
    private String local;
    private String motivo;
    private StatusConsulta status;

  // atributos para quando a consulta for realizada

    private String diagnostico;
    private List<String> prescricoes;

    public Consulta(Paciente paciente, Medico medico, LocalDateTime dataHora, String local, String motivo) {
        this.paciente = paciente;
        this.medico = medico;
        this.dataHora = dataHora;
        this.local = local;
        this.motivo = motivo;
        this.status = StatusConsulta.AGENDADA; // Toda consulta começa como agendada
        this.diagnostico = "";
        this.prescricoes = new ArrayList<>();
        }


    public void realizarConsulta(String diagnostico, List<String> prescricoes) {
        if (this.status == StatusConsulta.AGENDADA) {
            this.diagnostico = diagnostico;
            this.prescricoes = prescricoes;
            this.status = StatusConsulta.REALIZADA;
            System.out.println("A consulta foi alterada para 'Realizada'");
        } else {
            throw new IllegalStateException("Erro: A consulta deve estar agendada para ser realizada.");
        }
    }

    public void cancelarConsulta() {
        if (this.status == StatusConsulta.AGENDADA) {
            this.status = StatusConsulta.CANCELADA;
            System.out.println("A consulta foi alterada para 'Cancelada'");
        } else {
            throw new IllegalStateException("Erro: A consulta deve estar agendada para ser cancelada.");
        }
    }


    public Paciente getPaciente() {
        return paciente;
    }
    public Medico getMedico() {
        return medico;
    }
    public LocalDateTime getDataHora() {
        return dataHora;
    }
    public String getLocal() {
        return local;
    }
    public String getMotivo() {
        return motivo;
    }
    public StatusConsulta getStatus() {
        return status;
    }
    public void setStatus(StatusConsulta status) {
        this.status = status;
    }
    public String getDiagnostico() {
        return diagnostico;
    }
    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }
    public List<String> getPrescricoes() {
        return prescricoes;
    }
    public void setPrescricoes(List<String> prescricoes) {
        this.prescricoes = prescricoes;
    }



    @Override
    public String toString() {
        return "Consulta [dataHora=" + dataHora + ", local=" + local + ", medico=" + medico.getNome() + ", motivo=" + motivo
                + ", paciente=" + paciente.getNome() + ", status=" + status.getStatusFormatado() + "]";
    }


    
}
