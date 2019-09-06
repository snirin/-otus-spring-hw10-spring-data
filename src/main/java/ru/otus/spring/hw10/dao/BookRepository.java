package ru.otus.spring.hw10.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.hw10.models.Book;

@Transactional
public interface BookRepository extends JpaRepository<Book, Integer> {
}
