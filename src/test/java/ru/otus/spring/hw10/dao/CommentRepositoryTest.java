package ru.otus.spring.hw10.dao;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.otus.spring.hw10.models.Author;
import ru.otus.spring.hw10.models.Book;
import ru.otus.spring.hw10.models.Comment;
import ru.otus.spring.hw10.models.Genre;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class CommentRepositoryTest {
    private static final String AUTHOR_1 = "Author1";
    private static final String GENRE_1 = "Genre1";
    private static final String BOOK_1 = "Book1";
    private static final String COMMENT_1 = "Comment1";

    @Autowired
    private TestEntityManager em;

    @Autowired
    private CommentRepository commentRepository;

    @Test
    public void getByBookId() {
        Author author1 = em.persistAndFlush(new Author(0, AUTHOR_1));
        Genre genre1 = em.persistAndFlush(new Genre(0, GENRE_1));
        Book book1 = em.persistAndFlush(new Book(0, BOOK_1, author1, genre1));
        Comment comment = em.persistAndFlush(new Comment(0, book1, COMMENT_1));
        em.clear();

        List<Comment> comments = commentRepository.findByBook_Id(book1.getId());
        assertThat(comments).isEqualTo(singletonList(comment));
    }
}

