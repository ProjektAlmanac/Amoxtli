package io.github.projektalmanac.amoxtli.backend.service;

import io.github.projektalmanac.amoxtli.backend.entity.User;
import io.github.projektalmanac.amoxtli.backend.generated.model.CredencialesDto;
import io.github.projektalmanac.amoxtli.backend.generated.model.SessionTokenDto;
import io.github.projektalmanac.amoxtli.backend.repository.UserRepository;


import org.springframework.stereotype.Service;
import org.springframework.beens.factory.annotation.Autorired;
import io.github.projektalmanac.amoxtli.backend.config.*;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserReposiory userReposiory;

    public CredencialesDto iniciarSesion(CredencialesDto credenciales){
        //Validacion de regla de negocio

        Optional<User> usuario = userReposiory.findByEmailAndPasswordHash(credenciales.getEmail(),credenciales.getContrasena());
        // Existe una cuenta con el correo electronico dado y la constraseña es la misma a la que se tiene en el sistema
        if (usuario.isPresent()){
            //creamos el Dto de SessionTokenDto con el dato de id usuario y generamos un token
            SessionTokenDto sessionTokenDto = new SessionTokenDto();
            //SecurityConfig securityConfig = new SecurityConfig();
            //String token = (String) securityConfig.jwtAccessTokenConverter(usuario.getPasswordHash());
            sessionTokenDto.setIdUsuario(usuario.getId());
            sessionTokenDto.setToken("asdfjlkñañlsdkfj");

            return sessionTokenDto;
        }else{
            return null;
        }


    }

}

