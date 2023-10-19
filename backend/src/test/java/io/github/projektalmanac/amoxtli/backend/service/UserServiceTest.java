package io.github.projektalmanac.amoxtli.backend.service;

import com.google.api.services.books.model.Volume;
import io.github.projektalmanac.amoxtli.backend.entity.Book;
import io.github.projektalmanac.amoxtli.backend.entity.Exchange;
import io.github.projektalmanac.amoxtli.backend.enums.Status;
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
import java.math.BigDecimal;
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
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

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
    private Book book1, book2;
    private Volume.VolumeInfo volumeInfo;
    private Volume.VolumeInfo.ImageLinks imageLinks;
    private Exchange intercambio, intercambio2;
    private Status status, status2;
    private AceptarIntercambioRequestDto aceptarIntercambio, aceptarIntercambio2, aceptarIntercambio3;

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

        volumeInfo = new Volume.VolumeInfo();
        imageLinks = new Volume.VolumeInfo.ImageLinks();

        volumeInfo.setAuthors(Arrays.asList("Autor1", "Autor2", "Autor3"));
        volumeInfo.setTitle("Titulo 1");
        imageLinks.setMedium("http://books.google.com/books/content?id=O9ztAAAAMAAJ&printsec=frontcover&img=1&zoom=1&source=gbs_api");
        volumeInfo.setImageLinks(imageLinks);
        volumeInfo.setCategories(Arrays.asList("Categoria1", "Categoria2"));
        volumeInfo.setPublisher("Editorial 1");
        volumeInfo.setDescription("Sinopsis del libro");
        volumeInfo.setLanguage("Español");
        volumeInfo.setPublishedDate("1990");

        status = Status.PENDIENTE;
        status2 = Status.ACEPTADO;

        intercambio = new Exchange();
        intercambio.setId(1);
        intercambio.setStatus(status);
        intercambio.setBookAccepting(book1);
        intercambio.setUserAccepting(user1);
        intercambio.setUserOfferor(user);
        intercambio.setBookOfferor(null);

        aceptarIntercambio = new AceptarIntercambioRequestDto();
        aceptarIntercambio.setIdLibro(new BigDecimal(1));

        aceptarIntercambio2 = new AceptarIntercambioRequestDto();
        aceptarIntercambio2.setIdLibro(null);

        aceptarIntercambio3 = new AceptarIntercambioRequestDto();
        aceptarIntercambio3.setIdLibro(new BigDecimal(2));

        book2 = new Book();
        book2.setId(2);
        book2.setIsbn("2222222222");
        book2.setDescription("Casi nuevo el libro");

        intercambio2 = new Exchange();
        intercambio2.setId(1);
        intercambio2.setStatus(status2);
        intercambio2.setBookAccepting(book1);
        intercambio2.setUserAccepting(user1);
        intercambio2.setUserOfferor(user);
        intercambio2.setBookOfferor(book2);
    }

    @Test
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

        UsuarioIdDto result = userService.createUser(usuarioDto);

        assertNotNull(result);

        ////// ingresa un correo que ya existe./////////////
        UsuarioDto usuarioDto1 = new UsuarioDto();
        usuarioDto1.setNombre("Axel");
        usuarioDto1.setApellildos("Guzman");
        usuarioDto1.setCorreo("prueba1@example.com");
        usuarioDto1.setPassword("12345");

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(new User()));

        assertThrows(EmailAlreadyExistsException.class, () -> {
            userService.createUser(usuarioDto1);
        });

        ////// ingresa un correo con la sintaxis erronea:///////////////
        UsuarioDto usuarioDto2 = new UsuarioDto();
        usuarioDto2.setNombre("Miguel");
        usuarioDto2.setApellildos("Ortega");
        usuarioDto2.setCorreo("holaamigo.com");
        usuarioDto2.setPassword("12345");

        assertThrows(InvalidEmailFormatException.class, () -> {
            userService.createUser(usuarioDto2);
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
        Integer userId = 1;

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
        Assertions.assertThrows(UserNotFoundException.class,() ->{
            userService.getUsuario(id);
        });
        //Caso 2. El usuario existe, pero su correo no está verificado
        Integer idUser = 1;
        when(userRepository.findById(idUser)).thenReturn(Optional.of(this.user));
        Assertions.assertThrows(EmailUserNotVerificationException.class,() ->{
            userService.getUsuario(idUser);
        });

    }

    @Test
    void actualizaUsuario() {

        // Caso 1. EL usuario no existe
        Integer idUser = 1;
        when(userRepository.findById(idUser)).thenReturn(Optional.empty());
        Assertions.assertThrows(UserNotFoundException.class,() ->{
            userService.actualizaUsuario(idUser,perfilUsuarioDto);
        });

        // Caso 2. El usuario existe, pero su correo no está verificado
        Integer idUser1 = 1;
        when(userRepository.findById(idUser1)).thenReturn(Optional.of(this.user));
        Assertions.assertThrows(EmailUserNotVerificationException.class,() ->{
            userService.actualizaUsuario(idUser1,perfilUsuarioDto);
        });

        // Caso 3. Se actualizan los cambios correctamente
        Integer id = 2;
        when(userRepository.findById(id)).thenReturn(Optional.of(this.user1));
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
        when(userRepository.findById(1)).thenReturn(Optional.empty());
        Assertions.assertThrows(UserNotFoundException.class,() ->{
            userService.actualizaFoto(1,body);
        });
        // Caso 2: El correo del usuario verificado
        when(userRepository.findById(1)).thenReturn(Optional.of(this.user));
        Assertions.assertThrows(EmailUserNotVerificationException.class,() ->{
            userService.actualizaFoto(1,body);
        });

        // Caso 3. Se guarda la foto correctamente
        when(userRepository.findById(2)).thenReturn(Optional.of(this.user1));
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

        // Caso 2. El usuario existe, pero su correo no está verificado
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

    @Test
    void getLibrosUsuario() {

        //Caso 1: El usuario no existe
        when(userRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> {
            userService.getLibrosUsuario(1);
        });

        //Caso 2: El usuario existe, pero no tiene registrado ningún libro

        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        user.setBooks(new ArrayList<>());

        assertThrows(EmptyResourceException.class, () -> {
            userService.getLibrosUsuario(1);
        });

        //Caso 3: Caso de éxito en la obtención de los libros de un usuario

        List<Book> userBooks = new ArrayList<>();
        userBooks.add(book1);
        user.setBooks(userBooks);

        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        List<Volume.VolumeInfo> volumeInfoList = new ArrayList<>();
        volumeInfoList.add(volumeInfo);

        when(googleBookService.searchVolumeInfo(userBooks)).thenReturn(volumeInfoList);

        LibrosUsuarioDto result = userService.getLibrosUsuario(1);

        Assertions.assertEquals(user.getBooks().size(), result.getLibros().size());
        Assertions.assertEquals(userBooks.get(0).getId(), result.getLibros().get(0).getId());
        Assertions.assertEquals(userBooks.get(0).getIsbn(), result.getLibros().get(0).getIsbn());
        Assertions.assertEquals(volumeInfo.getAuthors().get(0), result.getLibros().get(0).getAutor());
        Assertions.assertEquals(volumeInfo.getTitle(), result.getLibros().get(0).getTitulo());
        Assertions.assertEquals(URI.create(imageLinks.getMedium()), result.getLibros().get(0).getUrlPortada());
    }

    @Test
    void getIntercambios(){

        //Caso 1: El usuario no existe
        when(userRepository.findById(3)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> {
            userService.getIntercambios(3);
        });

        //Caso 2: El usuario existe pero no tiene registrado ningun intercambio
        user.setExchangesOfferor(new ArrayList<>());
        user.setExchangesAccepting(new ArrayList<>());
        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        assertThrows(EmptyResourceException.class, () -> {
            userService.getIntercambios(1);
        });

        //Caso 3: El usuario existe y tiene registrados intercambios
        List<Exchange> exchangesOfferor =  new ArrayList<>();
        exchangesOfferor.add(intercambio);
        user1.setExchangesOfferor(exchangesOfferor);
        user1.setExchangesAccepting(new ArrayList<>());
        when(userRepository.findById(2)).thenReturn(Optional.of(user1));

        GetIntercambios200ResponseDto result = userService.getIntercambios(2);

        Assertions.assertEquals(intercambio.getId(), result.getIntercambios().get(0).getId());
        Assertions.assertEquals(intercambio.getUserOfferor().getId(), result.getIntercambios().get(0).getOfertante().getId());
        Assertions.assertEquals(intercambio.getUserOfferor().getName(), result.getIntercambios().get(0).getOfertante().getNombre());
        Assertions.assertEquals(intercambio.getUserOfferor().getLastName(), result.getIntercambios().get(0).getOfertante().getApellidos());
        Assertions.assertEquals(intercambio.getUserOfferor().getPhone(), result.getIntercambios().get(0).getOfertante().getTelefono());
        Assertions.assertEquals(intercambio.getUserOfferor().getEmail(), result.getIntercambios().get(0).getOfertante().getCorreo());
        Assertions.assertEquals(intercambio.getUserAccepting().getId(), result.getIntercambios().get(0).getAceptante().getId());
        Assertions.assertEquals(intercambio.getUserAccepting().getName(), result.getIntercambios().get(0).getAceptante().getNombre());
        Assertions.assertEquals(intercambio.getUserAccepting().getLastName(), result.getIntercambios().get(0).getAceptante().getApellidos());
        Assertions.assertEquals(intercambio.getUserAccepting().getPhone(), result.getIntercambios().get(0).getAceptante().getTelefono());
        Assertions.assertEquals(intercambio.getUserAccepting().getEmail(), result.getIntercambios().get(0).getAceptante().getCorreo());
        Assertions.assertEquals(intercambio.getBookAccepting().getId(), result.getIntercambios().get(0).getLibroAceptante().getId());
        Assertions.assertEquals(intercambio.getBookAccepting().getIsbn(), result.getIntercambios().get(0).getLibroAceptante().getIsbn());
        Assertions.assertEquals(intercambio.getBookAccepting().getDescription(), result.getIntercambios().get(0).getLibroAceptante().getDescripcion());
        Assertions.assertEquals(JsonNullable.of(intercambio.getBookOfferor()), result.getIntercambios().get(0).getLibroOfertante());
        Assertions.assertEquals(intercambio.getStatus().getStatus(), result.getIntercambios().get(0).getEstado().getValue());

    }

    @Test
    void aceptarIntercambio(){

        //Caso 1: El usuario no existe
        when(userRepository.findById(3)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> {
            userService.aceptarIntercambio(3, 1, aceptarIntercambio);
        });

        //Caso 2: El intercambio no existe o no esta relacionado con el usuario

        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(exchangeRepository.findByIdAndUserAccepting(3,user)).thenReturn(Optional.empty());
        assertThrows(IntercambioNotFoundException.class, () -> {
            userService.aceptarIntercambio(1, 3, aceptarIntercambio);
        });

        //Caso 3: El id del libro es inválido

        when(exchangeRepository.findByIdAndUserAccepting(2,user)).thenReturn(Optional.of(intercambio));
        assertThrows(InvalidIdException.class, () -> {
            userService.aceptarIntercambio(1, 2, aceptarIntercambio2);
        });

        //CASO 4: El libro no existe

        when(bookRepository.findById(1)).thenReturn(null);
        assertThrows(ResourceNotFoundException.class, () -> {
            userService.aceptarIntercambio(1, 2, aceptarIntercambio);
        });

        //Caso 5: Caso de éxito

        when(userRepository.findById(2)).thenReturn(Optional.of(user1));
        when(exchangeRepository.findByIdAndUserAccepting(2,user1)).thenReturn(Optional.of(intercambio));
        when(bookRepository.findById(2)).thenReturn(book1);
        when(exchangeRepository.save(intercambio)).thenReturn(intercambio2);
        when(userRepository.save(user1)).thenReturn(user1);

        IntercambioDto result = userService.aceptarIntercambio(2, 2, aceptarIntercambio3);

        Assertions.assertEquals(intercambio2.getId(), result.getId());
        Assertions.assertEquals(intercambio2.getUserOfferor().getId(), result.getOfertante().getId());
        Assertions.assertEquals(intercambio2.getUserOfferor().getName(), result.getOfertante().getNombre());
        Assertions.assertEquals(intercambio2.getUserOfferor().getLastName(), result.getOfertante().getApellidos());
        Assertions.assertEquals(intercambio2.getUserOfferor().getPhone(), result.getOfertante().getTelefono());
        Assertions.assertEquals(intercambio2.getUserOfferor().getEmail(), result.getOfertante().getCorreo());
        Assertions.assertEquals(intercambio2.getUserAccepting().getId(), result.getAceptante().getId());
        Assertions.assertEquals(intercambio2.getUserAccepting().getName(), result.getAceptante().getNombre());
        Assertions.assertEquals(intercambio2.getUserAccepting().getLastName(), result.getAceptante().getApellidos());
        Assertions.assertEquals(intercambio2.getUserAccepting().getPhone(), result.getAceptante().getTelefono());
        Assertions.assertEquals(intercambio2.getUserAccepting().getEmail(), result.getAceptante().getCorreo());
        Assertions.assertEquals(intercambio2.getBookAccepting().getId(), result.getLibroAceptante().getId());
        Assertions.assertEquals(intercambio2.getBookAccepting().getIsbn(), result.getLibroAceptante().getIsbn());
        Assertions.assertEquals(intercambio2.getBookAccepting().getDescription(), result.getLibroAceptante().getDescripcion());
        Assertions.assertEquals(intercambio2.getBookOfferor().getId(), result.getLibroOfertante().get().getId());
        Assertions.assertEquals(intercambio2.getBookOfferor().getIsbn(), result.getLibroOfertante().get().getIsbn());
        Assertions.assertEquals(intercambio2.getBookOfferor().getDescription(), result.getLibroOfertante().get().getDescripcion());
        Assertions.assertEquals(intercambio2.getStatus().getStatus(), result.getEstado().getValue());

    }

    @Test
    void getIntercambioTest() {

        // Caso 1: El usuario no existe

        // Arrange
        when(userRepository.findById(1)).thenReturn(Optional.empty());

        // Assert
        assertThrows(UserNotFoundException.class, () -> {
            // Act
            userService.getIntercambio(1, 1);
        });

        // Caso 2: El usuario no tiene un intercambio con el ID dado

        // Arrange
        var usuario = new User();
        user.setId(1);

        when(userRepository.findById(1)).thenReturn(Optional.of(usuario));

        // Assert
        assertThrows(IntercambioNotFoundException.class, () -> {
            // Act
            userService.getIntercambio(1, 1);
        });

        // Caso 3: El usuario tiene el intercambio como aceptante

        // Arrange
        var intercambio = new Exchange();
        intercambio.setId(1);
        intercambio.setUserAccepting(usuario);

        usuario.getExchangesAccepting().add(intercambio);

        when(userRepository.findById(1)).thenReturn(Optional.of(usuario));

        // Act
        var result = userService.getIntercambio(1, 1);

        // Assert
        assertEquals(intercambio.getId(), result.getId());

        // Caso 4: El usuario tiene el intercambio como ofertante

        // Arrange
        intercambio.setUserAccepting(null);
        intercambio.setUserOfferor(usuario);
        usuario.getExchangesOfferor().add(intercambio);
        usuario.getExchangesAccepting().clear();

        when(userRepository.findById(1)).thenReturn(Optional.of(usuario));

        // Act
        result = userService.getIntercambio(1, 1);

        // Assert
        assertEquals(intercambio.getId(), result.getId());
    }
}
