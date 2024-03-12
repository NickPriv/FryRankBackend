package com.fryrank.dal;

import com.fryrank.model.GetAllReviewsOutput;
import com.fryrank.model.Review;

public interface ReviewDAL {

    GetAllReviewsOutput getAllReviewsByRestaurantId(final String restaurantId);

    Review addNewReview(final Review review);
}
