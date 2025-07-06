package com.erp.acmf_api.domain.enuns;

public enum TipoResidencia {
    CASA(1, "Casa"),
    APARTAMENTO(2, "Apartamento");

    private final int code;
    private final String label;

    TipoResidencia(int code, String label) {
        this.code = code;
        this.label = label;
    }

    public int getCode() {
        return code;
    }

    public String getLabel() {
        return label;
    }
}
