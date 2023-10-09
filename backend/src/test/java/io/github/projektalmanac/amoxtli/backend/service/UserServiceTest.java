package io.github.projektalmanac.amoxtli.backend.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.Optional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import io.github.projektalmanac.amoxtli.backend.entity.User;
import io.github.projektalmanac.amoxtli.backend.service.UserService;
import io.github.projektalmanac.amoxtli.backend.exception.InvalidUserSessionException;
import io.github.projektalmanac.amoxtli.backend.exception.EmailAlreadyExistsException;
import io.github.projektalmanac.amoxtli.backend.exception.InvalidEmailFormatException;
import io.github.projektalmanac.amoxtli.backend.exception.UserNotFoundException;
import io.github.projektalmanac.amoxtli.backend.generated.model.CodigoVerificacionDto;
import io.github.projektalmanac.amoxtli.backend.generated.model.UsuarioDto;
import io.github.projektalmanac.amoxtli.backend.generated.model.CredencialesDto;
import io.github.projektalmanac.amoxtli.backend.generated.model.SessionTokenDto;
import io.github.projektalmanac.amoxtli.backend.generated.model.UsuarioIdDto;
import io.github.projektalmanac.amoxtli.backend.repository.UserRepository;
import io.github.projektalmanac.amoxtli.backend.config.SecurityConfig;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private JavaMailSender javaMailSender;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    String email, password, passwordHash, salt;
    CredencialesDto credencialesDto;
    SessionTokenDto sessionTokenDto;

    SecurityConfig seguridad;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        seguridad = new SecurityConfig();
        email = "test@example.com";
        password = "passwordHash";
        salt = seguridad.saltHash();
        passwordHash = seguridad.hashContrasena(password + salt);
        // System.out.println("Password hash -> "+passwordHash);

        // credencialdto
        credencialesDto = new CredencialesDto();
        credencialesDto.setEmail(email);
        credencialesDto.setContrasena(password);
    }

    @Test
    void iniciarSesion() {
        // Indicacion del test que se ejecuta
        System.out.println("Test:: iniciarSesion");

        // 1.- cuando el usuario no tiene una cuenta es decir su correo no existe en el
        // sistema
        Exception exception = assertThrows(InvalidUserSessionException.class, () -> {
            userService.iniciarSesion(credencialesDto);
        });
        String mensajeEsperado = "Este correo no esta asociado a ninguna cuenta, intente registrarse.";
        String mensajeEncontrado = exception.getMessage();

        assertEquals(mensajeEsperado, mensajeEncontrado); // assertTrue(actualMessage.contains(expectedMessage));

        // 2.- El usuario envia sus credenciales mal, es decir su contraseña no es
        // correcta

        // Supongase que se tiene en el repositorio un usuario registrado
        User user = new User();
        user.setEmail(email);
        user.setPasswordHash(passwordHash);
        user.setPasswordSalt(salt);
        user.setId(1);
        Optional<User> expectedUser = Optional.of(user);
        // mock sobre el metodo findByEmail
        Mockito.when(userRepository.findByEmail(email))
                .thenReturn(expectedUser);

        // verificacion de lanzamiento de exepcion
        CredencialesDto credencialesDto1 = new CredencialesDto();
        credencialesDto1.setContrasena("otherPass");
        credencialesDto1.setEmail(email);
        Exception exception2 = assertThrows(InvalidUserSessionException.class, () -> {
            // no es la misma contrasenia a la que se tiene en el repositorio (DB)
            userService.iniciarSesion(credencialesDto1);
        });
        mensajeEsperado = "Contrasenia incorrecta.";
        mensajeEncontrado = exception2.getMessage();
        assertEquals(mensajeEsperado, mensajeEncontrado);

        // 3- Supongase que se tiene un usuario registrado y este ingresa correctamente
        // sus credenciales

        // Considere que el usuario registrado es user, definido en punto 2.

        Mockito.when(userRepository.findByEmail(email))
                .thenReturn(expectedUser);
        // Bajo la suposicion de que el usuario, ingreso sus credenciales correctamente
        // ahora solo debemos validar esto
        // Para ello userService nos debe regresar un sessionTokenDto
        sessionTokenDto = userService.iniciarSesion(credencialesDto); // System.out.println(sessionTokenDto);
        // comparamos que id en la sesion sea el mismo que el del usuario user
        assertEquals(sessionTokenDto.getIdUsuario(), user.getId());

        // System.out.println("Para el usuario: "+user.getId()+" se tiene el token:
        // "+sessionTokenDto.getToken());
    }

    @Test
    void createuser() {
        // crea usuario exitosamente://////////////
        UsuarioDto usuarioDto = new UsuarioDto();
        usuarioDto.setNombre("Yael");
        usuarioDto.setApellildos("Ortega");
        usuarioDto.setCorreo("prueba1@example.com");
        usuarioDto.setPassword("password");

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).then(returnsFirstArg());

        UsuarioIdDto result = userService.createuser(usuarioDto);

        assertNotNull(result);

        ////// ingresa un correo que ya existe./////////////
        UsuarioDto usuarioDto1 = new UsuarioDto();
        usuarioDto1.setNombre("Axel");
        usuarioDto1.setApellildos("Guzman");
        usuarioDto1.setCorreo("prueba1@example.com");
        usuarioDto1.setPassword("12345");

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(new User()));

        assertThrows(EmailAlreadyExistsException.class, () -> {
            userService.createuser(usuarioDto1);
        });

        ////// ingresa un correo con la sintaxis erronea:///////////////
        UsuarioDto usuarioDto2 = new UsuarioDto();
        usuarioDto2.setNombre("Miguel");
        usuarioDto2.setApellildos("Ortega");
        usuarioDto2.setCorreo("holaamigo.com");
        usuarioDto2.setPassword("12345");

        assertThrows(InvalidEmailFormatException.class, () -> {
            userService.createuser(usuarioDto2);
        });

    }

    @Test
    void hashes() {
        System.out.println("Test:: Hash sobre el password");
        // usuario existente
        User user = new User();
        user.setEmail(email);
        user.setPasswordHash(passwordHash);
        user.setPasswordSalt(salt);
        Optional<User> expectedUser = Optional.of(user);

        // variable passwordRepositorio auxiliar para legibilidad
        String passwRep = expectedUser.get().getPasswordHash();

        // 1. considere un usuario que ingresa una constraseña incorrecta
        assertFalse(seguridad.matchContrasena(password.substring(2) + salt, passwRep));
        assertFalse(seguridad.matchContrasena("password" + salt, passwRep));

        // 2. Considere que un usuario ingresa su contraseña correcta
        assertTrue(seguridad.matchContrasena(password + salt, passwRep));
    }

    @Test
    void enviarCorreoVerificacion() throws MessagingException {

        /////// se envia correo de verificacio con exito//////////
        int userId = 1;
        String email = "correo@example.com";
        User user = new User();
        user.setId(userId);
        user.setEmail(email);

        MimeMessage mimeMessage = mock(MimeMessage.class);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);

        userService.enviarCorreoVerificacion(userId);

        assertNotNull(user.getVerificationCode());
        verify(userRepository, times(1)).save(user);
        verify(javaMailSender, times(1)).send(mimeMessage);

        //////// usuario no encontrado////////////

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UserNotFoundException.class, () -> {
            userService.enviarCorreoVerificacion(userId);
        });

    }

    @Test
    void generadorToken() {
        System.out.println("Test:: generadorToken");
        // String clave = "email.com";
        String token = userService.generadorToken(10);
        System.out.println(token);
        String userTokenInfo = userService.infoSesion(token);
        System.out.println(userTokenInfo);
        assertTrue(token != null, "Error: No se pudo generar el token");
    }

    @Test
    void verificaCorreo() {
        long userId = 1;

        //////// Codigo valido.///////
        CodigoVerificacionDto codigoVerificacionDto = new CodigoVerificacionDto();
        codigoVerificacionDto.setCodigo("123456"); // Código de verificación válido

        User user = new User();
        user.setVerificationCode("123456");

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        assertTrue(userService.verificaCorreo(userId, codigoVerificacionDto));

        //////// codigo invalido://////////

        codigoVerificacionDto.setCodigo("codigo erroneo"); // Código de verificación inválido

        User user1 = new User();
        user1.setVerificationCode("123456"); // Código almacenado en el usuario

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        assertThrows(InvalidEmailFormatException.class, () -> {
            userService.verificaCorreo(userId, codigoVerificacionDto);
        });

        //////////// usuario no encontrado://///////////

        codigoVerificacionDto.setCodigo("123456"); // Código de verificación

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> {
            userService.verificaCorreo(userId, codigoVerificacionDto);
        });
    }
}