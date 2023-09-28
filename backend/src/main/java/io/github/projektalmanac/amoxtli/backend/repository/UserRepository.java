package io.github.projektalmanac.amoxtli.backend.repository;

import io.github.projektalmanac.amoxtli.backend.entity.User;
import io.github.projektalmanac.amoxtli.backend.generated.model.UsuarioDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    Optional<User> findByEmail(String correo);

    Optional<User> findByName(String correo);
}
