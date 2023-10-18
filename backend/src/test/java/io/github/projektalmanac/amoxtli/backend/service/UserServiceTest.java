package io.github.projektalmanac.amoxtli.backend.service;

import com.google.api.services.books.model.Volume;
import io.github.projektalmanac.amoxtli.backend.entity.Book;
import io.github.projektalmanac.amoxtli.backend.exception.*;
import io.github.projektalmanac.amoxtli.backend.generated.model.*;
import io.github.projektalmanac.amoxtli.backend.repository.BookRepository;
import io.github.projektalmanac.amoxtli.backend.repository.ExchangeRepository;
import io.github.projektalmanac.amoxtli.backend.service.consume.GoogleBookService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openapitools.jackson.nullable.JsonNullable;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.mail.javamail.JavaMailSender;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.Optional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import io.github.projektalmanac.amoxtli.backend.entity.User;
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

    @Mock
    private GoogleBookService googleBookService;

    @Mock
    private ExchangeRepository exchangeRepository;

    @Mock
    private BookRepository bookRepository;

    String email, password, passwordHash, salt;
    CredencialesDto credencialesDto;
    SessionTokenDto sessionTokenDto;

    SecurityConfig seguridad;


    private User user;
    private User user1;
    private PerfilUsuarioDto perfilUsuarioDto;
    private PerfilUsuarioDto perfilUsuarioDtoCambio;

    private Book book1;

    @BeforeEach
    void setUp() {
        seguridad = new SecurityConfig();
        email = "test@example.com";
        password = "passwordHash";
        salt = SecurityConfig.saltHash();
        passwordHash = SecurityConfig.hashContrasena(password + salt);
        // System.out.println("Password hash -> "+passwordHash);

        // credencialdto
        credencialesDto = new CredencialesDto();
        credencialesDto.setEmail(email);
        credencialesDto.setContrasena(password);

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

        book1 = new Book();
        book1.setId(1);
        book1.setIsbn("1111111111");
        book1.setDescription("Casi nuevo el libro");

    }

    /*@Test
    void iniciarSesion() {
        // Indicación del test que se ejecuta
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
        assertFalse(SecurityConfig.matchContrasena(password.substring(2) + salt, passwRep));
        assertFalse(SecurityConfig.matchContrasena("password" + salt, passwRep));

        // 2. Considere que un usuario ingresa su contraseña correcta
        assertTrue(SecurityConfig.matchContrasena(password + salt, passwRep));
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
        Assertions.assertNotNull(token, "Error: No se pudo generar el token");
    }

    @Test
    void verificaCorreo() {
        int userId = 1;

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

    @Test
    void getUsuario() {

        //Caso 1. El usuario no existe en la base de datos
        Integer id = 0;
        when(userRepository.findById(id)).thenReturn(Optional.empty());
        Assertions.assertThrows(UserNotFoundException.class, () -> {
            userService.getUsuario(id);
        });
        //Caso 2. El usuario existe pero su correo no está verificado
        Integer idUser = 1;
        when(userRepository.findById(idUser)).thenReturn(Optional.of(this.user));
        Assertions.assertThrows(EmailUserNotVerificationException.class, () -> {
            userService.getUsuario(idUser);
        });

    }

    @Test
    void actualizaUsuario() {

        // Caso 1. EL usuario no existe
        Integer idUser = 1;
        when(userRepository.findById(idUser)).thenReturn(Optional.empty());
        Assertions.assertThrows(UserNotFoundException.class, () -> {
            userService.actualizaUsuario(idUser, perfilUsuarioDto);
        });

        // Caso 2. El usuario existe pero su correo no esta autenticado
        Integer idUser1 = 1;
        when(userRepository.findById(idUser)).thenReturn(Optional.of(this.user));
        Assertions.assertThrows(EmailUserNotVerificationException.class, () -> {
            userService.actualizaUsuario(idUser1, perfilUsuarioDto);
        });

        // Caso 3. Se actualizan los cambios correctamente
        Integer id = 2;
        when(userRepository.findById(id)).thenReturn(Optional.of(this.user1));
        when(userRepository.save(any(User.class))).then(returnsFirstArg());
        PerfilUsuarioDto perfil = userService.actualizaUsuario(id, perfilUsuarioDtoCambio);
        Assertions.assertEquals(perfilUsuarioDtoCambio.getApellidos(), perfil.getApellidos());
        Assertions.assertEquals(perfilUsuarioDtoCambio.getTelefono(), perfil.getTelefono());

    }

    @Test
    void actualizaFoto() throws IOException {
        // Representación de una imagen.
        Resource body = new ByteArrayResource(new byte[]{1, 2, 3});
        // Caso 1. El usuario no existe
        when(userRepository.findById(1)).thenReturn(Optional.empty());
        Assertions.assertThrows(UserNotFoundException.class, () -> {
            userService.actualizaFoto(1, body);
        });
        // Caso 2: El correo del usuario verificado
        when(userRepository.findById(1)).thenReturn(Optional.of(this.user));
        Assertions.assertThrows(EmailUserNotVerificationException.class, () -> {
            userService.actualizaFoto(1, body);
        });

        // Caso 3. Se guarda la foto correctamente
        when(userRepository.findById(2)).thenReturn(Optional.of(this.user1));
        when(userRepository.save(this.user1)).thenReturn(this.user1);
        userService.actualizaFoto(2, body);
        Assertions.assertArrayEquals(body.getInputStream().readAllBytes(), this.user1.getPhoto());

    }*/

    @Test
    void getLibrosUsuario() {

        //Caso 1: El usuario no existe
        when(userRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> {
            userService.getLibrosUsuario(1);
        });

        //Caso 2: El usuario existe pero no tiene registrado ningun libro

        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        user.setBooks(new ArrayList<>());

        assertThrows(EmptyResourceException.class, () -> {
            userService.getLibrosUsuario(1);
        });

        //Caso 3:

        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        List<Book> userBooks = new ArrayList<>();
        userBooks.add(book1);

        List<Volume.VolumeInfo> volumeInfoList = new ArrayList<>();

        Volume.VolumeInfo volumeInfo = new Volume.VolumeInfo();

        List<String> authors = Arrays.asList("Autor1", "Autor2", "Autor3");
        volumeInfo.setAuthors(authors);

       // when(googleBookService.searchVolumeInfo(userBooks)).thenReturn(Arrays.asList(/* aquí coloca tus objetos VolumeInfo ficticios */));
    }
}