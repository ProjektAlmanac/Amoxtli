package io.github.projektalmanac.amoxtli.backend.exception;

public class IdUserNotWorkException extends RuntimeException{
    public IdUserNotWorkException(){
        super("El id proporcionado no es un número verifica el ID");
    }
}
