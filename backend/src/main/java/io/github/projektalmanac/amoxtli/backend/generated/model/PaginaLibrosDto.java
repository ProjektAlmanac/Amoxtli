package io.github.projektalmanac.amoxtli.backend.generated.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import io.github.projektalmanac.amoxtli.backend.generated.model.InfoBasicaLibroDto;
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
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2023-09-19T23:47:06.706578245-06:00[America/Mexico_City]")
public class PaginaLibrosDto {

  private Integer pagSiguiente;

  private Integer pagAnterior;

  private Integer resultados;

  private InfoBasicaLibroDto libros;

  public PaginaLibrosDto pagSiguiente(Integer pagSiguiente) {
    this.pagSiguiente = pagSiguiente;
    return this;
  }

  /**
   * Número de página siguiente
   * @return pagSiguiente
  */
  
  @Schema(name = "pagSiguiente", description = "Número de página siguiente", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
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
  
  @Schema(name = "pagAnterior", description = "Número de página anterior", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
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
  
  @Schema(name = "resultados", description = "Número de resultados por página", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("resultados")
  public Integer getResultados() {
    return resultados;
  }

  public void setResultados(Integer resultados) {
    this.resultados = resultados;
  }

  public PaginaLibrosDto libros(InfoBasicaLibroDto libros) {
    this.libros = libros;
    return this;
  }

  /**
   * Get libros
   * @return libros
  */
  @Valid 
  @Schema(name = "libros", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("libros")
  public InfoBasicaLibroDto getLibros() {
    return libros;
  }

  public void setLibros(InfoBasicaLibroDto libros) {
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

