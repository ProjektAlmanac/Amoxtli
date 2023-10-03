package io.github.projektalmanac.amoxtli.backend.service;

import io.github.projektalmanac.amoxtli.backend.entity.User;
import io.github.projektalmanac.amoxtli.backend.exception.*;
import io.github.projektalmanac.amoxtli.backend.generated.model.PerfilUsuarioDto;
import io.github.projektalmanac.amoxtli.backend.mapper.UserMapper;
import io.github.projektalmanac.amoxtli.backend.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class UserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }
    public PerfilUsuarioDto getUsuario(Integer id) {

        LOGGER.info(">>PerfilUsuarioDto {}", id);
        User user = this.userRepository.getUserById(id);
        if (user == null) {
            throw new UserNotFoundException(id);
        }
        if (!user.isVerifiedEmail()) {
            throw new UnauthenticatedUserException(id);
        }
        PerfilUsuarioDto perfilUsuarioDto = UserMapper.INSTANCE.userToUserDto(user);

        if (perfilUsuarioDto == null) {
            throw new IllegalArgumentException("Error, el mapeo no fue exitoso");
        }

        LOGGER.info(">>PerfilUsuarioDto {}", id);
        return perfilUsuarioDto;
    }

    public PerfilUsuarioDto actualizaUsuario(Integer id, PerfilUsuarioDto perfilUsuarioDto){
        LOGGER.info(">>actualizaUsuario {}", id);
        if(!id.equals(perfilUsuarioDto.getId())){
            throw new BadRequestException(id);
        }
        User user = this.userRepository.getUserById(id);
        if(user == null) {
            throw new UserNotFoundException(id);
        }
        if(!user.isVerifiedEmail()) {
            throw new UnauthenticatedUserException(id);
        }
        User userAux = UserMapper.INSTANCE.usuarioDtoToUser(perfilUsuarioDto);
        if(!user.getEmail().equals(userAux.getEmail())){
            user.setVerifiedEmail(false);
        }
        user.setName(userAux.getName());
        user.setLastName(userAux.getLastName());
        user.setEmail(userAux.getEmail());
        user.setPhone(userAux.getPhone());
        user.setPhotoDescription(userAux.getPhotoDescription());
        user.setInterests(userAux.getInterests());
        user = this.userRepository.save(user);

        PerfilUsuarioDto resultChange = UserMapper.INSTANCE.userToUserDto(user);

        if (resultChange == null){
            throw new IllegalArgumentException("Error, el mapeo no fue exitoso");
        }
        LOGGER.info("<<actualizaUsuario {}", id);
        return resultChange;
    }

    public void actualizaFoto(String id, Resource body) throws IOException {
        LOGGER.info(">>actualizaFoto {}",id);
        Integer idUser = null;
        try {
            idUser = Integer.parseInt(id);
        }catch (Exception e) {
            LOGGER.info("Error al parsear el id {}",id);
            throw new IdUserNotWorkException();
        }
        User user = this.userRepository.getUserById(idUser);
        if (user == null){
            throw new UserNotFoundException(idUser);
        }
        if (!user.isVerifiedEmail()) {
            throw new UnauthenticatedUserException(idUser);
        }

        byte[] imagen = body.getInputStream().readAllBytes();

        user.setPhoto(imagen);
        user = this.userRepository.save(user);

        if (user == null){
            throw new UserNotFoundException(idUser);
        }
        LOGGER.info("<<actualizaFoto {}",id);
    }



}