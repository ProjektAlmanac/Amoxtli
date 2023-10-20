package io.github.projektalmanac.amoxtli.backend.exception;

public class GoogleBooksException extends RuntimeException {
    public GoogleBooksException(Throwable cause) {
        super("El servicio de libros no se encuentra disponible en estos momentos, por favor intente más tarde.", cause);
    }
}


