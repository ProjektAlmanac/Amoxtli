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
 * Informacińo requerida para crear un nuevo intercambio
 */

@Schema(name = "CreacionIntercambio", description = "Informacińo requerida para crear un nuevo intercambio")
@JsonTypeName("CreacionIntercambio")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2023-09-19T23:47:06.706578245-06:00[America/Mexico_City]")
public class CreacionIntercambioDto {

  private Integer idAceptante;

  private Integer idLibroAceptante;

  public CreacionIntercambioDto() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public CreacionIntercambioDto(Integer idAceptante, Integer idLibroAceptante) {
    this.idAceptante = idAceptante;
    this.idLibroAceptante = idLibroAceptante;
  }

  public CreacionIntercambioDto idAceptante(Integer idAceptante) {
    this.idAceptante = idAceptante;
    return this;
  }

  /**
   * ID del usuario que recibe la oferta de intercambio
   * @return idAceptante
  */
  @NotNull 
  @Schema(name = "idAceptante", description = "ID del usuario que recibe la oferta de intercambio", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("idAceptante")
  public Integer getIdAceptante() {
    return idAceptante;
  }

  public void setIdAceptante(Integer idAceptante) {
    this.idAceptante = idAceptante;
  }

  public CreacionIntercambioDto idLibroAceptante(Integer idLibroAceptante) {
    this.idLibroAceptante = idLibroAceptante;
    return this;
  }

  /**
   * ID del libro que pertenece al usuario que recibe la oferta de intercambio
   * @return idLibroAceptante
  */
  @NotNull 
  @Schema(name = "idLibroAceptante", description = "ID del libro que pertenece al usuario que recibe la oferta de intercambio", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("idLibroAceptante")
  public Integer getIdLibroAceptante() {
    return idLibroAceptante;
  }

  public void setIdLibroAceptante(Integer idLibroAceptante) {
    this.idLibroAceptante = idLibroAceptante;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CreacionIntercambioDto creacionIntercambio = (CreacionIntercambioDto) o;
    return Objects.equals(this.idAceptante, creacionIntercambio.idAceptante) &&
        Objects.equals(this.idLibroAceptante, creacionIntercambio.idLibroAceptante);
  }

  @Override
  public int hashCode() {
    return Objects.hash(idAceptante, idLibroAceptante);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CreacionIntercambioDto {\n");
    sb.append("    idAceptante: ").append(toIndentedString(idAceptante)).append("\n");
    sb.append("    idLibroAceptante: ").append(toIndentedString(idLibroAceptante)).append("\n");
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

