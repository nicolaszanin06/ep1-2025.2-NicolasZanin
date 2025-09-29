package model.enums;

public enum StatusConsulta {
    AGENDADA("Agendada"),
    REALIZADA("Realizada"),
    CANCELADA("Cancelada");

    private final String statusFormatado;

    StatusConsulta(String statusFormatado) {
        this.statusFormatado = statusFormatado;
    }

    public String getStatusFormatado() {
        return statusFormatado;
    }

}
