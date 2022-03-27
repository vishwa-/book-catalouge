package com.catalouge.book.bookcatalouge.service;

import com.catalouge.book.bookcatalouge.model.Book;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

public interface BookService {
    Book addBook(Book book);
    Book updateBook();
    Book deleteBook();
    List<Book> searchBookBy(Specification<Book> specs);
    Optional<Book> searchBookByTitle();
    Optional<Book> searchBookByAuthor();
    Optional<Book> searchBookByISBN();
    Optional<Book> searchBookById(Long id);
}
