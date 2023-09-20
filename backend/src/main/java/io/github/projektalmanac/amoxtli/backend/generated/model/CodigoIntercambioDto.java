package io.github.projektalmanac.amoxtli.backend.generated.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import java.util.UUID;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import javax.validation.Valid;
import javax.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import javax.annotation.Generated;

/**
 * Contiene el código requerido para completar un intercambio
 */

@Schema(name = "CodigoIntercambio", description = "Contiene el código requerido para completar un intercambio")
@JsonTypeName("CodigoIntercambio")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2023-09-19T23:47:06.706578245-06:00[America/Mexico_City]")
public class CodigoIntercambioDto {

  private UUID codigo;

  public CodigoIntercambioDto() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public CodigoIntercambioDto(UUID codigo) {
    this.codigo = codigo;
  }

  public CodigoIntercambioDto codigo(UUID codigo) {
    this.codigo = codigo;
    return this;
  }

  /**
   * Código requerido para completar un intercambio
   * @return codigo
  */
  @NotNull @Valid 
  @Schema(name = "codigo", description = "Código requerido para completar un intercambio", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("codigo")
  public UUID getCodigo() {
    return codigo;
  }

  public void setCodigo(UUID codigo) {
    this.codigo = codigo;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CodigoIntercambioDto codigoIntercambio = (CodigoIntercambioDto) o;
    return Objects.equals(this.codigo, codigoIntercambio.codigo);
  }

  @Override
  public int hashCode() {
    return Objects.hash(codigo);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CodigoIntercambioDto {\n");
    sb.append("    codigo: ").append(toIndentedString(codigo)).append("\n");
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

