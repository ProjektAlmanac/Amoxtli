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

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }
    public PerfilUsuarioDto getUsuario(Integer id) {

        User user = this.userRepository.getUserById(id);
        if (user == null) {
            throw new UserNotFoundException(id);
        }
        if (!user.isVerifiedEmail()) {
            throw new EmailUserNotVerificationException(id);
        }
        PerfilUsuarioDto perfilUsuarioDto = UserMapper.INSTANCE.userToUserDto(user);

        return perfilUsuarioDto;
    }

    public PerfilUsuarioDto actualizaUsuario(Integer idUser, PerfilUsuarioDto perfilUsuarioDto){

        User user = this.userRepository.getUserById(idUser);
        if(user == null) {
            throw new UserNotFoundException(idUser);
        }
        if(!user.isVerifiedEmail()) {
            throw new EmailUserNotVerificationException(idUser);
        }
        User userAux = UserMapper.INSTANCE.usuarioDtoToUser(perfilUsuarioDto);

        user.setId(idUser);
        user.setName(userAux.getName());
        user.setLastName(userAux.getLastName());
        user.setEmail(userAux.getEmail());
        user.setPhone(userAux.getPhone());
        user.setPhotoDescription(userAux.getPhotoDescription());
        user.setInterests(userAux.getInterests());
        user.setEmail(userAux.getEmail());
        user.setVerifiedEmail(userAux.isVerifiedEmail());
        user = this.userRepository.save(user);

        PerfilUsuarioDto resultChange = UserMapper.INSTANCE.userToUserDto(user);

        return resultChange;
    }

    public void actualizaFoto(Integer idUser, Resource body) throws IOException {

        User user = this.userRepository.getUserById(idUser);
        if (user == null){
            throw new UserNotFoundException(idUser);
        }
        if (!user.isVerifiedEmail()) {
            throw new EmailUserNotVerificationException(idUser);
        }

        byte[] imagen = body.getInputStream().readAllBytes();

        user.setPhoto(imagen);
        user = this.userRepository.save(user);


    }



}