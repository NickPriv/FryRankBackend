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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.fryrank.Constants.*;
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
            String decodedAccountId = decodeToken(accountId, token_key);
            return reviewDAL.getAllReviewsByAccountId(decodedAccountId);
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
    public Review addNewReviewForRestaurant(@RequestHeader("Authorization") String jwtToken, @RequestBody @NonNull final Review review) throws ValidatorException, AccessDeniedException {
        String decodedAccountId =  decodeToken(jwtToken, token_key);
        if(!decodedAccountId.equals(review.getAccountId()) && review.getAccountId()!=null) {
            throw new AccessDeniedException("Invalid accountId from JWT.");
        }
        BindingResult bindingResult = new BeanPropertyBindingResult(review, REVIEW_VALIDATOR_ERRORS_OBJECT_NAME);
        ReviewValidator validator = new ReviewValidator();
        validator.validate(review, bindingResult);

        if(bindingResult.hasErrors()) {
            throw new ValidatorException(bindingResult.getAllErrors(), GENERIC_VALIDATOR_ERROR_MESSAGE);
        }
        return reviewDAL.addNewReview(review);
    }
}
