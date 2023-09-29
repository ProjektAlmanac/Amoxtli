package io.github.projektalmanac.amoxtli.backend.generated.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import java.net.URI;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import javax.validation.Valid;
import javax.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import javax.annotation.Generated;

/**
 * Información detallada respecto a un libro
 */

@Schema(name = "DetallesLibro", description = "Información detallada respecto a un libro")
@JsonTypeName("DetallesLibro")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
public class DetallesLibroDto {

  private String isbn;

  private String autor;

  private String titulo;

  private URI urlPortada;

  @Valid
  private List<String> generos = new ArrayList<>();

  private String editorial;

  private String sinopsis;

  private String idioma;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  private LocalDate fechaPublicacion;

  public DetallesLibroDto() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public DetallesLibroDto(String isbn, String autor, String titulo, URI urlPortada, List<String> generos, String editorial, String idioma, LocalDate fechaPublicacion) {
    this.isbn = isbn;
    this.autor = autor;
    this.titulo = titulo;
    this.urlPortada = urlPortada;
    this.generos = generos;
    this.editorial = editorial;
    this.idioma = idioma;
    this.fechaPublicacion = fechaPublicacion;
  }

  public DetallesLibroDto isbn(String isbn) {
    this.isbn = isbn;
    return this;
  }

  /**
   * ISBN (International Standard Book Number) del libro
   * @return isbn
  */
  @NotNull 
  @Schema(name = "isbn", description = "ISBN (International Standard Book Number) del libro", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("isbn")
  public String getIsbn() {
    return isbn;
  }

  public void setIsbn(String isbn) {
    this.isbn = isbn;
  }

  public DetallesLibroDto autor(String autor) {
    this.autor = autor;
    return this;
  }

  /**
   * Autor del libro
   * @return autor
  */
  @NotNull 
  @Schema(name = "autor", description = "Autor del libro", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("autor")
  public String getAutor() {
    return autor;
  }

  public void setAutor(String autor) {
    this.autor = autor;
  }

  public DetallesLibroDto titulo(String titulo) {
    this.titulo = titulo;
    return this;
  }

  /**
   * Título del libro
   * @return titulo
  */
  @NotNull 
  @Schema(name = "titulo", description = "Título del libro", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("titulo")
  public String getTitulo() {
    return titulo;
  }

  public void setTitulo(String titulo) {
    this.titulo = titulo;
  }

  public DetallesLibroDto urlPortada(URI urlPortada) {
    this.urlPortada = urlPortada;
    return this;
  }

  /**
   * URL de la portada del libro
   * @return urlPortada
  */
  @NotNull @Valid 
  @Schema(name = "urlPortada", description = "URL de la portada del libro", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("urlPortada")
  public URI getUrlPortada() {
    return urlPortada;
  }

  public void setUrlPortada(URI urlPortada) {
    this.urlPortada = urlPortada;
  }

  public DetallesLibroDto generos(List<String> generos) {
    this.generos = generos;
    return this;
  }

  public DetallesLibroDto addGenerosItem(String generosItem) {
    if (this.generos == null) {
      this.generos = new ArrayList<>();
    }
    this.generos.add(generosItem);
    return this;
  }

  /**
   * Géneros literarios del libro
   * @return generos
  */
  @NotNull 
  @Schema(name = "generos", description = "Géneros literarios del libro", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("generos")
  public List<String> getGeneros() {
    return generos;
  }

  public void setGeneros(List<String> generos) {
    this.generos = generos;
  }

  public DetallesLibroDto editorial(String editorial) {
    this.editorial = editorial;
    return this;
  }

  /**
   * Editorial que publica el libro
   * @return editorial
  */
  @NotNull 
  @Schema(name = "editorial", description = "Editorial que publica el libro", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("editorial")
  public String getEditorial() {
    return editorial;
  }

  public void setEditorial(String editorial) {
    this.editorial = editorial;
  }

  public DetallesLibroDto sinopsis(String sinopsis) {
    this.sinopsis = sinopsis;
    return this;
  }

  /**
   * Sinopsis del libro
   * @return sinopsis
  */
  
  @Schema(name = "sinopsis", description = "Sinopsis del libro", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("sinopsis")
  public String getSinopsis() {
    return sinopsis;
  }

  public void setSinopsis(String sinopsis) {
    this.sinopsis = sinopsis;
  }

  public DetallesLibroDto idioma(String idioma) {
    this.idioma = idioma;
    return this;
  }

  /**
   * Idioma del libro
   * @return idioma
  */
  @NotNull 
  @Schema(name = "idioma", description = "Idioma del libro", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("idioma")
  public String getIdioma() {
    return idioma;
  }

  public void setIdioma(String idioma) {
    this.idioma = idioma;
  }

  public DetallesLibroDto fechaPublicacion(LocalDate fechaPublicacion) {
    this.fechaPublicacion = fechaPublicacion;
    return this;
  }

  /**
   * Fecha de publicación del libro
   * @return fechaPublicacion
  */
  @NotNull @Valid 
  @Schema(name = "fechaPublicacion", description = "Fecha de publicación del libro", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("fechaPublicacion")
  public LocalDate getFechaPublicacion() {
    return fechaPublicacion;
  }

  public void setFechaPublicacion(LocalDate fechaPublicacion) {
    this.fechaPublicacion = fechaPublicacion;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DetallesLibroDto detallesLibro = (DetallesLibroDto) o;
    return Objects.equals(this.isbn, detallesLibro.isbn) &&
        Objects.equals(this.autor, detallesLibro.autor) &&
        Objects.equals(this.titulo, detallesLibro.titulo) &&
        Objects.equals(this.urlPortada, detallesLibro.urlPortada) &&
        Objects.equals(this.generos, detallesLibro.generos) &&
        Objects.equals(this.editorial, detallesLibro.editorial) &&
        Objects.equals(this.sinopsis, detallesLibro.sinopsis) &&
        Objects.equals(this.idioma, detallesLibro.idioma) &&
        Objects.equals(this.fechaPublicacion, detallesLibro.fechaPublicacion);
  }

  @Override
  public int hashCode() {
    return Objects.hash(isbn, autor, titulo, urlPortada, generos, editorial, sinopsis, idioma, fechaPublicacion);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DetallesLibroDto {\n");
    sb.append("    isbn: ").append(toIndentedString(isbn)).append("\n");
    sb.append("    autor: ").append(toIndentedString(autor)).append("\n");
    sb.append("    titulo: ").append(toIndentedString(titulo)).append("\n");
    sb.append("    urlPortada: ").append(toIndentedString(urlPortada)).append("\n");
    sb.append("    generos: ").append(toIndentedString(generos)).append("\n");
    sb.append("    editorial: ").append(toIndentedString(editorial)).append("\n");
    sb.append("    sinopsis: ").append(toIndentedString(sinopsis)).append("\n");
    sb.append("    idioma: ").append(toIndentedString(idioma)).append("\n");
    sb.append("    fechaPublicacion: ").append(toIndentedString(fechaPublicacion)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

