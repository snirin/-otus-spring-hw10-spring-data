package ru.otus.spring.hw10.service;

import java.util.List;
import java.util.Optional;

import ru.otus.spring.hw10.models.Book;

public interface BookService {
    boolean update(long id, String name, Long authorId, Long genreId);

    boolean updateName(long id, String name);

    boolean updateAuthor(long id, long authorId);

    boolean updateGenre(long id, long genreId);

    Book save(Book book);

    boolean deleteById(long id);

    Optional<Book> findById(long id);

    List<Book> findAll();

    long count();
}
