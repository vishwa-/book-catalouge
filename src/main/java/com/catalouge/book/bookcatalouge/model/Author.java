package com.catalouge.book.bookcatalouge.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.List;

@Data
@Entity
public class Author {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    @ManyToMany(mappedBy="authors")
    private List<Book> books;
}
