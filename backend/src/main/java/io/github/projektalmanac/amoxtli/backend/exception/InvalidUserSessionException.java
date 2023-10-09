package io.github.projektalmanac.amoxtli.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


public  class InvalidUserSessionException  extends  RuntimeException{
    /*public InvalidUserSessionException(){
        super("Verifique que sus credenciales sean correctas, e intente nuevamente.");
    }*/

    public  InvalidUserSessionException(String mensaje){
        super(mensaje);
    }

}