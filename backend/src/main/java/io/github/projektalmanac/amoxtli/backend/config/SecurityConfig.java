package io.github.projektalmanac.amoxtli.backend.config;

import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    // Nivel de fuerza del hash, entre mayor sea, más costo computacional tendrá, pero hará más seguro el sistema
    private static final int NIVEL_HASH = 12;
    protected static BCryptPasswordEncoder hash = new BCryptPasswordEncoder(NIVEL_HASH);
    //Clave para el token
    private static final String CLAVE_SECRETA = "cc471927e7a0a3d3d56cdab65312ca4d311b65dd518af5e4b8007f1ef3908465";//System.getenv("JWT_SECRET_KEY"); // Cargar desde una variable de entorno

    /**
     * Método que realiza un hash sobre la contraseña del usuario
     * Este método se usara para:
     * - Guardar contraseña en DB del usuario
     * - Comparar contraseña al iniciar sesión el usuario
     * @param contrasena contraseña del usuario
     * @return contraseña hasheada
     */
    public static String hashContrasena(String contrasena) {
        return hash.encode(contrasena.trim());
    }

    public static  String saltHash(){
        return BCrypt.gensalt(NIVEL_HASH);
    }

    /**
     * Se encarga de comparar la contraseña hasheada durante el registro, realizando la autenticación
     * @param contrasena: Contraseña que se obtiene del dtoCredenciales
     * @param hashedPassword: Contraseña que se tiene almacenada en DB
     * @return true or false
     */
    public static boolean matchContrasena(String contrasena, String hashedPassword){
        return hash.matches(contrasena.trim(), hashedPassword.trim());
    }


    /**
     * Generador de token de usuario mediante el paquete JWT,
     * este token contiene los @param, los cuales pueden ser más de uno.
     * Sin embargo, pueden agregarse más si asi lo necesitan
     * usamos una fuerza de hash de 8, además de la clave secreta que es
     * una variable de entorno creada con OpenSSL con el mismo nivel de fuerza
     * <p>
     * NOTA: PARA TENER EN EL SISTEMA LA CLAVE SECRETA SE DEBE CREAR UNA VARIABLE DE ENTORNO
     * LLAMADA 'JWT_SECRET_KEY' USANDO OPENSSL, GENERE LA SUYA CON LA SIMPLE INSTRUCCIóN:
     * export JWT_SECRET_KEY=$(openssl rand -hex 32)
     * O
     * openssl rand -hex 32
     * export JWT_SECRET_KEY="TuClaveSecretaHexadecimal"
     * @param idUsuario: Es el ID del usuario que inicia sesión
     * @return TOKEN JWT
     */
    public static String generadorToken(String idUsuario) {
        return Jwts.builder()
                .setSubject(idUsuario)
                .signWith(SignatureAlgorithm.HS256, CLAVE_SECRETA)
                .compact();
    }

    /**
     * Decodificar información del usuario con base al token que se generó cuando se inició sesión,
     * esto se hace a partir del parse de JWT, que necesita el token del usuario actual, asi
     * como la CLAVE_SECRETA, la cual es un valor asociado a una variable de entorno que se creó usando openSSL
     * @param token: Es el tone que se genera al inicio de sesión
     * @return ID de usuario, esto es por la definición en como se construyo el token
     */
    public static Claims decodificarToken(String token) {
        return Jwts.parser()
                .setSigningKey(CLAVE_SECRETA)
                .parseClaimsJws(token)
                .getBody();
    }
    @Override
    public void configure(org.springframework.security.config.annotation.web.builders.HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/**").permitAll()
                .and()
                .csrf().disable()
                .headers().frameOptions().sameOrigin();
    }
}
