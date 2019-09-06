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

    public boolean update(int id, String name, Integer authorId, Integer genreId) {
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

    public boolean updateName(int id, String name) {
        return update(id, name, null, null);
    }

    public boolean updateAuthor(int id, int authorId) {
        return update(id, null, authorId, null);
    }

    public boolean updateGenre(int id, int genreId) {
        return update(id, null, null, genreId);
    }

    //Вынесено в сервис для загрузки полей с ленивой инициализацией
    public String findAllToString() {
        return bookRepository.findAll().toString();
    }

    public String getToString(int id) {
        return bookRepository.findById(id).map(Book::toString).orElse(null);
    }
}
