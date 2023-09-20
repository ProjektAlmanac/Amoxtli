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


/**
 * Datos de un libro que está registrado como parte del catálogo de un usario
 */
export interface LibroRegistrado { 
    /**
     * ID del libro
     */
    readonly id: number;
    /**
     * ISBN (International Standard Book Number) del lib
     */
    isbn: string;
    /**
     * Descripción de la condición en la que se encuentra la copia del libro que posee el
     */
    descripcion: string;
}

