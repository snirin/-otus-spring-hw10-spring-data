package ru.otus.spring.hw10.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;


    @ManyToOne
    @JoinColumn(name = "genre_id")
    private Genre genre;

    public Book withId(long id) {
        this.id = id;
        return this;
    }

    public Book withName(String name) {
        this.name = name;
        return this;
    }

    public Book withAuthor(Author author) {
        this.author = author;
        return this;
    }

    public Book withGenre(Genre genre) {
        this.genre = genre;
        return this;
    }
}

