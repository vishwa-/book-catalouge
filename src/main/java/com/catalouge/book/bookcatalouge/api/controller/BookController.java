package com.catalouge.book.bookcatalouge.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookController {
    @GetMapping("/")
    public String index() {
        return "hey Vishwa!!";
    }
}
