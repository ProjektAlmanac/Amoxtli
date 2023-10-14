package io.github.projektalmanac.amoxtli.backend;

import io.github.projektalmanac.amoxtli.backend.entity.*;
import io.github.projektalmanac.amoxtli.backend.enums.Status;
import io.github.projektalmanac.amoxtli.backend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class Application {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BookRepository bookRepository;

	@Autowired
	private ExchangeRepository exchangeRepository;
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);

	}

	@PostConstruct
	public void postConstruct() {
		var user = new User();
		user.setName("Juan");
		user.setLastName("Pérez");
		user.setId(1);
		user.setVerifiedEmail(true);
		user.setPhone("5578476989");

		userRepository.save(user);

		var user2 = new User();
		user2.setName("Maria");
		user2.setLastName("Pérez");
		user2.setId(2);
		user2.setVerifiedEmail(true);
		user2.setPhone("5582134679");

		userRepository.save(user2);

		var book = new Book();
		book.setId(1);
		book.setIsbn("9788413621654");
		book.setDescription("Casi nuevo el libro");

		bookRepository.save(book);

		var book2 = new Book();
		book2.setId(2);
		book2.setIsbn("9788413621654");
		book2.setDescription("Casi nuevo el libro");

		bookRepository.save(book2);

		Status status = Status.CANCELADO;

		var intercambio = new Exchange();
		intercambio.setId(1);
		intercambio.setStatus(status);
		intercambio.setBookAccepting(book);
		intercambio.setUserAccepting(user2);
		intercambio.setUserOfferor(user2);
		intercambio.setBookOfferor(null);

		exchangeRepository.save(intercambio);

	}
}
