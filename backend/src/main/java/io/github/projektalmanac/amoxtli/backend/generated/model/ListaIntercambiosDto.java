package io.github.projektalmanac.amoxtli.backend.generated.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import io.github.projektalmanac.amoxtli.backend.generated.model.IntercambioDto;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import javax.validation.Valid;
import javax.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import javax.annotation.Generated;

/**
 * Lista de intercambios
 */

@Schema(name = "ListaIntercambios", description = "Lista de intercambios")
@JsonTypeName("ListaIntercambios")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2023-09-20T16:35:03.036426542-06:00[America/Mexico_City]")
public class ListaIntercambiosDto {

  @Valid
  private List<@Valid IntercambioDto> intercambios = new ArrayList<>();

  public ListaIntercambiosDto() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public ListaIntercambiosDto(List<@Valid IntercambioDto> intercambios) {
    this.intercambios = intercambios;
  }

  public ListaIntercambiosDto intercambios(List<@Valid IntercambioDto> intercambios) {
    this.intercambios = intercambios;
    return this;
  }

  public ListaIntercambiosDto addIntercambiosItem(IntercambioDto intercambiosItem) {
    if (this.intercambios == null) {
      this.intercambios = new ArrayList<>();
    }
    this.intercambios.add(intercambiosItem);
    return this;
  }

  /**
   * Get intercambios
   * @return intercambios
  */
  @NotNull @Valid 
  @Schema(name = "intercambios", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("intercambios")
  public List<@Valid IntercambioDto> getIntercambios() {
    return intercambios;
  }

  public void setIntercambios(List<@Valid IntercambioDto> intercambios) {
    this.intercambios = intercambios;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ListaIntercambiosDto listaIntercambios = (ListaIntercambiosDto) o;
    return Objects.equals(this.intercambios, listaIntercambios.intercambios);
  }

  @Override
  public int hashCode() {
    return Objects.hash(intercambios);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ListaIntercambiosDto {\n");
    sb.append("    intercambios: ").append(toIndentedString(intercambios)).append("\n");
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

