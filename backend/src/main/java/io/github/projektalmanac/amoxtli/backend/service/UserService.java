package io.github.projektalmanac.amoxtli.backend.service;

import java.util.List;
import java.util.Optional;

import com.google.api.services.books.model.Volume.VolumeInfo;
import io.github.projektalmanac.amoxtli.backend.entity.*;
import io.github.projektalmanac.amoxtli.backend.generated.model.*;
import io.github.projektalmanac.amoxtli.backend.mapper.BookMapper;
import io.github.projektalmanac.amoxtli.backend.repository.*;
import io.github.projektalmanac.amoxtli.backend.service.consume.GoogleBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.projektalmanac.amoxtli.backend.entity.User;
import io.github.projektalmanac.amoxtli.backend.exception.*;
import io.github.projektalmanac.amoxtli.backend.mapper.UserMapper;
import io.github.projektalmanac.amoxtli.backend.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;

import java.io.IOException;

@Service
public class UserService {

    @Autowired
    private GoogleBookService googleBookService;

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public LibrosUsuarioDto getLibrosUsuario(Integer id) {

        Optional<User> userOpt = userRepository.findById(id);

        if (userOpt.isEmpty()) {
            throw new UserNotFoundException(id);
        }

        User user = userOpt.get();
        List<Book> books = user.getBooks();

        if (books.isEmpty()) {
            throw new EmptyResourceException();
        }

        GoogleBookService googleBookService = new GoogleBookService();

        List<VolumeInfo> librosGoogleBooks = googleBookService.searchVolumeInfo(books);

        LibrosUsuarioDto booksDto = BookMapper.INSTANCE.toLibrosUsuarioDto(books, librosGoogleBooks);

        return booksDto;

    }

    public PerfilUsuarioDto getUsuario(Integer id) {

        User user = this.userRepository.getUserById(id);
        if (user == null) {
            throw new UserNotFoundException(id);
        }
        if (!user.isVerifiedEmail()) {
            throw new EmailUserNotVerificationException(id);
        }

        return UserMapper.INSTANCE.userToUserDto(user);
    }

    public PerfilUsuarioDto actualizaUsuario(Integer idUser, PerfilUsuarioDto perfilUsuarioDto) {

        User user = this.userRepository.getUserById(idUser);

        if (user == null) {
            throw new UserNotFoundException(idUser);
        }
        if (!user.isVerifiedEmail()) {
            throw new EmailUserNotVerificationException(idUser);
        }

        User userAux = UserMapper.INSTANCE.usuarioDtoToUser(perfilUsuarioDto);
        userAux.setId(idUser);
        userAux.setPhoto(user.getPhoto());
        user = this.userRepository.save(userAux);

        return UserMapper.INSTANCE.userToUserDtoWithoutPhoto(user);
    }

    public void actualizaFoto(Integer idUser, Resource body) throws IOException {

        User user = this.userRepository.getUserById(idUser);
        if (user == null) {
            throw new UserNotFoundException(idUser);
        }
        if (!user.isVerifiedEmail()) {
            throw new EmailUserNotVerificationException(idUser);
        }

        byte[] imagen = body.getInputStream().readAllBytes();

        user.setPhoto(imagen);

        this.userRepository.save(user);
    }

}