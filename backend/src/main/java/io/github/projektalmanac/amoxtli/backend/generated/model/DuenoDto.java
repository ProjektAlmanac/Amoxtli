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
 * Detalles de un usuario que es dueño de un libro
 */

@Schema(name = "Dueno", description = "Detalles de un usuario que es dueño de un libro")
@JsonTypeName("Dueno")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2023-09-20T16:35:03.036426542-06:00[America/Mexico_City]")
public class DuenoDto {

  private Integer id;

  private String nombre;

  private String apellido;

  private JsonNullable<URI> foto = JsonNullable.<URI>undefined();

  public DuenoDto() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public DuenoDto(Integer id, String nombre, String apellido, URI foto) {
    this.id = id;
    this.nombre = nombre;
    this.apellido = apellido;
    this.foto = JsonNullable.of(foto);
  }

  public DuenoDto id(Integer id) {
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

  public DuenoDto nombre(String nombre) {
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

  public DuenoDto apellido(String apellido) {
    this.apellido = apellido;
    return this;
  }

  /**
   * Apellido o apellidos del usuario
   * @return apellido
  */
  @NotNull 
  @Schema(name = "apellido", description = "Apellido o apellidos del usuario", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("apellido")
  public String getApellido() {
    return apellido;
  }

  public void setApellido(String apellido) {
    this.apellido = apellido;
  }

  public DuenoDto foto(URI foto) {
    this.foto = JsonNullable.of(foto);
    return this;
  }

  /**
   * Foto de perfil del usuario
   * @return foto
  */
  @NotNull @Valid 
  @Schema(name = "foto", description = "Foto de perfil del usuario", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("foto")
  public JsonNullable<URI> getFoto() {
    return foto;
  }

  public void setFoto(JsonNullable<URI> foto) {
    this.foto = foto;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DuenoDto dueno = (DuenoDto) o;
    return Objects.equals(this.id, dueno.id) &&
        Objects.equals(this.nombre, dueno.nombre) &&
        Objects.equals(this.apellido, dueno.apellido) &&
        Objects.equals(this.foto, dueno.foto);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, nombre, apellido, foto);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DuenoDto {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    nombre: ").append(toIndentedString(nombre)).append("\n");
    sb.append("    apellido: ").append(toIndentedString(apellido)).append("\n");
    sb.append("    foto: ").append(toIndentedString(foto)).append("\n");
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

