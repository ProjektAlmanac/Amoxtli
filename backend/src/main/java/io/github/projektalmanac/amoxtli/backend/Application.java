package io.github.projektalmanac.amoxtli.backend;

import io.github.projektalmanac.amoxtli.backend.entity.*;
import io.github.projektalmanac.amoxtli.backend.enums.Status;
import io.github.projektalmanac.amoxtli.backend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class Application {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ExchangeRepository exchangeRepository;
	@Autowired
	private BookRepository bookRepository;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);

	}

	@PostConstruct
	public void postConstruct() {
		var user = new User();
		user.setName("Maria");
		user.setLastName("Pérez");
		user.setId(1);
		user.setPhone("1234567890");
		user.setEmail("jperaz@gmail.com");
		user.setVerifiedEmail(true);
		user.setPhone("5578476989");

		var user2 = new User();
		user2.setName("Carlos");
		user2.setLastName("Castro");
		user2.setId(2);
		user2.setVerifiedEmail(true);

		var user3 = new User();
		user3.setName("Samuel");
		user3.setLastName("Morales");
		user3.setId(3);
		user3.setVerifiedEmail(true);
		List<Book> libros = new ArrayList<>();
		List<Book> libros2 = new ArrayList<>();

		var book = new Book();
		book.setIsbn("9783716026434");
		book.setDescription("EL libro esta en buen estado");

		var book1 = new Book();
		book1.setIsbn("9780156014045");
		book1.setDescription("Tiene algunas hojas dobladas");

		var book2 = new Book();
		book2.setIsbn("9786077350140");
		book2.setDescription("Solo tiene una hoja rota");

		libros.add(book);
		libros.add(book1);
		user.setBooks(libros);

		libros2.add(book);
		libros2.add(book1);
		libros2.add(book2);
		user2.setBooks(libros2);

		userRepository.save(user);
		userRepository.save(user2);
		userRepository.save(user3);

		bookRepository.save(book);
		bookRepository.save(book1);
		bookRepository.save(book2);

	}
}
