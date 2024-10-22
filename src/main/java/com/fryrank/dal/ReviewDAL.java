package com.fryrank.dal;

import com.fryrank.model.AggregateReviewFilter;
import com.fryrank.model.GetAggregateReviewInformationOutput;
import com.fryrank.model.GetAllReviewsOutput;
import com.fryrank.model.Review;

import java.util.List;

public interface ReviewDAL {

    GetAllReviewsOutput getAllReviewsByRestaurantId(final String restaurantId);

    GetAllReviewsOutput getAllReviewsByAccountId(final String accountId);

    GetAllReviewsOutput getTop10MostRecentReviews();

    GetAggregateReviewInformationOutput getAggregateReviewInformationForRestaurants(final List<String> restaurantIds, final AggregateReviewFilter aggregateReviewFilter);

    Review addNewReview(final Review review);
}
