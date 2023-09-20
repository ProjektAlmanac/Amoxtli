package io.github.projektalmanac.amoxtli.backend.generated.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import java.net.URI;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import javax.validation.Valid;
import javax.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import javax.annotation.Generated;

/**
 * Datos del perfil de un usuario
 */

@Schema(name = "PerfilUsuario", description = "Datos del perfil de un usuario")
@JsonTypeName("PerfilUsuario")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2023-09-20T16:35:03.036426542-06:00[America/Mexico_City]")
public class PerfilUsuarioDto {

  private Integer id;

  private String nombre;

  private String apellidos;

  private String correo;

  private String teléfono;

  private JsonNullable<String> descripciónFoto = JsonNullable.<String>undefined();

  private JsonNullable<String> intereses = JsonNullable.<String>undefined();

  private JsonNullable<URI> fotoPerfil = JsonNullable.<URI>undefined();

  private Boolean correoVerificado;

  public PerfilUsuarioDto() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public PerfilUsuarioDto(Integer id, String nombre, String apellidos, String correo, String teléfono, String descripciónFoto, String intereses, URI fotoPerfil, Boolean correoVerificado) {
    this.id = id;
    this.nombre = nombre;
    this.apellidos = apellidos;
    this.correo = correo;
    this.teléfono = teléfono;
    this.descripciónFoto = JsonNullable.of(descripciónFoto);
    this.intereses = JsonNullable.of(intereses);
    this.fotoPerfil = JsonNullable.of(fotoPerfil);
    this.correoVerificado = correoVerificado;
  }

  public PerfilUsuarioDto id(Integer id) {
    this.id = id;
    return this;
  }

  /**
   * ID del usuario
   * @return id
  */
  @NotNull 
  @Schema(name = "id", description = "ID del usuario", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("id")
  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public PerfilUsuarioDto nombre(String nombre) {
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

  public PerfilUsuarioDto apellidos(String apellidos) {
    this.apellidos = apellidos;
    return this;
  }

  /**
   * Apellido o apellidos del usuario
   * @return apellidos
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

  public PerfilUsuarioDto correo(String correo) {
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

  public PerfilUsuarioDto teléfono(String teléfono) {
    this.teléfono = teléfono;
    return this;
  }

  /**
   * Numero telefónico del usuario
   * @return teléfono
  */
  @NotNull 
  @Schema(name = "teléfono", description = "Numero telefónico del usuario", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("teléfono")
  public String getTeléfono() {
    return teléfono;
  }

  public void setTeléfono(String teléfono) {
    this.teléfono = teléfono;
  }

  public PerfilUsuarioDto descripciónFoto(String descripciónFoto) {
    this.descripciónFoto = JsonNullable.of(descripciónFoto);
    return this;
  }

  /**
   * Descripción de la foto de perfil del usuario
   * @return descripciónFoto
  */
  @NotNull 
  @Schema(name = "descripciónFoto", description = "Descripción de la foto de perfil del usuario", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("descripciónFoto")
  public JsonNullable<String> getDescripciónFoto() {
    return descripciónFoto;
  }

  public void setDescripciónFoto(JsonNullable<String> descripciónFoto) {
    this.descripciónFoto = descripciónFoto;
  }

  public PerfilUsuarioDto intereses(String intereses) {
    this.intereses = JsonNullable.of(intereses);
    return this;
  }

  /**
   * Descripción de los intereses del usuario
   * @return intereses
  */
  @NotNull 
  @Schema(name = "intereses", description = "Descripción de los intereses del usuario", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("intereses")
  public JsonNullable<String> getIntereses() {
    return intereses;
  }

  public void setIntereses(JsonNullable<String> intereses) {
    this.intereses = intereses;
  }

  public PerfilUsuarioDto fotoPerfil(URI fotoPerfil) {
    this.fotoPerfil = JsonNullable.of(fotoPerfil);
    return this;
  }

  /**
   * URL de la foto de perfil del usuario
   * @return fotoPerfil
  */
  @NotNull @Valid 
  @Schema(name = "fotoPerfil", description = "URL de la foto de perfil del usuario", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("fotoPerfil")
  public JsonNullable<URI> getFotoPerfil() {
    return fotoPerfil;
  }

  public void setFotoPerfil(JsonNullable<URI> fotoPerfil) {
    this.fotoPerfil = fotoPerfil;
  }

  public PerfilUsuarioDto correoVerificado(Boolean correoVerificado) {
    this.correoVerificado = correoVerificado;
    return this;
  }

  /**
   * Valor que indica si el usuario ha verificado su dirección de correo electrónico
   * @return correoVerificado
  */
  @NotNull 
  @Schema(name = "correoVerificado", description = "Valor que indica si el usuario ha verificado su dirección de correo electrónico", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("correoVerificado")
  public Boolean getCorreoVerificado() {
    return correoVerificado;
  }

  public void setCorreoVerificado(Boolean correoVerificado) {
    this.correoVerificado = correoVerificado;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PerfilUsuarioDto perfilUsuario = (PerfilUsuarioDto) o;
    return Objects.equals(this.id, perfilUsuario.id) &&
        Objects.equals(this.nombre, perfilUsuario.nombre) &&
        Objects.equals(this.apellidos, perfilUsuario.apellidos) &&
        Objects.equals(this.correo, perfilUsuario.correo) &&
        Objects.equals(this.teléfono, perfilUsuario.teléfono) &&
        Objects.equals(this.descripciónFoto, perfilUsuario.descripciónFoto) &&
        Objects.equals(this.intereses, perfilUsuario.intereses) &&
        Objects.equals(this.fotoPerfil, perfilUsuario.fotoPerfil) &&
        Objects.equals(this.correoVerificado, perfilUsuario.correoVerificado);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, nombre, apellidos, correo, teléfono, descripciónFoto, intereses, fotoPerfil, correoVerificado);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PerfilUsuarioDto {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    nombre: ").append(toIndentedString(nombre)).append("\n");
    sb.append("    apellidos: ").append(toIndentedString(apellidos)).append("\n");
    sb.append("    correo: ").append(toIndentedString(correo)).append("\n");
    sb.append("    teléfono: ").append(toIndentedString(teléfono)).append("\n");
    sb.append("    descripciónFoto: ").append(toIndentedString(descripciónFoto)).append("\n");
    sb.append("    intereses: ").append(toIndentedString(intereses)).append("\n");
    sb.append("    fotoPerfil: ").append(toIndentedString(fotoPerfil)).append("\n");
    sb.append("    correoVerificado: ").append(toIndentedString(correoVerificado)).append("\n");
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

