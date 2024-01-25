package com.fryrank.controller;

import com.fryrank.dal.ReviewDAL;
import com.fryrank.model.Review;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.fryrank.Constants.BASE_URI;

@RestController
@RequestMapping(BASE_URI + "/reviews")
public class ReviewController {

    @Autowired
    private ReviewDAL reviewDAL;

    @GetMapping
    public List<Review> getAllReviewsForRestaurant(@RequestParam("restaurantId") @NonNull final String restaurantId) {
        return reviewDAL.getAllReviewsByRestaurantId(restaurantId);
    }

    @PostMapping
    public Review addNewReviewForRestaurant(@RequestBody @NonNull final Review review) {
        return reviewDAL.addNewReview(review);
    }
}
