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
 * Información de un error
 */

@Schema(name = "Error", description = "Información de un error")
@JsonTypeName("Error")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2023-09-19T23:47:06.706578245-06:00[America/Mexico_City]")
public class ErrorDto {

  private String mensaje;

  private Integer codigo;

  public ErrorDto() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public ErrorDto(String mensaje, Integer codigo) {
    this.mensaje = mensaje;
    this.codigo = codigo;
  }

  public ErrorDto mensaje(String mensaje) {
    this.mensaje = mensaje;
    return this;
  }

  /**
   * Mensaje amigable que describe el error
   * @return mensaje
  */
  @NotNull 
  @Schema(name = "mensaje", description = "Mensaje amigable que describe el error", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("mensaje")
  public String getMensaje() {
    return mensaje;
  }

  public void setMensaje(String mensaje) {
    this.mensaje = mensaje;
  }

  public ErrorDto codigo(Integer codigo) {
    this.codigo = codigo;
    return this;
  }

  /**
   * Código único asociado con el error
   * @return codigo
  */
  @NotNull 
  @Schema(name = "codigo", description = "Código único asociado con el error", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("codigo")
  public Integer getCodigo() {
    return codigo;
  }

  public void setCodigo(Integer codigo) {
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
    ErrorDto error = (ErrorDto) o;
    return Objects.equals(this.mensaje, error.mensaje) &&
        Objects.equals(this.codigo, error.codigo);
  }

  @Override
  public int hashCode() {
    return Objects.hash(mensaje, codigo);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ErrorDto {\n");
    sb.append("    mensaje: ").append(toIndentedString(mensaje)).append("\n");
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

