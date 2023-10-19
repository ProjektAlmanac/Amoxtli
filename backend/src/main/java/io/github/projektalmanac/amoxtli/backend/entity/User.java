package io.github.projektalmanac.amoxtli.backend.entity;

import io.github.projektalmanac.amoxtli.backend.exception.EmptyResourceException;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String lastName;
    private String email;
    private String passwordHash;
    private String passwordSalt;
    private String phone;
    @Lob
    private byte[] photo;
    private String photoDescription;
    private String interests;
    private boolean verifiedEmail;
    private String verificationCode;
    @OneToMany(targetEntity = Book.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_user")
    private List<Book> books = new ArrayList<>();

    @OneToMany(mappedBy = "userAccepting")
    private List<Exchange> exchangesAccepting =  new ArrayList<>();

    @OneToMany(mappedBy = "userOfferor")
    private List<Exchange> exchangesOfferor =  new ArrayList<>();

    public boolean addBook(Book book) {

        if (book == null) {
            // TODO: Cambiar a excepción correcta
            throw new EmptyResourceException();
        }

        if (books.contains(book)) {
            // Checo si el libro está en la lista de libros por que no se puede agregar un
            // libro dos veces
            return false;
        }

        return books.add(book);
    }

    public boolean addExchangesAccepting(Exchange intercambio) {

        if (intercambio == null) {
            throw new EmptyResourceException();
        }

        if (exchangesAccepting.contains(intercambio)) {
            //Verificar que no se encuentre en intercambio todavia en la lista de intercambios aceptados en el usuario
            return false;
        }

        return exchangesAccepting.add(intercambio);
    }

    public boolean removeExchangesOfferor(Exchange intercambio) {

        if (intercambio == null) {
            throw new EmptyResourceException();
        }

        return exchangesOfferor.remove(intercambio);
    }

}
