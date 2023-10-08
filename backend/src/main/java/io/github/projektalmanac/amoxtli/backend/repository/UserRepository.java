package io.github.projektalmanac.amoxtli.backend.repository;

import io.github.projektalmanac.amoxtli.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("SELECT CASE WHEN COUNT(b) > 0 THEN true ELSE false END FROM User u JOIN u.books b WHERE b.isbn = :isbn AND u.id = :userId")
    boolean existsBookByIsbnAndUserId(@Param("isbn") String isbn, @Param("userId") Integer userId);

    public User getUserById(Integer id);
}
