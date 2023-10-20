package io.github.projektalmanac.amoxtli.backend.repository;

import io.github.projektalmanac.amoxtli.backend.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book,Integer> {
    Book findByIsbn(String isbn);
    Book findById(int id);
    Book findFirstByIsbn(String isbn);

    List<Book> findAllByIsbn(String isbn);
}
