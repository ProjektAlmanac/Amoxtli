package io.github.projektalmanac.amoxtli.backend.generated.Entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.*;

@Entity @Data public class Exchange {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private int id;
    private  int status;
    private String offerorConfirmationCode;
    private String confirmationCodeAccepting;
}
