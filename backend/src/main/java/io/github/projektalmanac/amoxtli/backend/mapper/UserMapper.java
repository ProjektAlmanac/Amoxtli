package io.github.projektalmanac.amoxtli.backend.mapper;

import io.github.projektalmanac.amoxtli.backend.entity.User;
import io.github.projektalmanac.amoxtli.backend.generated.model.PerfilUsuarioDto;
import io.github.projektalmanac.amoxtli.backend.generated.model.UsuarioDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.openapitools.jackson.nullable.JsonNullable;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Base64;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);


    @Mapping(source = "correo", target = "email")
    @Mapping(source = "nombre", target = "name")
    @Mapping(source = "apellildos", target = "lastName")
    @Mapping(source = "password", target = "passwordHash")
    User usuarioDtoToUser(UsuarioDto user);

    @Mapping(source = "nombre", target = "name")
    @Mapping(source = "apellidos", target = "lastName")
    @Mapping(source = "correo", target = "email")
    @Mapping(source = "telefono", target = "phone")
    @Mapping(target = "photoDescription", expression = "java(mapJsonToString(userDto.getDescripcionFoto()))")
    @Mapping(target = "interests", expression = "java(mapJsonToString(userDto.getIntereses()))")
    @Mapping(source = "correoVerificado", target = "verifiedEmail")
    User usuarioDtoToUser(PerfilUsuarioDto userDto);

    // Método para obtener el valor de JsonNulleable
    default String mapJsonToString(JsonNullable<String> campoDto) {
        return campoDto.orElse(null);
    }

    @Mapping(source = "name", target = "nombre")
    @Mapping(source = "lastName", target = "apellidos")
    @Mapping(source = "email", target = "correo")
    @Mapping(source = "phone", target = "telefono")
    @Mapping(target = "descripcionFoto", expression = "java(org.openapitools.jackson.nullable.JsonNullable.of(user.getPhotoDescription()))")
    @Mapping(target = "intereses", expression = "java(org.openapitools.jackson.nullable.JsonNullable.of(user.getInterests()))")
    @Mapping(source = "verifiedEmail", target = "correoVerificado")
    PerfilUsuarioDto userToUserDtoWithoutPhoto(User user);


    @Mapping(source = "name", target = "nombre")
    @Mapping(source = "lastName", target = "apellidos")
    @Mapping(source = "email", target = "correo")
    @Mapping(source = "phone", target = "telefono")
    @Mapping(target = "descripcionFoto", expression = "java(org.openapitools.jackson.nullable.JsonNullable.of(user.getPhotoDescription()))")
    @Mapping(target = "intereses", expression = "java(org.openapitools.jackson.nullable.JsonNullable.of(user.getInterests()))")
    @Mapping(target = "fotoPerfil", expression = "java(mapBytetoUri(user.getPhoto()))")
    @Mapping(source = "verifiedEmail", target = "correoVerificado")
    PerfilUsuarioDto userToUserDto(User user);

    // Método para parsear unn byte Array en un URI, usando Base64
    default JsonNullable<URI> mapBytetoUri(byte[] photo) {
        if (photo == null) return JsonNullable.undefined();
        if (photo.length == 0) return JsonNullable.undefined();

        String imgBase64 = Base64.getEncoder().encodeToString(photo);

        String inicioUrl;
        if (photo.length >= 2 && photo[0] == (byte) 0xFF && photo[1] == (byte) 0xD8) {
            inicioUrl = "data:image/jpeg;base64,";
        } else {
            inicioUrl = "data:image/png;base64,";
        }

        try {
            URI jpegUri = new URI(inicioUrl + imgBase64);
            return JsonNullable.of(jpegUri);
        } catch (URISyntaxException e) {
            return JsonNullable.undefined();
        }
    }

}
