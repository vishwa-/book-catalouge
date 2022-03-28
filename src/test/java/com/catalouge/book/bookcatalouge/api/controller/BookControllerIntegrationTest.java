package com.catalouge.book.bookcatalouge.api.controller;

import com.catalouge.book.bookcatalouge.model.Author;
import com.catalouge.book.bookcatalouge.model.Book;
import com.catalouge.book.bookcatalouge.service.BookService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.Collections;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class BookControllerIntegrationTest {

    @MockBean
    private BookService bookService;

    @Autowired
    private BookController bookController;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void whenBookControllerInjected_thenNotNull(){
        assertNotNull(bookController);
    }


    @Test
    public void whenPostRequestToBookAndValidBook_thenCorrectResponse() throws Exception {
        Author expectedAuthor = new Author();
        expectedAuthor.setName("J.K. Rowling");

        Book expectedBook = new Book();
        expectedBook.setAuthors(Collections.singletonList(expectedAuthor));
        expectedBook.setISBN("1234567890123");
        expectedBook.setTitle("Harry Potter and the Philosopher’s Stone");
        expectedBook.setPublicationDate(LocalDate.of(2019, 04, 28));

        when(bookService.addBook(any())).thenReturn(expectedBook);
        String user = "{\n" +
                "    \"title\":\"Harry Potter and the Philosopher’s Stone\",\n" +
                "    \"isbn\":\"1234567890123\",\n" +
                "    \"publicationDate\" : \"2019-04-28\",\n" +
                "    \"authors\" : [\n" +
                "        {\"name\" : \"J.K. Rowling\"} \n" +
                "    ]\n" +
                "        \n" +
                "}";
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/book")
                        .content(user)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void whenPostRequestToBookAndInValidBookEmptyAuthor_then400Response() throws Exception {
        String user = "{\n" +
                "    \"title\":\"new Book\",\n" +
                "    \"isbn\":\"1234567890123\",\n" +
                "    \"publicationDate\" : \"2019-04-28\",\n" +
                "    \"authors\" : [\n" +
                "    ]\n" +
                "        \n" +
                "}";
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/book")
                        .content(user)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("fieldErrors[0].message").value("Authors must not be empty"));
    }

    @Test
    public void whenPostRequestToBookAndInValidBookNullAuthor_then400Response() throws Exception {
        String user = "{\n" +
                "    \"title\":\"Harry Potter and the Philosopher’s Stone\",\n" +
                "    \"isbn\":\"1234567890123\",\n" +
                "    \"publicationDate\" : \"2019-04-28\"\n" +
                "}";
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/book")
                        .content(user)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("fieldErrors[0].message").value("Authors must not be empty"));
    }

    @Test
    public void whenPostRequestToBookAndInValidBookNullTitle_then400Response() throws Exception {
        String user = "{\n" +
                "    \"isbn\":\"1234567890122\",\n" +
                "    \"publicationDate\" : \"2019-04-28\",\n" +
                "    \"authors\" : [ \n" +
                "        {\"name\": \"J.K. Rowling\"}\n" +
                "    ]\n" +
                "}";
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/book")
                        .content(user)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("fieldErrors[0].message").value("Title is mandatory"));
    }

    @Test
    public void whenPostRequestToBookAndInValidBookEmptyTitle_then400Response() throws Exception {
        String user = "{\n" +
                "    \"title\":\"\",\n" +
                "    \"isbn\":\"1234567890122\",\n" +
                "    \"publicationDate\" : \"2019-04-28\",\n" +
                "    \"authors\" : [ \n" +
                "        {\"name\": \"J.K. Rowling\"}\n" +
                "    ]\n" +
                "}";
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/book")
                        .content(user)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("fieldErrors[0].message").value("Title is mandatory"));
    }

    @Test
    public void whenPostRequestToBookAndInValidBookNullPublicationDate_then400Response() throws Exception {
        String user = "{\n" +
                "    \"title\":\"Harry Potter and the Philosopher’s Stone\",\n" +
                "    \"isbn\":\"1234567890123\",\n" +
                "    \"authors\" : [ \n" +
                "        {\"name\": \"J.K. Rowling\"}\n" +
                "    ]\n" +
                "}";
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/book")
                        .content(user)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("fieldErrors[0].message").value("Date of publication is mandatory"));
    }

    @Test
    public void whenPostRequestToBookAndInValidBookEmptyPublicationDate_then400Response() throws Exception {
        String user = "{\n" +
                "    \"title\":\"Harry Potter and the Philosopher’s Stone\",\n" +
                "    \"isbn\":\"1234567890123\",\n" +
                "    \"publicationDate\" : \"\",\n" +
                "    \"authors\" : [ \n" +
                "        {\"name\": \"J.K. Rowling\"}\n" +
                "    ]\n" +
                "}";
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/book")
                        .content(user)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("fieldErrors[0].message").value("Date of publication is mandatory"));
    }

    @Test
    public void whenPostRequestToBookAndInValidBookNullISBN_then400Response() throws Exception {
        String user = "{\n" +
                "    \"title\":\"Harry Potter and the Philosopher’s Stone\",\n" +
                "    \"publicationDate\" : \"2019-04-28\",\n" +
                "    \"authors\" : [ \n" +
                "        {\"name\": \"J.K. Rowling\"}\n" +
                "    ]\n" +
                "}";
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/book")
                        .content(user)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("fieldErrors[0].message").value("ISBN is mandatory"));
    }

    @Test
    public void whenPostRequestToBookAndInValidBookEmptyISBN_then400Response() throws Exception {
        String user = "{\n" +
                "    \"title\":\"Harry Potter and the Philosopher’s Stone\",\n" +
                "    \"isbn\":\"\",\n" +
                "    \"publicationDate\" : \"2019-04-28\",\n" +
                "    \"authors\" : [ \n" +
                "        {\"name\": \"J.K. Rowling\"}\n" +
                "    ]\n" +
                "}";
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/book")
                        .content(user)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("fieldErrors[0].message").value("ISBN is mandatory"));
    }

    @Test
    public void whenPostRequestToBookAndInValidBookShortISBN_then400Response() throws Exception {
        String user = "{\n" +
                "    \"title\":\"Harry Potter and the Philosopher’s Stone\",\n" +
                "    \"isbn\":\"12344\",\n" +
                "    \"publicationDate\" : \"2019-04-28\",\n" +
                "    \"authors\" : [ \n" +
                "        {\"name\": \"J.K. Rowling\"}\n" +
                "    ]\n" +
                "}";
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/book")
                        .content(user)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("fieldErrors[0].message").value("ISBN should be 13 digits"));
    }

    @Test
    public void whenSearchRequestToBookAndValidBook_thenCorrectResponse() throws Exception {
        Author expectedAuthor = new Author();
        expectedAuthor.setName("J.K. Rowling");

        Book expectedBook = new Book();
        expectedBook.setAuthors(Collections.singletonList(expectedAuthor));
        expectedBook.setISBN("1234567890123");
        expectedBook.setTitle("Harry Potter and the Philosopher’s Stone");
        expectedBook.setPublicationDate(LocalDate.of(2019, 04, 28));

        when(bookService.addBook(any())).thenReturn(expectedBook);
        when(bookService.searchBookBy(any())).thenReturn(Collections.singletonList(expectedBook));
        String user = "{\n" +
                "    \"title\":\"Harry Potter and the Philosopher’s Stone\",\n" +
                "    \"isbn\":\"1234567890123\",\n" +
                "    \"publicationDate\" : \"2019-04-28\",\n" +
                "    \"authors\" : [\n" +
                "        {\"name\" : \"J.K. Rowling\"} \n" +
                "    ]\n" +
                "        \n" +
                "}";
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/book")
                        .content(user)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/books?search=ISBN:1234567890123 ")
                    .content(user)
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$", hasSize(1)))
            .andExpect(jsonPath("$[0].title").isNotEmpty())
            .andExpect(jsonPath("$[0].title").value("Harry Potter and the Philosopher’s Stone"));
    }


    @Test
    public void whenUpdateRequestToBookAndValidBook_thenCorrectResponse() throws Exception {
        Author expectedAuthor = new Author();
        expectedAuthor.setName("J.K. Rowling");

        Book expectedBook = new Book();
        expectedBook.setId(1L);
        expectedBook.setAuthors(Collections.singletonList(expectedAuthor));
        expectedBook.setISBN("1234567890123");
        expectedBook.setTitle("Harry Potter and the Philosopher’s Stone");
        expectedBook.setPublicationDate(LocalDate.of(2019, 04, 28));

        when(bookService.addBook(any())).thenReturn(expectedBook);
        when(bookService.searchBookBy(any())).thenReturn(Collections.singletonList(expectedBook));
        String user = "{\n" +
                "    \"title\":\"Harry Potter and the Philosopher’s Stone\",\n" +
                "    \"isbn\":\"1234567890123\",\n" +
                "    \"publicationDate\" : \"2019-04-28\",\n" +
                "    \"authors\" : [\n" +
                "        {\"name\" : \"J.K. Rowling\"} \n" +
                "    ]\n" +
                "        \n" +
                "}";
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/book")
                        .content(user)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/books?search=ISBN:1234567890123 ")
                        .content(user)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].title").isNotEmpty())
                .andExpect(jsonPath("$[0].title").value("Harry Potter and the Philosopher’s Stone"));

        String updatedUser = "{\n" +
                "    \"title\":\"Harry Potter\",\n" +
                "    \"isbn\":\"1234567890123\",\n" +
                "    \"publicationDate\" : \"2019-04-28\",\n" +
                "    \"authors\" : [\n" +
                "        {\"name\" : \"J.K. Rowling\"} \n" +
                "    ]\n" +
                "        \n" +
                "}";
        Book updatedExpectedBook = new Book();
        updatedExpectedBook.setId(1L);
        updatedExpectedBook.setAuthors(Collections.singletonList(expectedAuthor));
        updatedExpectedBook.setISBN("1234567890123");
        updatedExpectedBook.setTitle("Harry Potter");
        updatedExpectedBook.setPublicationDate(LocalDate.of(2019, 04, 28));
        when(bookService.updateBook(any(), any())).thenReturn(updatedExpectedBook);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/book/1")
                        .content(updatedUser)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("title").isNotEmpty())
                .andExpect(jsonPath("title").value("Harry Potter"));


    }

}