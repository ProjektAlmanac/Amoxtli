package io.github.projektalmanac.amoxtli.backend.generated.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.annotation.JsonValue;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import javax.validation.Valid;
import javax.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import javax.annotation.Generated;

/**
 * JSONPatchRequestRemoveDto
 */

@JsonTypeName("JSONPatchRequestRemove")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2023-09-19T23:47:06.706578245-06:00[America/Mexico_City]")
public class JSONPatchRequestRemoveDto implements PatchRequestInnerDto {

  private String path;

  /**
   * The operation to perform.
   */
  public enum OpEnum {
    REMOVE("remove");

    private String value;

    OpEnum(String value) {
      this.value = value;
    }

    @JsonValue
    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static OpEnum fromValue(String value) {
      for (OpEnum b : OpEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  private OpEnum op;

  public JSONPatchRequestRemoveDto() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public JSONPatchRequestRemoveDto(String path, OpEnum op) {
    this.path = path;
    this.op = op;
  }

  public JSONPatchRequestRemoveDto path(String path) {
    this.path = path;
    return this;
  }

  /**
   * A JSON Pointer path.
   * @return path
  */
  @NotNull 
  @Schema(name = "path", description = "A JSON Pointer path.", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("path")
  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public JSONPatchRequestRemoveDto op(OpEnum op) {
    this.op = op;
    return this;
  }

  /**
   * The operation to perform.
   * @return op
  */
  @NotNull 
  @Schema(name = "op", description = "The operation to perform.", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("op")
  public OpEnum getOp() {
    return op;
  }

  public void setOp(OpEnum op) {
    this.op = op;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    JSONPatchRequestRemoveDto jsONPatchRequestRemove = (JSONPatchRequestRemoveDto) o;
    return Objects.equals(this.path, jsONPatchRequestRemove.path) &&
        Objects.equals(this.op, jsONPatchRequestRemove.op);
  }

  @Override
  public int hashCode() {
    return Objects.hash(path, op);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class JSONPatchRequestRemoveDto {\n");
    sb.append("    path: ").append(toIndentedString(path)).append("\n");
    sb.append("    op: ").append(toIndentedString(op)).append("\n");
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

