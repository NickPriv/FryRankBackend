package com.fryrank.controller;

import com.fryrank.dal.ReviewDAL;
import com.fryrank.model.*;
import com.fryrank.validator.ReviewValidator;
import com.fryrank.validator.ValidatorException;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.fryrank.Constants.API_PATH;
import static com.fryrank.Constants.GENERIC_VALIDATOR_ERROR_MESSAGE;
import static com.fryrank.Constants.REVIEW_VALIDATOR_ERRORS_OBJECT_NAME;
import static com.fryrank.utils.TokenUtils.decodeToken;

@RestController
public class ReviewController {

    private static final String REVIEWS_URI = API_PATH + "/reviews";
    private static final String AGGREGATE_REVIEWS_URI = REVIEWS_URI + "/aggregateInformation";
    private static final String REVIEWS_URI_TOP = REVIEWS_URI + "/top";

    @Autowired
    private ReviewDAL reviewDAL;

    @Value("${TOKEN_KEY}")
    private String token_key;

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

    @GetMapping(value = REVIEWS_URI_TOP)
    public GetAllReviewsOutput getTopReviews(@RequestParam final Integer count) {
        return reviewDAL.getTopMostRecentReviews(count);
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

    @PostMapping(value = REVIEWS_URI) //parse the token
    public Review addNewReviewForRestaurant(@RequestBody @NonNull final Review review) throws ValidatorException {
        String decodedAccountId = (review.getAccountId() != null) ? decodeToken(review.getAccountId(), token_key) : null;

        Review validatedReview = new Review(
                review.getReviewId(),
                review.getRestaurantId(),
                review.getScore(),
                review.getTitle(),
                review.getBody(),
                review.getIsoDateTime(),
                decodedAccountId,
                review.getUserMetadata()
        );

        BindingResult bindingResult = new BeanPropertyBindingResult(validatedReview, REVIEW_VALIDATOR_ERRORS_OBJECT_NAME);
        ReviewValidator validator = new ReviewValidator();
        validator.validate(validatedReview, bindingResult);

        if(bindingResult.hasErrors()) {
            throw new ValidatorException(bindingResult.getAllErrors(), GENERIC_VALIDATOR_ERROR_MESSAGE);
        }
        return reviewDAL.addNewReview(validatedReview);
    }
}
