package model.enums;

public enum PlanoDeSaude {
    UNIMED("Unimed", 1, 0.15, false),
    AMIL("Amil", 2, 0.10, false),
    BRADESCO_SAUDE("Bradesco Saúde", 3, 0.12, false),
    SULAMERICA("SulAmérica", 4, 0.25, true),
    HAPVIDA("Hapvida", 5, 0.30, false),
    NOTREDAME("NotreDame Intermédica", 6, 0.05, true),
    OUTROS("Outros", 7, 0.0, false);

    private final String nome;
    private final int codigo;
    private final double desconto;
    private final boolean especialInternacaoCurta;

    PlanoDeSaude(String nome, int codigo, double desconto, boolean especialInternacaoCurta) {
        this.nome = nome;
        this.codigo = codigo;
        this.desconto = desconto;
        this.especialInternacaoCurta = especialInternacaoCurta;
    }

    public String getNome() {
        return nome;
    }

    public int getCodigo() {
        return codigo;
    }

    public double getDesconto() {
        return desconto;
    }

    public boolean isEspecialInternacaoCurta() {
        return especialInternacaoCurta;
    }

    public static PlanoDeSaude getPorCodigo(int codigo) {
        for (PlanoDeSaude cdd : PlanoDeSaude.values()) {
            if (cdd.getCodigo() == codigo) {
                return cdd;
            }
        }
        throw new IllegalArgumentException("Código de plano inválido: " + codigo);
    }
}
