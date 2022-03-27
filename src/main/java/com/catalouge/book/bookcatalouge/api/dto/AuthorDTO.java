package com.catalouge.book.bookcatalouge.api.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@NotBlank(message = "Name is mandatory")
public class AuthorDTO {
    private String name;
}
