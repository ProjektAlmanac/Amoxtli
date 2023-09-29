package io.github.projektalmanac.amoxtli.backend.exception;

public class EmptyResourceException extends RuntimeException{

    public EmptyResourceException() { super("El recurso está vacío.");}
}
