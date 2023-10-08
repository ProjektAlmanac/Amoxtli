package io.github.projektalmanac.amoxtli.backend.exception;

public class EmailUserNotVerificationException extends RuntimeException{

    public EmailUserNotVerificationException(Integer userId){
        super("El usuario con id: " + userId + ", tiene el correo no verificado.");
    }
}
