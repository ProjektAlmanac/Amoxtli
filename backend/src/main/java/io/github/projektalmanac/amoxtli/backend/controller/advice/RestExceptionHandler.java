package io.github.projektalmanac.amoxtli.backend.controller.advice;

import io.github.projektalmanac.amoxtli.backend.exception.UserNotFoundException;
import io.github.projektalmanac.amoxtli.backend.generated.model.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public final ResponseEntity<ErrorDto> handleUserNotFoundException(UserNotFoundException ex, WebRequest request){
        var error = new ErrorDto(ex.getMessage(), 1);
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

}