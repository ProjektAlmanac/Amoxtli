package io.github.projektalmanac.amoxtli.backend.generated.controller;

import io.github.projektalmanac.amoxtli.backend.generated.api.LibrosApi;
import io.github.projektalmanac.amoxtli.backend.generated.model.DetallesLibroDto;
import io.github.projektalmanac.amoxtli.backend.generated.model.LibroConDuenosDto;
import io.github.projektalmanac.amoxtli.backend.generated.model.PaginaLibrosDto;
import org.springframework.http.ResponseEntity;

public class BookController implements LibrosApi {
    @Override
    public ResponseEntity<DetallesLibroDto> getDetallesLibro(String isbn) {
        return null;
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
