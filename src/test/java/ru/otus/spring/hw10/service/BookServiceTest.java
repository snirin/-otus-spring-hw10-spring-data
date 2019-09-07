package ru.otus.spring.hw10.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import ru.otus.spring.hw10.models.Author;
import ru.otus.spring.hw10.models.Book;
import ru.otus.spring.hw10.models.Genre;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

@RunWith(SpringRunner.class)
@Import(BookService.class)
@DataJpaTest
public class BookServiceTest {
    private static final String AUTHOR_1 = "Author1";
    private static final String AUTHOR_2 = "Author2";
    private static final String GENRE_1 = "Genre1";
    private static final String GENRE_2 = "Genre2";
    private static final String BOOK_1 = "Book1";
    private static final String BOOK_2 = "Book2";
    private static final long ABSENT_ID = 12345L;

    @Autowired
    private TestEntityManager em;

    @Autowired
    private BookService bookService;

    private Book book1;
    private Author author1;
    private Author author2;
    private Genre genre1;
    private Genre genre2;

    @Before
    public void setUp() {
        author1 = em.persistAndFlush(new Author(0, AUTHOR_1));
        author2 = em.persistAndFlush(new Author(0, AUTHOR_2));
        genre1 = em.persistAndFlush(new Genre(0, GENRE_1));
        genre2 = em.persistAndFlush(new Genre(0, GENRE_2));
        book1 = em.persistAndFlush(new Book(0, BOOK_1, author1, genre1));
        em.clear();
    }

    @Test
    public void update_ifPresent() {
        boolean updated = bookService.update(book1.getId(), BOOK_2, author2.getId(), genre2.getId());
        assertTrue(updated);
        em.flush();
        em.clear();
        Book result = em.find(Book.class, book1.getId());
        Book expected = new Book(book1.getId(), BOOK_2, author2, genre2);
        assertEquals(expected, result);
    }

    @Test
    public void update_ifAbsent() {
        boolean updated = bookService.update(ABSENT_ID, BOOK_2, author2.getId(), genre2.getId());
        assertFalse(updated);

        Book result = em.find(Book.class, ABSENT_ID);
        assertNull(result);
    }

    @Test
    public void updateName() {
        boolean updated = bookService.updateName(book1.getId(), BOOK_2);
        assertTrue(updated);
        em.flush();
        em.clear();
        Book result = em.find(Book.class, book1.getId());
        Book expected = book1.withName(BOOK_2);
        assertEquals(expected, result);
    }

    @Test
    public void updateAuthor() {
        boolean updated = bookService.updateAuthor(book1.getId(), author2.getId());
        assertTrue(updated);
        em.flush();
        em.clear();
        Book result = em.find(Book.class, book1.getId());
        Book expected = book1.withAuthor(author2);
        assertEquals(expected, result);
    }

    @Test
    public void updateGenre() {
        boolean updated = bookService.updateGenre(book1.getId(), genre2.getId());
        assertTrue(updated);
        em.flush();
        em.clear();
        Book result = em.find(Book.class, book1.getId());
        Book expected = book1.withGenre(genre2);
        assertEquals(expected, result);
    }
}
