package io.github.projektalmanac.amoxtli.backend.controller;

import io.github.projektalmanac.amoxtli.backend.generated.api.LibrosApi;
import io.github.projektalmanac.amoxtli.backend.generated.model.*;
import io.github.projektalmanac.amoxtli.backend.service.*;
import io.github.projektalmanac.amoxtli.backend.generated.model.DetallesLibroDto;
import io.github.projektalmanac.amoxtli.backend.generated.model.LibroConDuenosDto;
import io.github.projektalmanac.amoxtli.backend.generated.model.PaginaLibrosDto;
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
        return null;
    }

    @Override
    public ResponseEntity<PaginaLibrosDto> getLibros(Integer pagina) {
        return null;
    }
}
