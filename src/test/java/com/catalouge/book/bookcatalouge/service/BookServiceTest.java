package com.catalouge.book.bookcatalouge.service;

import com.catalouge.book.bookcatalouge.model.Author;
import com.catalouge.book.bookcatalouge.model.Book;
import com.catalouge.book.bookcatalouge.repository.BookRepository;
import com.sipios.springsearch.SearchCriteria;
import com.sipios.springsearch.SpecificationsBuilder;
import com.sipios.springsearch.anotation.SearchSpec;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KFunction;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;
import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
@RunWith(SpringJUnit4ClassRunner.class)
public class BookServiceTest {

    @Mock
    BookRepository bookRepository;

    @InjectMocks
    BookServiceImpl bookService;

    @Test
    public void addBook() {

        Author expectedAuthor = new Author();
        expectedAuthor.setName("J.K. Rowling");

        Book expectedBook = new Book();
        expectedBook.setAuthors(Collections.singletonList(expectedAuthor));
        expectedBook.setISBN("1234567890123");
        expectedBook.setTitle("Harry Potter and the Philosopher’s Stone");
        expectedBook.setPublicationDate(LocalDate.of(2019, 04, 28));

        when(bookRepository.save(any(Book.class)))
                .thenReturn(expectedBook);
        Book actualBook = bookService.addBook(expectedBook);
        assertEquals(expectedBook.getISBN(), actualBook.getISBN());

    }
}