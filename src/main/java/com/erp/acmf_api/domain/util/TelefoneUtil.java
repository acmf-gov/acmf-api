package com.erp.acmf_api.domain.util;

public class TelefoneUtil {
    public static String desformatarTelefone(String numeroTelefone) {
        if (numeroTelefone == null) {
            return "";
        }
        return numeroTelefone.replaceAll("\\D", "");
    }
}
