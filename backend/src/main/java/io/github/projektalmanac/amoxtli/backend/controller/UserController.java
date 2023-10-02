package io.github.projektalmanac.amoxtli.backend.controller;

import io.github.projektalmanac.amoxtli.backend.generated.api.UsuariosApi;
import io.github.projektalmanac.amoxtli.backend.generated.model.*;
import io.github.projektalmanac.amoxtli.backend.service.UserService;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;


import javax.mail.MessagingException;
import javax.validation.Valid;
import java.util.List;

@RestController
public class UserController implements UsuariosApi {

    @Autowired
    private UserService userService;

    @Override
    public ResponseEntity<IntercambioDto> aceptarIntercambio(Integer idUsuario, Integer idIntercambio, AceptarIntercambioRequestDto aceptarIntercambioRequestDto) {
        return null;
    }

    @Override
    public ResponseEntity<Void> actualizarFotoPerfil(String id, Resource body) {
        return null;
    }

    @Override
    public ResponseEntity<PerfilUsuarioDto> actualizarUsuario(Integer id, PerfilUsuarioDto perfilUsuarioDto) {
        return null;
    }

    @Override
    public ResponseEntity<IntercambioDto> addIntercambio(Integer id, CreacionIntercambioDto creacionIntercambioDto) {
        return null;
    }

    @Override
    public ResponseEntity<LibroRegistradoDto> addLibro(Integer id, LibroRegistradoDto libroRegistradoDto) {
        return null;
    }

    @PostMapping(path = "/usuarios")
    @Override
    public ResponseEntity<UsuarioIdDto> crearUsuario(@RequestBody @Valid UsuarioDto usuario) {

        UsuarioIdDto nuevousuario = userService.createuser(usuario);


        if (nuevousuario != null) {
            // devolveer solamente ID
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevousuario);

        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @Override
    public ResponseEntity<IntercambioDto> finalizarIntercambio(Integer idUsuario, Integer idIntercambio, CodigoIntercambioDto codigoIntercambioDto) {
        return null;
    }

    @Override
    public ResponseEntity<CodigoIntercambioDto> getCodigoIntercambio(Integer idUsuario, Integer idIntercambio) {
        return null;
    }

    @Override
    public ResponseEntity<GetIntercambios200ResponseDto> getIntercambios(Integer id) {
        return null;
    }

    @Override
    public ResponseEntity<LibrosUsuarioDto> getLibrosUsuario(Integer id) {
        return null;
    }

    @Override
    public ResponseEntity<PerfilUsuarioDto> getUsuario(Integer id) {
        return null;
    }


    @PostMapping(path = "/usuarios/{id}/mandarCorreoConfirmacion")
    @Override
    public ResponseEntity<String> mandarCorreoConfirmacion(@PathVariable long id) {
        // Obtén el correo del usuario usando el ID
        String correoUsuario = userService.obtenerCorreoPorId(id);

        if (correoUsuario == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado.");
        }

        String codigoVerificacion = userService.generarCodigoVerificacion();

        try {
            // Envia el correo de verificación
            userService.enviarCorreoVerificacion(correoUsuario, codigoVerificacion);//cambiar a variables de entorno

            // Almacena el código de verificación en la base de datos
            userService.guardarCodigoVerificacion(id, codigoVerificacion);

            return ResponseEntity.ok("Correo de verificación enviado con éxito.");
        } catch (MessagingException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al enviar el correo de verificación.");
        }
    }


    @Override
    public ResponseEntity<ValidaPuedeIntercambiar200ResponseDto> validaPuedeIntercambiar(Integer id) {
        return null;
    }


    @PostMapping(path = "/usuarios/{id}/verificarCorreo")
    @Override
    public ResponseEntity<String> verificarCorreo(@PathVariable long id, @RequestBody @Valid CodigoVerificacionDto codigoVerificacionDto) { //no debe regresar nada y remodificar usuariosAPI
        // Verificar el código de verificación ingresado por el usuario
        boolean codigoCorrecto = userService.verificaCorreo(id, codigoVerificacionDto);

        if (codigoCorrecto) {
            return ResponseEntity.ok("Código de verificación correcto. Correo confirmado.");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Código de verificación incorrecto.");//modificar codigos de error en exception y advice.
        }

    }
}
