package io.github.projektalmanac.amoxtli.backend.generated.enums;

public enum Status {
    CANCELADO(0),
    ACEPTADO(1),
    RECHAZADO(2),
    PENDIENTE(3);

    private final int status;
    Status(int i){status = i;}
    public int getStatus(){
        return status;
    }
}
