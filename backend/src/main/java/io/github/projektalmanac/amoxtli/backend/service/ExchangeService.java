package io.github.projektalmanac.amoxtli.backend.service;

import io.github.projektalmanac.amoxtli.backend.entity.Exchange;
import io.github.projektalmanac.amoxtli.backend.enums.Status;
import io.github.projektalmanac.amoxtli.backend.generated.model.*;
import io.github.projektalmanac.amoxtli.backend.mapper.ExchangeMapper;
import io.github.projektalmanac.amoxtli.backend.repository.ExchangeRepository;
import io.github.projektalmanac.amoxtli.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import io.github.projektalmanac.amoxtli.backend.config.*;
import io.github.projektalmanac.amoxtli.backend.entity.*;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import io.github.projektalmanac.amoxtli.backend.exception.ExchangeErrorProcess;

@Service
public class ExchangeService {

    @Autowired
    ExchangeRepository exchangeRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    SecurityConfig securityConfig;

    private static final String[] MENSAJE_ERROR = {
            "Usuario no autorizado", "Operación prohibida",
            "Intercambio no encontrado"

    };

    public IntercambioDto intercambioFinalizado(int idUsuario, int idIntercambio, CodigoIntercambioDto codigo){
        /*
         * Reglas de negocio:
         * 1. El usuario que está escaneando existe y el código no es nulo
         * 2. El usuario que escanea el QR no es el mismo que lo genera
         * 3. El usuario que escanea el QR debe ser el que se estableció en el intercambio
         * 4. Cuando se establezca que ambos usuarios poseen el mismo código, el intercambio pasa a tener un estatus de COMPLETADO
         */

        //Regla 1
        Optional <User> usuario = userRepository.findById(idUsuario);
        if (usuario.isEmpty() || codigo == null) {
            throw new ExchangeErrorProcess(MENSAJE_ERROR[0]); //401
        }

        var intercambio = exchangeRepository.findById(idIntercambio).orElseThrow(() -> new ExchangeErrorProcess(MENSAJE_ERROR[2]));//404
        //regla 2
        //Quien genero el QR es quien también está escaneando el código
        //El usuario que oferto genero el QR y está tratando de escanear el código
        if (Objects.equals(intercambio.getUserOfferor().getId(), usuario.get().getId()) && intercambio.getOfferorConfirmationCode()!=null)
        {
            throw new ExchangeErrorProcess(MENSAJE_ERROR[1]); //403
        }
            //El usuario que solito el intercambio, genero el QR y está tratando de escanear el código
        else if (Objects.equals(intercambio.getUserAccepting().getId(), usuario.get().getId()) && intercambio.getConfirmationCodeAccepting()!=null) {
            throw new ExchangeErrorProcess(MENSAJE_ERROR[1]); //403
        }

        //regla 3: El usuario que escanea es el ofertante o el aceptante, pero no es el mismo que enero el código
        intercambio.setStatus(Status.COMPLETADO);
        exchangeRepository.save(intercambio);

        //eliminando libros de los usuarios
        User ofertante = intercambio.getUserOfferor();
        User aceptante = intercambio.getUserAccepting();

        ofertante.getBooks().remove(intercambio.getBookOfferor());
        aceptante.getBooks().remove(intercambio.getBookAccepting());

        userRepository.save(ofertante);
        userRepository.save(aceptante);

        return ExchangeMapper.INSTANCE.toIntercambioDto(intercambio);
    }


    public CodigoIntercambioDto codigo (int idUsuario, int idIntercambio){

        /* Reglas de negocio sobre este método
         *
         * 1. El usuario existe
         * 2. Debe existir un intercambio asociado al ID del intercambio
         * 3. El intercambio aún no debe estar con status de Completado
         * 4. Si el intercambio fue cancelado, no se permite generar el código
         */
        //Regla 1
        //puede ser el ofertante o el aceptante
        Optional <User> usuario = userRepository.findById(idUsuario);
        if (usuario.isEmpty()) {
            throw new ExchangeErrorProcess(MENSAJE_ERROR[0]); //401
        }
        //Regla 2
        Optional<Exchange>  intercambio = exchangeRepository.findById(idIntercambio);
        if (intercambio.isEmpty()) {
            throw new ExchangeErrorProcess(MENSAJE_ERROR[2]);//404
        }
        //reglas 3 y 4
        if (intercambio.get().getStatus().equals(Status.COMPLETADO) || intercambio.get().getStatus().equals(Status.CANCELADO)){
            throw new ExchangeErrorProcess(MENSAJE_ERROR[1]); //403
        }

        var codigoUUID = UUID.randomUUID(); //generando el código
        salvarCodigo(codigoUUID.toString(),intercambio.get(),usuario.get());
        return new CodigoIntercambioDto(codigoUUID);

    }

    public void salvarCodigo(String codigoUuid, Exchange exchange, User user) {

        /*
         * ¿Quién genero el código?
         */

        //Fue el ofertante
        if (exchange.getUserOfferor().equals(user)){
            exchange.setOfferorConfirmationCode(codigoUuid);
        }else{ //fue quien solicito el intercambio
            exchange.setConfirmationCodeAccepting(codigoUuid);
        }

        exchangeRepository.save(exchange);
    }
}
