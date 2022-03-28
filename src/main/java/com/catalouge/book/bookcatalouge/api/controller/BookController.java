package com.catalouge.book.bookcatalouge.api.controller;

import com.catalouge.book.bookcatalouge.Exception.BookNotFoundException;
import com.catalouge.book.bookcatalouge.api.dto.BookDTO;
import com.catalouge.book.bookcatalouge.model.Book;
import com.catalouge.book.bookcatalouge.service.BookService;
import com.sipios.springsearch.anotation.SearchSpec;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/")
@Slf4j
public class BookController {
    @Autowired
    private BookService bookService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("book")
    public BookDTO addBook(@Valid @RequestBody BookDTO bookDTO) {
        log.info("Add book called");
        Book book = convertToEntity(bookDTO);
        Book bookCreated = bookService.addBook(book);
        return convertToDto(bookCreated);

    }

    @GetMapping("book/{id}")
    public BookDTO getBook(@PathVariable Long id) {
        log.info("Get book called with id=" + id);
        Book result = bookService.searchBookById(id).orElseThrow(() ->new BookNotFoundException("Could not find a book with id=" + id));
        return convertToDto(result);

    }

    @GetMapping("books")
    public List<BookDTO> searchBookBy(@SearchSpec Specification<Book> specs) {
        log.info("Searching book with criteria=" + specs);
        List<Book> foundBooks = bookService.searchBookBy(specs);
        return foundBooks.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @PutMapping("book/{id}")
    public BookDTO updateBook(@PathVariable Long id, @Valid @RequestBody BookDTO bookDTO) {
        log.info("update book called id=" + id);
        Book book = convertToEntity(bookDTO);
        Book result = bookService.updateBook(id, book);
        return convertToDto(result);
    }

    @DeleteMapping("book/{id}")
    public Long deleteBook(@PathVariable Long id) {
        log.info("Delete book called id=" + id);
        return bookService.deleteBook(id);
    }


    private BookDTO convertToDto(Book book) {
        BookDTO bookDTO = modelMapper.map(book, BookDTO.class);
        return bookDTO;
    }

    private Book convertToEntity(BookDTO bookDTO) {

        Book post = modelMapper.map(bookDTO, Book.class);

        return post;
    }


}
