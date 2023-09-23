package io.github.projektalmanac.amoxtli.backend.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String lastName;
    private String email;
    private String hashPassword;
    private String salPassword;
    private String phone;
    private byte[] photo;
    private String descriptionPhoto;
    private String interests;
    private boolean  verifiedEmail;
    private String verifiedCode;
    @OneToMany(targetEntity = Book.class,fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name = "id_book")
    private List<Book> libro = new ArrayList<>();

}
