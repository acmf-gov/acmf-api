package com.erp.acmf_api.domain.enuns;

public enum StatusPagamento {

    PAGO(1, "Pago"),
    PENDENTE(2, "Pendente"),
    PAGO_SEM_ENTREGA(3, "Pago sem entrega"),
    CANCELADO(4, "Pedido cancelado ou pagamento estornado");

    private final int code;
    private final String label;

    StatusPagamento(int code, String label) {
        this.code = code;
        this.label = label;
    }

    public int getCode() {
        return code;
    }

    public String getLabel() {
        return label;
    }

    public static StatusPagamento fromCodigo(int codigo) {
        for (StatusPagamento status : values()) {
            if (status.getCode() == codigo) {
                return status;
            }
        }
        throw new IllegalArgumentException("Código de status inválido: " + codigo);
    }

}

