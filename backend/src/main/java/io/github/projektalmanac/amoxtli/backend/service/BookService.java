package io.github.projektalmanac.amoxtli.backend.service;

import com.google.api.services.books.model.Volume.VolumeInfo;
import io.github.projektalmanac.amoxtli.backend.entity.Book;
import io.github.projektalmanac.amoxtli.backend.entity.User;
import io.github.projektalmanac.amoxtli.backend.exception.BookAlreadyExistsException;
import io.github.projektalmanac.amoxtli.backend.exception.ResourceNotFoundException;
import io.github.projektalmanac.amoxtli.backend.exception.UserNotFoundException;
import io.github.projektalmanac.amoxtli.backend.generated.model.*;
import io.github.projektalmanac.amoxtli.backend.mapper.BookMapper;
import io.github.projektalmanac.amoxtli.backend.mapper.UserMapper;
import io.github.projektalmanac.amoxtli.backend.repository.BookRepository;
import io.github.projektalmanac.amoxtli.backend.repository.UserRepository;
import io.github.projektalmanac.amoxtli.backend.service.consume.GoogleBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

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

        libroRegistradoDto.setIsbn(libroRegistradoDto.getIsbn().replace("-",""));
        // Regla de negocio: No se permite agregar dos libros con el mismo isbn en el mismo perfil
        boolean result = userRepository.existsBookByIsbnAndUserId(libroRegistradoDto.getIsbn(), user.getId());

        if (result) throw new BookAlreadyExistsException(); //Revisar el código del error

        // Se verifica si el ISBN es válido, en caso de no serlo, se mandará una excepción desde el servicio de Google Books
        googleBookService.getVolumeInfoByIsbn(libroRegistradoDto.getIsbn());

        Book book = BookMapper.INSTANCE.toBook(libroRegistradoDto);

        book = bookRepository.save(book);
        user.addBook(book);
        userRepository.save(user);


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
           // duenoDto.setId(dueno.getId());
           // duenoDto.setNombre(dueno.getName());
           // duenoDto.setApellido(dueno.getLastName());
            //duenoDto.setFoto(dueno.getPhoto());
            duenoDto = UserMapper.INSTANCE.userToUserDto1(dueno);
            duenosDtoList.add(duenoDto);
        }


        LibroConDuenosDto libroConDuenosDto;


        libroConDuenosDto = BookMapper.INSTANCE.toLibroConDuenosDto(isbn, libroGoogleBooks);
        libroConDuenosDto.setDuenos(duenosDtoList);

        // Retornar el libro mapeado con la información de los dueños
        return libroConDuenosDto;

    }


    public List<InfoBasicaLibroDto> getLibros(Integer pagina, Integer tamanoPagina) {

        // Utiliza Spring Data JPA para obtener los resultados paginados del repositorio
        Pageable pageable = PageRequest.of(pagina - 1, tamanoPagina); // Spring Data JPA usa índices basados en 0, por eso se resta 1 a la página
        Page<Book> bookPage = bookRepository.findAll(pageable);

        Set<InfoBasicaLibroDto> librosDtoSet = new HashSet<>();

        // Convierte la página de libros a una lista de InfoBasicaLibroDto
        List<InfoBasicaLibroDto> libros = new ArrayList<>();
        for (Book book : bookPage.getContent()) {
            VolumeInfo libroGoogleBooks = googleBookService.getVolumeInfoByIsbn(book.getIsbn());
            InfoBasicaLibroDto infoBasicaLibroDto = BookMapper.INSTANCE.toInfoBasicaLibroDto(book.getIsbn(), libroGoogleBooks);
            librosDtoSet.add(infoBasicaLibroDto);
        }
        // Convertir el HashSet a ArrayList
        List<InfoBasicaLibroDto> librosDto = new ArrayList<>(librosDtoSet);

        return librosDto;


        /*
        // Obtener todos los libros de la base de datos
        List<Book> books = bookRepository.findAll();

        Set<InfoBasicaLibroDto> librosDtoSet = new HashSet<>();

        for (Book book : books) {
            VolumeInfo libroGoogleBooks = googleBookService.getVolumeInfoByIsbn(book.getIsbn());

            InfoBasicaLibroDto infoBasicaLibroDto = BookMapper.INSTANCE.toInfoBasicaLibroDto(book.getIsbn(), libroGoogleBooks);
            librosDtoSet.add(infoBasicaLibroDto);
        }

        // Convertir el HashSet a ArrayList
        List<InfoBasicaLibroDto> librosDto = new ArrayList<>(librosDtoSet);

        return librosDto;*/
    }

    public PaginaLibrosDto getLibrospag(Integer pagina, Integer tamanoPagina, List<InfoBasicaLibroDto> libros) {
        // Calcular página anterior y página siguiente
        int pagAnterior = (pagina > 1) ? pagina - 1 : 1;
        int pagSiguiente = pagina + 1;

        // Crear objeto PaginaLibrosDto
        PaginaLibrosDto paginaLibrosDto = new PaginaLibrosDto();
        paginaLibrosDto.setPagAnterior(pagAnterior);
        paginaLibrosDto.setPagSiguiente(pagSiguiente);
        paginaLibrosDto.setResultados(libros.size());
        paginaLibrosDto.setLibros(libros);

        return paginaLibrosDto;
    }
}
