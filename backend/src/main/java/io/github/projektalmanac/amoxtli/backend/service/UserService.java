package io.github.projektalmanac.amoxtli.backend.service;

import io.github.projektalmanac.amoxtli.backend.entity.User;
import io.github.projektalmanac.amoxtli.backend.exception.InvalidUserSessionException;
import io.github.projektalmanac.amoxtli.backend.generated.model.CredencialesDto;
import io.github.projektalmanac.amoxtli.backend.generated.model.SessionTokenDto;
import io.github.projektalmanac.amoxtli.backend.repository.UserRepository;
import io.github.projektalmanac.amoxtli.backend.config.SecurityConfig;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SecurityConfig seguridad;

    public String generadorToken(long idUsuario)
    {
        return seguridad.generadorToken(String.valueOf(idUsuario));
    }

    public String infoSesion(String token){
        return seguridad.decodificarToken(token).getSubject();
    }

    /**
     *  Metodo encargado de validar un inicio de sesion con las credenciales
     *  asociadas al usuario capturado en el frontend,
     *  realiza el proceso se autenticacion
     * @param credenciales: Es el dto de las credenciales, contiene Usuario y Password
     * @return sessionTokenDto or null
     */
    public SessionTokenDto iniciarSesion(CredencialesDto credenciales){ //hash password
        /*
         * Pre proceso: Se debe antes que nada, realizar un hash sobre
         * la contraseña contenida en el DTO de credenciales,
         * para lo cual se tiene una variable local:
         * credencialPass, es de tipo String por que el mecanismo de hasheo hashContrasen(String),
         * planteado en seguridad es de tipo String.
         */
        //String credencialPass = seguridad.hashContrasena(credenciales.getContrasena());

        //System.out.println("Constrasena -> "+credenciales.getContrasena()+"\n"+credencialPass);

        /*
         * Reglas de negocio:
         * 1- Usuario previamente registrado
         *  Para ello usamos el email del usuario y mediante una consulta al repositorio con findByEmail
         *  Si hay cooincidencia pasamos a la segunda regla de validacion
         *  Si no, se retorna un null
         * 2- Autenticacion de usuario
         *  Se valida que la contrasena sea la correcta, para lo cual se hace un Match con la hash de la
         *  contrasena, guarda durante el proceso de registro de usuario, para lo que usamos el mecanismo de
         *  matchContrasena(String,String), que se planteo en seguridad y que nos retorna
         *  true o false, si hay coincidencia.
         * Cuando no hay coincidencia se retona un null, pero en caso contrario se retorna el DTO de sesionToken que
         * contiene el id y token asociados al usuario autenticado.
         */

        //regla de negocio 1
        Optional<User> usuario = userRepository.findByEmail(credenciales.getEmail());
        if (usuario.isPresent()){
            //regla de negocio 2
            //System.out.println("Repositorio: "+usuario.get().getPasswordHash());
            //System.out.println("DTO: "+credencialPass);
            if (seguridad.matchContrasena(credenciales.getContrasena()+usuario.get().getPasswordSalt(),usuario.get().getPasswordHash())){
                //creamos el Dto de SessionTokenDto con el dato de id usuario y generamos un token
                /*SessionTokenDto sessionTokenDto = new SessionTokenDto();
                sessionTokenDto.setIdUsuario(usuario.get().getId());
                sessionTokenDto.setToken(generadorToken());*/
                long id = usuario.get().getId();
                return new SessionTokenDto(id, generadorToken(id));
            }else{
                throw new InvalidUserSessionException("Contrasenia incorrecta.");
            }

        }else{
            throw new InvalidUserSessionException("Este correo no esta asociado a ninguna cuenta, intente registrarse.");
        }


    }

}

