package io.github.projektalmanac.amoxtli.backend.serviceTest;

import io.github.projektalmanac.amoxtli.backend.entity.Book;
import io.github.projektalmanac.amoxtli.backend.entity.Exchange;
import io.github.projektalmanac.amoxtli.backend.entity.User;
import io.github.projektalmanac.amoxtli.backend.enums.Status;
import io.github.projektalmanac.amoxtli.backend.exception.*;
import io.github.projektalmanac.amoxtli.backend.generated.model.CreacionIntercambioDto;
import io.github.projektalmanac.amoxtli.backend.generated.model.PerfilUsuarioDto;
import io.github.projektalmanac.amoxtli.backend.generated.model.ValidaPuedeIntercambiar200ResponseDto;
import io.github.projektalmanac.amoxtli.backend.repository.UserRepository;
import io.github.projektalmanac.amoxtli.backend.service.UserService;
import nonapi.io.github.classgraph.utils.Assert;
import org.hibernate.mapping.Any;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    @Test
    void validaIntercambio() {
        // Caso 1. El usuario no existe
        Integer idUser = 1;

        Optional<User> userOptional = Optional.empty();
        when(userRepository.findById(idUser)).thenReturn(userOptional);
        Assertions.assertThrows(UserNotFoundException.class,() ->{
            userService.validaIntercambio(idUser);
        });

        // Caso 2. El usuario existe pero su correo no esta verificado
        User user = new User();
        user.setId(idUser);
        user.setVerifiedEmail(false);
        userOptional = Optional.of(user);
        when(userRepository.findById(idUser)).thenReturn(userOptional);
        Assertions.assertThrows(EmailUserNotVerificationException.class,() ->{
            userService.validaIntercambio(idUser);
        });

        // Caso 3. El usuario no tiene información de contacto
        user.setVerifiedEmail(true);
        user.setPhone(null);
        userOptional = Optional.of(user);
        when(userRepository.findById(idUser)).thenReturn(userOptional);
        Assertions.assertThrows(UnregisteredPhoneNumberException.class,() ->{
            userService.validaIntercambio(idUser);
        });
        // Caso 4. El usuario no tiene libros registrados

        List<Book> books = new ArrayList<>();
        user.setPhone("58587599");
        user.setBooks(books);
        userOptional = Optional.of(user);
        when(userRepository.findById(idUser)).thenReturn(userOptional);
        Assertions.assertThrows(NoBooksRegisteredException.class,() ->{
            userService.validaIntercambio(idUser);
        });

        // Caso 5. El usuario tiene mas intercambios aceptados/solicitados de los permitidos
        Book book = new Book();
        book.setId(1);
        book.setIsbn("123456789");
        book.setDescription("Libro de prueba");

        books.add(book);
        user.setBooks(books);

        Exchange auxIntercambio = new Exchange();
        auxIntercambio.setId(1);
        auxIntercambio.setStatus(Status.ACEPTADO);
        auxIntercambio.setBookAccepting(book);
        auxIntercambio.setBookOfferor(book);
        auxIntercambio.setUserAccepting(user);

        Exchange auxIntercambio2 = new Exchange();
        auxIntercambio2.setId(2);
        auxIntercambio2.setStatus(Status.PENDIENTE);
        auxIntercambio2.setBookAccepting(book);
        auxIntercambio2.setBookOfferor(null);
        auxIntercambio2.setUserAccepting(user);

        List<Exchange> intercambiosAceptados = new ArrayList<>();
        intercambiosAceptados.add(auxIntercambio);
        intercambiosAceptados.add(auxIntercambio);
        intercambiosAceptados.add(auxIntercambio);
        List<Exchange> intercambiosSolicitados = new ArrayList<>();
        intercambiosSolicitados.add(auxIntercambio2);
        intercambiosSolicitados.add(auxIntercambio2);

        user.setExchangesOfferor(intercambiosSolicitados);
        user.setExchangesAccepting(intercambiosAceptados);
        userOptional = Optional.of(user);
        when(userRepository.findById(idUser)).thenReturn(userOptional);
        ValidaPuedeIntercambiar200ResponseDto result = userService.validaIntercambio(idUser);
        Assertions.assertEquals(false,result.getPuedeIntercambiar());

        // Caso 6. El usuario tiene menos intercambios aceptados/solicitados de los permitidos
        intercambiosAceptados.remove(0);
        intercambiosSolicitados.remove(0);
        user.setExchangesOfferor(intercambiosSolicitados);
        user.setExchangesAccepting(intercambiosAceptados);
        userOptional = Optional.of(user);
        when(userRepository.findById(idUser)).thenReturn(userOptional);
        result = userService.validaIntercambio(idUser);
        Assertions.assertEquals(true,result.getPuedeIntercambiar());


    }

    @Test
    void solicitaIntercambio() {
        // Caso 1. El cuerpo de la petición es nulo
        Assertions.assertThrows(IllegalArgumentException.class,() ->{
            userService.solicitaIntercambio(1,null);
        });

        // Caso 2. Los id de los usuarios son iguales
        CreacionIntercambioDto creacionIntercambioDto = new CreacionIntercambioDto(1,1);
        Assertions.assertThrows(SelfExchangeException.class,() ->{
            userService.solicitaIntercambio(1,creacionIntercambioDto);
        });

        // Caso 3. Los usuarios no existen
        // 3.1. El usuario que solicita el intercambio no existe
        Integer idUserSolicitante = 1;
        creacionIntercambioDto.setIdAceptante(2);
        when(userRepository.findById(idUserSolicitante)).thenReturn(Optional.empty());
        Assertions.assertThrows(UserNotFoundException.class,() ->{
            userService.solicitaIntercambio(idUserSolicitante,creacionIntercambioDto);
        });

        // 3.2. El usuario que acepta el intercambio no existe
        Integer idUserAceptante = 2;
        when(userRepository.findById(any())).thenReturn(Optional.empty());
        Assertions.assertThrows(UserNotFoundException.class,() ->{
            userService.solicitaIntercambio(idUserSolicitante,creacionIntercambioDto);
        });

     
    }
}
