package io.github.projektalmanac.amoxtli.backend.config;

import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    // Nivel de fuerza del hash, entre mayor sea mas costo computacional tendra, pero hara mas seguro el sistema
    private static final int NIVEL_HASH = 12;
    protected  static BCryptPasswordEncoder hash = new BCryptPasswordEncoder(NIVEL_HASH);

    /**
     * Metodo que realiza un hash sobre la contraseña del ususario
     * Este metodo se usara para:
     * - Guardar contrasena en DB del usuario
     * - Comparar contrasena al iniciar sesion el usuario
     * @param contrasena
     * @return contraseña hasheada
     */
    public static String hashContrasena(String contrasena) {
        return hash.encode(contrasena.trim());
    }

    /**
     * Se encarga de comparar la contrasena hasheada durante el registro, realizando la autenticacion
     * @param contrasena: Contrasena que se obtiene del dtoCredenciales
     * @param hashedPassword: Contrasena que se tiene almacenada en DB
     * @return true or false
     */
    public static boolean matchContrasena(String contrasena, String hashedPassword){
        return hash.matches(contrasena.trim(), hashedPassword.trim());
    }



    public static String generadorToken(String idUsuario, String claveSecreta) {
        return Jwts.builder()
                .setSubject(idUsuario)
                .signWith(SignatureAlgorithm.HS256, claveSecreta)
                .compact();
    }

    public static String hashToken(String token) {
        return hash.encode(token);
    }

    public static Claims decodificarToken(String token,String claveSecreta) {
        return Jwts.parser()
                .setSigningKey(claveSecreta)
                .parseClaimsJws(token)
                .getBody();
    }
    @Override
    public void configure(org.springframework.security.config.annotation.web.builders.HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/**").permitAll()
                .and()
                .csrf().disable();
    }
}
