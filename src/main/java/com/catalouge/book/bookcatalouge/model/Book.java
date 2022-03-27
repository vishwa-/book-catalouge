package com.catalouge.book.bookcatalouge.model;

import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
public class Book {
    @Id
    @GeneratedValue
    private Long id;
    private String title;
    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "Book_Author",
            joinColumns = { @JoinColumn(name = "author_id") },
            inverseJoinColumns = { @JoinColumn(name = "book_id") }
    )
    private List<Author> authors;
    private String ISBN;
    LocalDate publicationDate;

}
