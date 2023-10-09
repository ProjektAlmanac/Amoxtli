package io.github.projektalmanac.amoxtli.backend.exception;

public class InvalidUserException extends RuntimeException{
    public InvalidUserException(String message) {
        super(message);
    }
}
