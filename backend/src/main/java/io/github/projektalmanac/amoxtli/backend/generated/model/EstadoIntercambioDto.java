package io.github.projektalmanac.amoxtli.backend.generated.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonValue;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import javax.validation.Valid;
import javax.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import javax.annotation.Generated;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Representa los posibles estados de un intercambio de libros
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
public enum EstadoIntercambioDto {
  
  PENDIENTE("Pendiente"),
  
  ACEPTADO("Aceptado"),
  
  RECHAZADO("Rechazado"),
  
  CANCELADO("Cancelado"),
  COMPLETADO("Completado");

  private String value;

  EstadoIntercambioDto(String value) {
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
  public static EstadoIntercambioDto fromValue(String value) {
    for (EstadoIntercambioDto b : EstadoIntercambioDto.values()) {
      if (b.value.equals(value)) {
        return b;
      }
    }
    throw new IllegalArgumentException("Unexpected value '" + value + "'");
  }
}

