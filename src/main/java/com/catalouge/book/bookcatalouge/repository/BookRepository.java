package com.catalouge.book.bookcatalouge.repository;

import com.catalouge.book.bookcatalouge.model.Book;
import org.springframework.data.repository.CrudRepository;

public interface BookRepository extends CrudRepository<Book, Long> {

}
