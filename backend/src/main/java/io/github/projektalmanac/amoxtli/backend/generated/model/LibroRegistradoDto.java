package io.github.projektalmanac.amoxtli.backend.generated.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import javax.validation.Valid;
import javax.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import javax.annotation.Generated;

/**
 * Datos de un libro que está registrado como parte del catálogo de un usario
 */

@Schema(name = "LibroRegistrado", description = "Datos de un libro que está registrado como parte del catálogo de un usario")
@JsonTypeName("LibroRegistrado")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2023-09-19T23:47:06.706578245-06:00[America/Mexico_City]")
public class LibroRegistradoDto {

  private Integer id;

  private String isbn;

  private String descripcion;

  public LibroRegistradoDto() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public LibroRegistradoDto(Integer id, String isbn, String descripcion) {
    this.id = id;
    this.isbn = isbn;
    this.descripcion = descripcion;
  }

  public LibroRegistradoDto id(Integer id) {
    this.id = id;
    return this;
  }

  /**
   * ID del libro
   * @return id
  */
  
  @Schema(name = "id", accessMode = Schema.AccessMode.READ_ONLY, description = "ID del libro", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("id")
  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public LibroRegistradoDto isbn(String isbn) {
    this.isbn = isbn;
    return this;
  }

  /**
   * ISBN (International Standard Book Number) del lib
   * @return isbn
  */
  @NotNull 
  @Schema(name = "isbn", description = "ISBN (International Standard Book Number) del lib", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("isbn")
  public String getIsbn() {
    return isbn;
  }

  public void setIsbn(String isbn) {
    this.isbn = isbn;
  }

  public LibroRegistradoDto descripcion(String descripcion) {
    this.descripcion = descripcion;
    return this;
  }

  /**
   * Descripción de la condición en la que se encuentra la copia del libro que posee el
   * @return descripcion
  */
  @NotNull 
  @Schema(name = "descripcion", description = "Descripción de la condición en la que se encuentra la copia del libro que posee el", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("descripcion")
  public String getDescripcion() {
    return descripcion;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    LibroRegistradoDto libroRegistrado = (LibroRegistradoDto) o;
    return Objects.equals(this.id, libroRegistrado.id) &&
        Objects.equals(this.isbn, libroRegistrado.isbn) &&
        Objects.equals(this.descripcion, libroRegistrado.descripcion);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, isbn, descripcion);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class LibroRegistradoDto {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    isbn: ").append(toIndentedString(isbn)).append("\n");
    sb.append("    descripcion: ").append(toIndentedString(descripcion)).append("\n");
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

