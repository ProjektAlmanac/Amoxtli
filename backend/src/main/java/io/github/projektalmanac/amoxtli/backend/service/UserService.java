package io.github.projektalmanac.amoxtli.backend.service;

import io.github.projektalmanac.amoxtli.backend.entity.User;
import io.github.projektalmanac.amoxtli.backend.exception.EmailAlreadyExistsException;
import io.github.projektalmanac.amoxtli.backend.exception.InvalidEmailFormatException;
import io.github.projektalmanac.amoxtli.backend.exception.InvalidUserException;
import io.github.projektalmanac.amoxtli.backend.exception.UserNotFoundException;
import io.github.projektalmanac.amoxtli.backend.generated.model.CodigoVerificacionDto;
import io.github.projektalmanac.amoxtli.backend.generated.model.UsuarioDto;
import io.github.projektalmanac.amoxtli.backend.generated.model.UsuarioIdDto;
import io.github.projektalmanac.amoxtli.backend.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Optional;

import com.google.api.services.books.model.Volume.VolumeInfo;
import io.github.projektalmanac.amoxtli.backend.entity.*;
import io.github.projektalmanac.amoxtli.backend.generated.model.*;
import io.github.projektalmanac.amoxtli.backend.mapper.BookMapper;
import io.github.projektalmanac.amoxtli.backend.service.consume.GoogleBookService;
import org.springframework.stereotype.Service;

import io.github.projektalmanac.amoxtli.backend.exception.*;
import io.github.projektalmanac.amoxtli.backend.mapper.UserMapper;
import org.springframework.core.io.Resource;

import java.io.IOException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.validation.Valid;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Service
public class UserService {
    private UserRepository userRepository;
    private GoogleBookService googleBookService;
    private JavaMailSender javaMailSender;

    private String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
    private Pattern pattern = Pattern.compile(emailRegex);

    @Autowired
    public UserService(UserRepository userRepository, GoogleBookService googleBookService, JavaMailSender javaMailSender) {
        this.userRepository = userRepository;
        this.googleBookService = googleBookService;
        this.javaMailSender = javaMailSender;
    }

    public LibrosUsuarioDto getLibrosUsuario(Integer id) {

        Optional<User> userOpt = userRepository.findById(id);

        if (userOpt.isEmpty()) {
            throw new UserNotFoundException(id);
        }

        User user = userOpt.get();
        List<Book> books = user.getBooks();

        if (books.isEmpty()) {
            throw new EmptyResourceException();
        }

        GoogleBookService googleBookService = new GoogleBookService();

        List<VolumeInfo> librosGoogleBooks = googleBookService.searchVolumeInfo(books);

        LibrosUsuarioDto booksDto = BookMapper.INSTANCE.toLibrosUsuarioDto(books, librosGoogleBooks);

        return booksDto;

    }

    public UsuarioIdDto createuser(UsuarioDto usuario) {

        // Verifica que ningún campo esté vacío
        if (usuario.getNombre() != null && !usuario.getNombre().isEmpty() && usuario.getApellildos() != null &&
                !usuario.getApellildos().isEmpty() && usuario.getCorreo() != null && !usuario.getCorreo().isEmpty()
                && usuario.getPassword() != null && !usuario.getPassword().isEmpty()) {

            Matcher matcher = pattern.matcher(usuario.getCorreo());

            if (!matcher.matches()) {
                throw new InvalidEmailFormatException("El formato del correo electrónico no es válido.");
            }

            // Regla de negocio: No se puede crear más de un usuario con el mismo correo
            Optional<User> userOpt = userRepository.findByEmail(usuario.getCorreo());

            if (userOpt.isPresent()) {

                throw new EmailAlreadyExistsException("El correo electrónico ya está registrado.");

            }
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
            userRepository.save(usuario1);

            UsuarioIdDto usuarioidDto1 = new UsuarioIdDto();
            usuarioidDto1.setId(usuario1.getId());

            return usuarioidDto1;

        }

        throw new InvalidUserException("Todos los campos son obligatorios.");

    }


    public void enviarCorreoVerificacion(int idusuario) throws MessagingException {

        Optional<User> userOpt = userRepository.findById(idusuario);

        if (userOpt.isEmpty()) {

            throw new UserNotFoundException(idusuario);

        }
        //genera codigo
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
        ///fin de genera codigo

        User usuario = userOpt.get();

        usuario.setVerificationCode(String.valueOf(codigo));
        userRepository.save(usuario);

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(usuario.getEmail());
        helper.setSubject("Código de Verificación");
        helper.setText("Su código de verificación es: " + codigo);

        javaMailSender.send(message);
    }

    public PerfilUsuarioDto getUsuario(Integer id) {

        User user = this.userRepository.getUserById(id);
        if (user == null) {
            throw new UserNotFoundException(id);
        }
        if (!user.isVerifiedEmail()) {
            throw new EmailUserNotVerificationException(id);
        }

        return UserMapper.INSTANCE.userToUserDto(user);
    }

    public PerfilUsuarioDto actualizaUsuario(Integer idUser, PerfilUsuarioDto perfilUsuarioDto) {

        User user = this.userRepository.getUserById(idUser);

        if (user == null) {
            throw new UserNotFoundException(idUser);
        }
        if (!user.isVerifiedEmail()) {
            throw new EmailUserNotVerificationException(idUser);
        }

        User userAux = UserMapper.INSTANCE.usuarioDtoToUser(perfilUsuarioDto);
        userAux.setId(idUser);
        userAux.setPhoto(user.getPhoto());
        user = this.userRepository.save(userAux);

        return UserMapper.INSTANCE.userToUserDtoWithoutPhoto(user);
    }

    public void actualizaFoto(Integer idUser, Resource body) throws IOException {

        User user = this.userRepository.getUserById(idUser);
        if (user == null) {
            throw new UserNotFoundException(idUser);
        }
        if (!user.isVerifiedEmail()) {
            throw new EmailUserNotVerificationException(idUser);
        }

        byte[] imagen = body.getInputStream().readAllBytes();

        user.setPhoto(imagen);

        this.userRepository.save(user);
    }

    public boolean verificaCorreo(long id, @Valid CodigoVerificacionDto codigoVerificacionDto) {

        Optional<User> usuarioOpt = userRepository.findById(id);
        if (!usuarioOpt.isPresent()) {

            throw new UserNotFoundException((int) id);

        }

        User usuario1 = usuarioOpt.get();
        String codigoAlmacenado = usuario1.getVerificationCode();
        String codigoIngresado = codigoVerificacionDto.getCodigo();


        // Verifica si codigoAlmacenado es null antes de realizar la comparación
        if (codigoAlmacenado != null && codigoAlmacenado.equals(codigoIngresado)) {
            // Código de verificación correcto
            usuario1.setVerifiedEmail(true);
            userRepository.save(usuario1);
            return true;
        } else {
            throw new InvalidEmailFormatException("El codigo ingresado es erroneo");
        }
    }

}