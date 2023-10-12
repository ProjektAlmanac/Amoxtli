package io.github.projektalmanac.amoxtli.backend.entity;

import io.github.projektalmanac.amoxtli.backend.enums.Status;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Exchange {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private Status status;
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
