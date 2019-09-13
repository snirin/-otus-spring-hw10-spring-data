package ru.otus.spring.hw10.dao;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.hw10.models.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
    @EntityGraph(value = "BooksWithAttributes")
    @Override
    List<Book> findAll();
}
