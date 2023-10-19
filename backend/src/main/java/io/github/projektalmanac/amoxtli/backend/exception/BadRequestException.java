package io.github.projektalmanac.amoxtli.backend.exception;

public class BadRequestException extends RuntimeException{

    public BadRequestException(Integer userId){
        super("El usuario con id: " + userId + " intento cambiar el ID unico.");
    }
    public BadRequestException(String mensaje){
        super(mensaje);
    }
}
