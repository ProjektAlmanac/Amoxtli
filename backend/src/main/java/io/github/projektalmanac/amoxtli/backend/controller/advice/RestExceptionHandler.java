package io.github.projektalmanac.amoxtli.backend.controller.advice;

import io.github.projektalmanac.amoxtli.backend.exception.*;
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
    public final ResponseEntity<ErrorDto> handleUserNotFoundException(UserNotFoundException ex, WebRequest request) {
        var error = new ErrorDto(ex.getMessage(), 1);
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidUserException.class)
    public final ResponseEntity<ErrorDto> handleInvalidUserException(InvalidUserException ex, WebRequest request) {
        var error = new ErrorDto(ex.getMessage(), 5); // Código 400 para Bad Request  //acmbiar el codigo en forma de id
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public final ResponseEntity<ErrorDto> handleEmailAlreadyExistsException(EmailAlreadyExistsException ex, WebRequest request) {
        var error = new ErrorDto(ex.getMessage(), 6);
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InvalidEmailFormatException.class)
    public final ResponseEntity<ErrorDto> handleInvalidEmailFormatException(InvalidEmailFormatException ex, WebRequest request) {
        var error = new ErrorDto(ex.getMessage(), 7);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }



}
