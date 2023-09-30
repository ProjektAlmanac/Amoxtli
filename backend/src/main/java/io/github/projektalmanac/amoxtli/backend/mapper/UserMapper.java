package io.github.projektalmanac.amoxtli.backend.mapper;

import io.github.projektalmanac.amoxtli.backend.entity.User;
import io.github.projektalmanac.amoxtli.backend.generated.model.PerfilUsuarioDto;
import io.github.projektalmanac.amoxtli.backend.generated.model.UsuarioDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.openapitools.jackson.nullable.*;

import java.net.URI;
import java.util.Base64;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    /*@Mapping(source = "id", target = "id")
    @Mapping(source = "nombre", target = "name")
    @Mapping(source = "apellildos", target = "lastName")
    User usuarioDtoToUser(UsuarioDto user);*/

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "nombre")
    @Mapping(source = "lastName", target = "apellidos")
    @Mapping(source = "email", target = "correo")
    @Mapping(source = "phone", target = "teléfono")
    @Mapping(target = "descripciónFoto", expression = "java(org.openapitools.jackson.nullable.JsonNullable.of(user.getPhotoDescription()))")
    @Mapping(target = "intereses",expression = "java(org.openapitools.jackson.nullable.JsonNullable.of(user.getInterests()))")
    @Mapping(target = "fotoPerfil",expression = "java(mapBytetoUri(user.getPhoto()))")
    @Mapping(source = "verifiedEmail", target = "correoVerificado")
    PerfilUsuarioDto userToUserDto(User user);
    default JsonNullable<URI> mapBytetoUri(byte[] photo){
        try{
            String datos = Base64.getEncoder().encodeToString(photo);
            URI photoUri = new URI(datos);
            return JsonNullable.of(photoUri);
        }catch (Exception e){
            e.printStackTrace();
        }
        return JsonNullable.undefined();
    }
}
