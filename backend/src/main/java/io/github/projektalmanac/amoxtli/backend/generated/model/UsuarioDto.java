package io.github.projektalmanac.amoxtli.backend.generated.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.annotation.Generated;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * Datos para la creación de un usuario
 */

@Schema(name = "Usuario", description = "Datos para la creación de un usuario")
@JsonTypeName("Usuario")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2023-09-20T16:35:03.036426542-06:00[America/Mexico_City]")
public class UsuarioDto {

 //quitar id

  private String correo;

  private String nombre;

  private String apellidos;

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
    this.apellidos = apellildos;
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

  public UsuarioDto apellidos(String apellidos) {
    this.apellidos = apellidos;
    return this;
  }

  /**
   * Apellido o apellidos del usuario
   * @return apellildos
  */
  @NotNull 
  @Schema(name = "apellidos", description = "Apellido o apellidos del usuario", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("apellidos")
  public String getApellidos() {
    return apellidos;
  }

  public void setApellidos(String apellidos) {
    this.apellidos = apellidos;
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
        Objects.equals(this.apellidos, usuario.apellidos) &&
        Objects.equals(this.password, usuario.password);
  }

  @Override
  public int hashCode() {
    return Objects.hash(correo, nombre, apellidos, password);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UsuarioDto {\n");
    sb.append("    correo: ").append(toIndentedString(correo)).append("\n");
    sb.append("    nombre: ").append(toIndentedString(nombre)).append("\n");
    sb.append("    apellidos: ").append(toIndentedString(apellidos)).append("\n");
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

