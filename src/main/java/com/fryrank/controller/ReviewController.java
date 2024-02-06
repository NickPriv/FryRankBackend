package com.fryrank.controller;

import com.fryrank.Constants;
import com.fryrank.dal.ReviewDAL;
import com.fryrank.model.Review;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ReviewController {

    private static final String REVIEWS_URI = Constants.API_PATH + "/reviews";

    @Autowired
    private ReviewDAL reviewDAL;

    @GetMapping(value = REVIEWS_URI)
    public List<Review> getAllReviewsForRestaurant(@RequestParam("restaurantId") @NonNull final String restaurantId) {
        return reviewDAL.getAllReviewsByRestaurantId(restaurantId);
    }

    @PostMapping(value = REVIEWS_URI)
    public Review addNewReviewForRestaurant(@RequestBody @NonNull final Review review) {
        return reviewDAL.addNewReview(review);
    }
}
