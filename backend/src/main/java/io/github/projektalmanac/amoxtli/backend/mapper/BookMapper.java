package io.github.projektalmanac.amoxtli.backend.mapper;

import com.google.api.services.books.model.Volume.VolumeInfo;
import io.github.projektalmanac.amoxtli.backend.entity.Book;
import io.github.projektalmanac.amoxtli.backend.generated.model.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.net.URI;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Mapper
public interface BookMapper {
    BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);

    @Mapping(target = "id", source = "book.id")
    @Mapping(target = "isbn", source = "book.isbn")
    @Mapping(target = "titulo", source = "libroGoogleBooks.title")
    @Mapping(target = "autor", expression = "java(libroGoogleBooks.getAuthors().get(0))")
    @Mapping(target = "urlPortada", source = "libroGoogleBooks.imageLinks")
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
    @Mapping(target = "urlPortada", source = "libroGoogleBooks.imageLinks")
    @Mapping(target = "generos", source = "libroGoogleBooks.categories")
    @Mapping(target = "editorial", source = "libroGoogleBooks.publisher")
    @Mapping(target = "sinopsis", source = "libroGoogleBooks.description")
    @Mapping(target = "idioma", source = "libroGoogleBooks.language")
    @Mapping(target = "fechaPublicacion", source = "libroGoogleBooks.publishedDate" )
    DetallesLibroDto toDetallesLibroDto(String ISBN, VolumeInfo libroGoogleBooks);

    @Mapping(target = "isbn", source = "libroRegistradoDto.isbn")
    @Mapping(target = "description", source = "libroRegistradoDto.descripcion")
    Book toBook(LibroRegistradoDto libroRegistradoDto);

    @Mapping(target = "descripcion", source = "description")
    LibroRegistradoDto toLibroRegistradoDto(Book book);


    @Mapping(target = "isbn", source = "ISBN")
    @Mapping(target = "autor", expression = "java(libroGoogleBooks.getAuthors().get(0))")
    @Mapping(target = "titulo", source = "libroGoogleBooks.title")
    @Mapping(target = "urlPortada", source = "libroGoogleBooks.imageLinks")
    @Mapping(target = "generos", source = "libroGoogleBooks.categories")
    @Mapping(target = "editorial", source = "libroGoogleBooks.publisher")
    @Mapping(target = "sinopsis", source = "libroGoogleBooks.description")
    @Mapping(target = "idioma", source = "libroGoogleBooks.language")
    @Mapping(target = "fechaPublicacion", source = "libroGoogleBooks.publishedDate" )
    //@Mapping(target = "duenos", source = "dueños" )
    LibroConDuenosDto toLibroConDuenosDto(String ISBN, VolumeInfo libroGoogleBooks);

    default URI stringToUri(String string) {
        if (string == null) return null;
        return URI.create(string);
    }

    default LocalDate stringToDate(String string) {
        if (string == null) return null;
        if (!string.contains("-")) {
            return LocalDate.ofYearDay(Integer.parseInt(string), 1);
        }
        return LocalDate.parse(string, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    default URI mapImageLinks(VolumeInfo.ImageLinks imageLinks) {
        if(imageLinks == null) {
            return null;
        }
        var imageUrl = getImageUrl(imageLinks);
        if (imageUrl == null) {
            return null;
        }
        return URI.create(imageUrl);
    }

    default String getImageUrl(VolumeInfo.ImageLinks imageLinks) {
        if (imageLinks == null) return null;
        if (imageLinks.getMedium() != null) return imageLinks.getMedium();
        if (imageLinks.getLarge() != null) return imageLinks.getLarge();
        if (imageLinks.getExtraLarge() != null) return imageLinks.getExtraLarge();
        if (imageLinks.getThumbnail() != null) return imageLinks.getThumbnail();
        if (imageLinks.getSmallThumbnail() != null) return imageLinks.getSmallThumbnail();
        return null;
    }

    LibroConDuenosDto toLibroConDuenosDto(Book book);
}
