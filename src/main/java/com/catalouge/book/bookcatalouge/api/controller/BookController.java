package com.catalouge.book.bookcatalouge.api.controller;

import com.catalouge.book.bookcatalouge.api.dto.BookDTO;
import com.catalouge.book.bookcatalouge.model.Book;
import com.catalouge.book.bookcatalouge.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

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
//        Book result = bookService.searchBookByTitle().orElseThrow(() ->new BookNotFoundException("Couldnt find a book with id=" + id));

        return null;

    }

    @PutMapping("book")
    public BookDTO updateBook() {
        log.info("update book called");
        return null;

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
