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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    String email,password;
    CredencialesDto credencialesDto;
    SessionTokenDto sessionTokenDto;
    @BeforeEach
    void setUp(){
        MockitoAnnotations.initMocks(this);

        email = "test@example.com";
        password = "passwordHash";

        //credencialdto
        credencialesDto = new CredencialesDto();
        credencialesDto.setEmail(email);
        credencialesDto.setContrasena(password);
    }
    @Test
    void iniciarSesion() {
        System.out.println("Test:: iniciarSesion");
        //1.- cuando el usuario no tiene una cuenta es decir su correo no existe en el sistema
        assertNull(userService.iniciarSesion(credencialesDto));

        //2.- cuando el correo y password son encontrados en el sistema
        // Configura el comportamiento del repositorio mock
        User user = new User();
        user.setEmail(email);
        user.setPasswordHash(password);
        Optional<User> expectedUser = Optional.of(user);

        Mockito.when(userRepository.findByEmailAndPasswordHash(email, password))
                .thenReturn(expectedUser);


        // Llama al método que utiliza el UserRepository
        sessionTokenDto = userService.iniciarSesion(credencialesDto);

        assertEquals(sessionTokenDto.getIdUsuario(),user.getId());
        System.out.println("Para el usuario: "+user.getId()+" se tiene el token: "+sessionTokenDto.getToken());
    }





    @Test
    void generadorToken(){
        System.out.println("Test:: generadorToken");
        String token = userService.generadorToken();
        System.out.println(token);
        assertTrue(token != null,"Error: No se pudo generar el token");
    }
}