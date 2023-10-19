package io.github.projektalmanac.amoxtli.backend.exception;

public class SelfExchangeException extends RuntimeException{

    public SelfExchangeException(Integer userId){
        super("El usuario con id: " + userId + " intento intercambiar un libro consigo mismo.");
    }

}
