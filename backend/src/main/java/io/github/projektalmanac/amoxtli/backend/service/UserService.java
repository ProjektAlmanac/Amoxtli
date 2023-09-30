package io.github.projektalmanac.amoxtli.backend.service;

import io.github.projektalmanac.amoxtli.backend.entity.User;
import io.github.projektalmanac.amoxtli.backend.exception.UnauthenticatedUserException;
import io.github.projektalmanac.amoxtli.backend.exception.UserNotFoundException;
import io.github.projektalmanac.amoxtli.backend.generated.model.JSONPatchRequestAddReplaceTestDto;
import io.github.projektalmanac.amoxtli.backend.generated.model.JSONPatchRequestRemoveDto;
import io.github.projektalmanac.amoxtli.backend.generated.model.PatchRequestInnerDto;
import io.github.projektalmanac.amoxtli.backend.generated.model.PerfilUsuarioDto;
import io.github.projektalmanac.amoxtli.backend.mapper.UserMapper;
import io.github.projektalmanac.amoxtli.backend.repository.UserRepository;
import org.openapitools.jackson.nullable.JsonNullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

@Service
public class UserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    private UserRepository userRepository;

    @Autowired
    UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }
    public PerfilUsuarioDto getUsuario(Integer id) {
        User user1 = new User();
        user1.setName("Eduardo");
        user1.setLastName("Castro Ramón");
        user1.setEmail("ecastro@gmail.com");
        user1.setVerifiedEmail(true);
        user1.setInterests("Me llamo Eduardo, un gusto conocerte");
        this.userRepository.save(user1);

        LOGGER.info(">>PerfilUsuarioDto {}", id);
        User user = this.userRepository.getUserById(id);
        LOGGER.info("user",user);
        if (user == null) {
            throw new UserNotFoundException(id);
        }
        if (!user.isVerifiedEmail()) {
            throw new UnauthenticatedUserException(id);
        }
        PerfilUsuarioDto perfilUsuarioDto = UserMapper.INSTANCE.userToUserDto(user);
        if (perfilUsuarioDto == null) {
            throw new IllegalArgumentException("No se pudo completar el mapeo");
        }

        LOGGER.info(">>PerfilUsuarioDto {}", id);
        return perfilUsuarioDto;
    }

    public PerfilUsuarioDto actualizaUsuario(Integer id, List<PatchRequestInnerDto> patchRequestInnerDto){

        User user = this.userRepository.getUserById(id);
        if(user == null) {
            throw new UserNotFoundException(id);
        }
        for (var pathOperation : patchRequestInnerDto){
            if (pathOperation instanceof JSONPatchRequestRemoveDto){
                LOGGER.info("Es una instancia de Path request remove DTO");
                var removeOperation = (JSONPatchRequestRemoveDto) pathOperation;
                removeOperation.getPath().equals("id");
                switch (removeOperation.getPath()) {
                    case "phone":
                        user.setPhone(null);
                        break;
                }
            }
        }
        return null;
    }


}