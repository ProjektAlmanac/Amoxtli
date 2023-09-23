package io.github.projektalmanac.amoxtli.backend.generated.controller;

import io.github.projektalmanac.amoxtli.backend.generated.api.IniciarSesionApi;
import io.github.projektalmanac.amoxtli.backend.generated.model.CredencialesDto;
import io.github.projektalmanac.amoxtli.backend.generated.model.SessionTokenDto;
import org.springframework.http.ResponseEntity;

public class LoginController implements IniciarSesionApi {
    @Override
    public ResponseEntity<SessionTokenDto> iniciarSesion(CredencialesDto credencialesDto) {
        return null;
    }
}
