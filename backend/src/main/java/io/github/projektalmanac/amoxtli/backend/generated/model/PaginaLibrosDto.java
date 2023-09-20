package io.github.projektalmanac.amoxtli.backend.generated.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import io.github.projektalmanac.amoxtli.backend.generated.model.InfoBasicaLibroDto;
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
 * PaginaLibrosDto
 */

@JsonTypeName("PaginaLibros")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2023-09-20T16:35:03.036426542-06:00[America/Mexico_City]")
public class PaginaLibrosDto {

  private Integer pagSiguiente;

  private Integer pagAnterior;

  private Integer resultados;

  @Valid
  private List<@Valid InfoBasicaLibroDto> libros = new ArrayList<>();

  public PaginaLibrosDto() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public PaginaLibrosDto(Integer pagSiguiente, Integer pagAnterior, Integer resultados, List<@Valid InfoBasicaLibroDto> libros) {
    this.pagSiguiente = pagSiguiente;
    this.pagAnterior = pagAnterior;
    this.resultados = resultados;
    this.libros = libros;
  }

  public PaginaLibrosDto pagSiguiente(Integer pagSiguiente) {
    this.pagSiguiente = pagSiguiente;
    return this;
  }

  /**
   * Número de página siguiente
   * @return pagSiguiente
  */
  @NotNull 
  @Schema(name = "pagSiguiente", description = "Número de página siguiente", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("pagSiguiente")
  public Integer getPagSiguiente() {
    return pagSiguiente;
  }

  public void setPagSiguiente(Integer pagSiguiente) {
    this.pagSiguiente = pagSiguiente;
  }

  public PaginaLibrosDto pagAnterior(Integer pagAnterior) {
    this.pagAnterior = pagAnterior;
    return this;
  }

  /**
   * Número de página anterior
   * @return pagAnterior
  */
  @NotNull 
  @Schema(name = "pagAnterior", description = "Número de página anterior", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("pagAnterior")
  public Integer getPagAnterior() {
    return pagAnterior;
  }

  public void setPagAnterior(Integer pagAnterior) {
    this.pagAnterior = pagAnterior;
  }

  public PaginaLibrosDto resultados(Integer resultados) {
    this.resultados = resultados;
    return this;
  }

  /**
   * Número de resultados por página
   * @return resultados
  */
  @NotNull 
  @Schema(name = "resultados", description = "Número de resultados por página", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("resultados")
  public Integer getResultados() {
    return resultados;
  }

  public void setResultados(Integer resultados) {
    this.resultados = resultados;
  }

  public PaginaLibrosDto libros(List<@Valid InfoBasicaLibroDto> libros) {
    this.libros = libros;
    return this;
  }

  public PaginaLibrosDto addLibrosItem(InfoBasicaLibroDto librosItem) {
    if (this.libros == null) {
      this.libros = new ArrayList<>();
    }
    this.libros.add(librosItem);
    return this;
  }

  /**
   * Get libros
   * @return libros
  */
  @NotNull @Valid 
  @Schema(name = "libros", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("libros")
  public List<@Valid InfoBasicaLibroDto> getLibros() {
    return libros;
  }

  public void setLibros(List<@Valid InfoBasicaLibroDto> libros) {
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
    PaginaLibrosDto paginaLibros = (PaginaLibrosDto) o;
    return Objects.equals(this.pagSiguiente, paginaLibros.pagSiguiente) &&
        Objects.equals(this.pagAnterior, paginaLibros.pagAnterior) &&
        Objects.equals(this.resultados, paginaLibros.resultados) &&
        Objects.equals(this.libros, paginaLibros.libros);
  }

  @Override
  public int hashCode() {
    return Objects.hash(pagSiguiente, pagAnterior, resultados, libros);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PaginaLibrosDto {\n");
    sb.append("    pagSiguiente: ").append(toIndentedString(pagSiguiente)).append("\n");
    sb.append("    pagAnterior: ").append(toIndentedString(pagAnterior)).append("\n");
    sb.append("    resultados: ").append(toIndentedString(resultados)).append("\n");
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

