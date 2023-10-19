package io.github.projektalmanac.amoxtli.backend.service;


import com.google.api.services.books.model.Volume.VolumeInfo;
import io.github.projektalmanac.amoxtli.backend.config.SecurityConfig;
import io.github.projektalmanac.amoxtli.backend.entity.Book;
import io.github.projektalmanac.amoxtli.backend.entity.Exchange;
import io.github.projektalmanac.amoxtli.backend.entity.User;
import io.github.projektalmanac.amoxtli.backend.enums.Status;
import io.github.projektalmanac.amoxtli.backend.exception.*;
import io.github.projektalmanac.amoxtli.backend.generated.model.*;
import io.github.projektalmanac.amoxtli.backend.mapper.BookMapper;
import io.github.projektalmanac.amoxtli.backend.mapper.ExchangeMapper;
import io.github.projektalmanac.amoxtli.backend.mapper.UserMapper;
import io.github.projektalmanac.amoxtli.backend.repository.BookRepository;
import io.github.projektalmanac.amoxtli.backend.repository.ExchangeRepository;
import io.github.projektalmanac.amoxtli.backend.repository.UserRepository;
import io.github.projektalmanac.amoxtli.backend.service.consume.GoogleBookService;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.io.Resource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j

@Service
public class UserService extends UserServiceKt {
    private final UserRepository userRepository;
    private ExchangeRepository exchangeRepository;
    private GoogleBookService googleBookService;
    private final JavaMailSender javaMailSender;

    private static final String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
    private final Pattern pattern = Pattern.compile(EMAIL_REGEX);

    public UserService(
            UserRepository userRepository,
            ExchangeRepository exchangeRepository,
            GoogleBookService googleBookService,
            JavaMailSender javaMailSender,
            BookRepository bookRepository
    ) {
        super(userRepository, exchangeRepository, bookRepository);
        this.userRepository = userRepository;
        this.exchangeRepository = exchangeRepository;
        this.googleBookService = googleBookService;
        this.javaMailSender = javaMailSender;
    }

    public LibrosUsuarioDto getLibrosUsuario(Integer id) {

        Optional<User> userOpt = userRepository.findById(id);

        if (userOpt.isEmpty()) throw new UserNotFoundException(id);

        User user = userOpt.get();
        List<Book> books = user.getBooks();

        if (books.isEmpty()) throw new EmptyResourceException();

        List<VolumeInfo> librosGoogleBooks = googleBookService.searchVolumeInfo(books);

        return BookMapper.INSTANCE.toLibrosUsuarioDto(books, librosGoogleBooks);
    }

    public String generadorToken(long idUsuario) {
        return SecurityConfig.generadorToken(String.valueOf(idUsuario));
    }

    public String infoSesion(String token) {
        return SecurityConfig.decodificarToken(token).getSubject();
    }


    public SessionTokenDto createUser(UsuarioDto usuario) {

        // Verifica que ningún campo esté vacío
        if (usuario.getNombre().isEmpty()
            || usuario.getApellildos().isEmpty()
            || usuario.getCorreo().isEmpty()
            || usuario.getPassword().isEmpty()
        )
            throw new InvalidUserException("Todos los campos son obligatorios.");

        Matcher matcher = pattern.matcher(usuario.getCorreo());

        if (!matcher.matches())
            throw new InvalidEmailFormatException("El formato del correo electrónico no es válido.");

        // Regla de negocio: No se puede crear más de un usuario con el mismo correo
        Optional<User> userOpt = userRepository.findByEmail(usuario.getCorreo());

        if (userOpt.isPresent()) throw new EmailAlreadyExistsException("El correo electrónico ya está registrado.");

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);
        String salt = BCrypt.gensalt(12); // generando un salt con un factor de trabajo de 12
        String hashedPassword = passwordEncoder.encode(usuario.getPassword() + salt);

        //pasar de DTO a entidad,
        User usuario1 = new User();
        usuario1.setName(usuario.getNombre());
        usuario1.setLastName(usuario.getApellildos());
        usuario1.setEmail(usuario.getCorreo());
        usuario1.setPasswordHash(hashedPassword);
        usuario1.setPasswordSalt(salt);
        usuario1 = userRepository.save(usuario1);

        var userId = usuario1.getId();
        var sessionToken = new SessionTokenDto(userId, generadorToken(userId));
        generadorToken(usuario1.getId());

        return sessionToken;
    }


    public void enviarCorreoVerificacion(Integer idusuario) throws MessagingException {

        Optional<User> userOpt = userRepository.findById(idusuario);

        if (userOpt.isEmpty()) throw new UserNotFoundException(idusuario);

        String codigo = getCodigo();

        User usuario = userOpt.get();

        usuario.setVerificationCode(codigo);
        userRepository.save(usuario);

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(usuario.getEmail());
        helper.setSubject("Código de Verificación");
        helper.setText("Su código de verificación es: " + codigo);

        javaMailSender.send(message);
    }

    @NotNull
    private static String getCodigo() {
        int longitud = 6; // tamaño del código de verificación
        StringBuilder codigo = new StringBuilder();
        // Caracteres en el código de verificación
        String caracteresPermitidos = "0123456789";

        // Generar el código de verificación
        Random random = new Random();
        for (int i = 0; i < longitud; i++) {
            int indice = random.nextInt(caracteresPermitidos.length());
            codigo.append(caracteresPermitidos.charAt(indice));
        }
        return codigo.toString();
    }

    public PerfilUsuarioDto getUsuario(Integer id) {

        Optional<User> userOpt = this.userRepository.findById(id);
        if (userOpt.isEmpty()) throw new UserNotFoundException(id);

        User user = userOpt.get();
        if (!user.isVerifiedEmail()) throw new EmailUserNotVerificationException(id);

        return UserMapper.INSTANCE.userToUserDto(user);
    }

    public PerfilUsuarioDto actualizaUsuario(Integer idUser, PerfilUsuarioDto perfilUsuarioDto) {

        Optional<User> userOpt = this.userRepository.findById(idUser);

        if (userOpt.isEmpty()) throw new UserNotFoundException(idUser);

        User user = userOpt.get();

        if (!user.isVerifiedEmail()) throw new EmailUserNotVerificationException(idUser);

        User userAux = UserMapper.INSTANCE.usuarioDtoToUser(perfilUsuarioDto);
        userAux.setId(idUser);
        userAux.setPhoto(user.getPhoto());
        user = this.userRepository.save(userAux);

        return UserMapper.INSTANCE.userToUserDtoWithoutPhoto(user);
    }

    public void actualizaFoto(Integer idUser, Resource body) throws IOException {

        Optional<User> userOpt = this.userRepository.findById(idUser);

        if (userOpt.isEmpty()) throw new UserNotFoundException(idUser);

        User user = userOpt.get();

        if (!user.isVerifiedEmail()) throw new EmailUserNotVerificationException(idUser);

        byte[] imagen = body.getInputStream().readAllBytes();

        user.setPhoto(imagen);

        this.userRepository.save(user);
    }

    public boolean verificaCorreo(Integer id, @Valid CodigoVerificacionDto codigoVerificacionDto) {

        Optional<User> usuarioOpt = userRepository.findById(id);
        if (usuarioOpt.isEmpty()) throw new UserNotFoundException(id);

        User usuario1 = usuarioOpt.get();
        String codigoAlmacenado = usuario1.getVerificationCode();
        String codigoIngresado = codigoVerificacionDto.getCodigo();

        // Verifica si codigoAlmacenado es null antes de realizar la comparación
        if (codigoAlmacenado == null || !codigoAlmacenado.equals(codigoIngresado))
            throw new InvalidEmailFormatException("El código ingresado es erróneo");

        usuario1.setVerifiedEmail(true);
        userRepository.save(usuario1);
        return true;
    }

    /**
     * Método encargado de validar un inicio de sesión con las credenciales
     * asociadas al usuario capturado en el frontend,
     * realiza el proceso de autenticación
     *
     * @param credenciales: Es el DTO de las credenciales, contiene Usuario y Password
     * @return sessionTokenDto or null
     */
    public SessionTokenDto iniciarSesion(CredencialesDto credenciales) { //hash password
        /*
         * Pre proceso: Se debe antes que nada, realizar un hash sobre
         * la contraseña contenida en el DTO de credenciales,
         * para lo cual se tiene una variable local:
         * credencialPass, es de tipo String por que el mecanismo de hasheo hashContrasen(String),
         * planteado en seguridad es de tipo String.
         */

        /*
         * Reglas de negocio:
         * 1- Usuario previamente registrado
         *  Para ello usamos el email del usuario y mediante una consulta al repositorio con findByEmail
         *  Si hay coincidencia pasamos a la segunda regla de validación
         *  Si no, se retorna un null
         * 2- Autenticación de usuario
         *  Se valida que la contraseña sea la correcta, para lo cual se hace un Match con la hash de la
         *  contraseña, guarda durante el proceso de registro de usuario, para lo que usamos el mecanismo de
         *  matchContraseña(String,String), que se planteó en seguridad y que nos retorna
         *  true o false, si hay coincidencia.
         * Cuando no hay coincidencia se retorna un null, pero en caso contrario se retorna el DTO de sessionToken que
         * contiene el ID y token asociados al usuario autenticado.
         */

        //regla de negocio 1
        Optional<User> usuario = userRepository.findByEmail(credenciales.getEmail());
        if (usuario.isEmpty())
            throw new InvalidUserSessionException("Este correo no esta asociado a ninguna cuenta, intente registrarse.");
        //regla de negocio 2
        if (!SecurityConfig.matchContrasena(credenciales.getContrasena() + usuario.get().getPasswordSalt(), usuario.get().getPasswordHash()))
            throw new InvalidUserSessionException("Contrasenia incorrecta.");
        //creamos el DTO de SessionTokenDto con el dato de ID usuario y generamos un token
        int id = usuario.get().getId();
        return new SessionTokenDto(id, generadorToken(id));
    }

    public ValidaPuedeIntercambiar200ResponseDto validaIntercambio(Integer idUser) {

        var response = new ValidaPuedeIntercambiar200ResponseDto();
        response.setPuedeIntercambiar(false);

        Optional<User> userOpt = userRepository.findById(idUser);
        if (userOpt.isEmpty()) {
            throw new UserNotFoundException(idUser);
        }
        User user = userOpt.get();
        if (!user.isVerifiedEmail()) {
            response.mensaje("No cuentas con un correo verificado.");
            return response;
        }

        if (user.getPhone() == null || user.getPhone().isEmpty()) {
            response.mensaje("No cuenta con información de contacto.");
            return response;
        }

        if (user.getBooks().isEmpty()) {
            response.mensaje("No cuentas con libros registrados.");
            return response;
        }

        if (user.getExchangesAccepting().size() + user.getExchangesOfferor().size() >= 4 ) {
            response.mensaje("No puedes aceptar o solicitar más de 4 intercambios.");
            return response;
        }

        response.setPuedeIntercambiar(true);

        return response;
    }

    public IntercambioDto solicitaIntercambio(Integer idOfertante, CreacionIntercambioDto creacionIntercambioDto) {

        if(creacionIntercambioDto == null){
            throw new IllegalArgumentException("El cuerpo de la petición no puede ser nulo.");
        }
        // Recuperamos la información del usuario y del libro que recibe el intercambio
        Integer idAceptante = creacionIntercambioDto.getIdAceptante();
        Integer idLibroAceptante = creacionIntercambioDto.getIdLibroAceptante();

        if (idOfertante.equals(idAceptante)) {
            throw new SelfExchangeException(idOfertante);
        }

        // Obtenemos a los usuarios y al libro que se intercambiará

        User userOfertante = this.userRepository.findById(idOfertante).orElseThrow(() -> new UserNotFoundException(idOfertante));
        User userAceptante = this.userRepository.findById(idAceptante).orElseThrow(() -> new UserNotFoundException(idAceptante));

        // Extraemos el libro que se intercambiará de parte del usuario aceptante
        List<Book> libros = userAceptante.getBooks();
        Book book = libros.stream()
                .filter(libro -> libro.getId() == idLibroAceptante)
                .findFirst()
                .orElseThrow(() -> new BookNotFoundException("El libro no fue encontrado."));

        Exchange exchange = new Exchange();

        exchange.setStatus(Status.PENDIENTE);
        exchange.setUserOfferor(userOfertante);
        exchange.setUserAccepting(userAceptante);
        exchange.setBookAccepting(book);

        exchange = this.exchangeRepository.save(exchange);

        List<Exchange> exchangesOfertante = userOfertante.getExchangesOfferor();
        exchangesOfertante.add(exchange);

        userOfertante.setExchangesOfferor(exchangesOfertante);
        this.userRepository.save(userOfertante);

        // Se hace el mapeo de las entidades
        return  ExchangeMapper.INSTANCE.toIntercambioDto(exchange);

    }
}

