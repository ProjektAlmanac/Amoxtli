/**
 * amoxtli
 * No description provided (generated by Openapi Generator https://github.com/openapitools/openapi-generator)
 *
 * The version of the OpenAPI document: 1.0
 * 
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */
import { LibroRegistradoConDetalles } from './libroRegistradoConDetalles';


/**
 * Libros que figuran dentro del catálogo de un usuario
 */
export interface LibrosUsuario { 
    /**
     * Los libros del usuario
     */
    libros: Array<LibroRegistradoConDetalles>;
}
