package io.github.projektalmanac.amoxtli.backend;

import io.github.projektalmanac.amoxtli.backend.entity.User;
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

    String email,password, passwordHash;
    CredencialesDto credencialesDto;
    SessionTokenDto sessionTokenDto;

    SecurityConfig seguridad;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.initMocks(this);
        seguridad = new SecurityConfig();
        email = "test@example.com";
        password = "passwordHash";
        passwordHash = seguridad.hashContrasena(password);
        //System.out.println("Password hash -> "+passwordHash);

        //credencialdto
        credencialesDto = new CredencialesDto();
        credencialesDto.setEmail(email);
        credencialesDto.setContrasena(password);
    }
    @Test
    void iniciarSesion() {
        System.out.println("Test:: iniciarSesion");
        //1.- cuando el usuario no tiene una cuenta es decir su correo no existe en el sistema
        //assertNull(userService.iniciarSesion(credencialesDto));

        //2.- cuando el correo y password son encontrados en el sistema
        // Configura el comportamiento del repositorio mock
        User user = new User();
        user.setEmail(email);
        user.setPasswordHash(passwordHash);
        Optional<User> expectedUser = Optional.of(user);

        Mockito.when(userRepository.findByEmail(email))
                .thenReturn(expectedUser);


        // Llama al método que utiliza el UserRepository
        sessionTokenDto = userService.iniciarSesion(credencialesDto);
        System.out.println(sessionTokenDto);
        assertEquals(sessionTokenDto.getIdUsuario(),user.getId());
        System.out.println("Para el usuario: "+user.getId()+" se tiene el token: "+sessionTokenDto.getToken());
    }





    @Test
    void generadorToken(){
        System.out.println("Test:: generadorToken");
        //String clave = "email.com";
        String token = userService.generadorToken(10,"email.com");
        System.out.println(token);
        String userTokenInfo = userService.infoSesion(token,"email.com");
        System.out.println(userTokenInfo);
        assertTrue(token != null,"Error: No se pudo generar el token");
    }
}