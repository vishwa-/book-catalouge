package com.catalouge.book.bookcatalouge.repository;

import com.catalouge.book.bookcatalouge.model.Author;
import com.catalouge.book.bookcatalouge.model.Book;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.Collections;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
public class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Test
    public void testSaveNewBook() {

        Author expectedAuthor = new Author();
        expectedAuthor.setName("J.K. Rowling");

        Book expectedBook = new Book();
        expectedBook.setAuthors(Collections.singletonList(expectedAuthor));
        expectedBook.setISBN("1234567890123");
        expectedBook.setTitle("Harry Potter and the Philosopher’s Stone");
        expectedBook.setPublicationDate(LocalDate.of(2019, 04, 28));


        bookRepository.save(expectedBook);
        Book actualBook = bookRepository.findById(1L).get();

        assertEquals(expectedBook.getId(),actualBook.getId());
        assertEquals(expectedBook.getISBN(),actualBook.getISBN());
        assertEquals(expectedBook.getTitle(),actualBook.getTitle());
    }
}