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

import java.util.ArrayList;
import java.util.List;

@Mapper(uses = {BookMapper.class})
public interface ExchangeMapper {
    ExchangeMapper INSTANCE = Mappers.getMapper(ExchangeMapper.class);


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

    EstadoIntercambioDto toEstadoIntercambioDto(Status status);

    @Mapping(target = "ofertante", source = "userOfferor")
    @Mapping(target = "aceptante", source = "userAccepting")
    @Mapping(target = "libroAceptante", source = "bookAccepting")
    @Mapping(target = "libroOfertante", source = "bookOfferor")
    @Mapping(target = "estado", source = "status")
    IntercambioDto toIntercambioDto(Exchange exchange);

    default GetIntercambios200ResponseDto toGetIntercambios200ResponseDto(List<Exchange> intercambios){
        var resultado = new GetIntercambios200ResponseDto();
        resultado.setIntercambios(new ArrayList<>());

        for (int i = 0; i < intercambios.size(); i++) {
            var intercambio =  toIntercambioDto(intercambios.get(i));
            resultado.getIntercambios().add(intercambio);
        }
        return resultado;
    }

    default JsonNullable<LibroRegistradoDto> toBookOfferor(Book libro){
       LibroRegistradoDto libroDelOfertante = BookMapper.INSTANCE.toLibroRegistradoDto(libro);
        return JsonNullable.of(libroDelOfertante);
    }
}