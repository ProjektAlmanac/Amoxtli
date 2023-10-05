package io.github.projektalmanac.amoxtli.backend.exception;

public class BadRequestException extends RuntimeException{

    public BadRequestException(Integer userId){
        super("El usuario con id: " + userId + " realizó una solicitud incorrecta.");
    }
}
