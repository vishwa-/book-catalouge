package com.catalouge.book.bookcatalouge.Exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.format.DateTimeParseException;
import java.util.List;

@ControllerAdvice
@Slf4j
public class RestResponseEntityExceptionHandler {

    @ExceptionHandler(value={MethodArgumentNotValidException.class, DateTimeParseException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorViewModel processValidationError(MethodArgumentNotValidException ex) {
        log.error("In MethodArgumentNotValidException handler", ex);
        BindingResult result = ex.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();

        return processFieldErrors(fieldErrors);
    }
    @ExceptionHandler(value={BookNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorViewModel processElementNotFoundError(BookNotFoundException ex) {
        log.error("In MethodArgumentNotValidException handler", ex);
        ErrorViewModel dto = new ErrorViewModel(ErrorConstants.ERR_DATA_NOT_FOUND, ex.getMessage());
        return dto;
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorViewModel> processRuntimeException(Exception ex) {
        log.error("In Exception handler", ex);
        ResponseEntity.BodyBuilder builder;
        ErrorViewModel errorViewModel;
        ResponseStatus responseStatus = AnnotationUtils.findAnnotation(ex.getClass(), ResponseStatus.class);
        if (responseStatus != null) {
            builder = ResponseEntity.status(responseStatus.value());
            errorViewModel = new ErrorViewModel("error." + responseStatus.value().value(), responseStatus.reason());
        } else {
            builder = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR);
            errorViewModel = new ErrorViewModel(ErrorConstants.ERR_INTERNAL_SERVER_ERROR, "Internal server error");
        }
        return builder.body(errorViewModel);
    }

    private ErrorViewModel processFieldErrors(List<FieldError> fieldErrors) {
        ErrorViewModel dto = new ErrorViewModel(ErrorConstants.ERR_VALIDATION);

        for (FieldError fieldError : fieldErrors) {
            dto.add(fieldError.getObjectName(), fieldError.getField(), fieldError.getDefaultMessage());
        }

        return dto;
    }
}