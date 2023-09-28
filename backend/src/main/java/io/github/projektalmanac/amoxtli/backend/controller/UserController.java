package io.github.projektalmanac.amoxtli.backend.controller;

import io.github.projektalmanac.amoxtli.backend.generated.api.UsuariosApi;
import io.github.projektalmanac.amoxtli.backend.generated.model.*;
import io.github.projektalmanac.amoxtli.backend.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

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
    public ResponseEntity<PerfilUsuarioDto> actualizarUsuario(Integer id, List<PatchRequestInnerDto> patchRequestInnerDto) {
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

    @PostMapping
    @Override
    public ResponseEntity<UsuarioDto> crearUsuario(@RequestBody @Valid UsuarioDto usuario) {

        UsuarioDto nuevoUsuario = userService.createuser(usuario);

        if(nuevoUsuario != null) {

            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoUsuario);

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

    @Override
    public ResponseEntity<Void> mandarCorreoConfirmacion(Integer id) {
        return null;
    }

    @Override
    public ResponseEntity<ValidaPuedeIntercambiar200ResponseDto> validaPuedeIntercambiar(Integer id) {
        return null;
    }

    @Override
    public ResponseEntity<Void> verificarCorreo(Integer id, CodigoVerificacionDto codigoVerificacionDto) {
        return null;
    }
}
