package io.github.projektalmanac.amoxtli.backend.enums;

import lombok.Getter;

@Getter
public enum Status {
    CANCELADO("Cancelado"),
    ACEPTADO("Aceptado"),
    RECHAZADO("Rechazado"),
    PENDIENTE("Pendiente");

    private final String s;
    Status(String s) {
        this.s = s;
    }
}
