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
    private Book bookOfferor;
    @OneToOne
    private Book bookAccepting;
    @ManyToOne
    private User userAccepting;
    @ManyToOne
    private User userOfferor;
}
