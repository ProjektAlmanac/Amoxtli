package io.github.projektalmanac.amoxtli.backend.controller;

import io.github.projektalmanac.amoxtli.backend.generated.api.IniciarSesionApi;
import io.github.projektalmanac.amoxtli.backend.generated.model.CredencialesDto;
import io.github.projektalmanac.amoxtli.backend.generated.model.SessionTokenDto;
import io.github.projektalmanac.amoxtli.backend.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;



import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import javax.validation.Valid;

@RestController
public class LoginController implements IniciarSesionApi {

    @Autowired
    private UserService userService;
    @PostMapping
    @Override
    public ResponseEntity<SessionTokenDto> iniciarSesion(@RequestBody @Valid CredencialesDto credencialesDto){

        SessionTokenDto intentoSesion = userService.iniciarSesion(credencialesDto);

        if(intentoSesion != null) {

            return ResponseEntity.status(HttpStatus.OK).body(intentoSesion);

        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

    }
}
