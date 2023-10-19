package io.github.projektalmanac.amoxtli.backend.exception;

public class NoBooksRegisteredException extends RuntimeException{
    public NoBooksRegisteredException(Integer idUser) {
        super("El usuario con id: " + idUser + ", no cuenta con libros registrados.");
    }
}
