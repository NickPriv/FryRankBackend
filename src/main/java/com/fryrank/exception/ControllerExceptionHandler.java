package com.fryrank.exception;

import com.fryrank.validator.ValidatorException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class ControllerExceptionHandler {

    /**
     * Exception handler for any exceptions not explicitly handled below.
     * @param ex the exception
     * @return map containing the exception message
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, String> handleGenericException(Exception ex) {
        return Map.of("message", ex.getMessage());
    }

    /**
     * Exception handler for HttpMessageNotReadableException, which is thrown when null values are passed into a JSON object.
     * @param ex the exception
     * @return map containing the exception message
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public Map<String, String> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        return Map.of("message", ex.getMessage());
    }

    @ExceptionHandler(ValidatorException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public Map<String, String> handleValidatorException(ValidatorException ex) {
        StringBuilder message = new StringBuilder(ex.getMessage() + "\n" + "Errors:\n");
        for(ObjectError error : ex.getErrors()) {
            message.append("\t").append(error.toString()).append("\n");
        }
        return Map.of("message", message.toString());
    }
}
