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
 * GetIntercambios200ResponseDto
 */

@JsonTypeName("getIntercambios_200_response")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2023-09-20T16:35:03.036426542-06:00[America/Mexico_City]")
public class GetIntercambios200ResponseDto {

  @Valid
  private List<@Valid IntercambioDto> intercambios;

  public GetIntercambios200ResponseDto intercambios(List<@Valid IntercambioDto> intercambios) {
    this.intercambios = intercambios;
    return this;
  }

  public GetIntercambios200ResponseDto addIntercambiosItem(IntercambioDto intercambiosItem) {
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
  @Valid 
  @Schema(name = "intercambios", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
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
    GetIntercambios200ResponseDto getIntercambios200Response = (GetIntercambios200ResponseDto) o;
    return Objects.equals(this.intercambios, getIntercambios200Response.intercambios);
  }

  @Override
  public int hashCode() {
    return Objects.hash(intercambios);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class GetIntercambios200ResponseDto {\n");
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

