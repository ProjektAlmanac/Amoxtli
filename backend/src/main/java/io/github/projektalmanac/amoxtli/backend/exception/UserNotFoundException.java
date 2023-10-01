package io.github.projektalmanac.amoxtli.backend.exception;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(Integer userId) {
        super("No se encontró ningún usuario con id " + userId + ".");
    }
}
