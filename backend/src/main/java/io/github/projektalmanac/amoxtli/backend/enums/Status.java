package io.github.projektalmanac.amoxtli.backend.enums;

public enum Status {
    CANCELADO("Cancelado"),
    ACEPTADO("Aceptado"),
    RECHAZADO("Rechazado"),
    COMPLETADO("Completado"),
    PENDIENTE("Pendiente");

    private final String status;
    Status(String s){status = s;}
    public String getStatus(){
        return status;
    }
}
