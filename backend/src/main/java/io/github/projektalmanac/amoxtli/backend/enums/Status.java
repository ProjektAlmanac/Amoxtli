package io.github.projektalmanac.amoxtli.backend.enums;

import lombok.Getter;

@Getter
public enum Status {
    CANCELADO("Cancelado"),
    ACEPTADO("Aceptado"),
    RECHAZADO("Rechazado"),
    COMPLETADO("Completado"),
    PENDIENTE("Pendiente");

    private final String s;
    Status(String s) {
        this.s = s;
    }
}
