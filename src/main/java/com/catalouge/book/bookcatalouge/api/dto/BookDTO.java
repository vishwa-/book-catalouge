package com.catalouge.book.bookcatalouge.api.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.util.List;

@Data
public class BookDTO{
    private Long id;
    @NotBlank(message = "Title is mandatory")
    private String title;
    @NotEmpty(message = "Authors must not be empty")
    private List<AuthorDTO> authors;
    @NotBlank(message = "ISBN is mandatory")
    @Pattern(regexp = "^(\\d{13})?$", message = "ISBN should be 13 digits")
    private String ISBN;
    @NotNull(message = "Date of publication is mandatory")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    LocalDate publicationDate;
}
