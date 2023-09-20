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
 * Datos de inicio de sesión de un usuario
 */

@Schema(name = "Credenciales", description = "Datos de inicio de sesión de un usuario")
@JsonTypeName("Credenciales")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2023-09-19T23:47:06.706578245-06:00[America/Mexico_City]")
public class CredencialesDto {

  private String email;

  private String contrasena;

  public CredencialesDto() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public CredencialesDto(String email, String contrasena) {
    this.email = email;
    this.contrasena = contrasena;
  }

  public CredencialesDto email(String email) {
    this.email = email;
    return this;
  }

  /**
   * Correo electrónico del usuario
   * @return email
  */
  @NotNull 
  @Schema(name = "email", description = "Correo electrónico del usuario", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("email")
  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public CredencialesDto contrasena(String contrasena) {
    this.contrasena = contrasena;
    return this;
  }

  /**
   * Contraseña del usuario
   * @return contrasena
  */
  @NotNull 
  @Schema(name = "contrasena", description = "Contraseña del usuario", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("contrasena")
  public String getContrasena() {
    return contrasena;
  }

  public void setContrasena(String contrasena) {
    this.contrasena = contrasena;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CredencialesDto credenciales = (CredencialesDto) o;
    return Objects.equals(this.email, credenciales.email) &&
        Objects.equals(this.contrasena, credenciales.contrasena);
  }

  @Override
  public int hashCode() {
    return Objects.hash(email, contrasena);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CredencialesDto {\n");
    sb.append("    email: ").append(toIndentedString(email)).append("\n");
    sb.append("    contrasena: ").append(toIndentedString(contrasena)).append("\n");
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

