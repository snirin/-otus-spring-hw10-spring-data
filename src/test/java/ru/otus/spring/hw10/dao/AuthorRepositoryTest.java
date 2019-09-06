package ru.otus.spring.hw10.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.junit4.SpringRunner;
import ru.otus.spring.hw10.models.Author;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@Import(AuthorRepository.class)
@DataJpaTest
@EnableJpaRepositories
public class AuthorRepositoryTest {

    private static final String AUTHOR_1 = "Author1";
    private static final String AUTHOR_2 = "Author2";

    @Autowired
    private TestEntityManager em;

    @Autowired
    private AuthorRepository authorRepository;

    @Test
    public void insert() {
        int authorId = authorRepository.save(new Author(0, AUTHOR_1)).getId();
        Author expected = new Author(authorId, AUTHOR_1);
        Author result = em.find(Author.class, authorId);
        assertEquals(expected, result);
    }
}

