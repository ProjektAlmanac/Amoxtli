package io.github.projektalmanac.amoxtli.backend.exception;

public  class InvalidUserSessionException  extends  RuntimeException{
    public InvalidUserSessionException(){
        super("Credenciales incorrectas");
    }

    public InvalidUserSessionException(String email){
        super("No se encontró ningún usuario con email " + email + ".");
    }

}