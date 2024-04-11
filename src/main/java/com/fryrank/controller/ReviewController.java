package com.fryrank.controller;

import com.fryrank.dal.ReviewDAL;
import com.fryrank.model.AggregateReviewFilter;
import com.fryrank.model.GetAggregateReviewInformationOutput;
import com.fryrank.model.GetAllReviewsOutput;
import com.fryrank.model.Review;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.fryrank.Constants.API_PATH;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ReviewController {

    private static final String REVIEWS_URI = API_PATH + "/reviews";
    private static final String AGGREGATE_REVIEWS_URI = API_PATH + "/reviews/aggregateInformation";

    @Autowired
    private ReviewDAL reviewDAL;

    @GetMapping(value = REVIEWS_URI)
    public GetAllReviewsOutput getAllReviewsForRestaurant(@RequestParam("restaurantId") @NonNull final String restaurantId) {
        return reviewDAL.getAllReviewsByRestaurantId(restaurantId);
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
    public Review addNewReviewForRestaurant(@RequestBody @NonNull final Review review) {
        return reviewDAL.addNewReview(review);
    }
}
