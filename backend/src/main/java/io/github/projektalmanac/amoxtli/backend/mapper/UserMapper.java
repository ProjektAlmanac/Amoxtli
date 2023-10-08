package io.github.projektalmanac.amoxtli.backend.mapper;

import io.github.projektalmanac.amoxtli.backend.entity.User;
import io.github.projektalmanac.amoxtli.backend.generated.model.UsuarioDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);


    @Mapping(source = "correo", target = "email")
    @Mapping(source = "nombre", target = "name")
    @Mapping(source = "apellildos", target = "lastName")
    @Mapping(source = "password", target = "passwordHash")
    User usuarioDtoToUser(UsuarioDto user);
}
