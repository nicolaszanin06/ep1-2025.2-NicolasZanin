package model;

import java.time.LocalDateTime;

public class Internacao {
    private Paciente paciente;
    private Medico medico;
    private LocalDateTime dataInternacao;
    private LocalDateTime dataAlta;
    private int numeroQuarto;
    private double custoDiario;
    private boolean cancelada;
    private static int SEQ = 1;
    private final int id = SEQ++;

    public Internacao(Paciente paciente, Medico medico, LocalDateTime dataInternacao, LocalDateTime dataAlta,
            int numeroQuarto, double custoDiario) {
        this.paciente = paciente;
        this.medico = medico;
        this.dataInternacao = dataInternacao;
        this.dataAlta = dataAlta;
        this.numeroQuarto = numeroQuarto;
        this.custoDiario = custoDiario;
        this.cancelada = false;
    }

    // getters e setters

    public Paciente getPaciente() {
        return paciente;
    }

    public Medico getMedico() {
        return medico;
    }

    public LocalDateTime getDataInternacao() {
        return dataInternacao;
    }

    public LocalDateTime getDataAlta() {
        return dataAlta;
    }

    public int getNumeroQuarto() {
        return numeroQuarto;
    }

    public double getCustoDiario() {
        return custoDiario;
    }

    public double setCustoDiario(double custoDiario) {
        return this.custoDiario = custoDiario;
    }

    public boolean isCancelada() {
        return cancelada;
    }

    public void setDataAlta(LocalDateTime dataAlta) {
        this.dataAlta = dataAlta;
    }

    public void cancelarInternacao() {
        this.cancelada = true;
    }

    public boolean estaAtiva() {
        return !cancelada && (dataAlta == null || !dataAlta.isBefore(dataInternacao));

    }

    public long getTempoDeInternacao() {
        LocalDateTime fim = (dataAlta != null) ? dataAlta : LocalDateTime.now();
        return java.time.Duration.between(dataInternacao, fim).toDays();
    }

    public double calcularCustoTotal() {
        long dias = getTempoDeInternacao();
        return dias * custoDiario;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Internacao{" +
                "paciente=" + paciente.getNome() +
                ", medico=" + medico.getNome() +
                ", dataInternacao=" + dataInternacao +
                ", dataAlta=" + dataAlta +
                ", numeroQuarto=" + numeroQuarto +
                ", custoDiario=" + custoDiario +
                ", cancelada=" + cancelada +
                '}';
    }
}
