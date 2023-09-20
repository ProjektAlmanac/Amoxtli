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
 * Detalles de un usuario que es dueño de un libro
 */
export interface Dueno { 
    /**
     * ID del usuario
     */
    id: number;
    /**
     * Nombre del usuario
     */
    nombre: string;
    /**
     * Apellido o apellidos del usuario
     */
    apellido: string;
    /**
     * Foto de perfil del usuario
     */
    foto: string | null;
}

