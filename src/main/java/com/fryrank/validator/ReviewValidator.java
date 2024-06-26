package com.fryrank.validator;

import org.springframework.validation.Errors;
import com.fryrank.model.Review;
import org.springframework.validation.Validator;

import java.time.Instant;
import java.time.format.DateTimeParseException;
import java.util.List;

public class ReviewValidator implements Validator {
    public static final String ISO_DATE_TIME = "isoDateTime";
    public static final String ISO_DATE_TIME_REJECTION_REQUIRED_CODE = "field.required";
    public static final String ISO_DATE_TIME_REJECTION_FORMAT_CODE = "field.invalidFormat";
    public static final String ISO_DATE_TIME_REJECTION_FORMAT_REASON = "The provided isoDateTime is not in ISO format.";
    @Override
    public boolean supports(Class clazz) {
        return Review.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Review review = (Review) target;
        String isoDateTime = review.getIsoDateTime();
        if(isoDateTime == null) {
            errors.rejectValue(ISO_DATE_TIME, ISO_DATE_TIME_REJECTION_REQUIRED_CODE);
        }
        else {
            try {
                Instant.parse(isoDateTime);
            }
            catch(DateTimeParseException e) {
                errors.rejectValue(ISO_DATE_TIME, ISO_DATE_TIME_REJECTION_FORMAT_CODE, ISO_DATE_TIME_REJECTION_FORMAT_REASON);
            }
        }
    }

}
