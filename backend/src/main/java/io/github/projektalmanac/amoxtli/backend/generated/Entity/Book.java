package io.github.projektalmanac.amoxtli.backend.generated.Entity;

import lombok.Data;

import javax.persistence.*;

@Entity @Data public class Book {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private int id;
    private String isbn;
    private String description;
}
