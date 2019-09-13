package ru.otus.spring.hw10.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.hw10.dao.BookRepository;
import ru.otus.spring.hw10.models.Author;
import ru.otus.spring.hw10.models.Book;
import ru.otus.spring.hw10.models.Genre;

@Transactional
@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public boolean update(long id, String name, Long authorId, Long genreId) {
        Optional<Book> optional = bookRepository.findById(id);
        optional
                .ifPresent(book -> {
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
                })
        ;

        return optional.isPresent();
    }

    @Override
    public boolean updateName(long id, String name) {
        return update(id, name, null, null);
    }

    @Override
    public boolean updateAuthor(long id, long authorId) {
        return update(id, null, authorId, null);
    }

    @Override
    public boolean updateGenre(long id, long genreId) {
        return update(id, null, null, genreId);
    }

    @Override
    public Book save(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public boolean deleteById(long id) {
        bookRepository.deleteById(id);
//        bookRepository.flush();
        return true;
    }

    @Override
    public Optional<Book> findById(long id) {
        return bookRepository.findById(id);
    }

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public long count() {
        return bookRepository.count();
    }
}
