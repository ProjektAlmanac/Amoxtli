package io.github.projektalmanac.amoxtli.backend.controller;

import io.github.projektalmanac.amoxtli.backend.generated.api.UsuariosApi;
import io.github.projektalmanac.amoxtli.backend.generated.model.*;
import io.github.projektalmanac.amoxtli.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController implements UsuariosApi {


    private UserService userService;

    @Autowired
    UserController(UserService userService){
        this.userService = userService;
    }
    @Override
    public ResponseEntity<IntercambioDto> aceptarIntercambio(Integer idUsuario, Integer idIntercambio, AceptarIntercambioRequestDto aceptarIntercambioRequestDto) {
        return null;
    }

    @Override
    public ResponseEntity<PerfilUsuarioDto> actualizarUsuario(Integer id, List<PatchRequestInnerDto> patchRequestInnerDto) {
        this.userService.actualizaUsuario(id,patchRequestInnerDto);
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

    @Override
    public ResponseEntity<UsuarioIdDto> crearUsuario(UsuarioDto usuarioDto) {
        return null;
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
        PerfilUsuarioDto perfilUsuarioDto = this.userService.getUsuario(id);
        return ResponseEntity.ok(perfilUsuarioDto);
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
