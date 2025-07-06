package com.erp.acmf_api.domain.enuns;

public enum ModalidadePedido {

    EM_PACOTE(1, "EM_PACOTE"),
    PRONTO_ENTREGA(2, "PRONTO_ENTREGA");

    private final int code;
    private final String label;

    ModalidadePedido(int code, String label) {
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
