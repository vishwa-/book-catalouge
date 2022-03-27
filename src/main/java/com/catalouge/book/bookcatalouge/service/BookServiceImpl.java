package com.catalouge.book.bookcatalouge.service;

import com.catalouge.book.bookcatalouge.model.Book;
import com.catalouge.book.bookcatalouge.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookServiceImpl implements BookService{

    private final BookRepository bookRepository;
    @Override
    public Book addBook(Book book) {
        log.info("Add book service called with book={}", book);
        Book savedBook = bookRepository.save(book);
        return savedBook;
    }

    @Override
    public Book updateBook() {
        log.info("Update book called");
        return null;
    }

    @Override
    public Book deleteBook() {
        log.info("Delete book called");
        return null;
    }

    @Override
    public List<Book> searchBookBy(Specification<Book> specs) {
        return bookRepository.findAll(Specification.where(specs));
    }

    @Override
    public Optional<Book> searchBookByTitle() {
        return Optional.empty();
    }

    @Override
    public Optional<Book> searchBookByAuthor() {
        return Optional.empty();
    }

    @Override
    public Optional<Book> searchBookByISBN() {
        return Optional.empty();
    }
    @Override
    public Optional<Book> searchBookById(Long id) {
        return bookRepository.findById(id);
    }
}
