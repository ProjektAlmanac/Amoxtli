package io.github.projektalmanac.amoxtli.backend.mapper;

import io.github.projektalmanac.amoxtli.backend.entity.Book;
import io.github.projektalmanac.amoxtli.backend.entity.Exchange;
import io.github.projektalmanac.amoxtli.backend.entity.User;
import io.github.projektalmanac.amoxtli.backend.enums.Status;
import io.github.projektalmanac.amoxtli.backend.generated.model.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.openapitools.jackson.nullable.JsonNullable;

import java.util.List;

@Mapper(uses = {BookMapper.class, UserMapper.class})
public interface ExchangeMapper {
    ExchangeMapper INSTANCE = Mappers.getMapper(ExchangeMapper.class);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "isbn", source = "isbn")
    @Mapping(target = "descripcion", source = "description")
    LibroRegistradoDto toLibroRegistradoDto(Book book);

    //@Mapping(target = "value", source = "status" )
    EstadoIntercambioDto toEstadoIntercambioDto(Status status);

    @Mapping(target = "id",source = "id")
    @Mapping(target = "ofertante", source = "userOfferor")
    @Mapping(target = "aceptante", source = "userAccepting")
    @Mapping(target = "libroAceptante", source = "bookAccepting")
    @Mapping(target = "libroOfertante", source = "bookOfferor")
    @Mapping(target = "estado", source = "status")
    IntercambioDto toIntercambioDto(Exchange exchange);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "nombre", source = "name")
    @Mapping(target = "apellidos", source = "lastName")
    @Mapping(target = "telefono", source = "phone")
    @Mapping(target = "correo", source = "email")
    OfertanteDto toOfertanteDto(User user);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "nombre", source = "name")
    @Mapping(target = "apellidos", source = "lastName")
    @Mapping(target = "telefono", source = "phone")
    @Mapping(target = "correo", source = "email")
    AceptanteDto toAceptanteDto(User user);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "isbn", source = "isbn")
    @Mapping(target = "descripcion", source = "description")
    LibroAceptanteDto tOLibroAceptanteDto(Book book);

    default GetIntercambios200ResponseDto toGetIntercambios200ResponseDto(List<Exchange> intercambios){
        var resultado = new GetIntercambios200ResponseDto();

        for (int i = 0; i < intercambios.size(); i++) {
            var intercambio = intercambios.get(i);
            OfertanteDto ofertante = toOfertanteDto(intercambio.getUserOfferor());
            AceptanteDto aceptante = toAceptanteDto(intercambio.getUserAccepting());
            LibroAceptanteDto libroAceptante = tOLibroAceptanteDto(intercambio.getBookAccepting());
            LibroRegistradoDto libroOfertante = BookMapper.INSTANCE.toLibroRegistradoDto(intercambio.getBookOfferor());

            IntercambioDto intercambioDto = new IntercambioDto(intercambio.getId(), ofertante, aceptante, libroAceptante, libroOfertante, null);
            resultado.getIntercambios().add(intercambioDto);
        }
        return resultado;

    }

    default JsonNullable<LibroRegistradoDto> toBookOfferor(Book libro){
        LibroRegistradoDto libroDelOfertante = BookMapper.INSTANCE.toLibroRegistradoDto(libro);
        return JsonNullable.of(libroDelOfertante);
    }

    /*default IntercambioDto intercambioToIntercambioDto(Exchange exchange){
        var id = exchange.getId();
        var ofertante = toOfertanteDto(exchange.getUserOfferor());
        var aceptante = toAceptanteDto(exchange.getUserAccepting());
        var libroOfertante = BookMapper.INSTANCE.toLibroRegistradoDto(exchange.getBookOfferor());
        var libroAceptante = tOLibroAceptanteDto(exchange.getBookAccepting());//BookMapper.INSTANCE.toLibroRegistradoDto(exchange.getBookAccepting());
        var estado = toEstadoIntercambioDto(exchange.getStatus());

        return new IntercambioDto(id,ofertante,aceptante,libroAceptante,libroOfertante,estado);
    }*/


    /*default EstadoIntercambioDto toEstadoIntercambioDto(Status status){
        switch (status) {
            case PENDIENTE:
                return EstadoIntercambioDto.PENDIENTE;
            case ACEPTADO:
                return EstadoIntercambioDto.ACEPTADO;
            case RECHAZADO:
                return EstadoIntercambioDto.RECHAZADO;
            case COMPLETADO:
                return EstadoIntercambioDto.COMPLETADO;
            case CANCELADO:
                return EstadoIntercambioDto.CANCELADO;
            default:
                return EstadoIntercambioDto.PENDIENTE;
        }
    }*/




}
