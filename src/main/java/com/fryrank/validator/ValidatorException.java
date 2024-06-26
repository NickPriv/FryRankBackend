package com.fryrank.validator;

import lombok.Getter;
import org.springframework.validation.ObjectError;

import java.util.List;

@Getter
public class ValidatorException extends Exception {
    private final List<ObjectError> errors;

    public ValidatorException(List<ObjectError> errors, String message) {
        super(message);
        this.errors = errors;
    }

}
