package io.github.projektalmanac.amoxtli.backend.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Exchange {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private  int status;
    private String offerorConfirmationCode;
    private String confirmationCodeAccepting;
    @OneToOne
    private User userAccepting;
    @OneToOne
    private User userOfferor;
}
