package com.catalouge.book.bookcatalouge.service;

import com.catalouge.book.bookcatalouge.Exception.BookNotFoundException;
import com.catalouge.book.bookcatalouge.model.Author;
import com.catalouge.book.bookcatalouge.model.Book;
import com.catalouge.book.bookcatalouge.repository.BookRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.jms.Queue;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
@RunWith(SpringJUnit4ClassRunner.class)
public class BookServiceTest {

    @Mock
    JmsTemplate jmsTemplate;
    @Mock
    Queue queue;
    @Mock
    BookRepository bookRepository;

    @InjectMocks
    BookServiceImpl bookService;

    @Test
    public void addBook() {

        Author expectedAuthor = new Author();
        expectedAuthor.setName("J.K. Rowling");

        Book expectedBook = new Book();
        expectedBook.setId(1L);
        expectedBook.setAuthors(Collections.singletonList(expectedAuthor));
        expectedBook.setISBN("1234567890123");
        expectedBook.setTitle("Harry Potter and the Philosopher’s Stone");
        expectedBook.setPublicationDate(LocalDate.of(2019, 4, 28));

        when(bookRepository.save(any(Book.class)))
                .thenReturn(expectedBook);
        Book actualBook = bookService.addBook(expectedBook);
        assertEquals(expectedBook.getISBN(), actualBook.getISBN());
        verify(jmsTemplate, times(1)).convertAndSend(queue,"Book Added with id=1");
    }

    @Test
    public void updateBook() {

        Author expectedAuthor = new Author();
        expectedAuthor.setName("J.K. Rowling");

        Book expectedBook = new Book();
        expectedBook.setAuthors(Collections.singletonList(expectedAuthor));
        expectedBook.setISBN("1234567890123");
        expectedBook.setTitle("Harry Potter and the Philosopher’s Stone");
        expectedBook.setPublicationDate(LocalDate.of(2019, 4, 28));

        when(bookRepository.save(any(Book.class)))
                .thenReturn(expectedBook);
        Book actualBook = bookService.updateBook(1L, expectedBook);
        assertEquals(expectedBook.getISBN(), actualBook.getISBN());
        verify(jmsTemplate, times(1)).convertAndSend(queue,"Book Updated with id=1");
        verify(bookRepository, times(1)).save(expectedBook);
    }

    @Test(expected = BookNotFoundException.class)
    public void deletedBookNotFound() {

        when(bookRepository.findById(any(Long.class)))
                .thenReturn(Optional.empty());
        Long actualBookId = bookService.deleteBook(1L);

        verify(jmsTemplate, times(0)).convertAndSend(queue,"Book Deleted with id=1");
        verify(bookRepository, times(0)).deleteById(1L);
    }

    @Test
    public void deletedBook() {

        Author expectedAuthor = new Author();
        expectedAuthor.setName("J.K. Rowling");

        Book expectedBook = new Book();
        expectedBook.setId(1L);
        expectedBook.setAuthors(Collections.singletonList(expectedAuthor));
        expectedBook.setISBN("1234567890123");
        expectedBook.setTitle("Harry Potter and the Philosopher’s Stone");
        expectedBook.setPublicationDate(LocalDate.of(2019, 4, 28));

        when(bookRepository.findById(any(Long.class)))
                .thenReturn(Optional.of(expectedBook));
        Long actualBookId = bookService.deleteBook(1L);

        verify(jmsTemplate, times(1)).convertAndSend(queue,"Book Deleted with id=1");
        verify(bookRepository, times(1)).deleteById(1L);
    }
}