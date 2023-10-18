package io.github.projektalmanac.amoxtli.backend.service;

import io.github.projektalmanac.amoxtli.backend.entity.Book;
import io.github.projektalmanac.amoxtli.backend.entity.Exchange;
import io.github.projektalmanac.amoxtli.backend.enums.Status;
import io.github.projektalmanac.amoxtli.backend.exception.*;
import io.github.projektalmanac.amoxtli.backend.generated.model.*;
import io.github.projektalmanac.amoxtli.backend.repository.ExchangeRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openapitools.jackson.nullable.JsonNullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.mail.javamail.JavaMailSender;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import io.github.projektalmanac.amoxtli.backend.entity.User;
import io.github.projektalmanac.amoxtli.backend.service.UserService;
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
    ExchangeRepository exchangeRepository;



    private User user;
    private User user1;
    private PerfilUsuarioDto perfilUsuarioDto;
    private PerfilUsuarioDto perfilUsuarioDtoCambio;

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
        ValidaPuedeIntercambiar200ResponseDto result;
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
        result = userService.validaIntercambio(idUser);
        Assertions.assertFalse(result.getPuedeIntercambiar());

        // Caso 3. El usuario no tiene información de contacto
        user.setVerifiedEmail(true);
        user.setPhone(null);
        userOptional = Optional.of(user);
        when(userRepository.findById(idUser)).thenReturn(userOptional);
        result = userService.validaIntercambio(idUser);
        Assertions.assertFalse(result.getPuedeIntercambiar());
        // Caso 4. El usuario no tiene libros registrados

        List<Book> books = new ArrayList<>();
        user.setPhone("58587599");
        user.setBooks(books);
        userOptional = Optional.of(user);
        when(userRepository.findById(idUser)).thenReturn(userOptional);
        result = userService.validaIntercambio(idUser);
        Assertions.assertFalse(result.getPuedeIntercambiar());

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
        result = userService.validaIntercambio(idUser);
        Assertions.assertFalse(result.getPuedeIntercambiar());

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

        //Caso 4. La solicitud de intercambio se realiza correctamente

        User userSolicitante = new User();
        userSolicitante.setId(idUserSolicitante);
        userSolicitante.setVerifiedEmail(true);
        User userAceptante = new User();
        userAceptante.setId(idUserAceptante);
        userAceptante.setVerifiedEmail(true);
        Book book = new Book();
        book.setId(1);
        book.setDescription("Libro de prueba");
        List<Book> books = new ArrayList<>();
        books.add(book);
        userAceptante.setBooks(books);
        Exchange exchange = new Exchange();
        exchange.setId(1);
        exchange.setUserOfferor(userSolicitante);
        exchange.setUserAccepting(userAceptante);
        exchange.setBookAccepting(book);
        exchange.setStatus(Status.PENDIENTE);

        creacionIntercambioDto.setIdAceptante(idUserAceptante);
        when(userRepository.findById(idUserSolicitante)).thenReturn(Optional.of(userSolicitante));
        when(userRepository.findById(idUserAceptante)).thenReturn(Optional.of(userAceptante));
        when(exchangeRepository.save(any(Exchange.class))).thenReturn(exchange);
        IntercambioDto intercambioDto = userService.solicitaIntercambio(idUserSolicitante,creacionIntercambioDto);

        Assertions.assertNotNull(intercambioDto);

        Assertions.assertEquals(intercambioDto.getId(),exchange.getId());
        Assertions.assertEquals(intercambioDto.getOfertante().getId(),userSolicitante.getId());
        Assertions.assertEquals(intercambioDto.getAceptante().getId(),userAceptante.getId());
        Assertions.assertEquals(intercambioDto.getLibroAceptante().getId(),book.getId());


    }
}