package com.catalouge.book.bookcatalouge.service;

import com.catalouge.book.bookcatalouge.model.Book;

import java.util.Optional;

public interface BookService {
    Book addBook(Book book);
    Book updateBook();
    Book deleteBook();
    Optional<Book> searchBookByTitle();
    Optional<Book> searchBookByAuthor();
    Optional<Book> searchBookByISBN();
    Optional<Book> searchBookById(Long id);
}
