package io.github.projektalmanac.amoxtli.backend.service;

import java.util.List;
import java.util.Optional;


import io.github.projektalmanac.amoxtli.backend.entity.*;
import io.github.projektalmanac.amoxtli.backend.exception.UserNotFoundException;
import io.github.projektalmanac.amoxtli.backend.exception.EmptyResourceException;
import io.github.projektalmanac.amoxtli.backend.generated.model.*;
import io.github.projektalmanac.amoxtli.backend.mapper.BookMapper;
import io.github.projektalmanac.amoxtli.backend.repository.*;
import io.github.projektalmanac.amoxtli.backend.service.GoogleBookService; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GoogleBookService googleBookService;

    public LibrosUsuarioDto getLibrosUsuario(Integer id){

        Optional <User> userOpt = userRepository.findById(id);

        if(!userOpt.isPresent()){
            throw new UserNotFoundException(id);
        }

        User user = userOpt.get();
        List<Book> books = user.getBooks();

        if(books.isEmpty()){
            throw new EmptyResourceException();
        }

        GoogleBookService googleBookService = new GoogleBookService();

        List<VolumeInfo> librosGoogleBooks = googleBookService.searchVolumeInfo(books);

        LibrosUsuarioDto booksDto = BookMapper.INSTANCE.toLibrosUsuarioDto(books, librosGoogleBooks);
        
        return booksDto;

    }
}
