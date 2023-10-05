package io.github.projektalmanac.amoxtli.backend.exception;

public class UnauthenticatedUserException extends RuntimeException{

    public UnauthenticatedUserException(Integer userId){
        super("El usuario con id: " + userId + " aún no esta autenticado.");
    }
}
