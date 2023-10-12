package io.github.projektalmanac.amoxtli.backend.enums;

public enum Status {
    CANCELADO("Pendiente"),
    ACEPTADO("Aceptado"),
    RECHAZADO("Rechazado"),
    PENDIENTE("Cancelado");

    private final String status;
    Status(String s){status = s;}
    public String getStatus(){
        return status;
    }
}
