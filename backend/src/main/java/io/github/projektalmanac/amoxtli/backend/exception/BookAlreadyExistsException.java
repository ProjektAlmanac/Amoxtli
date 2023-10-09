package io.github.projektalmanac.amoxtli.backend.exception;

public class BookAlreadyExistsException extends RuntimeException {
    public BookAlreadyExistsException() {
        super("El libro ya está registrado.");
    }
}

