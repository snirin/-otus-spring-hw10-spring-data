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
import ru.otus.spring.hw10.models.Comment;
import ru.otus.spring.hw10.models.Genre;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@Import(CommentServiceImpl.class)
@DataJpaTest
public class CommentServiceTest {
    private static final String AUTHOR_1 = "Author1";
    private static final String GENRE_1 = "Genre1";
    private static final String BOOK_1 = "Book1";
    private static final String COMMENT_1 = "Comment1";
    private static final String COMMENT_2 = "Comment2";

    @Autowired
    private TestEntityManager em;

    @Autowired
    private CommentService commentService;

    private Book book1;
    private long commentId;

    @Before
    public void setUp() {
        Author author1 = em.persistAndFlush(new Author(0, AUTHOR_1));
        Genre genre1 = em.persistAndFlush(new Genre(0, GENRE_1));
        book1 = em.persistAndFlush(new Book(0, BOOK_1, author1, genre1));
        commentId = em.persistAndFlush(new Comment(0, book1, COMMENT_1)).getId();
        em.clear();
    }

    @Test
    public void update_ifExists() {
        boolean updated = commentService.update(commentId, COMMENT_2);
        assertTrue(updated);
        Comment result = em.find(Comment.class, commentId);
        Comment expected = new Comment(commentId, book1, COMMENT_2);
        assertEquals(expected, result);
    }

    @Test
    public void update_ifAbsent() {
        long absentId = 12345;
        boolean updated = commentService.update(absentId, COMMENT_2);
        assertFalse(updated);
        Comment result = em.find(Comment.class, absentId);
        assertNull(result);
    }
}
