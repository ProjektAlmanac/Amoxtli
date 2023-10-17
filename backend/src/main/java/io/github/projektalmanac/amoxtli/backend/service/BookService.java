package io.github.projektalmanac.amoxtli.backend.service;

import com.google.api.services.books.model.Volume.VolumeInfo;
import io.github.projektalmanac.amoxtli.backend.entity.Book;
import io.github.projektalmanac.amoxtli.backend.entity.Exchange;
import io.github.projektalmanac.amoxtli.backend.entity.User;
import io.github.projektalmanac.amoxtli.backend.enums.Status;
import io.github.projektalmanac.amoxtli.backend.exception.BookAlreadyExistsException;
import io.github.projektalmanac.amoxtli.backend.exception.ResourceNotFoundException;
import io.github.projektalmanac.amoxtli.backend.exception.UserNotFoundException;
import io.github.projektalmanac.amoxtli.backend.mapper.BookMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import io.github.projektalmanac.amoxtli.backend.generated.model.*;
import io.github.projektalmanac.amoxtli.backend.repository.*;
import io.github.projektalmanac.amoxtli.backend.service.consume.GoogleBookService;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private GoogleBookService googleBookService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ExchangeRepository exchangeRepository;

    public DetallesLibroDto getDetallesLibro(String isbn) {

        if(isbn == null || isbn.isEmpty()){
            throw new ResourceNotFoundException("ISBN no proporcionado o está vacío. Se requiere un ISBN válido para obtener detalles del libro.");
        }

        GoogleBookService googleBookService = new GoogleBookService();

        VolumeInfo libroGoogleBooks = googleBookService.getVolumeInfoByIsbn(isbn);

        DetallesLibroDto detallesLibroDto = BookMapper.INSTANCE.toDetallesLibroDto(isbn, libroGoogleBooks);

        return detallesLibroDto;
    }


    public LibroRegistradoDto addLibro(Integer id, LibroRegistradoDto libroRegistradoDto) {

        Optional<User> userOpt = userRepository.findById(id);

        if(userOpt.isEmpty()){
            throw new UserNotFoundException(id);
        }

        User user = userOpt.get();

		// Regla de negocio: No se permite agregar dos libros con el mismo isbn en el mismo perfil
		boolean result = userRepository.existsBookByIsbnAndUserId(libroRegistradoDto.getIsbn(), (int)user.getId());
			
		if (result) {
			throw new BookAlreadyExistsException(); //Revisar el codigo del error
		}

        // Se verifica si el ISBN es válido, en caso de no serlo, se mandara una excepción desde el servicio de Google Books
        googleBookService.getVolumeInfoByIsbn(libroRegistradoDto.getIsbn());

        Book book = BookMapper.INSTANCE.toBook(libroRegistradoDto);

        bookRepository.save(book); 
        user.addBook(book); 
        userRepository.save(user);

        book = bookRepository.findByIsbn(book.getIsbn()); 

        LibroRegistradoDto libroDto = BookMapper.INSTANCE.toLibroRegistradoDto(book);
        
        return libroDto;
    }

}
