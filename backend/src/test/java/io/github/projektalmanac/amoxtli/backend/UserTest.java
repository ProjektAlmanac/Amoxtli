package io.github.projektalmanac.amoxtli.backend;

import io.github.projektalmanac.amoxtli.backend.entity.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    User usuario = new User();

    @Test
    void getId() {
        //Se tiene un unico usuario por lo que su id debe ser 0
        assertEquals(0,usuario.getId());
    }

    @Test
    void getEmail() {
        String email =  "test@example.com";
        usuario.setEmail(email);
        assertEquals(email,usuario.getEmail());
    }

    @Test
    void getPasswordHash() {
        String passwordHash = "D3H89H329";
        usuario.setPasswordHash(passwordHash);
        assertEquals(passwordHash,usuario.getPasswordHash());
    }

}