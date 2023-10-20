package io.github.projektalmanac.amoxtli.backend;

import io.github.projektalmanac.amoxtli.backend.entity.*;
import io.github.projektalmanac.amoxtli.backend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Stream;

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


        var users = Stream.of(
                        User.builder()
                                .name("Juan")
                                .lastName("Pérez")
                                .phone("1234567890")
                                .email("jperez@example.com")
                                .verifiedEmail(true),
                        User.builder().name("Eduardo").lastName("Castro").verifiedEmail(true)
                                .email("eduardo@test.com")
                                .passwordHash("$2a$12$2cFDfIxQp1c7AU./jk2sdu/I7x/EGC1F0OkfiWvk.CHQ6FlHTF/9.") // 123456789
                                .passwordSalt("$2a$12$ZD7KBp.Dlki/7pQvg1i6ae")
                                .phone("1234567890"),
                        User.builder().name("Abigail").lastName("Mariscal").verifiedEmail(true).phone("0987654321"),
                        User.builder().name("Gisell").lastName("Soriano").verifiedEmail(true),
                        User.builder().name("Andy Leonardo").lastName("Serrano").verifiedEmail(true),
                        User.builder().name("Miguel").lastName("Guzmán").verifiedEmail(true)
                )
                .map(User.UserBuilder::build)
                .toList();

        var testIsbn = "1617293997";

        var books = List.of(
                new Book(0, "8439729391", "", users.get(1)),
                new Book(0, "6074535973", "", users.get(1)),
                new Book(0, "194778370X", "", users.get(1)),
                new Book(0, "6070726766", "", users.get(1)),

                new Book(0, "6075507728", "", users.get(2)),
                new Book(0, "6075278834", "", users.get(2)),
                new Book(0, "6075276114", "", users.get(2)),
                new Book(0, "849918538X", "", users.get(2)),
                new Book(0, "8498382661", "", users.get(2)),
                new Book(0, "6075509534", "", users.get(2)),

                new Book(0, testIsbn, "", users.get(1)),
                new Book(0, testIsbn, "", users.get(2)),
                new Book(0, testIsbn, "", users.get(3)),
                new Book(0, testIsbn, "", users.get(4)),
                new Book(0, testIsbn, "", users.get(5)),

                new Book(0, "8425426537", "", users.get(3)),
                new Book(0, "8804723963", "", users.get(3)),
                new Book(0, "8804723963", "", users.get(3)),
                new Book(0, "1505833345", "", users.get(3)),
                new Book(0, "3125357721", "", users.get(3)),

                new Book(0, "0199691622", "", users.get(4)),
                new Book(0, "968166602X", "", users.get(4)),
                new Book(0, "849561877X", "", users.get(4)),
                new Book(0, "6073167016", "", users.get(4)),

                new Book(0, "9684110545", "", users.get(5))
        );

        userRepository.saveAll(users);

        bookRepository.saveAll(books);
    }
}
