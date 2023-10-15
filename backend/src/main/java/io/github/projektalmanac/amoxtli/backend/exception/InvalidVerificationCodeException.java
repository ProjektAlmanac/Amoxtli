package io.github.projektalmanac.amoxtli.backend.exception;

public class InvalidVerificationCodeException extends RuntimeException {
        public InvalidVerificationCodeException() {
            super("El código de verificación es inválido.");
        }
}
