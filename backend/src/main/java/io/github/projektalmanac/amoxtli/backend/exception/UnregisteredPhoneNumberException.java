package io.github.projektalmanac.amoxtli.backend.exception;

public class UnregisteredPhoneNumberException extends RuntimeException{

    public UnregisteredPhoneNumberException(Integer id){
        super("El usuario: " + id + " no tiene un número de telefono registrado.");
    }
}
