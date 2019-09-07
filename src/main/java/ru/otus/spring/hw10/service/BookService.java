package ru.otus.spring.hw10.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.hw10.dao.BookRepository;
import ru.otus.spring.hw10.models.Author;
import ru.otus.spring.hw10.models.Book;
import ru.otus.spring.hw10.models.Genre;

@Service
@Transactional
public class BookService {
    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public boolean update(long id, String name, Long authorId, Long genreId) {
        Optional<Book> optional = bookRepository.findById(id);
        if (!optional.isPresent()) {
            return false;
        }

        Book book = optional.get();

        if (authorId != null) {
            book.setAuthor(new Author().withId(authorId));
        }

        if (genreId != null) {
            book.setGenre(new Genre().withId(genreId));
        }

        if (name != null) {
            book.setName(name);
        }

        bookRepository.save(book);
        return true;
    }

    public boolean updateName(long id, String name) {
        return update(id, name, null, null);
    }

    public boolean updateAuthor(long id, long authorId) {
        return update(id, null, authorId, null);
    }

    public boolean updateGenre(long id, long genreId) {
        return update(id, null, null, genreId);
    }
}
