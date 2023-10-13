package io.github.projektalmanac.amoxtli.backend.mapper;

import io.github.projektalmanac.amoxtli.backend.entity.Book;
import io.github.projektalmanac.amoxtli.backend.entity.Exchange;
import io.github.projektalmanac.amoxtli.backend.entity.User;
import io.github.projektalmanac.amoxtli.backend.enums.Status;
import io.github.projektalmanac.amoxtli.backend.generated.model.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ValueMapping;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

@Mapper
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

    @Mapping(target = "descripcion", source = "description")
    LibroAceptanteDto tOLibroAceptanteDto(Book book);

    @ValueMapping(source = "PENDIENTE", target = "PENDIENTE")
    @ValueMapping(source = "ACEPTADO", target = "ACEPTADO")
    @ValueMapping(source = "RECHAZADO", target = "RECHAZADO")
    @ValueMapping(source = "CANCELADO", target = "CANCELADO")
    EstadoIntercambioDto toEstadoIntercambioDto(Status status);

    default GetIntercambios200ResponseDto toGetIntercambios200ResponseDto(List<Exchange> intercambios){
        var resultado = new GetIntercambios200ResponseDto();
        resultado.setIntercambios(new ArrayList<>());

        for (int i = 0; i < intercambios.size(); i++) {
            var intercambio = intercambios.get(i);
            OfertanteDto ofertante = toOfertanteDto(intercambio.getUserOfferor());
            AceptanteDto aceptante = toAceptanteDto(intercambio.getUserAccepting());
            LibroAceptanteDto libroAceptante = tOLibroAceptanteDto(intercambio.getBookAccepting());

            LibroRegistradoDto libroOfertante;
            if (intercambio.getBookOfferor() == null) {
                libroOfertante = null;
            } else {
                libroOfertante = BookMapper.INSTANCE.toLibroRegistradoDto(intercambio.getBookOfferor());
            }

            EstadoIntercambioDto estado = toEstadoIntercambioDto(intercambio.getStatus());

            IntercambioDto intercambioDto = new IntercambioDto(intercambio.getId(), ofertante, aceptante, libroAceptante, libroOfertante, estado);
            resultado.getIntercambios().add(intercambioDto);
        }
        return resultado;

    }
}
