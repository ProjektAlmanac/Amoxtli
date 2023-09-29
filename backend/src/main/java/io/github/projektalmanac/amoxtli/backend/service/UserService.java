package io.github.projektalmanac.amoxtli.backend.service;

import io.github.projektalmanac.amoxtli.backend.entity.User;
import io.github.projektalmanac.amoxtli.backend.generated.model.CredencialesDto;
import io.github.projektalmanac.amoxtli.backend.generated.model.SessionTokenDto;
import io.github.projektalmanac.amoxtli.backend.repository.UserRepository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public static String generadorToken()
    {
        final int LONGITUD = 20;
        Random rnd = new Random();
        String numeros = "0123456789";
        String letras  = "abcdefghijklenopqrstuvwxyz";
        String letrasMayusculas = letras.toUpperCase();
        String simbolos  = "!$%-";

        List<String> preToken = new ArrayList<>();
        preToken.add(numeros);
        preToken.add(letras);
        preToken.add(letrasMayusculas);
        preToken.add(simbolos);

        Collections.shuffle(preToken, rnd);

        // Convierte la lista en un solo string usando String.join()
        String simbolosToken = String.join("", preToken);

        char[] token = new char[LONGITUD];
        for (int i = 0; i < LONGITUD; i++)
        {
            token[i] = simbolosToken.charAt(rnd.nextInt(simbolosToken.length()));

        }
        return new String(token);
    }

    public SessionTokenDto iniciarSesion(CredencialesDto credenciales){
        //Validacion de regla de negocio

        Optional<User> usuario = userRepository.findByEmailAndPasswordHash(credenciales.getEmail(),credenciales.getContrasena());
        // Existe una cuenta con el correo electronico dado y la constraseña es la misma a la que se tiene en el sistema
        if (usuario.isPresent() && !usuario.isEmpty()){
            //creamos el Dto de SessionTokenDto con el dato de id usuario y generamos un token
            SessionTokenDto sessionTokenDto = new SessionTokenDto();
            sessionTokenDto.setIdUsuario(usuario.get().getId());
            sessionTokenDto.setToken(generadorToken());

            return sessionTokenDto;
        }else{
            return null;
        }


    }

}

