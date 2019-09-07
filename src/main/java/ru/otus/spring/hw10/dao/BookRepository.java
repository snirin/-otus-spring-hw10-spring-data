package ru.otus.spring.hw10.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.hw10.models.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
}
