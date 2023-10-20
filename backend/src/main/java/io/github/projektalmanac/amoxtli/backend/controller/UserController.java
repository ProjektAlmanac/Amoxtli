package io.github.projektalmanac.amoxtli.backend.controller;

import io.github.projektalmanac.amoxtli.backend.exception.ExchangeErrorProcess;
import io.github.projektalmanac.amoxtli.backend.exception.InvalidPhotoException;
import io.github.projektalmanac.amoxtli.backend.exception.InvalidVerificationCodeException;
import io.github.projektalmanac.amoxtli.backend.generated.api.UsuariosApi;
import io.github.projektalmanac.amoxtli.backend.generated.model.*;
import io.github.projektalmanac.amoxtli.backend.service.UserService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import io.github.projektalmanac.amoxtli.backend.service.*;
import org.springframework.http.HttpStatus;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;

@RestController
public class UserController implements UsuariosApi {
    @Autowired
    private BookService bookService;

    @Autowired
    private ExchangeService exchangeService;

    private UserService userService;

    @Autowired
    UserController(UserService userService) {
        this.userService = userService;
    }

    @Override
    public ResponseEntity<IntercambioDto> aceptarIntercambio(Integer idUsuario, Integer idIntercambio,
            AceptarIntercambioRequestDto aceptarIntercambioRequestDto) {

        IntercambioDto result = userService.aceptarIntercambio(idUsuario,idIntercambio, aceptarIntercambioRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(result);
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
        IntercambioDto result = this.userService.solicitaIntercambio(id, creacionIntercambioDto);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @Override
    public ResponseEntity<LibroRegistradoDto> addLibro(Integer id, LibroRegistradoDto libroRegistradoDto) {
        LibroRegistradoDto result = bookService.addLibro(id, libroRegistradoDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PostMapping(path = "/usuarios")
    @Override
    public ResponseEntity<SessionTokenDto> crearUsuario(@RequestBody @Valid UsuarioDto usuario) {
        var nuevoUsuario = userService.createUser(usuario);

        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoUsuario);
    }

    /**
     * Metodo de solititud de finalizar un intercambio activo
     *entre dos usuarios, que previamente aceptaron realizar el intercambio,
     * se necesita que alguno de los dos genere el codigo QR
     * @param idUsuario Id del usuario (required)
     * @param idIntercambio Id del intercambio (required)
     * @param codigoIntercambioDto  (optional)
     * @return Intercmabio finalizado
     */
    @PostMapping(path = "/usuarios/{idUsuario}/intercambios/{idIntercambio}/finalizar")
    @Override
    public ResponseEntity<IntercambioDto> finalizarIntercambio(Integer idUsuario, Integer idIntercambio,
            CodigoIntercambioDto codigoIntercambioDto) {
        var exchange = userService.finalizeExchange(idUsuario, idIntercambio, codigoIntercambioDto.getCodigo());

        return ResponseEntity.status(HttpStatus.OK).body(exchange);
    }

    /**
     * Metodo para obtener un codigo para que front genere un Qr apartir del mismo
     * @param idUsuario Id del usuario (required)
     * @param idIntercambio Id del intercambio (required)
     * @return codigo base con informacion del intercambio
     */
    @GetMapping(path = "/usuarios/{idUsuario}/intercambios/{idIntercambio}/codigo")
    @Override
    public ResponseEntity<CodigoIntercambioDto> getCodigoIntercambio(Integer idUsuario, Integer idIntercambio) {
        var code = userService.getCode(idUsuario, idIntercambio);

        return ResponseEntity.status(HttpStatus.OK).body(code);
    }

    @GetMapping(path = "usuarios/{id}/intercambios")
    @Override
    public ResponseEntity<GetIntercambios200ResponseDto> getIntercambios(@PathVariable Integer id) {
        //HU-07
        GetIntercambios200ResponseDto result = userService.getIntercambios(id);
        return ResponseEntity.status(HttpStatus.OK).body(result);
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
        ValidaPuedeIntercambiar200ResponseDto result = userService.validaIntercambio(id);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }


    @PostMapping(path = "/usuarios/{id}/verificarCorreo")
    @Override
    public ResponseEntity<Void> verificarCorreo(@PathVariable Integer id, @RequestBody @Valid CodigoVerificacionDto codigoVerificacionDto) {
        boolean codigoCorrecto = userService.verificaCorreo(id, codigoVerificacionDto);

        if (!codigoCorrecto) throw new InvalidVerificationCodeException();

        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @Override
    public ResponseEntity<IntercambioDto> getIntercambio(Integer idUsuario, Integer idIntercambio) {
        var intercambio = userService.getIntercambio(idUsuario, idIntercambio);
        return ResponseEntity.status(HttpStatus.OK).body(intercambio);
    }
}
