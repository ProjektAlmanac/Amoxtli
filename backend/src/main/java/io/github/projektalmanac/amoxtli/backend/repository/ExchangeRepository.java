package io.github.projektalmanac.amoxtli.backend.repository;

import io.github.projektalmanac.amoxtli.backend.entity.Exchange;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExchangeRepository extends JpaRepository<Exchange,Integer> {
}
