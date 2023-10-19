package io.github.projektalmanac.amoxtli.backend.mapper;

import io.github.projektalmanac.amoxtli.backend.entity.Exchange;
import io.github.projektalmanac.amoxtli.backend.entity.User;
import io.github.projektalmanac.amoxtli.backend.enums.Status;
import io.github.projektalmanac.amoxtli.backend.generated.model.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(uses = {BookMapper.class, UserMapper.class})
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

    default IntercambioDto toIntercambioDto(Exchange exchange){
        var ofertante = toOfertanteDto(exchange.getUserOfferor());
        var aceptante = toAceptanteDto(exchange.getUserAccepting());
        var libroAceptante = BookMapper.INSTANCE.tOLibroAceptanteDto(exchange.getBookAccepting());
        var estado = toEstadoIntercambioDto(exchange.getStatus());
        return new IntercambioDto(exchange.getId(), ofertante, aceptante, libroAceptante, null, estado);

    }
    default GetIntercambios200ResponseDto toGetIntercambios200ResponseDto(List<Exchange> intercambios){
        var resultado = new GetIntercambios200ResponseDto();

        for (int i = 0; i < intercambios.size(); i++) {
            var intercambio = intercambios.get(i);
            OfertanteDto ofertante = toOfertanteDto(intercambio.getUserOfferor());
            AceptanteDto aceptante = toAceptanteDto(intercambio.getUserAccepting());
            LibroAceptanteDto libroAceptante = BookMapper.INSTANCE.tOLibroAceptanteDto(intercambio.getBookAccepting());
            LibroRegistradoDto libroOfertante = BookMapper.INSTANCE.toLibroRegistradoDto(intercambio.getBookOfferor());

            IntercambioDto intercambioDto = new IntercambioDto(intercambio.getId(), ofertante, aceptante, libroAceptante, libroOfertante, null);
            resultado.getIntercambios().add(intercambioDto);
        }
        return resultado;

    }
}
