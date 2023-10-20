package io.github.projektalmanac.amoxtli.backend.repository;

import io.github.projektalmanac.amoxtli.backend.entity.Exchange;
import io.github.projektalmanac.amoxtli.backend.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import io.github.projektalmanac.amoxtli.backend.entity.User;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface ExchangeRepository extends JpaRepository<Exchange,Integer> {

    //Optional<User> findByEmailAndPasswordHash(String email, String passwordHash);


    @Override
    Optional<Exchange> findById(Integer integer);
}
