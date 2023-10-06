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
 * Datos del perfil de un usuario
 */
export interface PerfilUsuario { 
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
    apellidos: string;
    /**
     * Correo electrónico del usuario
     */
    correo: string;
    /**
     * Numero telefónico del usuario
     */
    telefono: string;
    /**
     * Descripción de la foto de perfil del usuario
     */
    descripcionFoto: string | null;
    /**
     * Descripción de los intereses del usuario
     */
    intereses: string | null;
    /**
     * URL de la foto de perfil del usuario
     */
    fotoPerfil: string | null;
    /**
     * Valor que indica si el usuario ha verificado su dirección de correo electrónico
     */
    correoVerificado: boolean;
}

