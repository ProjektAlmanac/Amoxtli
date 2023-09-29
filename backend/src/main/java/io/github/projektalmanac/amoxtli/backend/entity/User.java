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
    private String passwordHash;
    private String passwordSalt;
    private String phone;
    private byte[] photo;
    private String photoDescription;
    private String interests;
    private boolean  verifiedEmail;
    private String verificationCode;
    @OneToMany(targetEntity = Book.class,fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name = "id_book")
    private List<Book> books = new ArrayList<>();

    public boolean addBook(Book book) {
		
		if(book == null) {
			throw new EmptyResourceException();
		}
		
		if(books.contains(book)) {
			// Checo si el libro está en la lista de libros por que no se puede agregar un libro dos veces
			return false;
		}
		
		return books.add(book); 
	}

}
