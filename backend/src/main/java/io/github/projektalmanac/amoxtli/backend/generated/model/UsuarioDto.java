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
 * Datos para la creación de un usuario
 */

@Schema(name = "Usuario", description = "Datos para la creación de un usuario")
@JsonTypeName("Usuario")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2023-09-19T23:47:06.706578245-06:00[America/Mexico_City]")
public class UsuarioDto {

  private String correo;

  private String nombre;

  private String apellildos;

  private String password;

  public UsuarioDto() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public UsuarioDto(String correo, String nombre, String apellildos, String password) {
    this.correo = correo;
    this.nombre = nombre;
    this.apellildos = apellildos;
    this.password = password;
  }

  public UsuarioDto correo(String correo) {
    this.correo = correo;
    return this;
  }

  /**
   * Correo electrónico del usuario
   * @return correo
  */
  @NotNull @javax.validation.constraints.Email
  @Schema(name = "correo", description = "Correo electrónico del usuario", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("correo")
  public String getCorreo() {
    return correo;
  }

  public void setCorreo(String correo) {
    this.correo = correo;
  }

  public UsuarioDto nombre(String nombre) {
    this.nombre = nombre;
    return this;
  }

  /**
   * Nombre del usuario
   * @return nombre
  */
  @NotNull 
  @Schema(name = "nombre", description = "Nombre del usuario", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("nombre")
  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public UsuarioDto apellildos(String apellildos) {
    this.apellildos = apellildos;
    return this;
  }

  /**
   * Apellido o apellidos del usuario
   * @return apellildos
  */
  @NotNull 
  @Schema(name = "apellildos", description = "Apellido o apellidos del usuario", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("apellildos")
  public String getApellildos() {
    return apellildos;
  }

  public void setApellildos(String apellildos) {
    this.apellildos = apellildos;
  }

  public UsuarioDto password(String password) {
    this.password = password;
    return this;
  }

  /**
   * Contraseña del usuario
   * @return password
  */
  @NotNull 
  @Schema(name = "password", description = "Contraseña del usuario", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("password")
  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UsuarioDto usuario = (UsuarioDto) o;
    return Objects.equals(this.correo, usuario.correo) &&
        Objects.equals(this.nombre, usuario.nombre) &&
        Objects.equals(this.apellildos, usuario.apellildos) &&
        Objects.equals(this.password, usuario.password);
  }

  @Override
  public int hashCode() {
    return Objects.hash(correo, nombre, apellildos, password);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UsuarioDto {\n");
    sb.append("    correo: ").append(toIndentedString(correo)).append("\n");
    sb.append("    nombre: ").append(toIndentedString(nombre)).append("\n");
    sb.append("    apellildos: ").append(toIndentedString(apellildos)).append("\n");
    sb.append("    password: ").append(toIndentedString(password)).append("\n");
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

