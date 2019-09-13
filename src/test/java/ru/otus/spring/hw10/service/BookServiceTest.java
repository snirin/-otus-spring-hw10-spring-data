package ru.otus.spring.hw10.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.spring.hw10.models.Author;
import ru.otus.spring.hw10.models.Book;
import ru.otus.spring.hw10.models.Comment;
import ru.otus.spring.hw10.models.Genre;

import static org.assertj.core.api.Assertions.assertThat;

@Import(BookServiceImpl.class)
@DataJpaTest
public class BookServiceTest {
    private static final String AUTHOR_1 = "Author1";
    private static final String AUTHOR_2 = "Author2";
    private static final String GENRE_1 = "Genre1";
    private static final String GENRE_2 = "Genre2";
    private static final String BOOK_1 = "Book1";
    private static final String BOOK_2 = "Book2";
    private static final String COMMENT_1 = "Comment1";
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

    @BeforeEach
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
        assertThat(updated).isTrue();
        em.flush();
        em.clear();
        Book result = em.find(Book.class, book1.getId());
        Book expected = new Book(book1.getId(), BOOK_2, author2, genre2);
        assertThat(result).isEqualTo(expected);
    }

    @Test
    public void update_ifAbsent() {
        boolean updated = bookService.update(ABSENT_ID, BOOK_2, author2.getId(), genre2.getId());
        assertThat(updated).isFalse();

        Book result = em.find(Book.class, ABSENT_ID);
        assertThat(result).isNull();
    }

    @Test
    public void updateName() {
        boolean updated = bookService.updateName(book1.getId(), BOOK_2);
        assertThat(updated).isTrue();
        em.flush();
        em.clear();
        Book result = em.find(Book.class, book1.getId());
        Book expected = book1.withName(BOOK_2);
        assertThat(result).isEqualTo(expected);
    }

    @Test
    public void updateAuthor() {
        boolean updated = bookService.updateAuthor(book1.getId(), author2.getId());
        assertThat(updated).isTrue();
        em.flush();
        em.clear();
        Book result = em.find(Book.class, book1.getId());
        Book expected = book1.withAuthor(author2);
        assertThat(result).isEqualTo(expected);
    }

    @Test
    public void updateGenre() {
        boolean updated = bookService.updateGenre(book1.getId(), genre2.getId());
        assertThat(updated).isTrue();
        em.flush();
        em.clear();
        Book result = em.find(Book.class, book1.getId());
        Book expected = book1.withGenre(genre2);
        assertThat(result).isEqualTo(expected);
    }

    @Test
    public void deleteCommentsByCascade() {
        Book book = em.persistAndFlush(new Book(0, BOOK_1, author1, genre1));
        long bookId = book.getId();

        Comment comment = em.persistAndFlush(new Comment(0, book, COMMENT_1));
        long commentId = comment.getId();
        em.detach(comment);

        comment = em.find(Comment.class, commentId);
        assertThat(comment).isNotNull();
        em.detach(comment);

        bookService.deleteById(bookId);
        em.flush();

        comment = em.find(Comment.class, commentId);
        assertThat(comment).isNull();
    }

}
