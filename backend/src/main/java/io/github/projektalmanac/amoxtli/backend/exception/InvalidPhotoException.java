package io.github.projektalmanac.amoxtli.backend.exception;

public class InvalidPhotoException extends RuntimeException {

    public InvalidPhotoException(){
        super("La foto proporcionada no funciona...");
    }
}
