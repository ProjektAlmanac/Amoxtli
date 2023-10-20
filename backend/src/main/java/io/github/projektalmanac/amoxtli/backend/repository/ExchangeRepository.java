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

    Optional<Exchange> findByIdAndUserOfferor(Integer id, User userOfferor);

    Optional<Exchange> findByIdAndUserAccepting(Integer id,User userAccepting);

    @Modifying
    @Transactional
    @Query("UPDATE Exchange e SET e.status = :statusNuevo WHERE e.id = :idIntercambio")
    int actualizarStatusIntercambio(@Param("idIntercambio") Integer idIntercambio, @Param("statusNuevo") Status statusNuevo);


    @Modifying
    @Transactional
    @Query("UPDATE Exchange e SET e.offerorConfirmationCode = :offerorConfirmationCode WHERE e.id = :idIntercambio")
    int actualizarOfferorConfirmationCode(@Param("idIntercambio") Integer idIntercambio, @Param("offerorConfirmationCode") String offerorConfirmationCode);


    @Modifying
    @Transactional
    @Query("UPDATE Exchange e SET e.confirmationCodeAccepting = :confirmationCodeAccepting WHERE e.id = :idIntercambio")
    int actualizarConfirmationCodeAccepting(@Param("idIntercambio") Integer idIntercambio, @Param("confirmationCodeAccepting") String confirmationCodeAccepting);

}
