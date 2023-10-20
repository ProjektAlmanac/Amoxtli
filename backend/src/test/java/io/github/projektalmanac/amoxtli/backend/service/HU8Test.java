package io.github.projektalmanac.amoxtli.backend.service;

import io.github.projektalmanac.amoxtli.backend.entity.Exchange;
import io.github.projektalmanac.amoxtli.backend.entity.User;
import io.github.projektalmanac.amoxtli.backend.enums.Status;
import io.github.projektalmanac.amoxtli.backend.exception.ExchangeErrorProcess;
import io.github.projektalmanac.amoxtli.backend.generated.model.CodigoIntercambioDto;
import io.github.projektalmanac.amoxtli.backend.generated.model.IntercambioDto;
import io.github.projektalmanac.amoxtli.backend.repository.ExchangeRepository;
import io.github.projektalmanac.amoxtli.backend.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class HU8Test {

    @InjectMocks
    private ExchangeService exchangeService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ExchangeRepository exchangeRepository;

    private CodigoIntercambioDto codigoIntercambioDto;

    private UUID codigo;

    @BeforeEach
    public void setUp() {
        // Inicializa los mocks
        MockitoAnnotations.initMocks(this);
        codigo = UUID.randomUUID();
        codigoIntercambioDto = new CodigoIntercambioDto(codigo);

    }

    /************************************
     *
     * TEST SOBRE EL METODO IntercambioFinalizado
     *************************************
     */
    @Test
    public void testIntercambioFinalizadoR1() {

        // Configurar que el usuario no existe
        when(userRepository.findById(1)).thenReturn(Optional.empty());

        // Ejecutar el método y verificar que lanza la excepción correcta
        Exception exception = assertThrows(ExchangeErrorProcess.class, () -> {
            exchangeService.intercambioFinalizado(1, 2,null);
        });

        String mensajeEsperado = "Usuario no autorizado";
        String mensajeEncontrado = exception.getMessage();

        assertEquals(mensajeEsperado, mensajeEncontrado);

    }

    @Test
    public void testIntercambioFinalizadoR2(){
        System.out.println("Inicio test testIntercambioFinalizadoR2");
        int id=1;
        int idIntercambio = 5;
        //supongase que es el usuario ofertador
        User user = new User();
        user.setId(id);
        Optional<User> expectedUser = Optional.of(user);
        // mock sobre el metodo findByEmail
        Mockito.when(userRepository.findById(id))
                .thenReturn(expectedUser);

        // Ejecutar el método y verificar que lanza la excepción correcta
        Exception exception = assertThrows(ExchangeErrorProcess.class, () -> {
            exchangeService.intercambioFinalizado(id, idIntercambio,codigoIntercambioDto);
        });

        String mensajeEsperado = "Intercambio no encontrado";
        String mensajeEncontrado = exception.getMessage();

        System.out.println(mensajeEncontrado);
        assertEquals(mensajeEsperado, mensajeEncontrado);
        System.out.println("Fin test testIntercambioFinalizadoR2");
    }

    @Test
    public void testIntercambioFinalizadoR2_2(){
        System.out.println("Inicio test testIntercambioFinalizadoR2_2");
        int id=1;
        int idIntercambio = 5;
        //supongase que es el usuario ofertador
        User user = new User();
        user.setId(id);
        Optional<User> expectedUser = Optional.of(user);
        // mock sobre el metodo findById
        Mockito.when(userRepository.findById(id))
                .thenReturn(expectedUser);

        // Configurar el intercambio existente
        //supongamos que el usuario ofertador genero el codigo y trata de escanearlo desde otro dispositivo
        Exchange exchange = new Exchange();
        exchange.setId(idIntercambio);
        exchange.setUserOfferor(user);
        exchange.setOfferorConfirmationCode(codigo.toString());
        exchange.setStatus(Status.PENDIENTE);
        when(exchangeRepository.findById(idIntercambio)).thenReturn(Optional.of(exchange));


        // Ejecutar el método y verificar que lanza la excepción correcta
        Exception exception = assertThrows(ExchangeErrorProcess.class, () -> {
            exchangeService.intercambioFinalizado(id, idIntercambio,codigoIntercambioDto);
        });

        String mensajeEsperado = "Operacion prohibida";
        String mensajeEncontrado = exception.getMessage();

        System.out.println(mensajeEncontrado);
        assertEquals(mensajeEsperado, mensajeEncontrado);
        System.out.println("fin test testIntercambioFinalizadoR2_2");
    }


    @Test
    void testIntercambioFinalizadoR3(){
        System.out.println("Inicio test testIntercambioFinalizadoR3");

        int id=1;
        int id2 = 6;
        int idIntercambio = 5;
        //supongase que es el usuario ofertador
        User ofertador = new User();
        ofertador.setId(id2);
        ofertador.setName("Andy");
        User aceptante = new User();
        aceptante.setId(id);
        aceptante.setName("Antar");
        Optional<User> expectedUser = Optional.of(aceptante);
        // mock sobre el metodo findById
        Mockito.when(userRepository.findById(id))
                .thenReturn(expectedUser);

        // Configurar el intercambio existente
        //supongamos que el usuario ofertador genero el codigo y  el aceptante trata de escanearlo desde otro dispositivo
        Exchange exchange = new Exchange();
        exchange.setId(idIntercambio);
        exchange.setUserOfferor(ofertador);
        exchange.setUserAccepting(aceptante);
        exchange.setOfferorConfirmationCode(codigo.toString());
        exchange.setStatus(Status.PENDIENTE);
        when(exchangeRepository.findById(idIntercambio)).thenReturn(Optional.of(exchange));


        IntercambioDto intercambioDto = exchangeService.intercambioFinalizado(id,idIntercambio,codigoIntercambioDto);

        //verificar que se genero el DTO
        assertNotNull(intercambioDto);
        // Verificar que el dto no está vacío
        assertNotNull(intercambioDto.getAceptante().toString());
        //verificar que el estado del intercambio pasa a COMPLETADO
        assertEquals(exchange.getStatus(),Status.COMPLETADO);

        //System.out.println(intercambioDto.toString());



        System.out.println("Fin test testIntercambioFinalizadoR3");
    }





    /************************************
     *
     * TEST SOBRE EL METODO CODIGO
     *************************************
     */
    @Test
    public void testCodigo_Success() {
        int id=1;
        int idIntercambio = 5;
        //supongase que es el usuario ofertador
        User user = new User();
        user.setId(id);
        Optional<User> expectedUser = Optional.of(user);
        // mock sobre el metodo findByEmail
        Mockito.when(userRepository.findById(id))
                .thenReturn(expectedUser);

        // Configurar el intercambio existente
        Exchange exchange = new Exchange();
        exchange.setId(idIntercambio);
        exchange.setUserOfferor(user);
        exchange.setStatus(Status.PENDIENTE);
        when(exchangeRepository.findById(idIntercambio)).thenReturn(Optional.of(exchange));

        // Ejecutar el método que queremos probar
        CodigoIntercambioDto codigoIntercambioDto = exchangeService.codigo(id, idIntercambio);

        // Verificar que se generó el código UUID
        assertNotNull(codigoIntercambioDto);

        // Verificar que el código no está vacío
        assertNotNull(codigoIntercambioDto.getCodigo().toString());

        //Verificar que bajo la suposicion anterior el atributo offerorConfirmationCode contiene el codigo
        assertEquals(exchange.getOfferorConfirmationCode(),codigoIntercambioDto.getCodigo().toString());


    }


    @Test
    public void testCodigo_UserNotAutorized() {
        // Configurar que el usuario no existe
        when(userRepository.findById(1)).thenReturn(Optional.empty());

        // Ejecutar el método y verificar que lanza la excepción correcta
        Exception exception = assertThrows(ExchangeErrorProcess.class, () -> {
            exchangeService.codigo(1, 2);
        });

        String mensajeEsperado = "Usuario no autorizado";
        String mensajeEncontrado = exception.getMessage();

        assertEquals(mensajeEsperado, mensajeEncontrado);
    }


    @Test
    public void testCodigo_ExchangeNotFound() {
        // Configurar que el usuario existe
        int id=1;
        int idIntercambio = 5;
        User user = new User();
        user.setId(id);
        Optional<User> expectedUser = Optional.of(user);
        // mock sobre el metodo findByEmail
        Mockito.when(userRepository.findById(id))
                .thenReturn(expectedUser);

        // Ejecutar el método y verificar que lanza la excepción correcta
        Exception exception = assertThrows(ExchangeErrorProcess.class, () -> {
            exchangeService.codigo(id, idIntercambio);
        });

        String mensajeEsperado = "Intercambio no encontrado";
        String mensajeEncontrado = exception.getMessage();

        System.out.println(mensajeEncontrado);
        assertEquals(mensajeEsperado, mensajeEncontrado);
    }


    @Test
    public void testCodigo_CompletedExchange() {
        // Configurar que el usuario existe
        int id=1;
        int idIntercambio = 5;
        User user = new User();
        user.setId(id);
        Optional<User> expectedUser = Optional.of(user);
        // mock sobre el metodo findByEmail
        Mockito.when(userRepository.findById(id))
                .thenReturn(expectedUser);

        // Configurar el intercambio cancelado
        Exchange exchange = new Exchange();
        exchange.setId(idIntercambio);
        exchange.setStatus(Status.COMPLETADO);
        when(exchangeRepository.findById(idIntercambio)).thenReturn(Optional.of(exchange));

        // Ejecutar el método y verificar que lanza la excepción correcta
        Exception exception = assertThrows(ExchangeErrorProcess.class, () -> {
            exchangeService.codigo(id, idIntercambio);
        });

        String mensajeEsperado = "Operacion prohibida";
        String mensajeEncontrado = exception.getMessage();

        System.out.println(mensajeEncontrado);
        assertEquals(mensajeEsperado, mensajeEncontrado);
    }


    @Test
    public void testCodigo_CancelledExchange() {
        // Configurar que el usuario existe
        int id=1;
        int idIntercambio = 5;
        User user = new User();
        user.setId(id);
        Optional<User> expectedUser = Optional.of(user);
        // mock sobre el metodo findByEmail
        Mockito.when(userRepository.findById(id))
                .thenReturn(expectedUser);

        // Configurar el intercambio cancelado
        Exchange exchange = new Exchange();
        exchange.setId(idIntercambio);
        exchange.setStatus(Status.CANCELADO);
        when(exchangeRepository.findById(idIntercambio)).thenReturn(Optional.of(exchange));

        // Ejecutar el método y verificar que lanza la excepción correcta
        Exception exception = assertThrows(ExchangeErrorProcess.class, () -> {
            exchangeService.codigo(id, idIntercambio);
        });

        String mensajeEsperado = "Operacion prohibida";
        String mensajeEncontrado = exception.getMessage();

        System.out.println(mensajeEncontrado);
        assertEquals(mensajeEsperado, mensajeEncontrado);
    }

}