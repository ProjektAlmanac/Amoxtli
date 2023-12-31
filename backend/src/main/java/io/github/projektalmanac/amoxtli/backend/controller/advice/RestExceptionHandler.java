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

    @ExceptionHandler(EmailUserNotVerificationException.class)
    public final ResponseEntity<ErrorDto> handleUnauthenticatedUserException(EmailUserNotVerificationException ex,
            WebRequest request) {
        var error = new ErrorDto(ex.getMessage(), 2);
        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(EmptyResourceException.class)
    public final ResponseEntity<Void> handleEmptyResourceException(EmptyResourceException ex, WebRequest request) {
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public final ResponseEntity<ErrorDto> handleResourceNotFoundException(ResourceNotFoundException ex,
            WebRequest request) {
        var error = new ErrorDto(ex.getMessage(), 1);
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BookAlreadyExistsException.class)
    public final ResponseEntity<ErrorDto> handleBookAlreadyExistsException(BookAlreadyExistsException ex,
            WebRequest request) {
        var error = new ErrorDto(ex.getMessage(), 6);
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadRequestException.class)
    public final ResponseEntity<ErrorDto> handleBadRequestException(BadRequestException ex, WebRequest request) {
        var error = new ErrorDto(ex.getMessage(), 3);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidIdException.class)
    public final ResponseEntity<ErrorDto> handleIdUserNotWorkException(BadRequestException ex, WebRequest request) {
        var error = new ErrorDto(ex.getMessage(), 4);
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidPhotoException.class)
    public final ResponseEntity<ErrorDto> handleInvalidPhotoException(InvalidPhotoException ex, WebRequest request) {
        var error = new ErrorDto(ex.getMessage(), 5);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(InvalidUserException.class)
    public final ResponseEntity<ErrorDto> handleInvalidUserException(InvalidUserException ex, WebRequest request) {
        var error = new ErrorDto(ex.getMessage(), 8);
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
    @ExceptionHandler(InvalidUserSessionException.class)
    public final ResponseEntity<ErrorDto> handleInvalidUserSesionException(InvalidUserSessionException ex, WebRequest request) {
        var error = new ErrorDto(ex.getMessage(), 9);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(InvalidVerificationCodeException.class)
    public final ResponseEntity<ErrorDto> handleInvalidVerificationCodeException(InvalidVerificationCodeException ex, WebRequest request) {
        var error = new ErrorDto(ex.getMessage(), 10);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(BookNotFoundException.class)
    public final ResponseEntity<ErrorDto> handleBookNotFoundException(BookNotFoundException ex, WebRequest request) {
        var error = new ErrorDto(ex.getMessage(), 10);
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(SelfExchangeException.class)
    public  final ResponseEntity<ErrorDto> handleSelfExchangeException(SelfExchangeException ex, WebRequest request){
        var error = new ErrorDto(ex.getMessage(), 11);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(UnregisteredPhoneNumberException.class)
    public final ResponseEntity<ErrorDto> handleUnregisteredPhoneNumberException(UnregisteredPhoneNumberException ex, WebRequest request) {
        var error = new ErrorDto(ex.getMessage(), 12);
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(NoBooksRegisteredException.class)
    public final ResponseEntity<ErrorDto> handleNoBooksRegisteredException(NoBooksRegisteredException ex, WebRequest request) {
        var error = new ErrorDto(ex.getMessage(), 13);
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IntercambioNotFoundException.class)
    public final ResponseEntity<ErrorDto> handleIntercambioNotFoundException(IntercambioNotFoundException ex, WebRequest request) {
        var error = new ErrorDto(ex.getMessage(), 14);
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(GoogleBooksException.class)
    public final ResponseEntity<ErrorDto> handleGoogleBooksException(GoogleBooksException ex, WebRequest request) {
        var error = new ErrorDto(ex.getMessage(), 14);
        return new ResponseEntity<>(error, HttpStatus.SERVICE_UNAVAILABLE);
    }

    @ExceptionHandler(InvalidExchangeCodeException.class)
    public final ResponseEntity<ErrorDto> handleInvalidExchangeCodeException(InvalidExchangeCodeException ex, WebRequest request) {
        var error = new ErrorDto(ex.getMessage(), 15);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ExchangeErrorProcess.class)
    public final ResponseEntity<ErrorDto> handleExchangeErrorProcess(ExchangeErrorProcess ex, WebRequest request) {
        var error = new ErrorDto(ex.getMessage(), 13);
        var codigoHttp = HttpStatus.NOT_FOUND;
        if(ex.getMessage().equals("Usuario no autorizado"))//401
            codigoHttp= HttpStatus.UNAUTHORIZED;
        if(ex.getMessage().equals("Intercambio no encontrado"))//404
            codigoHttp = HttpStatus.NOT_FOUND;
        if(ex.getMessage().equals("Operacion prohibida"))//403
            codigoHttp = HttpStatus.FORBIDDEN;
        return new ResponseEntity<>(error, codigoHttp);
    }
}
