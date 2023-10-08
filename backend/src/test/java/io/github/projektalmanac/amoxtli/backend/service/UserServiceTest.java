package io.github.projektalmanac.amoxtli.backend.service;

import io.github.projektalmanac.amoxtli.backend.entity.User;
import io.github.projektalmanac.amoxtli.backend.exception.EmailAlreadyExistsException;
import io.github.projektalmanac.amoxtli.backend.exception.InvalidEmailFormatException;
import io.github.projektalmanac.amoxtli.backend.exception.UserNotFoundException;
import io.github.projektalmanac.amoxtli.backend.generated.model.CodigoVerificacionDto;
import io.github.projektalmanac.amoxtli.backend.generated.model.UsuarioDto;
import io.github.projektalmanac.amoxtli.backend.generated.model.UsuarioIdDto;
import io.github.projektalmanac.amoxtli.backend.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private JavaMailSender javaMailSender;

    @InjectMocks
    private UserService userService;



    @Test
    void createuser() {
        //crea usuario exitosamente://////////////
        UsuarioDto usuarioDto = new UsuarioDto();
        usuarioDto.setNombre("Yael");
        usuarioDto.setApellidos("Ortega");
        usuarioDto.setCorreo("prueba1@example.com");
        usuarioDto.setPassword("password");

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenReturn(new User());

        UsuarioIdDto result = userService.createuser(usuarioDto);

        assertNotNull(result);

        //////ingresa un correo que ya existe./////////////
        UsuarioDto usuarioDto1 = new UsuarioDto();
        usuarioDto1.setNombre("Axel");
        usuarioDto1.setApellidos("Guzman");
        usuarioDto1.setCorreo("prueba1@example.com");
        usuarioDto1.setPassword("12345");

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(new User()));

        assertThrows(EmailAlreadyExistsException.class, () -> {
            userService.createuser(usuarioDto1);
        });


        //////ingresa un correo con la sintaxis erronea:///////////////
        UsuarioDto usuarioDto2 = new UsuarioDto();
        usuarioDto2.setNombre("Miguel");
        usuarioDto2.setApellidos("Ortega");
        usuarioDto2.setCorreo("holaamigo.com");
        usuarioDto2.setPassword("12345");

        assertThrows(InvalidEmailFormatException.class, () -> {
            userService.createuser(usuarioDto2);
        });


    }

    @Test
    void enviarCorreoVerificacion() throws MessagingException {

        ///////se envia correo de verificacio con exito//////////
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


        ////////usuario no encontrado////////////

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UserNotFoundException.class, () -> {
            userService.enviarCorreoVerificacion(userId);
        });

    }


    @Test
    void verificaCorreo() {
        long userId = 1;

        ////////Codigo valido.///////
        CodigoVerificacionDto codigoVerificacionDto = new CodigoVerificacionDto();
        codigoVerificacionDto.setCodigo("123456"); // Código de verificación válido

        User user = new User();
        user.setVerificationCode("123456");

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        assertTrue(userService.verificaCorreo(userId, codigoVerificacionDto));

        ////////codigo invalido://////////

        codigoVerificacionDto.setCodigo("codigo erroneo"); // Código de verificación inválido

        User user1 = new User();
        user1.setVerificationCode("123456"); // Código almacenado en el usuario

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        assertThrows(InvalidEmailFormatException.class, () -> {
            userService.verificaCorreo(userId, codigoVerificacionDto);
        });

        ////////////usuario no encontrado://///////////

        codigoVerificacionDto.setCodigo("123456"); // Código de verificación

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> {
            userService.verificaCorreo(userId, codigoVerificacionDto);
        });
    }

}