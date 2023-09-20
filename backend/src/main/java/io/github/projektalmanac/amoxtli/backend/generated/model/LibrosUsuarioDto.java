package io.github.projektalmanac.amoxtli.backend.generated.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import io.github.projektalmanac.amoxtli.backend.generated.model.LibroRegistradoConDetallesDto;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import javax.validation.Valid;
import javax.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import javax.annotation.Generated;

/**
 * Libros que figuran dentro del catálogo de un usuario
 */

@Schema(name = "LibrosUsuario", description = "Libros que figuran dentro del catálogo de un usuario")
@JsonTypeName("LibrosUsuario")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2023-09-20T16:35:03.036426542-06:00[America/Mexico_City]")
public class LibrosUsuarioDto {

  @Valid
  private List<@Valid LibroRegistradoConDetallesDto> libros = new ArrayList<>();

  public LibrosUsuarioDto() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public LibrosUsuarioDto(List<@Valid LibroRegistradoConDetallesDto> libros) {
    this.libros = libros;
  }

  public LibrosUsuarioDto libros(List<@Valid LibroRegistradoConDetallesDto> libros) {
    this.libros = libros;
    return this;
  }

  public LibrosUsuarioDto addLibrosItem(LibroRegistradoConDetallesDto librosItem) {
    if (this.libros == null) {
      this.libros = new ArrayList<>();
    }
    this.libros.add(librosItem);
    return this;
  }

  /**
   * Los libros del usuario
   * @return libros
  */
  @NotNull @Valid 
  @Schema(name = "libros", description = "Los libros del usuario", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("libros")
  public List<@Valid LibroRegistradoConDetallesDto> getLibros() {
    return libros;
  }

  public void setLibros(List<@Valid LibroRegistradoConDetallesDto> libros) {
    this.libros = libros;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    LibrosUsuarioDto librosUsuario = (LibrosUsuarioDto) o;
    return Objects.equals(this.libros, librosUsuario.libros);
  }

  @Override
  public int hashCode() {
    return Objects.hash(libros);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class LibrosUsuarioDto {\n");
    sb.append("    libros: ").append(toIndentedString(libros)).append("\n");
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

