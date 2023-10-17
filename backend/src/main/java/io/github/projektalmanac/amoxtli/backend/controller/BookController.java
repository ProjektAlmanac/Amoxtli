package io.github.projektalmanac.amoxtli.backend.controller;

import io.github.projektalmanac.amoxtli.backend.generated.api.LibrosApi;
import io.github.projektalmanac.amoxtli.backend.generated.model.DetallesLibroDto;
import io.github.projektalmanac.amoxtli.backend.generated.model.InfoBasicaLibroDto;
import io.github.projektalmanac.amoxtli.backend.generated.model.LibroConDuenosDto;
import io.github.projektalmanac.amoxtli.backend.generated.model.PaginaLibrosDto;
import io.github.projektalmanac.amoxtli.backend.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

        // Obtener la lista de libros de la página especificada
        List<InfoBasicaLibroDto> libros = bookService.getLibros(pagina, tamanoPagina);


        // Retornar la respuesta con el objeto PaginaLibrosDto
        PaginaLibrosDto paginaLibrosDto = bookService.getLibrospag(pagina, tamanoPagina, libros);
        return ResponseEntity.status(HttpStatus.OK).body(paginaLibrosDto);

    }

}
