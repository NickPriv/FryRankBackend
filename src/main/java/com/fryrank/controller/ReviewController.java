package com.fryrank.controller;

import com.fryrank.dal.ReviewDAL;
import com.fryrank.model.AggregateReviewFilter;
import com.fryrank.model.GetAggregateReviewInformationOutput;
import com.fryrank.model.GetAllReviewsOutput;
import com.fryrank.model.Review;
import com.fryrank.validator.ReviewValidator;
import com.fryrank.validator.ValidatorException;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.fryrank.Constants.API_PATH;
import static com.fryrank.Constants.GENERIC_VALIDATOR_ERROR_MESSAGE;
import static com.fryrank.Constants.REVIEW_VALIDATOR_ERRORS_OBJECT_NAME;

@RestController
public class ReviewController {

    private static final String REVIEWS_URI = API_PATH + "/reviews";
    private static final String AGGREGATE_REVIEWS_URI = REVIEWS_URI + "/aggregateInformation";

    @Autowired
    private ReviewDAL reviewDAL;

    @GetMapping(value = REVIEWS_URI)
    public GetAllReviewsOutput getAllReviews(
        @RequestParam(required = false) final String restaurantId,
        @RequestParam(required = false) final String accountId) {
        if (restaurantId != null) {
            return reviewDAL.getAllReviewsByRestaurantId(restaurantId);
        } else if (accountId != null) {
            return reviewDAL.getAllReviewsByAccountId(accountId);
        } else {
            throw new NullPointerException("At least one of restaurantId and accountId must not be null.");
        }
    }

    @GetMapping(value = AGGREGATE_REVIEWS_URI)
    public GetAggregateReviewInformationOutput getAggregateReviewInformationForRestaurants(
            @RequestParam(name = "ids") String ids,
            @RequestParam(name = "rating", defaultValue = "false") boolean includeRating
    ) {
        List<String> parsedIDs = Arrays.stream(ids.split(",")).sorted().collect(Collectors.toList());
        AggregateReviewFilter filter = new AggregateReviewFilter(includeRating);
        return reviewDAL.getAggregateReviewInformationForRestaurants(parsedIDs, filter);
    }

    @PostMapping(value = REVIEWS_URI)
    public Review addNewReviewForRestaurant(@RequestBody @NonNull final Review review) throws ValidatorException {
        BindingResult bindingResult = new BeanPropertyBindingResult(review, REVIEW_VALIDATOR_ERRORS_OBJECT_NAME);
        ReviewValidator validator = new ReviewValidator();
        validator.validate(review, bindingResult);

        if(bindingResult.hasErrors()) {
            throw new ValidatorException(bindingResult.getAllErrors(), GENERIC_VALIDATOR_ERROR_MESSAGE);
        }
        return reviewDAL.addNewReview(review);
    }
}
