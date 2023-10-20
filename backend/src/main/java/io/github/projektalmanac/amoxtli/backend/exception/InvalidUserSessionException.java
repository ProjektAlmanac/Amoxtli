package io.github.projektalmanac.amoxtli.backend.exception;


public  class InvalidUserSessionException  extends  RuntimeException{


    public  InvalidUserSessionException(String mensaje){
        super(mensaje);
    }

}