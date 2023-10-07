package io.github.projektalmanac.amoxtli.backend.mapper;

import io.github.projektalmanac.amoxtli.backend.entity.User;
import io.github.projektalmanac.amoxtli.backend.generated.model.PerfilUsuarioDto;
import io.github.projektalmanac.amoxtli.backend.generated.model.UsuarioDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.openapitools.jackson.nullable.JsonNullable;

import java.net.URI;
import java.util.Base64;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "nombre", target = "name")
    @Mapping(source = "apellidos", target = "lastName")
    @Mapping(source = "correo", target = "email")
    @Mapping(source = "telefono",target = "phone")
    @Mapping(target = "photoDescription",expression = "java(mapJsonToString(userDto.getDescripcionFoto()))")
    @Mapping(target = "interests", expression = "java(mapJsonToString(userDto.getIntereses()))")
    @Mapping(source = "correoVerificado", target = "verifiedEmail")
    User usuarioDtoToUser(PerfilUsuarioDto userDto);
    // Método para obtener el valor de JsonNulleable
    default String mapJsonToString(JsonNullable<String> campoDto){
        String campo = null;
        try {
            campo = campoDto.orElse(null);
            return campo;
        }catch (Exception e){
            e.printStackTrace();
        }
        return campo;
    }
    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "nombre")
    @Mapping(source = "lastName", target = "apellidos")
    @Mapping(source = "email", target = "correo")
    @Mapping(source = "phone", target = "telefono")
    @Mapping(target = "descripcionFoto", expression = "java(org.openapitools.jackson.nullable.JsonNullable.of(user.getPhotoDescription()))")
    @Mapping(target = "intereses",expression = "java(org.openapitools.jackson.nullable.JsonNullable.of(user.getInterests()))")
    @Mapping(source = "verifiedEmail", target = "correoVerificado")
    PerfilUsuarioDto userToUserDtoWithoutPhoto(User user);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "nombre")
    @Mapping(source = "lastName", target = "apellidos")
    @Mapping(source = "email", target = "correo")
    @Mapping(source = "phone", target = "telefono")
    @Mapping(target = "descripcionFoto", expression = "java(org.openapitools.jackson.nullable.JsonNullable.of(user.getPhotoDescription()))")
    @Mapping(target = "intereses",expression = "java(org.openapitools.jackson.nullable.JsonNullable.of(user.getInterests()))")
    @Mapping(target = "fotoPerfil",expression = "java(mapBytetoUri(user.getPhoto()))")
    @Mapping(source = "verifiedEmail", target = "correoVerificado")
    PerfilUsuarioDto userToUserDto(User user);
    // Método para parsear unn byte Array en un URI, usando Base64
    default JsonNullable<URI> mapBytetoUri(byte[] photo) {
        try {
            if (photo.length != 0){
                if (photo.length >= 2 && photo[0] == (byte) 0xFF && photo[1] == (byte) 0xD8) {
                    String jpegBase64 = Base64.getEncoder().encodeToString(photo);
                    URI jpegUri = new URI("data:image/jpeg;base64," + jpegBase64);
                    return JsonNullable.of(jpegUri);
                } else {
                    String pngBase64 = Base64.getEncoder().encodeToString(photo);
                    URI pngUri = new URI("data:image/png;base64," + pngBase64);
                    return JsonNullable.of(pngUri);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        return JsonNullable.undefined();
    }

}
