package io.github.projektalmanac.amoxtli.backend.service;

import io.github.projektalmanac.amoxtli.backend.entity.User;
import io.github.projektalmanac.amoxtli.backend.exception.UserNotFoundException;
import io.github.projektalmanac.amoxtli.backend.generated.model.UsuarioDto;
import io.github.projektalmanac.amoxtli.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UsuarioDto createuser(UsuarioDto usuario) {

        // Regla de negocio: No se puede crear más de un usuario con el mismo correo
        Optional<User> userOpt = userRepository.findByEmail(usuario.getCorreo());

        if (!userOpt.isPresent()) {
            User usuario1 = new User();
            usuario1.setName(usuario.getNombre());
            usuario1.setLastName(usuario.getApellidos());
            usuario1.setEmail(usuario.getCorreo());
            usuario1.setPasswordHash(usuario.getPassword());
            //pasar de DTO a entidad,
            //pasar de entidad a DTO,
             userRepository.save(usuario1);

             UsuarioDto usuarioDto1 = new UsuarioDto();
            usuarioDto1.setNombre(usuario1.getName());
            usuarioDto1.setApellidos(usuario1.getLastName());
            usuarioDto1.setCorreo(usuario1.getEmail());
            usuarioDto1.setPassword(usuario1.getPasswordHash());

            return usuarioDto1;
        } else {
            return null;
        }
    }


  /*  public UsuarioDto createuser(UsuarioDto usuario) {

        // Regla de negocio: No se puede crear más de un alumno con el mismo correo
        Optional <UsuarioDto> userOpt = userRepository.findByCorreo(usuario.getCorreo());

        }

    }*/
}
