package io.github.projektalmanac.amoxtli.backend.controller;

import io.github.projektalmanac.amoxtli.backend.exception.InvalidPhotoException;
import io.github.projektalmanac.amoxtli.backend.generated.api.UsuariosApi;
import io.github.projektalmanac.amoxtli.backend.generated.model.*;
import io.github.projektalmanac.amoxtli.backend.service.UserService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import io.github.projektalmanac.amoxtli.backend.service.*;
import org.springframework.http.HttpStatus;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.io.IOException;

@RestController
public class UserController implements UsuariosApi {
    @Autowired
    private BookService bookService;

    private UserService userService;

    @Autowired
    UserController(UserService userService) {
        this.userService = userService;
    }

    @Override
    public ResponseEntity<IntercambioDto> aceptarIntercambio(Integer idUsuario, Integer idIntercambio,
            AceptarIntercambioRequestDto aceptarIntercambioRequestDto) {
        return null;
    }

    @Override
    public ResponseEntity<Void> actualizarFotoPerfil(Integer id, Resource body) {
        try {
            this.userService.actualizaFoto(id, body);
            return ResponseEntity.noContent().build();
        } catch (IOException e) {
            throw new InvalidPhotoException();
        }

    }

    @Override
    public ResponseEntity<PerfilUsuarioDto> actualizarUsuario(Integer id, PerfilUsuarioDto perfilUsuarioDto) {
        this.userService.actualizaUsuario(id, perfilUsuarioDto);
        return ResponseEntity.ok(perfilUsuarioDto);
    }

    @Override
    public ResponseEntity<IntercambioDto> addIntercambio(Integer id, CreacionIntercambioDto creacionIntercambioDto) {
        return null;
    }

    @Override
    public ResponseEntity<LibroRegistradoDto> addLibro(Integer id, LibroRegistradoDto libroRegistradoDto) {
        LibroRegistradoDto result = bookService.addLibro(id, libroRegistradoDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
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
    public ResponseEntity<IntercambioDto> finalizarIntercambio(Integer idUsuario, Integer idIntercambio,
            CodigoIntercambioDto codigoIntercambioDto) {
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
        LibrosUsuarioDto result = userService.getLibrosUsuario(id);

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @Override
    public ResponseEntity<PerfilUsuarioDto> getUsuario(Integer id) {
        PerfilUsuarioDto perfilUsuarioDto = this.userService.getUsuario(id);
        return ResponseEntity.ok(perfilUsuarioDto);
    }


    @SneakyThrows
    @PostMapping(path = "/usuarios/{id}/mandarCorreoConfirmacion")
    @Override
    public ResponseEntity<Void> mandarCorreoConfirmacion(@PathVariable Integer id) {


        userService.enviarCorreoVerificacion(id);

        return ResponseEntity.status(HttpStatus.OK).body(null);
    }


    @Override
    public ResponseEntity<ValidaPuedeIntercambiar200ResponseDto> validaPuedeIntercambiar(Integer id) {
        return null;
    }


    @PostMapping(path = "/usuarios/{id}/verificarCorreo")
    @Override
    public ResponseEntity<Void> verificarCorreo(@PathVariable Integer id, @RequestBody @Valid CodigoVerificacionDto codigoVerificacionDto) {

        boolean codigoCorrecto = userService.verificaCorreo(id, codigoVerificacionDto);

        if (codigoCorrecto) {
            return ResponseEntity.status(HttpStatus.OK).body(null);

        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

}
