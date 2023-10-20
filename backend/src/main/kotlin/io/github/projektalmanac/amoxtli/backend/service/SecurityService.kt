package io.github.projektalmanac.amoxtli.backend.service

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.security.crypto.bcrypt.BCrypt
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import java.lang.IllegalStateException

@Service
open class SecurityService(private val secrets: Secrets) {

    // Nivel de fuerza del hash, entre mayor sea, más costo computacional tendrá, pero hará más seguro el sistema
    private val nivelHash = 12

    private var encoder = BCryptPasswordEncoder(nivelHash)


    /**
     * Método que realiza un hash sobre la contraseña del usuario
     * Este método se usara para:
     * - Guardar contraseña en DB del usuario
     * - Comparar contraseña al iniciar sesión el usuario
     * @param contrasena contraseña del usuario
     * @return contraseña hasheada
     */
    fun hashContrasena(contrasena: String): String {
        return encoder.encode(contrasena.trim())
    }

    fun saltHash(): String {
        return BCrypt.gensalt(nivelHash)
    }

    /**
     * Se encarga de comparar la contraseña hasheada durante el registro, realizando la autenticación
     * @param contrasena: Contraseña que se obtiene del dtoCredenciales
     * @param hashedPassword: Contraseña que se tiene almacenada en DB
     * @return true or false
     */
    fun matchContrasena(contrasena: String, hashedPassword: String): Boolean {
        return encoder.matches(contrasena.trim(), hashedPassword.trim())
    }


    /**
     * Generador de token de usuario mediante el paquete JWT,
     * este token contiene los @param, los cuales pueden ser más de uno.
     * Sin embargo, pueden agregarse más si asi lo necesitan
     * usamos una fuerza de hash de 8, además de la clave secreta que es
     * una variable de entorno creada con OpenSSL con el mismo nivel de fuerza
     *
     *
     * NOTA: PARA TENER EN EL SISTEMA LA CLAVE SECRETA SE DEBE CREAR UNA VARIABLE DE ENTORNO
     * LLAMADA 'JWT_SECRET_KEY' USANDO OPENSSL, GENERE LA SUYA CON LA SIMPLE INSTRUCCIóN:
     * export JWT_SECRET_KEY=$(openssl rand -hex 32)
     * O
     * openssl rand -hex 32
     * export JWT_SECRET_KEY="TuClaveSecretaHexadecimal"
     * @param idUsuario: Es el ID del usuario que inicia sesión
     * @return TOKEN JWT
     */
    fun generadorToken(idUsuario: String?): String {
        return Jwts.builder()
            .setSubject(idUsuario)
            .signWith(SignatureAlgorithm.HS256, secrets.secretKey)
            .compact()
    }

    /**
     * Decodificar información del usuario con base al token que se generó cuando se inició sesión,
     * esto se hace a partir del parse de JWT, que necesita el token del usuario actual, asi
     * como la CLAVE_SECRETA, la cual es un valor asociado a una variable de entorno que se creó usando openSSL
     * @param token: Es el tone que se genera al inicio de sesión
     * @return Id del usuario, esto es por la definición en como se construyo el token
     */
    fun decodificarToken(token: String?): Claims {
        return Jwts.parser()
            .setSigningKey(secrets.secretKey)
            .parseClaimsJws(token)
            .body
    }
}

@Service
open class Secrets {
    val secretKey: String by lazy {
        val envVar = "JWT_SECRET_KEY"
        val key = System.getenv(envVar)
        if (key.isNullOrEmpty())
            throw IllegalStateException("Favor de proporcionar una clave secreta para JWT mediante la variable de entorno $envVar")
        System.getenv(envVar)
    }
}