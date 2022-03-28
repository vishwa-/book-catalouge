package com.catalouge.book.bookcatalouge.service;

import com.catalouge.book.bookcatalouge.Exception.BookNotFoundException;
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
    public Book updateBook(Long id, Book book) {
        log.info("Update book called");
        book.setId(id);
        return bookRepository.save(book);
    }

    @Override
    public Long deleteBook(Long id) {
        log.info("Delete book called");
        Book bookToBeDeleted =
                bookRepository.findById(id)
                .orElseThrow(
                        () ->new BookNotFoundException("Could not find a book with id=" + id)
                );

        bookRepository.deleteById(bookToBeDeleted.getId());
        return bookToBeDeleted.getId();
    }

    @Override
    public List<Book> searchBookBy(Specification<Book> specs) {
        return bookRepository.findAll(Specification.where(specs));
    }

    @Override
    public Optional<Book> searchBookById(Long id) {
        return bookRepository.findById(id);
    }
}
