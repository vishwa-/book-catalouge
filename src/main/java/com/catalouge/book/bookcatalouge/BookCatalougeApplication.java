package com.catalouge.book.bookcatalouge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.catalouge.book.bookcatalouge")
public class BookCatalougeApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookCatalougeApplication.class, args);
	}
}
