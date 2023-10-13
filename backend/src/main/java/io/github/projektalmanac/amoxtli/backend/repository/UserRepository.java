package io.github.projektalmanac.amoxtli.backend.repository;

import io.github.projektalmanac.amoxtli.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    Optional<User> findByEmail(String correo);

    Optional<User> findById(long id);

    @Query("SELECT CASE WHEN COUNT(b) > 0 THEN true ELSE false END FROM User u JOIN u.books b WHERE b.isbn = :isbn AND u.id = :userId")
    boolean existsBookByIsbnAndUserId(@Param("isbn") String isbn, @Param("userId") Integer userId);

    public User getUserById(Integer id);

    Optional<User> findByEmailAndPasswordHash(String email, String passwordHash);
}
