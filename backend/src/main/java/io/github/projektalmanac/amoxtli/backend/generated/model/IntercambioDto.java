package io.github.projektalmanac.amoxtli.backend.generated.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.projektalmanac.amoxtli.backend.generated.model.AceptanteDto;
import io.github.projektalmanac.amoxtli.backend.generated.model.EstadoIntercambioDto;
import io.github.projektalmanac.amoxtli.backend.generated.model.LibroAceptanteDto;
import io.github.projektalmanac.amoxtli.backend.generated.model.LibroRegistradoDto;
import io.github.projektalmanac.amoxtli.backend.generated.model.OfertanteDto;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import javax.validation.Valid;
import javax.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import javax.annotation.Generated;

/**
 * Información relacionada con un intercambio de libros que realizará un par de usuarios
 */

@Schema(name = "Intercambio", description = "Información relacionada con un intercambio de libros que realizará un par de usuarios")
@JsonTypeName("Intercambio")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
public class IntercambioDto {

  private Integer id;

  private OfertanteDto ofertante;

  private AceptanteDto aceptante;

  private LibroAceptanteDto libroAceptante;

  private JsonNullable<LibroRegistradoDto> libroOfertante = JsonNullable.<LibroRegistradoDto>undefined();

  private EstadoIntercambioDto estado;

  public IntercambioDto() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public IntercambioDto(Integer id, OfertanteDto ofertante, AceptanteDto aceptante, LibroAceptanteDto libroAceptante, LibroRegistradoDto libroOfertante, EstadoIntercambioDto estado) {
    this.id = id;
    this.ofertante = ofertante;
    this.aceptante = aceptante;
    this.libroAceptante = libroAceptante;
    this.libroOfertante = JsonNullable.of(libroOfertante);
    this.estado = estado;
  }

  public IntercambioDto id(Integer id) {
    this.id = id;
    return this;
  }

  /**
   * ID del intercambio
   * @return id
  */
  @NotNull 
  @Schema(name = "id", description = "ID del intercambio", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("id")
  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public IntercambioDto ofertante(OfertanteDto ofertante) {
    this.ofertante = ofertante;
    return this;
  }

  /**
   * Get ofertante
   * @return ofertante
  */
  @NotNull @Valid 
  @Schema(name = "ofertante", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("ofertante")
  public OfertanteDto getOfertante() {
    return ofertante;
  }

  public void setOfertante(OfertanteDto ofertante) {
    this.ofertante = ofertante;
  }

  public IntercambioDto aceptante(AceptanteDto aceptante) {
    this.aceptante = aceptante;
    return this;
  }

  /**
   * Get aceptante
   * @return aceptante
  */
  @NotNull @Valid 
  @Schema(name = "aceptante", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("aceptante")
  public AceptanteDto getAceptante() {
    return aceptante;
  }

  public void setAceptante(AceptanteDto aceptante) {
    this.aceptante = aceptante;
  }

  public IntercambioDto libroAceptante(LibroAceptanteDto libroAceptante) {
    this.libroAceptante = libroAceptante;
    return this;
  }

  /**
   * Get libroAceptante
   * @return libroAceptante
  */
  @NotNull @Valid 
  @Schema(name = "libroAceptante", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("libroAceptante")
  public LibroAceptanteDto getLibroAceptante() {
    return libroAceptante;
  }

  public void setLibroAceptante(LibroAceptanteDto libroAceptante) {
    this.libroAceptante = libroAceptante;
  }

  public IntercambioDto libroOfertante(LibroRegistradoDto libroOfertante) {
    this.libroOfertante = JsonNullable.of(libroOfertante);
    return this;
  }

  /**
   * Get libroOfertante
   * @return libroOfertante
  */
  @NotNull @Valid 
  @Schema(name = "libroOfertante", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("libroOfertante")
  public JsonNullable<LibroRegistradoDto> getLibroOfertante() {
    return libroOfertante;
  }

  public void setLibroOfertante(JsonNullable<LibroRegistradoDto> libroOfertante) {
    this.libroOfertante = libroOfertante;
  }

  public IntercambioDto estado(EstadoIntercambioDto estado) {
    this.estado = estado;
    return this;
  }

  /**
   * Get estado
   * @return estado
  */
  @NotNull @Valid 
  @Schema(name = "estado", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("estado")
  public EstadoIntercambioDto getEstado() {
    return estado;
  }

  public void setEstado(EstadoIntercambioDto estado) {
    this.estado = estado;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    IntercambioDto intercambio = (IntercambioDto) o;
    return Objects.equals(this.id, intercambio.id) &&
        Objects.equals(this.ofertante, intercambio.ofertante) &&
        Objects.equals(this.aceptante, intercambio.aceptante) &&
        Objects.equals(this.libroAceptante, intercambio.libroAceptante) &&
        Objects.equals(this.libroOfertante, intercambio.libroOfertante) &&
        Objects.equals(this.estado, intercambio.estado);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, ofertante, aceptante, libroAceptante, libroOfertante, estado);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class IntercambioDto {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    ofertante: ").append(toIndentedString(ofertante)).append("\n");
    sb.append("    aceptante: ").append(toIndentedString(aceptante)).append("\n");
    sb.append("    libroAceptante: ").append(toIndentedString(libroAceptante)).append("\n");
    sb.append("    libroOfertante: ").append(toIndentedString(libroOfertante)).append("\n");
    sb.append("    estado: ").append(toIndentedString(estado)).append("\n");
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

