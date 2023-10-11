package io.github.projektalmanac.amoxtli.backend.serviceTest;

import io.github.projektalmanac.amoxtli.backend.entity.User;
import io.github.projektalmanac.amoxtli.backend.exception.BadRequestException;
import io.github.projektalmanac.amoxtli.backend.exception.EmailUserNotVerificationException;
import io.github.projektalmanac.amoxtli.backend.exception.UserNotFoundException;
import io.github.projektalmanac.amoxtli.backend.generated.model.PerfilUsuarioDto;
import io.github.projektalmanac.amoxtli.backend.repository.UserRepository;
import io.github.projektalmanac.amoxtli.backend.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openapitools.jackson.nullable.JsonNullable;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.net.URI;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserService userService;


    private User user;
    private User user1;
    private PerfilUsuarioDto perfilUsuarioDto;
    private PerfilUsuarioDto perfilUsuarioDtoCambio;

    @BeforeEach
    void setUp()  {
        //Se crea un usuario provicional
        user = new User();
        user.setId(1);
        user.setName("Eduardo");
        user.setLastName("Castro");
        user.setEmail("ecastro@gmail.com");
        user.setPhone("58587599");
        user.setPhoto("Foto".getBytes());
        user.setVerifiedEmail(false);

        user1 = new User();
        user1.setId(2);
        user1.setName("Eduardo");
        user1.setLastName("Castro");
        user1.setEmail("ecastro@gmail.com");
        user1.setPasswordSalt("34sdas");
        user1.setPasswordHash("dsads");
        user1.setPhone("58587599");
        user1.setPhoto("Foto".getBytes());
        user1.setPhotoDescription("Hola soy yo");
        user1.setInterests("Libros de ficcion");
        user1.setVerifiedEmail(true);

        perfilUsuarioDto = new PerfilUsuarioDto();
        perfilUsuarioDto.setId(1);
        perfilUsuarioDto.setNombre("Eduaro");
        perfilUsuarioDto.setApellidos("Castro");
        perfilUsuarioDto.setCorreo("ecastro@gmail.com");
        perfilUsuarioDto.setTelefono("585858332");
        perfilUsuarioDto.setFotoPerfil(JsonNullable.of(URI.create("foto")));
        perfilUsuarioDto.setCorreoVerificado(false);

        perfilUsuarioDtoCambio = new PerfilUsuarioDto();
        perfilUsuarioDtoCambio.setId(2);
        perfilUsuarioDtoCambio.setNombre("Eduaro");
        perfilUsuarioDtoCambio.setApellidos("Ramón");
        perfilUsuarioDtoCambio.setCorreo("ecastro@gmail.com");
        perfilUsuarioDtoCambio.setTelefono("585858332");
        perfilUsuarioDtoCambio.setFotoPerfil(JsonNullable.of(URI.create("foto")));
        perfilUsuarioDtoCambio.setCorreoVerificado(true);


    }



    @Test
    void getUsuario() {

        //Caso 1. El usuario no existe en la base de datos
        Integer id = 0;
        when(userRepository.getUserById(id)).thenReturn(null);
        Assertions.assertThrows(UserNotFoundException.class,() ->{
            userService.getUsuario(id);
        });
        //Caso 2. El usuario existe pero su correo no esta verificado
        Integer idUser = 1;
        when(userRepository.getUserById(idUser)).thenReturn(this.user);
        Assertions.assertThrows(EmailUserNotVerificationException.class,() ->{
            userService.getUsuario(idUser);
        });

    }

    @Test
    void actualizaUsuario() {

        // Caso 1. EL usuario no existe
        Integer idUser = 1;
        when(userRepository.getUserById(idUser)).thenReturn(null);
        Assertions.assertThrows(UserNotFoundException.class,() ->{
            userService.actualizaUsuario(idUser,perfilUsuarioDto);
        });

        // Caso 2. El usuario existe pero su correo no esta autenticado
        Integer idUser1 = 1;
        when(userRepository.getUserById(idUser)).thenReturn(this.user);
        Assertions.assertThrows(EmailUserNotVerificationException.class,() ->{
            userService.actualizaUsuario(idUser1,perfilUsuarioDto);
        });

        // Caso 3. Se actualizan los cambios correctamente
        Integer id = 2;
        when(userRepository.getUserById(id)).thenReturn(this.user1);
        when(userRepository.save(any(User.class))).then(returnsFirstArg());
        PerfilUsuarioDto perfil = userService.actualizaUsuario(id,perfilUsuarioDtoCambio);
        Assertions.assertEquals(perfilUsuarioDtoCambio.getApellidos(),perfil.getApellidos());
        Assertions.assertEquals(perfilUsuarioDtoCambio.getTelefono(),perfil.getTelefono());

    }

    @Test
    void actualizaFoto() throws IOException {
        // Representación de una imagen.
        Resource body = new ByteArrayResource(new byte[]{1, 2, 3});
        // Caso 1. El usuario no existe
        when(userRepository.getUserById(1)).thenReturn(null);
        Assertions.assertThrows(UserNotFoundException.class,() ->{
            userService.actualizaFoto(1,body);
        });
        // Caso 2: El correo del usuario verificado
        when(userRepository.getUserById(1)).thenReturn(this.user);
        Assertions.assertThrows(EmailUserNotVerificationException.class,() ->{
            userService.actualizaFoto(1,body);
        });

        // Caso 3. Se guarda la foto correctamente
        when(userRepository.getUserById(2)).thenReturn(this.user1);
        when(userRepository.save(this.user1)).thenReturn(this.user1);
        userService.actualizaFoto(2,body);
        Assertions.assertArrayEquals(body.getInputStream().readAllBytes(),this.user1.getPhoto());

    }
}