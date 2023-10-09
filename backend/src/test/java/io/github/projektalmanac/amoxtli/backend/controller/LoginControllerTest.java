package io.github.projektalmanac.amoxtli.backend.controller;

import io.github.projektalmanac.amoxtli.backend.controller.LoginController;
import io.github.projektalmanac.amoxtli.backend.exception.InvalidUserSessionException;
import io.github.projektalmanac.amoxtli.backend.generated.model.CredencialesDto;
import io.github.projektalmanac.amoxtli.backend.generated.model.SessionTokenDto;
import io.github.projektalmanac.amoxtli.backend.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.Objects;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class LoginControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private LoginController loginController;

    @BeforeEach
    void setUp() {
        // Inicializar Mockito
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void iniciarSesion_UsuarioValido() {
        // Configurar un usuario válido
        CredencialesDto credencialesDto = new CredencialesDto();
        credencialesDto.setEmail("test@example.com");
        credencialesDto.setContrasena("password");

        // Simular el comportamiento del servicio para un usuario válido
        SessionTokenDto sessionTokenDto = new SessionTokenDto();
        sessionTokenDto.setToken("token");

        when(userService.iniciarSesion(any(CredencialesDto.class)))
                .thenReturn(sessionTokenDto);

        // Llamar al método iniciarSesion del controlador
        ResponseEntity<SessionTokenDto> response = loginController.iniciarSesion(credencialesDto);

        // Verificar que la respuesta sea OK y contenga el token
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(Objects.requireNonNull(response.getBody()).getToken()).isEqualTo("token");
    }

    @Test
    void iniciarSesion_UsuarioInvalido() {
        //Indicacion del test que se ejecuta
        System.out.println("Test:: Sesion rechazada");

        // Configurar un usuario inválido
        CredencialesDto credencialesDto = null;

        //Indicacion del test que se ejecuta
        System.out.println("Test:: iniciarSesion");

        //1.- cuando el usuario no tiene una cuenta es decir su correo no existe en el sistema o ingresa mal sus credenciales
        Exception exception = assertThrows(InvalidUserSessionException.class, () -> {
            loginController.iniciarSesion(credencialesDto);
        });
        String mensajeEsperado = "Sesion rechazada, intente mas tarde.";
        String mensajeEncontrado = exception.getMessage();

        assertEquals(mensajeEsperado,mensajeEncontrado);
    }
}
