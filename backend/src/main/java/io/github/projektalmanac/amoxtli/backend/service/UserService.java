package io.github.projektalmanac.amoxtli.backend.service;

import io.github.projektalmanac.amoxtli.backend.entity.User;
import io.github.projektalmanac.amoxtli.backend.exception.*;
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
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.validation.Valid;
import java.util.Optional;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private JavaMailSender javaMailSender;

    private String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
    private Pattern pattern = Pattern.compile(emailRegex); // igual

    public UsuarioIdDto createuser(UsuarioDto usuario) {

        // Verifica que ningún campo esté vacío
        if (usuario.getNombre().isEmpty() && usuario.getApellidos().isEmpty()
                && usuario.getCorreo().isEmpty() && usuario.getPassword().isEmpty()) {

            throw new InvalidUserException("Todos los campos son obligatorios.");
        }

        Matcher matcher = pattern.matcher(usuario.getCorreo());
        System.out.println("Correo electrónico: " + usuario.getCorreo());
        System.out.println("Matcher.matches(): " + matcher.matches());

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
        usuario1.setLastName(usuario.getApellidos());
        usuario1.setEmail(usuario.getCorreo());
        usuario1.setPasswordHash(hashedPassword);
        usuario1.setPasswordSalt(salt);
        userRepository.save(usuario1);

        UsuarioIdDto usuarioidDto1 = new UsuarioIdDto();
        usuarioidDto1.setId((int) usuario1.getId());

        return usuarioidDto1;
    }

    /*public String generarCodigoVerificacion() {
        int longitud = 6; // tamaño del código de verificación
        StringBuilder codigo = new StringBuilder();

        // Caracteres  en el código de verificación
        String caracteresPermitidos = "0123456789";

        // Generar el código de verificación
        Random random = new Random();
        for (int i = 0; i < longitud; i++) {
            int indice = random.nextInt(caracteresPermitidos.length());
            codigo.append(caracteresPermitidos.charAt(indice));
        }

        return codigo.toString();

    }*/

    public void enviarCorreoVerificacion(int idusuario) throws MessagingException {

        Optional<User> userOpt = userRepository.findById(idusuario);

        if (!userOpt.isPresent()) {

            throw new UserNotFoundException((int) idusuario);

        }
        //genera codigo
        int longitud = 6; // tamaño del código de verificación
        StringBuilder codigo = new StringBuilder();

        // Caracteres  en el código de verificación
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

   /* public void guardarCodigoVerificacion(int idusuario, String codigoVerificacion) {

        Optional<User> userOpt = userRepository.findById(idusuario);

        if (userOpt.isPresent()) {
            User usuario = userOpt.get();
            usuario.setVerificationCode(codigoVerificacion);
            userRepository.save(usuario);

            log.info("Código de verificación antes de almanac: {}", codigoVerificacion);
            log.info(" Código de verificación almacenado en la base de datos:: {}", usuario.getVerificationCode());

        }

    }*/


    public boolean verificaCorreo(long id, @Valid CodigoVerificacionDto codigoVerificacionDto) {

        Optional<User> usuarioOpt = userRepository.findById(id);
        if (!usuarioOpt.isPresent()) {

            throw new UserNotFoundException((int) id);

        }

        User usuario1 = usuarioOpt.get();
        String codigoAlmacenado = usuario1.getVerificationCode();
        String codigoIngresado = codigoVerificacionDto.getCodigo();
        System.out.printf("Código de verificación ingresado: %s\n", codigoVerificacionDto.getCodigo());
        System.out.printf("Código de verificación almacenado en la base de datos: %s\n", usuario1.getVerificationCode());

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

