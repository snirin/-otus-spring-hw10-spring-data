package ru.otus.spring.hw10.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import ru.otus.spring.hw10.models.Author;

@Component
public interface AuthorRepository extends JpaRepository<Author, Integer> {
}
