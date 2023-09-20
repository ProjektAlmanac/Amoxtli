/**
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech) (7.0.1).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */
package io.github.projektalmanac.amoxtli.backend.generated.api;

import io.github.projektalmanac.amoxtli.backend.generated.model.CredencialesDto;
import io.github.projektalmanac.amoxtli.backend.generated.model.ErrorDto;
import io.github.projektalmanac.amoxtli.backend.generated.model.SessionTokenDto;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.annotation.Generated;

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2023-09-20T16:35:03.036426542-06:00[America/Mexico_City]")
@Validated
@Tag(name = "iniciarSesion", description = "the iniciarSesion API")
public interface IniciarSesionApi {

    /**
     * POST /iniciarSesion : Iniciar sesión
     * Recupera el token de inicio de sesión de un usuario
     *
     * @param credencialesDto  (optional)
     * @return OK (status code 200)
     *         or Bad Request (status code 400)
     */
    @Operation(
        operationId = "iniciarSesion",
        summary = "Iniciar sesión",
        description = "Recupera el token de inicio de sesión de un usuario",
        responses = {
            @ApiResponse(responseCode = "200", description = "OK", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = SessionTokenDto.class))
            }),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDto.class))
            })
        },
        security = {
            @SecurityRequirement(name = "Token")
        }
    )
    @RequestMapping(
        method = RequestMethod.POST,
        value = "/iniciarSesion",
        produces = { "application/json" },
        consumes = { "application/json" }
    )
    ResponseEntity<SessionTokenDto> iniciarSesion(
        @Parameter(name = "CredencialesDto", description = "") @Valid @RequestBody(required = false) CredencialesDto credencialesDto
    );

}
