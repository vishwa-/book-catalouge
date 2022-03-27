package com.catalouge.book.bookcatalouge.Exception;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ErrorViewModel implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String message;
    private final String description;

    private List<FieldErrorViewModel> fieldErrors;

    public ErrorViewModel(String message) {
        this(message, null);
    }

    public ErrorViewModel(String message, String description) {
        this.message = message;
        this.description = description;
    }

    public ErrorViewModel(String message, String description, List<FieldErrorViewModel> fieldErrors) {
        this.message = message;
        this.description = description;
        this.fieldErrors = fieldErrors;
    }

    public void add(String objectName, String field, String message) {
        if (fieldErrors == null) {
            fieldErrors = new ArrayList<>();
        }
        fieldErrors.add(new FieldErrorViewModel(objectName, field, message));
    }

    public String getMessage() {
        return message;
    }

    public String getDescription() {
        return description;
    }

    public List<FieldErrorViewModel> getFieldErrors() {
        return fieldErrors;
    }
}
