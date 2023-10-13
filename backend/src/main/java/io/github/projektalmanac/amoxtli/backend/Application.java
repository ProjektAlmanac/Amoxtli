package io.github.projektalmanac.amoxtli.backend;

import io.github.projektalmanac.amoxtli.backend.entity.User;
import io.github.projektalmanac.amoxtli.backend.entity.Book;
import io.github.projektalmanac.amoxtli.backend.repository.BookRepository;
import io.github.projektalmanac.amoxtli.backend.repository.ExchangeRepository;
import io.github.projektalmanac.amoxtli.backend.repository.UserRepository;
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
		user.setName("Juan");
		user.setLastName("Pérez");
		user.setId(1);
		user.setVerifiedEmail(true);

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
		List<Book> libro = new ArrayList<>();
		List<Book> libro2 = new ArrayList<>();

		var book = new Book();
		book.setIsbn("9783716026434");
		book.setDescription("El principito, pasta dura");

		var book1 = new Book();
		book1.setIsbn("9780156014045");
		book1.setDescription("El principito, pasta blanda");

		var book2 = new Book();
		book2.setIsbn("9786077350140");
		book2.setDescription("El principito, tapa blanda");

		libro.add(book);
		libro.add(book1);
		user.setBooks(libro);

		libro2.add(book);
		libro2.add(book1);
		libro2.add(book2);
		user2.setBooks(libro2);

		userRepository.save(user);
		userRepository.save(user2);
		userRepository.save(user3);

		bookRepository.save(book);
		bookRepository.save(book1);
		bookRepository.save(book2);

	}
}
