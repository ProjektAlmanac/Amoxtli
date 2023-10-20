package io.github.projektalmanac.amoxtli.backend.service;

import com.google.api.services.books.model.Volume;
import io.github.projektalmanac.amoxtli.backend.entity.Book;
import io.github.projektalmanac.amoxtli.backend.entity.User;
import io.github.projektalmanac.amoxtli.backend.exception.ResourceNotFoundException;
import io.github.projektalmanac.amoxtli.backend.exception.UserNotFoundException;
import io.github.projektalmanac.amoxtli.backend.generated.model.*;
import io.github.projektalmanac.amoxtli.backend.repository.BookRepository;
import io.github.projektalmanac.amoxtli.backend.repository.UserRepository;
import io.github.projektalmanac.amoxtli.backend.service.consume.GoogleBookService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.net.URI;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.AdditionalAnswers.returnsFirstArg;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private GoogleBookService googleBookService;

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;


    private User user;
    private User user1;
    private Book book1;
    private Volume.VolumeInfo volumeInfo;
    private Volume.VolumeInfo.ImageLinks imageLinks;
    private LibroRegistradoDto libroRegistradoDto, libroRegistradoDto2;

    @BeforeEach
    void setUp() {

        user = new User();
        user.setId(1);
        user.setName("Eduardo");
        user.setLastName("Castro");
        user.setEmail("ecastro@gmail.com");
        user.setPhone("58587599");
        user.setVerifiedEmail(false);

        book1 = new Book();
        book1.setId(1);
        book1.setIsbn("1111111111");
        book1.setDescription("Casi nuevo el libro");
        book1.setOwner(user);

        volumeInfo = new Volume.VolumeInfo();
        imageLinks = new Volume.VolumeInfo.ImageLinks();

        volumeInfo.setAuthors(Arrays.asList("Autor1", "Autor2", "Autor3"));
        volumeInfo.setTitle("Titulo 1");
        imageLinks.setMedium("http://books.google.com/books/content?id=O9ztAAAAMAAJ&printsec=frontcover&img=1&zoom=1&source=gbs_api");
        volumeInfo.setImageLinks(imageLinks);
        volumeInfo.setCategories(Arrays.asList("Categoria1", "Categoria2"));
        volumeInfo.setPublisher("Editorial 1");
        volumeInfo.setDescription("Sinopsis del libro");
        volumeInfo.setLanguage("Español");
        volumeInfo.setPublishedDate("1990");

        libroRegistradoDto = new LibroRegistradoDto();
        libroRegistradoDto.setId(1);
        libroRegistradoDto.setIsbn("1111111111");
        libroRegistradoDto.setDescripcion("Casi nuevo el libro");

        libroRegistradoDto2 = new LibroRegistradoDto();
        libroRegistradoDto2.setIsbn("ashwydr");

        user1 = new User();
        user1.setId(1);
        user1.setName("Eduardo");
        user1.setLastName("Castro");
        user1.setEmail("ecastro@gmail.com");
        user1.setPhone("58587599");
        user1.setVerifiedEmail(false);
        user1.addBook(book1);

    }

    @Test
    void getDetallesLibro() {

        //Caso 1: El ISBN no es proporcionado
        final String ISBN = null;
        assertThrows(ResourceNotFoundException.class, () -> {
            bookService.getDetallesLibro(ISBN);
        });

        //Caso 2: El ISBN no es válido

        final String ISBN2 = "ashwydr";
        when(googleBookService.getVolumeInfoByIsbn(ISBN2)).thenThrow(new ResourceNotFoundException("Libro con ISBN " + ISBN2 + " no encontrado en Google Books."));
        assertThrows(ResourceNotFoundException.class, () -> {
            bookService.getDetallesLibro(ISBN2);
        });

        //Caso 3: Caso de éxito donde se obtine adecuadamente los detalles de un libro

        final String ISBN3 = "1111111111";
        LocalDate fechaEsperada = LocalDate.ofYearDay(Integer.parseInt(volumeInfo.getPublishedDate()), 1);

        when(googleBookService.getVolumeInfoByIsbn(ISBN3)).thenReturn(volumeInfo);
        DetallesLibroDto result = bookService.getDetallesLibro(ISBN3);

        Assertions.assertEquals(ISBN3, result.getIsbn());
        Assertions.assertEquals(volumeInfo.getAuthors().get(0), result.getAutor());
        Assertions.assertEquals(volumeInfo.getTitle(), result.getTitulo());
        Assertions.assertEquals(URI.create(imageLinks.getMedium()), result.getUrlPortada());
        Assertions.assertEquals(volumeInfo.getCategories(), result.getGeneros());
        Assertions.assertEquals(volumeInfo.getDescription(), result.getSinopsis());
        Assertions.assertEquals(volumeInfo.getPublisher(), result.getEditorial());
        Assertions.assertEquals(volumeInfo.getLanguage(), result.getIdioma());
        Assertions.assertEquals(fechaEsperada, result.getFechaPublicacion());
    }

    @Test
    void addLibro(){
        //Caso 1: El usuario no existe
        when(userRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> {
            bookService.addLibro(1, libroRegistradoDto);
        });

        //Caso 2: El ISBN no es valido
        when(userRepository.findById(2)).thenReturn(Optional.of(user));
        when(googleBookService.getVolumeInfoByIsbn(libroRegistradoDto2.getIsbn())).thenThrow(new ResourceNotFoundException("Libro con ISBN " + libroRegistradoDto.getIsbn() + " no encontrado en Google Books 2."));
        assertThrows(ResourceNotFoundException.class, () -> {
            bookService.addLibro(2, libroRegistradoDto2);
        });

        //Caso 3: Caso de exito donde se registra correctamente un libro por un usuario

        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(googleBookService.getVolumeInfoByIsbn(libroRegistradoDto.getIsbn())).thenReturn(volumeInfo);
        when(bookRepository.save(any(Book.class))).then(returnsFirstArg());

        LibroRegistradoDto result = bookService.addLibro(1, libroRegistradoDto);

        Assertions.assertEquals(book1.getId(), result.getId());
        Assertions.assertEquals(book1.getIsbn(), result.getIsbn());
        Assertions.assertEquals(book1.getDescription(), result.getDescripcion());
    }
}