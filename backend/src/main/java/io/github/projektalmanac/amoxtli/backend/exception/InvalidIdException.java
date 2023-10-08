package io.github.projektalmanac.amoxtli.backend.exception;

public class InvalidIdException extends RuntimeException{
    public InvalidIdException(){
        super("El id proporcionado no es un número valido");
    }
}
