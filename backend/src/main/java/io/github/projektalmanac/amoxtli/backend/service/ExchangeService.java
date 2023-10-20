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

    private final static String[] MENSAJE_ERROR = {
            "Usuario no autorizado", "Operacion prohibida",
            "Intercambio no encontrado"

    };


    public IntercambioDto intercambioFinalizado(int idUsuario, int idIntercambio, CodigoIntercambioDto codigo){
        /*
         * Reglas de negocio:
         * 1. El usuario que esta escaneando existe y el Codigo no es nulo
         * 2. El usuario que escanea el QR no es el mismo que lo genera
         * 3. El usuario que escanea el QR debe ser el que se establecio en el intercambio
         * 4. Cuando se establezca que ambos usuarios poseen el mismo codigo, el intercmabio pasa a tener un estatus de COMPLETADO
         */


        //Regla 1
        Optional <User> usuario = userRepository.findById(idUsuario);
        if(usuario.isPresent() && codigo!=null){
            Optional<Exchange>  intercambio = exchangeRepository.findById(idIntercambio);
            //regla 2
            //System.out.println("hola");
            if (intercambio.isEmpty()){
                throw new ExchangeErrorProcess(MENSAJE_ERROR[2]);//404
            }
                //Quien genero el QR es quien tambien esta escaneando el codigo
                //El usuario que oferto genero el QR y esta tratando de escanear el codigo
            if (Objects.equals(intercambio.get().getUserOfferor().getId(), usuario.get().getId()) && intercambio.get().getOfferorConfirmationCode()!=null)
            {
                throw new ExchangeErrorProcess(MENSAJE_ERROR[1]); //403
            }
                //El usuario que solito el intercambio, genero el QR y esta tratando de escanear el codigo
            else if (Objects.equals(intercambio.get().getUserAccepting().getId(), usuario.get().getId()) && intercambio.get().getConfirmationCodeAccepting()!=null) {
                throw new ExchangeErrorProcess(MENSAJE_ERROR[1]); //403
            }

            //regla 3: El usuario que escanea es el ofertador o el aceptante, pero no es el mismo que enero el codigo
            actualizarIntercambio(5,intercambio.get());
            //return new IntercambioDto(intercambio.get().getId(),null,null,null,null,null);

            //eliminando libros de los usuarios
            User ofertante = intercambio.get().getUserOfferor();
            User aceptante = intercambio.get().getUserAccepting();

            ofertante.getBooks().remove(intercambio.get().getBookOfferor());
            aceptante.getBooks().remove(intercambio.get().getBookAccepting());

            userRepository.save(ofertante);
            userRepository.save(aceptante);


            return ExchangeMapper.INSTANCE.toIntercambioDto(intercambio.get());


            /*if(Objects.equals(intercambio.get().getUserOfferor(),usuario) || Objects.equals(intercambio.get().getUserAccepting(),usuario)){
                actualizarIntercambio(5,intercambio.get());
                //return new IntercambioDto(intercambio.get().getId(),null,null,null,null,null);

                //eliminando libros de los usuarios
                User ofertante = intercambio.get().getUserOfferor();
                User aceptante = intercambio.get().getUserAccepting();

                ofertante.getBooks().remove(intercambio.get().getBookOfferor());
                aceptante.getBooks().remove(intercambio.get().getBookAccepting());

                userRepository.save(ofertante);
                userRepository.save(aceptante);
                System.out.println("Hola");

                return ExchangeMapper.INSTANCE.toIntercambioDto(intercambio.get());
            }else{
                System.out.println("Como");
                throw new ExchangeErrorProcess("Usuario no autorizado"); //401
            }*/
        }else{
            throw new ExchangeErrorProcess(MENSAJE_ERROR[0]); //401
        }



    }


    public CodigoIntercambioDto codigo (int idUsuario, int idIntercambio){

        /* Reglas de negocio sobre este metodo
         *
         * 1. El usuario existe
         * 2. Debe existir un intercambio asociado a el id del inercambio
         * 3. El intercambio aun no debe estar con status de Completado
         * 4. Si el intercambio fue cancelado, no se permite generar el codigo
         */
        //Regla 1
        //puede ser el ofertante o el aceptante
        Optional <User> usuario = userRepository.findById(idUsuario);
        //System.out.println("Usuario "+ usuario.toString());
        if (usuario.isPresent()){
            //Regla 2
            //Optional<Exchange>  intercambio = exchangeRepository.findByIdAndUserOfferor(idIntercambio,usuario.get());
            Optional<Exchange>  intercambio = exchangeRepository.findById(idIntercambio);
            System.out.println("Intercambio");
            if (intercambio.isPresent()){
                //reglas 3 y 4
                if (intercambio.get().getStatus().equals(Status.COMPLETADO) || intercambio.get().getStatus().equals(Status.CANCELADO)){
                    throw new ExchangeErrorProcess(MENSAJE_ERROR[1]); //403
                }else {
                    var codigo_UUID = UUID.randomUUID(); //generando el codigo
                    salvar_codigo(codigo_UUID.toString(),intercambio.get(),usuario.get());
                    return new CodigoIntercambioDto(codigo_UUID);
                }
            }else{
                throw new ExchangeErrorProcess(MENSAJE_ERROR[2]);//404
            }
        }else{
            throw new ExchangeErrorProcess(MENSAJE_ERROR[0]); //401
        }

    }

    public void salvar_codigo(String codigoUuid, Exchange exchange, User user) {

        System.out.println("Codigocd");
        /*
         * Quien genero el codigo?
         */

        //Fue el ofertador
        if (exchange.getUserOfferor().equals(user)){
            exchange.setOfferorConfirmationCode(codigoUuid);
            //exchangeRepository.actualizarOfferorConfirmationCode(exchange.getId(),codigoUuid);
        }else{ //fue quien solicito el intercambio
            //exchangeRepository.actualizarConfirmationCodeAccepting(exchange.getId(),codigoUuid);
            exchange.setConfirmationCodeAccepting(codigoUuid);
        }

        exchangeRepository.save(exchange);


    }

    /**
     * Metodo auxiliar para cambiar el estatus de un intercambio
     * el cual depende del arumento 'opcion'
     * 1.-actualizar el estatus a Cancelado
     * 2.-actualizar el estatus a Aceptado
     * 3.-actualizar el estatus a Rechazado
     * 4.-actualizar el estatus a Pendiente
     * 5.-actualizar el estatus a Completado
     * @param opcion 1,..,5
     * @param exchange
     * @return 1 exito, 0 fracaso
     */
    public void actualizarIntercambio(int opcion, Exchange exchange){

        switch (opcion){
            case 1:
                //actualizar el estatus a Cancelado
                exchange.setStatus(Status.CANCELADO);
                break;
            case 2:
                //actualizar el estatus a Aceptado
                exchange.setStatus(Status.ACEPTADO);
                break;
            case 3:
                //actualizar el estatus a Rechazado
                exchange.setStatus(Status.RECHAZADO);
                break;
            case 4:
                //actualizar el estatus a Pendiente
                exchange.setStatus(Status.PENDIENTE);
                break;
            case 5:
                //actualizar el estatus a Completado
                exchange.setStatus(Status.COMPLETADO);
                break;
            default:
                //actualizar el estatus a Pendiente
                exchange.setStatus(Status.PENDIENTE);
                break;
        }

        exchangeRepository.save(exchange);

        //return 0;

    }
}
