package ru.otus.spring.hw10.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.hw10.dao.BookRepository;
import ru.otus.spring.hw10.models.Author;
import ru.otus.spring.hw10.models.Book;
import ru.otus.spring.hw10.models.Genre;

@Transactional
@Repository
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @PersistenceContext
    private EntityManager em;

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
        return true;
    }

    @Override
    public Optional<Book> findById(long id) {
        return bookRepository.findById(id);
    }

    @Override
    public List<Book> findAll() {
        EntityGraph<?> entityGraph = em.getEntityGraph("BooksWithAttributes");
        TypedQuery<Book> query = em.createQuery("select b from Book b", Book.class);
        query.setHint("javax.persistence.fetchgraph", entityGraph);
        return query.getResultList();
    }

    @Override
    public long count() {
        return bookRepository.count();
    }
}
