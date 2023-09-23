package io.github.projektalmanac.amoxtli.backend.generated.repository;

import io.github.projektalmanac.amoxtli.backend.generated.Entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository public interface IBookRepository extends JpaRepository<Book,Integer> {
}
