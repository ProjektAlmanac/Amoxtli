package io.github.projektalmanac.amoxtli.backend.repository;

import io.github.projektalmanac.amoxtli.backend.entity.Exchange;
import io.github.projektalmanac.amoxtli.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExchangeRepository extends JpaRepository<Exchange,Integer> {
    Optional<Exchange> findByIdAndUserAccepting(Integer id, User userAccepting);
}
