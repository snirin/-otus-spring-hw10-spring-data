package ru.otus.spring.hw10.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.hw10.dao.AuthorRepository;
import ru.otus.spring.hw10.dao.BookRepository;
import ru.otus.spring.hw10.dao.CommentRepository;
import ru.otus.spring.hw10.dao.GenreRepository;
import ru.otus.spring.hw10.models.Author;
import ru.otus.spring.hw10.models.Book;
import ru.otus.spring.hw10.models.Comment;
import ru.otus.spring.hw10.models.Genre;
import ru.otus.spring.hw10.service.BookService;
import ru.otus.spring.hw10.service.CommentService;

@ShellComponent
@Transactional
public class ShellCommands {
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;
    private final BookRepository bookRepository;
    private final BookService bookService;
    private final CommentRepository commentRepository;
    private final CommentService commentService;

    public ShellCommands(AuthorRepository authorRepository, GenreRepository genreRepository, BookRepository bookRepository, BookService bookService, CommentRepository commentRepository, CommentService commentService) {
        this.authorRepository = authorRepository;
        this.genreRepository = genreRepository;
        this.bookRepository = bookRepository;
        this.bookService = bookService;
        this.commentRepository = commentRepository;
        this.commentService = commentService;
    }

    /*
    AUTHORS
     */
    @ShellMethod(value = "Author:Insert", key = {"ai"})
    public int authorInsert(@ShellOption String name) {
        return authorRepository.save(new Author(0, name)).getId();
    }

    @ShellMethod(value = "Author:Update", key = {"au"})
    public boolean authorUpdate(@ShellOption int id, @ShellOption String name) {
        authorRepository.save(new Author(id, name));
        return true;
    }

    @ShellMethod(value = "Author:Delete", key = {"ad"})
    public boolean authorDelete(@ShellOption int id) {
        authorRepository.deleteById(id);
        return true;
    }

    @ShellMethod(value = "Author:Get", key = {"ag"})
    public String authorGet(@ShellOption int id) {
        return authorRepository.findById(id).map(Author::toString).orElse(null);
    }

    @ShellMethod(value = "Author:GetAll", key = {"aga"})
    public String authorGet() {
        return authorRepository.findAll().toString();
    }

    @ShellMethod(value = "Author:Count", key = {"ac"})
    public int authorCount() {
        return (int) authorRepository.count();
    }
    /*
    AUTHORS - END
     */

    /*
    GENRES
     */
    @ShellMethod(value = "Genre:Insert", key = {"gi"})
    public int genreInsert(@ShellOption String name) {
        return genreRepository.save(new Genre(0, name)).getId();
    }

    @ShellMethod(value = "Genre:Update", key = {"gu"})
    public boolean genreUpdate(@ShellOption int id, @ShellOption String name) {
        genreRepository.save(new Genre(id, name));
        return true;
    }

    @ShellMethod(value = "Genre:Delete", key = {"gd"})
    public boolean genreDelete(@ShellOption int id) {
        genreRepository.deleteById(id);
        return true;
    }

    @ShellMethod(value = "Genre:Get", key = {"gg"})
    public String genreGet(@ShellOption int id) {
        return genreRepository.findById(id).map(Genre::toString).orElse(null);
    }

    @ShellMethod(value = "Genre:GetAll", key = {"gga"})
    public String genreGet() {
        return genreRepository.findAll().toString();
    }

    @ShellMethod(value = "Genre:Count", key = {"gc"})
    public int genreCount() {
        return (int) genreRepository.count();
    }
    /*
    GENRES - END
     */


    /*
    BOOKS
     */
    @ShellMethod(value = "Book:Insert", key = {"bi"})
    public int bookInsert(@ShellOption String name, @ShellOption int authorId, @ShellOption int genreId) {
        return bookRepository.save(new Book(0, name, new Author(authorId, ""), new Genre(genreId, ""))).getId();
    }

    @ShellMethod(value = "Book:Update", key = {"bu"})
    public boolean bookUpdate(@ShellOption int id, @ShellOption String name, @ShellOption int authorId, @ShellOption int genreId) {
        return bookService.update(id, name, authorId, genreId);
    }

    @ShellMethod(value = "Book:UpdateName", key = {"bun"})
    public boolean bookUpdateName(@ShellOption int id, @ShellOption String name) {
        return bookService.updateName(id, name);
    }

    @ShellMethod(value = "Book:UpdateAuthor", key = {"bua"})
    public boolean bookUpdateAuthor(@ShellOption int id, @ShellOption int authorId) {
        return bookService.updateAuthor(id, authorId);
    }

    @ShellMethod(value = "Book:UpdateGenre", key = {"bug"})
    public boolean bookUpdateGenre(@ShellOption int id, @ShellOption int genreId) {
        return bookService.updateGenre(id, genreId);
    }

    @ShellMethod(value = "Book:Delete", key = {"bd"})
    public boolean bookDelete(@ShellOption int id) {
        bookRepository.deleteById(id);
        return true;
    }

    @ShellMethod(value = "Book:Get", key = {"bg"})
    public String bookGet(@ShellOption int id) {
        return bookService.getToString(id);
    }

    @ShellMethod(value = "Book:GetAll", key = {"bga"})
    public String bookGet() {
        return bookService.findAllToString();
    }

    @ShellMethod(value = "Book:Count", key = {"bc"})
    public int bookCount() {
        return (int) bookRepository.count();
    }
    /*
    BOOKS - END
     */

    /*
    COMMENTS
     */
    @ShellMethod(value = "Comment:Insert", key = {"ci"})
    public int commentInsert(@ShellOption int bookId, @ShellOption String text) {
        return commentRepository.save(new Comment(0, new Book().withId(bookId), text)).getId();
    }

    @ShellMethod(value = "Comment:Update", key = {"cu"})
    public boolean commentUpdate(@ShellOption int id, @ShellOption String text) {
        return commentService.update(id, text);
    }

    @ShellMethod(value = "Comment:Delete", key = {"cd"})
    public boolean commentDelete(@ShellOption int id) {
        commentRepository.deleteById(id);
        return true;
    }

    @ShellMethod(value = "Comment:Get", key = {"cg"})
    public String commentGet(@ShellOption int id) {
        return commentRepository.findById(id).map(Comment::toString).orElse(null);
    }

    @ShellMethod(value = "Comment:GetAll", key = {"cga"})
    public String commentGet() {
        return commentRepository.findAll().toString();
    }

    @ShellMethod(value = "Comment:GetByBook", key = {"cgb"})
    public String commentGetByBook(@ShellOption int bookId) {
        return commentRepository.findByBook_Id(bookId).toString();
    }

    @ShellMethod(value = "Comment:Count", key = {"cc"})
    public int commentCount() {
        return (int) commentRepository.count();
    }
    /*
    COMMENTS - END
     */
}
