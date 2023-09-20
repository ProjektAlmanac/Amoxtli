/**
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech) (7.0.1).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */
package io.github.projektalmanac.amoxtli.backend.generated.api;

import io.github.projektalmanac.amoxtli.backend.generated.model.DetallesLibroDto;
import io.github.projektalmanac.amoxtli.backend.generated.model.ErrorDto;
import io.github.projektalmanac.amoxtli.backend.generated.model.LibroConDuenosDto;
import io.github.projektalmanac.amoxtli.backend.generated.model.PaginaLibrosDto;
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

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2023-09-19T23:47:06.706578245-06:00[America/Mexico_City]")
@Validated
@Tag(name = "libros", description = "the libros API")
public interface LibrosApi {

    /**
     * GET /libros/{isbn}/detalles : Recuperar información de un libro
     * Recupera información detallada de un libro mediante su ISBN
     *
     * @param isbn ISBN del libro (required)
     * @return OK (status code 200)
     *         or Not Found (status code 404)
     */
    @Operation(
        operationId = "getDetallesLibro",
        summary = "Recuperar información de un libro",
        description = "Recupera información detallada de un libro mediante su ISBN",
        tags = {  },
        responses = {
            @ApiResponse(responseCode = "200", description = "OK", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = DetallesLibroDto.class))
            }),
            @ApiResponse(responseCode = "404", description = "Not Found", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDto.class))
            })
        }
    )
    @RequestMapping(
        method = RequestMethod.GET,
        value = "/libros/{isbn}/detalles",
        produces = { "application/json" }
    )
    ResponseEntity<DetallesLibroDto> getDetallesLibro(
        @Parameter(name = "isbn", description = "ISBN del libro", required = true, in = ParameterIn.PATH) @PathVariable("isbn") String isbn
    );


    /**
     * GET /libros/{isbn} : Recuperar libro
     * Recupera un la información de un libro
     *
     * @param isbn ISBN del libro (required)
     * @return OK (status code 200)
     *         or Not Found (status code 404)
     */
    @Operation(
        operationId = "getLibro",
        summary = "Recuperar libro",
        description = "Recupera un la información de un libro",
        tags = {  },
        responses = {
            @ApiResponse(responseCode = "200", description = "OK", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = LibroConDuenosDto.class))
            }),
            @ApiResponse(responseCode = "404", description = "Not Found", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDto.class))
            })
        }
    )
    @RequestMapping(
        method = RequestMethod.GET,
        value = "/libros/{isbn}",
        produces = { "application/json" }
    )
    ResponseEntity<LibroConDuenosDto> getLibro(
        @Parameter(name = "isbn", description = "ISBN del libro", required = true, in = ParameterIn.PATH) @PathVariable("isbn") String isbn
    );


    /**
     * GET /libros : Recuperar libros
     * Recupera una lista de todos los libros ofrecidos por usuarios
     *
     * @param pagina Página a recuperar (optional)
     * @return OK (status code 200)
     */
    @Operation(
        operationId = "getLibros",
        summary = "Recuperar libros",
        description = "Recupera una lista de todos los libros ofrecidos por usuarios",
        tags = {  },
        responses = {
            @ApiResponse(responseCode = "200", description = "OK", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = PaginaLibrosDto.class))
            })
        }
    )
    @RequestMapping(
        method = RequestMethod.GET,
        value = "/libros",
        produces = { "application/json" }
    )
    ResponseEntity<PaginaLibrosDto> getLibros(
        @Parameter(name = "pagina", description = "Página a recuperar", in = ParameterIn.QUERY) @Valid @RequestParam(value = "pagina", required = false) Integer pagina
    );

}
