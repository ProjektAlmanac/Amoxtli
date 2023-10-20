package io.github.projektalmanac.amoxtli.backend.service;

import io.github.projektalmanac.amoxtli.backend.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SecurityServiceTest {

    @Mock
    private Secrets secrets;

    @InjectMocks
    private SecurityService securityService;

    @Test
    void generadorToken() {
        // String clave = "email.com";
        when(secrets.getSecretKey()).thenReturn("secret");

        String token = securityService.generadorToken("10");
        String userTokenInfo = securityService.decodificarToken(token).getSubject();

        assertNotNull(token);
        assertEquals("10", userTokenInfo);
    }

    @Test
    void hashes() {
        // usuario existente

        var password = "password";
        var salt = securityService.saltHash();
        var passwordHash = securityService.hashContrasena(password + salt);

        User user = new User();
        user.setEmail("email.com");
        user.setPasswordHash(passwordHash);
        user.setPasswordSalt(salt);
        Optional<User> expectedUser = Optional.of(user);

        // 1. considere un usuario que ingresa una constraseña incorrecta
        var result = securityService.matchContrasena(password.substring(2) + salt, passwordHash);
        var result2 = securityService.matchContrasena("incorrectPassword" + salt, passwordHash);
        assertFalse(result);
        assertFalse(result2);

        // 2. Considere que un usuario ingresa su contraseña correcta
        result = securityService.matchContrasena(password + salt, passwordHash);
        assertTrue(result);
    }
}
