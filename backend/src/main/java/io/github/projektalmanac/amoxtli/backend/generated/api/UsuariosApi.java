/**
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech) (7.0.1).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */
package io.github.projektalmanac.amoxtli.backend.generated.api;

import io.github.projektalmanac.amoxtli.backend.generated.model.AceptarIntercambioRequestDto;
import io.github.projektalmanac.amoxtli.backend.generated.model.CodigoIntercambioDto;
import io.github.projektalmanac.amoxtli.backend.generated.model.CodigoVerificacionDto;
import io.github.projektalmanac.amoxtli.backend.generated.model.CreacionIntercambioDto;
import io.github.projektalmanac.amoxtli.backend.generated.model.ErrorDto;
import io.github.projektalmanac.amoxtli.backend.generated.model.GetIntercambios200ResponseDto;
import io.github.projektalmanac.amoxtli.backend.generated.model.IntercambioDto;
import io.github.projektalmanac.amoxtli.backend.generated.model.LibroRegistradoDto;
import io.github.projektalmanac.amoxtli.backend.generated.model.LibrosUsuarioDto;
import io.github.projektalmanac.amoxtli.backend.generated.model.PatchRequestInnerDto;
import io.github.projektalmanac.amoxtli.backend.generated.model.PerfilUsuarioDto;
import io.github.projektalmanac.amoxtli.backend.generated.model.UsuarioDto;
import io.github.projektalmanac.amoxtli.backend.generated.model.UsuarioIdDto;
import io.github.projektalmanac.amoxtli.backend.generated.model.ValidaPuedeIntercambiar200ResponseDto;
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
@Tag(name = "usuarios", description = "the usuarios API")
public interface UsuariosApi {

    /**
     * POST /usuarios/{idUsuario}/intercambios/{idIntercambio}/aceptar : Aceptar intercambio
     * Acepta una solicitud de intercambio
     *
     * @param idUsuario Id del usuario (required)
     * @param idIntercambio Id del intercambio (required)
     * @param aceptarIntercambioRequestDto  (optional)
     * @return Ok (status code 200)
     *         or Unauthorized (status code 401)
     *         or Forbidden (status code 403)
     *         or Not Found (status code 404)
     */
    @Operation(
        operationId = "aceptarIntercambio",
        summary = "Aceptar intercambio",
        description = "Acepta una solicitud de intercambio",
        responses = {
            @ApiResponse(responseCode = "200", description = "Ok", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = IntercambioDto.class)),
                @Content(mediaType = "application/xml", schema = @Schema(implementation = IntercambioDto.class))
            }),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDto.class)),
                @Content(mediaType = "application/xml", schema = @Schema(implementation = ErrorDto.class))
            }),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDto.class)),
                @Content(mediaType = "application/xml", schema = @Schema(implementation = ErrorDto.class))
            }),
            @ApiResponse(responseCode = "404", description = "Not Found", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDto.class)),
                @Content(mediaType = "application/xml", schema = @Schema(implementation = ErrorDto.class))
            })
        },
        security = {
            @SecurityRequirement(name = "Token")
        }
    )
    @RequestMapping(
        method = RequestMethod.POST,
        value = "/usuarios/{idUsuario}/intercambios/{idIntercambio}/aceptar",
        produces = { "application/json", "application/xml" },
        consumes = { "application/json" }
    )
    ResponseEntity<IntercambioDto> aceptarIntercambio(
        @Parameter(name = "idUsuario", description = "Id del usuario", required = true, in = ParameterIn.PATH) @PathVariable("idUsuario") Integer idUsuario,
        @Parameter(name = "idIntercambio", description = "Id del intercambio", required = true, in = ParameterIn.PATH) @PathVariable("idIntercambio") Integer idIntercambio,
        @Parameter(name = "AceptarIntercambioRequestDto", description = "") @Valid @RequestBody(required = false) AceptarIntercambioRequestDto aceptarIntercambioRequestDto
    );


    /**
     * PATCH /usuarios/{id} : Actualizar usuario
     * Actualiza los datos de un usuario
     *
     * @param id Id del usuario (required)
     * @param patchRequestInnerDto  (optional)
     * @return OK (status code 200)
     *         or Unauthorized (status code 401)
     *         or Forbidden (status code 403)
     *         or Not Found (status code 404)
     */
    @Operation(
        operationId = "actualizarUsuario",
        summary = "Actualizar usuario",
        description = "Actualiza los datos de un usuario",
        responses = {
            @ApiResponse(responseCode = "200", description = "OK", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = PerfilUsuarioDto.class))
            }),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDto.class))
            }),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDto.class))
            }),
            @ApiResponse(responseCode = "404", description = "Not Found", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDto.class))
            })
        },
        security = {
            @SecurityRequirement(name = "Token")
        }
    )
    @RequestMapping(
        method = RequestMethod.PATCH,
        value = "/usuarios/{id}",
        produces = { "application/json" },
        consumes = { "application/json-patch+json" }
    )
    ResponseEntity<PerfilUsuarioDto> actualizarUsuario(
        @Parameter(name = "id", description = "Id del usuario", required = true, in = ParameterIn.PATH) @PathVariable("id") Integer id,
        @Parameter(name = "PatchRequestInnerDto", description = "") @Valid @RequestBody(required = false) List<PatchRequestInnerDto> patchRequestInnerDto
    );


    /**
     * POST /usuarios/{id}/intercambios : Realizar solicitud de intercambio
     * Realiza una solicitud de intercambio con otro usuario
     *
     * @param id Id del usuario que realiza la oferta de intercambio (required)
     * @param creacionIntercambioDto  (optional)
     * @return OK (status code 200)
     *         or Unauthorized (status code 401)
     *         or Forbidden (status code 403)
     *         or Not Found (status code 404)
     */
    @Operation(
        operationId = "addIntercambio",
        summary = "Realizar solicitud de intercambio",
        description = "Realiza una solicitud de intercambio con otro usuario",
        responses = {
            @ApiResponse(responseCode = "200", description = "OK", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = IntercambioDto.class))
            }),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDto.class))
            }),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDto.class))
            }),
            @ApiResponse(responseCode = "404", description = "Not Found", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDto.class))
            })
        },
        security = {
            @SecurityRequirement(name = "Token")
        }
    )
    @RequestMapping(
        method = RequestMethod.POST,
        value = "/usuarios/{id}/intercambios",
        produces = { "application/json" },
        consumes = { "application/json" }
    )
    ResponseEntity<IntercambioDto> addIntercambio(
        @Parameter(name = "id", description = "Id del usuario que realiza la oferta de intercambio", required = true, in = ParameterIn.PATH) @PathVariable("id") Integer id,
        @Parameter(name = "CreacionIntercambioDto", description = "") @Valid @RequestBody(required = false) CreacionIntercambioDto creacionIntercambioDto
    );


    /**
     * POST /usuarios/{id}/libros : Añadir libro a catálogo de usuario
     * Añade un libro al catálogo de un usuario
     *
     * @param id Id del usuario (required)
     * @param libroRegistradoDto  (optional)
     * @return Created (status code 201)
     *         or Bad Request (status code 400)
     *         or Unauthorized (status code 401)
     *         or Forbidden (status code 403)
     *         or Not Found (status code 404)
     */
    @Operation(
        operationId = "addLibro",
        summary = "Añadir libro a catálogo de usuario",
        description = "Añade un libro al catálogo de un usuario",
        responses = {
            @ApiResponse(responseCode = "201", description = "Created", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = LibroRegistradoDto.class))
            }),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDto.class))
            }),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDto.class))
            }),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDto.class))
            }),
            @ApiResponse(responseCode = "404", description = "Not Found", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDto.class))
            })
        },
        security = {
            @SecurityRequirement(name = "Token")
        }
    )
    @RequestMapping(
        method = RequestMethod.POST,
        value = "/usuarios/{id}/libros",
        produces = { "application/json" },
        consumes = { "application/json" }
    )
    ResponseEntity<LibroRegistradoDto> addLibro(
        @Parameter(name = "id", description = "Id del usuario", required = true, in = ParameterIn.PATH) @PathVariable("id") Integer id,
        @Parameter(name = "LibroRegistradoDto", description = "") @Valid @RequestBody(required = false) LibroRegistradoDto libroRegistradoDto
    );


    /**
     * POST /usuarios/ : Registrar usuario
     * Registra a un nuevo usuario en el sistema
     *
     * @param usuarioDto  (optional)
     * @return Created (status code 201)
     *         or Bad Request (status code 400)
     */
    @Operation(
        operationId = "crearUsuario",
        summary = "Registrar usuario",
        description = "Registra a un nuevo usuario en el sistema",
        responses = {
            @ApiResponse(responseCode = "201", description = "Created", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioIdDto.class))
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
        value = "/usuarios/",
        produces = { "application/json" },
        consumes = { "application/json" }
    )
    ResponseEntity<UsuarioIdDto> crearUsuario(
        @Parameter(name = "UsuarioDto", description = "") @Valid @RequestBody(required = false) UsuarioDto usuarioDto
    );


    /**
     * POST /usuarios/{idUsuario}/intercambios/{idIntercambio}/finalizar : Finalizar intercambio
     * Envía el código de intercambio para finalizarlo
     *
     * @param idUsuario Id del usuario (required)
     * @param idIntercambio Id del intercambio (required)
     * @param codigoIntercambioDto  (optional)
     * @return OK (status code 200)
     *         or Unauthorized (status code 401)
     *         or Forbidden (status code 403)
     *         or Not Found (status code 404)
     */
    @Operation(
        operationId = "finalizarIntercambio",
        summary = "Finalizar intercambio",
        description = "Envía el código de intercambio para finalizarlo",
        responses = {
            @ApiResponse(responseCode = "200", description = "OK", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = IntercambioDto.class)),
                @Content(mediaType = "application/xml", schema = @Schema(implementation = IntercambioDto.class))
            }),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDto.class)),
                @Content(mediaType = "application/xml", schema = @Schema(implementation = ErrorDto.class))
            }),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDto.class)),
                @Content(mediaType = "application/xml", schema = @Schema(implementation = ErrorDto.class))
            }),
            @ApiResponse(responseCode = "404", description = "Not Found", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDto.class)),
                @Content(mediaType = "application/xml", schema = @Schema(implementation = ErrorDto.class))
            })
        },
        security = {
            @SecurityRequirement(name = "Token")
        }
    )
    @RequestMapping(
        method = RequestMethod.POST,
        value = "/usuarios/{idUsuario}/intercambios/{idIntercambio}/finalizar",
        produces = { "application/json", "application/xml" },
        consumes = { "application/json" }
    )
    ResponseEntity<IntercambioDto> finalizarIntercambio(
        @Parameter(name = "idUsuario", description = "Id del usuario", required = true, in = ParameterIn.PATH) @PathVariable("idUsuario") Integer idUsuario,
        @Parameter(name = "idIntercambio", description = "Id del intercambio", required = true, in = ParameterIn.PATH) @PathVariable("idIntercambio") Integer idIntercambio,
        @Parameter(name = "CodigoIntercambioDto", description = "") @Valid @RequestBody(required = false) CodigoIntercambioDto codigoIntercambioDto
    );


    /**
     * GET /usuarios/{idUsuario}/intercambios/{idIntercambio}/codigo : Obtener código de intercambio
     * Recupera el código requerido para finalizar un intercambio
     *
     * @param idUsuario Id del usuario (required)
     * @param idIntercambio Id del intercambio (required)
     * @return OK (status code 200)
     *         or Unauthorized (status code 401)
     *         or Forbidden (status code 403)
     *         or Not Found (status code 404)
     */
    @Operation(
        operationId = "getCodigoIntercambio",
        summary = "Obtener código de intercambio",
        description = "Recupera el código requerido para finalizar un intercambio",
        tags = {  },
        responses = {
            @ApiResponse(responseCode = "200", description = "OK", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = CodigoIntercambioDto.class))
            }),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDto.class))
            }),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDto.class))
            }),
            @ApiResponse(responseCode = "404", description = "Not Found", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDto.class))
            })
        },
        security = {
            @SecurityRequirement(name = "Token")
        }
    )
    @RequestMapping(
        method = RequestMethod.GET,
        value = "/usuarios/{idUsuario}/intercambios/{idIntercambio}/codigo",
        produces = { "application/json" }
    )
    ResponseEntity<CodigoIntercambioDto> getCodigoIntercambio(
        @Parameter(name = "idUsuario", description = "Id del usuario", required = true, in = ParameterIn.PATH) @PathVariable("idUsuario") Integer idUsuario,
        @Parameter(name = "idIntercambio", description = "Id del intercambio", required = true, in = ParameterIn.PATH) @PathVariable("idIntercambio") Integer idIntercambio
    );


    /**
     * GET /usuarios/{id}/intercambios : Recuperar intercambios de usuario
     * Recupera todos los intercambios en los que participa un usuario
     *
     * @param id Id del usuario que realiza la oferta de intercambio (required)
     * @return OK (status code 200)
     *         or Unauthorized (status code 401)
     *         or Forbidden (status code 403)
     *         or Not Found (status code 404)
     */
    @Operation(
        operationId = "getIntercambios",
        summary = "Recuperar intercambios de usuario",
        description = "Recupera todos los intercambios en los que participa un usuario",
        tags = {  },
        responses = {
            @ApiResponse(responseCode = "200", description = "OK", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = GetIntercambios200ResponseDto.class))
            }),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDto.class))
            }),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDto.class))
            }),
            @ApiResponse(responseCode = "404", description = "Not Found", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDto.class))
            })
        },
        security = {
            @SecurityRequirement(name = "Token")
        }
    )
    @RequestMapping(
        method = RequestMethod.GET,
        value = "/usuarios/{id}/intercambios",
        produces = { "application/json" }
    )
    ResponseEntity<GetIntercambios200ResponseDto> getIntercambios(
        @Parameter(name = "id", description = "Id del usuario que realiza la oferta de intercambio", required = true, in = ParameterIn.PATH) @PathVariable("id") Integer id
    );


    /**
     * GET /usuarios/{id}/libros : Recuperar libros de un usuario
     * Recupera la lista de todos los libros en el catálogo de un usuario
     *
     * @param id Id del usuario (required)
     * @return OK (status code 200)
     *         or No Content (status code 204)
     *         or Not Found (status code 404)
     */
    @Operation(
        operationId = "getLibrosUsuario",
        summary = "Recuperar libros de un usuario",
        description = "Recupera la lista de todos los libros en el catálogo de un usuario",
        tags = {  },
        responses = {
            @ApiResponse(responseCode = "200", description = "OK", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = LibrosUsuarioDto.class))
            }),
            @ApiResponse(responseCode = "204", description = "No Content"),
            @ApiResponse(responseCode = "404", description = "Not Found", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDto.class))
            })
        }
    )
    @RequestMapping(
        method = RequestMethod.GET,
        value = "/usuarios/{id}/libros",
        produces = { "application/json" }
    )
    ResponseEntity<LibrosUsuarioDto> getLibrosUsuario(
        @Parameter(name = "id", description = "Id del usuario", required = true, in = ParameterIn.PATH) @PathVariable("id") Integer id
    );


    /**
     * GET /usuarios/{id} : Recuperar usuario
     * Recupera un usuario mediante su ID
     *
     * @param id Id del usuario (required)
     * @return OK (status code 200)
     *         or Unauthorized (status code 401)
     *         or Forbidden (status code 403)
     *         or Not Found (status code 404)
     */
    @Operation(
        operationId = "getUsuario",
        summary = "Recuperar usuario",
        description = "Recupera un usuario mediante su ID",
        tags = {  },
        responses = {
            @ApiResponse(responseCode = "200", description = "OK", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = PerfilUsuarioDto.class))
            }),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDto.class))
            }),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDto.class))
            }),
            @ApiResponse(responseCode = "404", description = "Not Found", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDto.class))
            })
        },
        security = {
            @SecurityRequirement(name = "Token")
        }
    )
    @RequestMapping(
        method = RequestMethod.GET,
        value = "/usuarios/{id}",
        produces = { "application/json" }
    )
    ResponseEntity<PerfilUsuarioDto> getUsuario(
        @Parameter(name = "id", description = "Id del usuario", required = true, in = ParameterIn.PATH) @PathVariable("id") Integer id
    );


    /**
     * POST /usuarios/{id}/mandarCorreoConfirmacion : Mandar correo de confirmación
     * Manda un correo de confirmación de dirección de correo electrónico al usuario
     *
     * @param id Id del usuario (required)
     * @return No Content (status code 204)
     *         or Unauthorized (status code 401)
     *         or Forbidden (status code 403)
     *         or Not Found (status code 404)
     */
    @Operation(
        operationId = "mandarCorreoConfirmacion",
        summary = "Mandar correo de confirmación",
        description = "Manda un correo de confirmación de dirección de correo electrónico al usuario",
        responses = {
            @ApiResponse(responseCode = "204", description = "No Content"),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDto.class))
            }),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDto.class))
            }),
            @ApiResponse(responseCode = "404", description = "Not Found", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDto.class))
            })
        },
        security = {
            @SecurityRequirement(name = "Token")
        }
    )
    @RequestMapping(
        method = RequestMethod.POST,
        value = "/usuarios/{id}/mandarCorreoConfirmacion",
        produces = { "application/json" }
    )
    ResponseEntity<Void> mandarCorreoConfirmacion(
        @Parameter(name = "id", description = "Id del usuario", required = true, in = ParameterIn.PATH) @PathVariable("id") Integer id
    );


    /**
     * POST /usuarios/{id}/validaPuedeIntercambiar : Validar puede intercambiar
     * Valida que el usuario cumpla los requerimientos para poder realizar intercambios de libros con otros usuarios
     *
     * @param id Id del usuario (required)
     * @return OK (status code 200)
     *         or Unauthorized (status code 401)
     *         or Forbidden (status code 403)
     *         or Not Found (status code 404)
     */
    @Operation(
        operationId = "validaPuedeIntercambiar",
        summary = "Validar puede intercambiar",
        description = "Valida que el usuario cumpla los requerimientos para poder realizar intercambios de libros con otros usuarios",
        responses = {
            @ApiResponse(responseCode = "200", description = "OK", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ValidaPuedeIntercambiar200ResponseDto.class))
            }),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDto.class))
            }),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDto.class))
            }),
            @ApiResponse(responseCode = "404", description = "Not Found", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDto.class))
            })
        },
        security = {
            @SecurityRequirement(name = "Token")
        }
    )
    @RequestMapping(
        method = RequestMethod.POST,
        value = "/usuarios/{id}/validaPuedeIntercambiar",
        produces = { "application/json" }
    )
    ResponseEntity<ValidaPuedeIntercambiar200ResponseDto> validaPuedeIntercambiar(
        @Parameter(name = "id", description = "Id del usuario", required = true, in = ParameterIn.PATH) @PathVariable("id") Integer id
    );


    /**
     * POST /usuarios/{id}/verificarCorreo : Verificar correo
     * Verifica la dirección de correo de un usuario
     *
     * @param id Id del usuario (required)
     * @param codigoVerificacionDto  (optional)
     * @return No Content (status code 204)
     *         or Bad Request (status code 400)
     *         or Unauthorized (status code 401)
     *         or Forbidden (status code 403)
     *         or Not Found (status code 404)
     */
    @Operation(
        operationId = "verificarCorreo",
        summary = "Verificar correo",
        description = "Verifica la dirección de correo de un usuario",
        responses = {
            @ApiResponse(responseCode = "204", description = "No Content"),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDto.class))
            }),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDto.class))
            }),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDto.class))
            }),
            @ApiResponse(responseCode = "404", description = "Not Found", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDto.class))
            })
        },
        security = {
            @SecurityRequirement(name = "Token")
        }
    )
    @RequestMapping(
        method = RequestMethod.POST,
        value = "/usuarios/{id}/verificarCorreo",
        produces = { "application/json" },
        consumes = { "application/json" }
    )
    ResponseEntity<Void> verificarCorreo(
        @Parameter(name = "id", description = "Id del usuario", required = true, in = ParameterIn.PATH) @PathVariable("id") Integer id,
        @Parameter(name = "CodigoVerificacionDto", description = "") @Valid @RequestBody(required = false) CodigoVerificacionDto codigoVerificacionDto
    );

}
