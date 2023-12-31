package io.github.projektalmanac.amoxtli.backend.controller;

import io.github.projektalmanac.amoxtli.backend.generated.api.LibrosApi;
import io.github.projektalmanac.amoxtli.backend.generated.model.DetallesLibroDto;
import io.github.projektalmanac.amoxtli.backend.generated.model.LibroConDuenosDto;
import io.github.projektalmanac.amoxtli.backend.generated.model.PaginaLibrosDto;
import io.github.projektalmanac.amoxtli.backend.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookController implements LibrosApi {

    @Autowired
    private BookService bookService;

    @Override
    public ResponseEntity<DetallesLibroDto> getDetallesLibro(String isbn) {
        
        DetallesLibroDto result = bookService.getDetallesLibro(isbn); 
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @Override
    public ResponseEntity<LibroConDuenosDto> getLibro(String isbn) {
        LibroConDuenosDto lib = bookService.getLibroConDuenos(isbn);

        return ResponseEntity.status(HttpStatus.OK).body(lib);
    }

    @Override
    public ResponseEntity<PaginaLibrosDto> getLibros(Integer pagina) {

        // Establecer el tamaño de la página (por ejemplo, 10 resultados por página)
        int tamanoPagina = 10;

        // Obtener los libros de la página especificada
        var libros = bookService.getLibros(pagina, tamanoPagina);

        return ResponseEntity.status(HttpStatus.OK).body(libros);

    }

}
