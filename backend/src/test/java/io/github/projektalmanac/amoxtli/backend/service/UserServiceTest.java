package io.github.projektalmanac.amoxtli.backend.service;

import io.github.projektalmanac.amoxtli.backend.entity.User;
import io.github.projektalmanac.amoxtli.backend.exception.InvalidUserSessionException;
import io.github.projektalmanac.amoxtli.backend.generated.model.CredencialesDto;
import io.github.projektalmanac.amoxtli.backend.generated.model.SessionTokenDto;
import io.github.projektalmanac.amoxtli.backend.repository.UserRepository;
import io.github.projektalmanac.amoxtli.backend.service.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import io.github.projektalmanac.amoxtli.backend.config.SecurityConfig;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    String email,password, passwordHash,salt;
    CredencialesDto credencialesDto;
    SessionTokenDto sessionTokenDto;

    SecurityConfig seguridad;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.initMocks(this);
        seguridad = new SecurityConfig();
        email = "test@example.com";
        password = "passwordHash";
        salt = seguridad.saltHash();
        passwordHash = seguridad.hashContrasena(password+salt);
        //System.out.println("Password hash -> "+passwordHash);

        //credencialdto
        credencialesDto = new CredencialesDto();
        credencialesDto.setEmail(email);
        credencialesDto.setContrasena(password);
    }
    @Test
    void iniciarSesion() {
        //Indicacion del test que se ejecuta
        System.out.println("Test:: iniciarSesion");

        //1.- cuando el usuario no tiene una cuenta es decir su correo no existe en el sistema
        Exception exception = assertThrows(InvalidUserSessionException.class, () -> {
            userService.iniciarSesion(credencialesDto);
        });
        String mensajeEsperado = "Este correo no esta asociado a ninguna cuenta, intente registrarse.";
        String mensajeEncontrado = exception.getMessage();

        assertEquals(mensajeEsperado,mensajeEncontrado); //assertTrue(actualMessage.contains(expectedMessage));

        //2.- El usuario envia sus credenciales mal, es decir su contraseña no es correcta

            // Supongase que se tiene en el repositorio un usuario registrado
        User user = new User();
        user.setEmail(email);
        user.setPasswordHash(passwordHash);
        user.setPasswordSalt(salt);
        Optional<User> expectedUser = Optional.of(user);
            //mock sobre el metodo findByEmail
        Mockito.when(userRepository.findByEmail(email))
                .thenReturn(expectedUser);

            //verificacion de lanzamiento de exepcion
        CredencialesDto credencialesDto1 = new CredencialesDto();
        credencialesDto1.setContrasena("otherPass");
        credencialesDto1.setEmail(email);
        Exception exception2 = assertThrows(InvalidUserSessionException.class, () -> {
            //no es la misma contrasenia a la que se tiene en el repositorio (DB)
            userService.iniciarSesion(credencialesDto1);
        });
        mensajeEsperado = "Contrasenia incorrecta.";
        mensajeEncontrado = exception2.getMessage();
        assertEquals(mensajeEsperado,mensajeEncontrado);


        // 3- Supongase que se tiene un usuario registrado y este ingresa correctamente sus credenciales

            // Considere que el usuario registrado es user, definido en punto 2.

            // Bajo la suposicion de que el usuario, ingreso sus credenciales correctamente ahora solo debemos validar esto
            // Para ello userService nos debe regresar un sessionTokenDto
        sessionTokenDto = userService.iniciarSesion(credencialesDto); //System.out.println(sessionTokenDto);
        //comparamos que id en la sesion sea el mismo que el del usuario user
        assertEquals(sessionTokenDto.getIdUsuario(),user.getId());

        //System.out.println("Para el usuario: "+user.getId()+" se tiene el token: "+sessionTokenDto.getToken());


    }

    @Test
    void hashes(){
        System.out.println("Test:: Hash sobre el password");
        //usuario existente
        User user = new User();
        user.setEmail(email);
        user.setPasswordHash(passwordHash);
        user.setPasswordSalt(salt);
        Optional<User> expectedUser = Optional.of(user);

        Mockito.when(userRepository.findByEmail(email))
                .thenReturn(expectedUser);
        //variable passwordRepositorio auxiliar para legibilidad
        String passwRep = expectedUser.get().getPasswordHash();

        //1. considere un usuario que ingresa una constraseña incorrecta
        assertFalse(seguridad.matchContrasena(password.substring(2)+salt,passwRep));
        assertFalse(seguridad.matchContrasena("password"+salt,passwRep));

        //2. Considere que un usuario ingresa su contraseña correcta
        assertTrue(seguridad.matchContrasena(password+salt,passwRep));


    }




    @Test
    void generadorToken(){
        System.out.println("Test:: generadorToken");
        //String clave = "email.com";
        String token = userService.generadorToken(10);
        System.out.println(token);
        String userTokenInfo = userService.infoSesion(token);
        System.out.println(userTokenInfo);
        assertTrue(token != null,"Error: No se pudo generar el token");
    }
}