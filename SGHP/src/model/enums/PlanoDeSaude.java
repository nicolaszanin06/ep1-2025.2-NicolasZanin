package model.enums;

public enum PlanoDeSaude {
    UNIMED("Unimed", 1, 0.15),
    AMIL("Amil", 2, 0.10),
    BRADESCO_SAUDE("Bradesco Saúde", 3, 0.12),
    SULAMERICA("SulAmérica", 4, 0.25),
    HAPVIDA("Hapvida", 5, 0.30),
    NOTREDAME("NotreDame Intermédica", 6, 0.05),
    OUTROS("Outros", 7, 0.0);

    private final String nome;
    private final int codigo;
    private final double desconto;

    PlanoDeSaude(String nome, int codigo, double desconto) {
        this.nome = nome;
        this.codigo = codigo;
        this.desconto = desconto;
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
    public static PlanoDeSaude getPorCodigo(int codigo) {
        for (PlanoDeSaude cdd : PlanoDeSaude.values()) {
            if (cdd.getCodigo() == codigo) {
                return cdd;
            }
        }
        throw new IllegalArgumentException("Código de plano inválido: " + codigo);
    }
}
