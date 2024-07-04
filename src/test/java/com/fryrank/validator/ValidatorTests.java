package com.fryrank.validator;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;

import java.util.List;

import static com.fryrank.Constants.REVIEW_VALIDATOR_ERRORS_OBJECT_NAME;
import static com.fryrank.TestConstants.*;
import static com.fryrank.validator.ReviewValidator.ISO_DATE_TIME_REJECTION_FORMAT_CODE;
import static com.fryrank.validator.ReviewValidator.ISO_DATE_TIME_REJECTION_REQUIRED_CODE;

@RunWith(MockitoJUnitRunner.class)
public class ValidatorTests {

    private final ReviewValidator reviewValidator = new ReviewValidator();

    @Test
    public void testValidateReviewSuccess() {
        Errors errors = new BeanPropertyBindingResult(TEST_REVIEW_1, REVIEW_VALIDATOR_ERRORS_OBJECT_NAME);
        reviewValidator.validate(TEST_REVIEW_1, errors);

        Assert.assertFalse(errors.hasErrors());
    }

    @Test
    public void testValidateReviewNullISODateTime() {
        Errors errors = new BeanPropertyBindingResult(TEST_REVIEW_NULL_ISO_DATETIME, REVIEW_VALIDATOR_ERRORS_OBJECT_NAME);
        reviewValidator.validate(TEST_REVIEW_NULL_ISO_DATETIME, errors);

        Assert.assertTrue(errors.hasErrors());
        List<ObjectError> allErrors = errors.getAllErrors();
        Assert.assertEquals(1, allErrors.size());
        Assert.assertEquals(allErrors.get(0).getCode(), ISO_DATE_TIME_REJECTION_REQUIRED_CODE);
    }

    @Test
    public void testValidateReviewBadFormatISODateTime() {
        Errors errors = new BeanPropertyBindingResult(TEST_REVIEW_BAD_ISO_DATETIME, REVIEW_VALIDATOR_ERRORS_OBJECT_NAME);
        reviewValidator.validate(TEST_REVIEW_BAD_ISO_DATETIME, errors);

        Assert.assertTrue(errors.hasErrors());
        List<ObjectError> allErrors = errors.getAllErrors();
        Assert.assertEquals(1, allErrors.size());
        Assert.assertEquals(allErrors.get(0).getCode(), ISO_DATE_TIME_REJECTION_FORMAT_CODE);
    }
}
