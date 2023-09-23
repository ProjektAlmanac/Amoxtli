package io.github.projektalmanac.amoxtli.backend.generated.repository;

import io.github.projektalmanac.amoxtli.backend.generated.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository public interface IUserRepository extends JpaRepository<User,Integer> {
}
