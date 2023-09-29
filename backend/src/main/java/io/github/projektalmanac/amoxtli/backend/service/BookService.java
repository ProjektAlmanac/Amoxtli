package io.github.projektalmanac.amoxtli.backend.service;

import org.springframework.stereotype.Service;
import io.github.projektalmanac.amoxtli.backend.generated.model.*;
import io.github.projektalmanac.amoxtli.backend.repository.*;
import io.github.projektalmanac.amoxtli.backend.service.GoogleBookService; 

@Service
public class BookService {

    @Autowired
    private GoogleBookService googleBookService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    public DetallesLibroDto getDetallesLibro(String isbn) {

        if(isbn == null || isbn.isEmpty()){
            throw new ResourceNotFoundException("ISBN no proporcionado o está vacío. Se requiere un ISBN válido para obtener detalles del libro.");
        }

        GoogleBookService googleBookService = new GoogleBookService();

        VolumeInfo libroGoogleBooks = getVolumeInfoByIsbn(isbn); 

        DetallesLibroDto detallesLibroDto = toDetallesLibroDto(isbn, libroGoogleBooks); 

        return detallesLibroDto;
    }

    public LibroRegistradoDto addLibro(Integer id, LibroRegistradoDto libroRegistradoDto) {

        Optional <User> userOpt = userRepository.findById(id);

        if(!userOpt.isPresent()){
            throw new UserNotFoundException(id);
        }

        User user = userOpt.get();

		// Regla de negocio: No se permite agregar dos libros con el mismo isbn en el mismo perfil
		boolean result = userRepository.existsBookByIsbnAndUserId(libroRegistradoDto.getIsbn(), user.getId());
			
		if (result) {
			throw new BookAlreadyExistsException(); //Revisar el codigo del error 
		}

        Book book = toBook(libroRegistradoDto);

        bookRepository.save(book); 
        user.addBook(book); 
        userRepository.save(user);

        book = bookRepository.findByIsbn(book.getIsbn()); 

        LibroRegistradoDto libroDto = toLibroRegistradoDto(book);
        
        return libroDto;
    }
}
