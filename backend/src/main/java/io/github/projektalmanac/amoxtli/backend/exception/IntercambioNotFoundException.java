package io.github.projektalmanac.amoxtli.backend.exception;

public class IntercambioNotFoundException extends RuntimeException {

    public IntercambioNotFoundException(Integer userId) {
        super("El intercambio con ID " + userId + " no se encontró.");
    }
}
