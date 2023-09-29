package io.github.projektalmanac.amoxtli.backend.mapper;

import io.github.projektalmanac.amoxtli.backend.entity.Book;
import io.github.projektalmanac.amoxtli.backend.entity.User;
import io.github.projektalmanac.amoxtli.backend.generated.model.LibroRegistradoConDetallesDto;
import io.github.projektalmanac.amoxtli.backend.generated.model.LibrosUsuarioDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface BookMapper {
    BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);

    @Mapping(target = "id", source = "book.id")
    @Mapping(target = "isbn", source = "book.isbn")
    @Mapping(target = "titulo", source = "libroGoogleBooks.title")
    @Mapping(target = "autor", expression = "java(libroGoogleBooks.getAuthors().get(0))")
    @Mapping(target = "urlPortada", expression = "java(libroGoogleBooks.getImageLinks() != null ? libroGoogleBooks.getImageLinks().getMedium() : null)")
    LibroRegistradoConDetallesDto toLibroRegistradoConDetallesDto(Book book, VolumeInfo libroGoogleBooks);

    default LibrosUsuarioDto toLibrosUsuarioDto(List<Book> books, List<VolumeInfo> librosGoogleBooks) {
        var resultado = new LibrosUsuarioDto();

        for (int i = 0; i < books.size(); i++) {
            var libro = toLibroRegistradoConDetallesDto(books.get(i), librosGoogleBooks.get(i));
            resultado.getLibros().add(libro);
        }

        return resultado;
    }

    @Mapping(target = "isbn", source = "ISBN")
    @Mapping(target = "autor", expression = "java(libroGoogleBooks.getAuthors().get(0))")
    @Mapping(target = "titulo", source = "libroGoogleBooks.title")
    @Mapping(target = "urlPortada", expression = "java(libroGoogleBooks.getImageLinks() != null ? libroGoogleBooks.getImageLinks().getMedium() : null)")
    @Mapping(target = "generos", source = "libroGoogleBooks.categories")
    @Mapping(target = "editorial", source = "libroGoogleBooks.publisher")
    @Mapping(target = "sinopsis", source = "libroGoogleBooks.description")
    @Mapping(target = "idioma", source = "libroGoogleBooks.language")
    @Mapping(target = "fechaPublicacion", expression = "java(libroGoogleBooks.getPublishedDate() != null && !libroGoogleBooks.getPublishedDate().isEmpty() ? LocalDate.parse(libroGoogleBooks.getPublishedDate(), DateTimeFormatter.ofPattern(\"yyyy-MM-dd\")) : null)")
    DetallesLibroDto toDetallesLibroDto(String ISBN, VolumeInfo libroGoogleBooks);
}
