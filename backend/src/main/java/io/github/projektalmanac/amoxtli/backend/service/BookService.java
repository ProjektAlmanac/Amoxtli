package io.github.projektalmanac.amoxtli.backend.service;

import org.springframework.stereotype.Service;
import io.github.projektalmanac.amoxtli.backend.generated.model.*;
import io.github.projektalmanac.amoxtli.backend.service.GoogleBookService; 

@Service
public class BookService {

    @Autowired
    private GoogleBookService googleBookService;

    public DetallesLibroDto getDetallesLibro(String isbn) {

        if(isbn == null || isbn.isEmpty()){
            throw new ResourceNotFoundException("ISBN no proporcionado o está vacío. Se requiere un ISBN válido para obtener detalles del libro.");
        }

        GoogleBookService googleBookService = new GoogleBookService();

        VolumeInfo libroGoogleBooks = getVolumeInfoByIsbn(isbn); 

        DetallesLibroDto detallesLibroDto = toDetallesLibroDto(isbn, libroGoogleBooks); 

        return detallesLibroDto;
    }
}
