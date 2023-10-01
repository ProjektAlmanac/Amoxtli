package io.github.projektalmanac.amoxtli.backend.service;

import io.github.projektalmanac.amoxtli.backend.entity.User;
import io.github.projektalmanac.amoxtli.backend.exception.UserNotFoundException;
import io.github.projektalmanac.amoxtli.backend.generated.model.CodigoVerificacionDto;
import io.github.projektalmanac.amoxtli.backend.generated.model.UsuarioDto;
import io.github.projektalmanac.amoxtli.backend.generated.model.UsuarioIdDto;
import io.github.projektalmanac.amoxtli.backend.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.validation.Valid;
import java.util.Optional;
import java.util.Random;
@Slf4j
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private JavaMailSender javaMailSender;


    public UsuarioIdDto createuser(UsuarioDto usuario) {

        // Verifica que ningún campo esté vacío
        if (usuario.getNombre() != null && !usuario.getNombre().isEmpty() &&
                usuario.getApellidos() != null && !usuario.getApellidos().isEmpty() &&
                usuario.getCorreo() != null && !usuario.getCorreo().isEmpty() &&
                usuario.getPassword() != null && !usuario.getPassword().isEmpty()) {


            // Regla de negocio: No se puede crear más de un usuario con el mismo correo
            Optional<User> userOpt = userRepository.findByEmail(usuario.getCorreo());

            if (!userOpt.isPresent()) {

                //pasar de DTO a entidad,
                User usuario1 = new User();
                usuario1.setName(usuario.getNombre());
                usuario1.setLastName(usuario.getApellidos());
                usuario1.setEmail(usuario.getCorreo());
                usuario1.setPasswordHash(usuario.getPassword());
                userRepository.save(usuario1);

                UsuarioIdDto usuarioidDto1 = new UsuarioIdDto();
                usuarioidDto1.setId((int) usuario1.getId());

                return usuarioidDto1;

            } else {
                return null;
            }
        } else {
            // Al menos uno de los campos está vacío
            return null;
        }
    }

    public String obtenerCorreoPorId(long id) {
        Optional<User> userOpt = userRepository.findById(id);
        return userOpt.get().getEmail();
    }


    public String generarCodigoVerificacion() {
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

    }

    public void enviarCorreoVerificacion(String correoUsuario, String codigoVerificacion) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(correoUsuario);
        helper.setSubject("Código de Verificación");
        helper.setText("Su código de verificación es: " + codigoVerificacion);

        javaMailSender.send(message);
    }

    public void guardarCodigoVerificacion(long id, String codigoVerificacion) {

        Optional<User> userOpt = userRepository.findById(id);

        if (userOpt.isPresent()) {
            User usuario = userOpt.get();
            usuario.setVerificationCode(codigoVerificacion);
            userRepository.save(usuario);

            log.info("Código de verificación antes de almacenar: {}",codigoVerificacion);
            log.info(" Código de verificación almacenado en la base de datos:: {}",usuario.getVerificationCode());;

        } else {
            throw new UserNotFoundException("Usuario con ID " + id + " no encontrado");
        }

    }


    public boolean verificaCorreo(long id, @Valid CodigoVerificacionDto codigoVerificacionDto) {

        Optional<User> usuarioOpt = userRepository.findById(id);
        if (usuarioOpt.isPresent()) {
            User usuario1 = usuarioOpt.get();
            String codigoAlmacenado = usuario1.getVerificationCode();
            String codigoIngresado = codigoVerificacionDto.getCodigo();
            System.out.printf("Código de verificación ingresado: %s\n", codigoVerificacionDto.getCodigo());
            System.out.printf("Código de verificación almacenado en la base de datos: %s\n", usuario1.getVerificationCode());

            // Verifica si codigoAlmacenado es null antes de realizar la comparación
            if (codigoAlmacenado != null && codigoAlmacenado.equals(codigoIngresado)) {
                // Código de verificación correcto
                usuario1.setVerifiedEmail(true);
                return true;
            }

        }
        // Código de verificación incorrecto o usuario no encontrado
        return false;

    }
}
