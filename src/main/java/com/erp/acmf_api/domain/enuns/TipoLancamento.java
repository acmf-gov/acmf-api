package com.erp.acmf_api.domain.enuns;

public enum TipoLancamento {
    RECEITA(1, "RECEITA"),
    DESPESA(2, "DESPESA"),
    DIVIDA(3, "DIVIDA"),
    ESTORNO(4, "ESTORNO");

    private final int code;
    private final String label;

    TipoLancamento(int code, String label) {
        this.code = code;
        this.label = label;
    }

    public int getCode() {
        return code;
    }

    public String getLabel() {
        return label;
    }

    public static TipoLancamento fromCodigo(int codigo) {
        for (TipoLancamento status : values()) {
            if (status.getCode() == codigo) {
                return status;
            }
        }
        throw new IllegalArgumentException("Código de status inválido: " + codigo);
    }
}
