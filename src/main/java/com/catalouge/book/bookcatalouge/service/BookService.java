package com.catalouge.book.bookcatalouge.service;

import com.catalouge.book.bookcatalouge.model.Book;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

public interface BookService {
    Book addBook(Book book);
    Book updateBook(Long id, Book book);
    Long deleteBook(Long id);
    List<Book> searchBookBy(Specification<Book> specs);
    Optional<Book> searchBookById(Long id);
}
