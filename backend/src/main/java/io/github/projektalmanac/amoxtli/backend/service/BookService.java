package io.github.projektalmanac.amoxtli.backend.service;

import com.google.api.services.books.model.Volume.VolumeInfo;
import io.github.projektalmanac.amoxtli.backend.entity.Book;
import io.github.projektalmanac.amoxtli.backend.entity.User;
import io.github.projektalmanac.amoxtli.backend.exception.BookAlreadyExistsException;
import io.github.projektalmanac.amoxtli.backend.exception.ResourceNotFoundException;
import io.github.projektalmanac.amoxtli.backend.exception.UserNotFoundException;
import io.github.projektalmanac.amoxtli.backend.generated.model.DetallesLibroDto;
import io.github.projektalmanac.amoxtli.backend.generated.model.DuenoDto;
import io.github.projektalmanac.amoxtli.backend.generated.model.LibroConDuenosDto;
import io.github.projektalmanac.amoxtli.backend.generated.model.LibroRegistradoDto;
import io.github.projektalmanac.amoxtli.backend.mapper.BookMapper;
import io.github.projektalmanac.amoxtli.backend.repository.BookRepository;
import io.github.projektalmanac.amoxtli.backend.repository.UserRepository;
import io.github.projektalmanac.amoxtli.backend.service.consume.GoogleBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private GoogleBookService googleBookService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    public DetallesLibroDto getDetallesLibro(String isbn) {

        if (isbn == null || isbn.isEmpty()) {
            throw new ResourceNotFoundException("ISBN no proporcionado o está vacío. Se requiere un ISBN válido para obtener detalles del libro.");
        }

        VolumeInfo libroGoogleBooks = googleBookService.getVolumeInfoByIsbn(isbn);

        return BookMapper.INSTANCE.toDetallesLibroDto(isbn, libroGoogleBooks);
    }


    public LibroRegistradoDto addLibro(Integer id, LibroRegistradoDto libroRegistradoDto) {

        Optional<User> userOpt = userRepository.findById(id);

        if (userOpt.isEmpty()) throw new UserNotFoundException(id);

        User user = userOpt.get();

        // Regla de negocio: No se permite agregar dos libros con el mismo isbn en el mismo perfil
        boolean result = userRepository.existsBookByIsbnAndUserId(libroRegistradoDto.getIsbn(), user.getId());

        if (result) throw new BookAlreadyExistsException(); //Revisar el código del error

        // Se verifica si el ISBN es válido, en caso de no serlo, se mandará una excepción desde el servicio de Google Books
        googleBookService.getVolumeInfoByIsbn(libroRegistradoDto.getIsbn());

        Book book = BookMapper.INSTANCE.toBook(libroRegistradoDto);

        bookRepository.save(book);
        user.addBook(book);
        userRepository.save(user);

        book = bookRepository.findByIsbn(book.getIsbn());

        return BookMapper.INSTANCE.toLibroRegistradoDto(book);
    }

    public LibroConDuenosDto getLibroConDuenos(String isbn) {
        // obtener los detalles del libro
        VolumeInfo libroGoogleBooks = googleBookService.getVolumeInfoByIsbn(isbn);

        // Verificar si el libro existe en la base de datos
        Book book = bookRepository.findFirstByIsbn(isbn);

        if (book == null) {
            throw new ResourceNotFoundException("Libro no encontrado con el ISBN proporcionado: " + isbn);
        }

        // Obtener los dueños del libro
        List<User> duenos = userRepository.findUsersByBookIsbn(isbn);
        List<DuenoDto> duenosDtoList = new ArrayList<>();

        for (User dueno : duenos) {
            DuenoDto duenoDto = new DuenoDto();
            duenoDto.setId(dueno.getId());
            duenoDto.setNombre(dueno.getName());
            duenoDto.setApellido(dueno.getLastName());
            duenoDto.setFoto(dueno.getPhoto());
            duenosDtoList.add(duenoDto);
        }


        LibroConDuenosDto libroConDuenosDto;


        libroConDuenosDto = BookMapper.INSTANCE.toLibroConDuenosDto(isbn, libroGoogleBooks);
        libroConDuenosDto.setDuenos(duenosDtoList);

        // Retornar el libro mapeado con la información de los dueños
        return libroConDuenosDto;

    }

}
