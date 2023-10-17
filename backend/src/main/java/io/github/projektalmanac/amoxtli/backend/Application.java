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
	private BookRepository bookRepository;

	@Autowired
	private ExchangeRepository exchangeRepository;
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);

	}

	@PostConstruct
	public void postConstruct() {
		var user = new User();
		user.setName("Maria");
		user.setLastName("Pérez");
		user.setId(1);
		user.setVerifiedEmail(true);
		user.setPhone("5578476989");

		userRepository.save(user);

		var user2 = new User();
		user2.setName("Juan");
		user2.setLastName("Martinez");
		user2.setId(2);
		user2.setVerifiedEmail(true);
		user2.setPhone("5582134679");

		userRepository.save(user2);

		var user3 = new User();
		user3.setName("Pedro");
		user3.setLastName("Lopez");
		user3.setId(3);
		user3.setVerifiedEmail(true);
		user3.setPhone("5579461382");

		userRepository.save(user3);

		var book = new Book();
		book.setId(1);
		book.setIsbn("1111111111");
		book.setDescription("Casi nuevo el libro");

		bookRepository.save(book);

		var book2 = new Book();
		book2.setId(2);
		book2.setIsbn("2222222222");
		book2.setDescription("Casi nuevo el libro");

		bookRepository.save(book2);

		var book3 = new Book();
		book3.setId(3);
		book3.setIsbn("3333333333");
		book3.setDescription("Casi nuevo el libro");

		bookRepository.save(book3);

		Status status = Status.PENDIENTE;
		Status status2 = Status.ACEPTADO;

		var intercambio = new Exchange();
		intercambio.setId(1);
		intercambio.setStatus(status2);
		intercambio.setBookAccepting(book);
		intercambio.setUserAccepting(user);
		intercambio.setUserOfferor(user2);
		intercambio.setBookOfferor(book2);

		exchangeRepository.save(intercambio);

		user.addExchangesAccepting(intercambio);
		var intercambio2 = new Exchange();
		intercambio2.setId(2);
		intercambio2.setStatus(status);
		intercambio2.setBookAccepting(book2);
		intercambio2.setUserAccepting(user);
		intercambio2.setUserOfferor(user3);
		intercambio2.setBookOfferor(null);
		exchangeRepository.save(intercambio2);

		List<Exchange> exchangesOfferor =  new ArrayList<>();
		exchangesOfferor.add(intercambio2);
		
		user.setExchangesOfferor(exchangesOfferor);

	}
}
