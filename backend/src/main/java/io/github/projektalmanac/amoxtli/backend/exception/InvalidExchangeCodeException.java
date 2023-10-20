package io.github.projektalmanac.amoxtli.backend.exception;

public class InvalidExchangeCodeException extends RuntimeException {
        public InvalidExchangeCodeException() {
            super("El código de intercambio no coincide");
        }
}
