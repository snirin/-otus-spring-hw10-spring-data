package ru.otus.spring.hw10.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.hw10.models.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
