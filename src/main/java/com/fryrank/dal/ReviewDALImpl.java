package com.fryrank.dal;

import com.fryrank.model.*;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@Repository
public class ReviewDALImpl implements ReviewDAL {

    public static final String RESTAURANT_ID_KEY = "restaurantId";

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public GetAllReviewsOutput getAllReviewsByRestaurantId(@NonNull final String restaurantId) {
        final Query query = new Query();
        final Criteria equalToRestaurantIdCriteria = Criteria.where(RESTAURANT_ID_KEY).is(restaurantId);
        query.addCriteria(equalToRestaurantIdCriteria);
        final List<Review> reviews = mongoTemplate.find(query, Review.class);

        return new GetAllReviewsOutput(reviews);
    }

    @Override
    public GetAggregateReviewInformationOutput getAggregateReviewInformationForRestaurants(@NonNull final List<String> restaurantIds, @NonNull final AggregateReviewFilter aggregateReviewFilter) {
        final Query query = new Query().addCriteria(Criteria.where(RESTAURANT_ID_KEY).in(restaurantIds));

        Map<String, AggregateReviewInformation> restaurantIdToAggregateReviewInformation = new HashMap<String, AggregateReviewInformation>();

        for(String restaurantId : restaurantIds) {
            final Criteria includeRestaurantIdsCriteria = Criteria.where(RESTAURANT_ID_KEY).is(restaurantId);
            final MatchOperation filterToRestaurantId = match(includeRestaurantIdsCriteria);
            final GroupOperation averageScoreGroupOperation = group(RESTAURANT_ID_KEY).avg("score").as("avgScore");
            final Aggregation aggregation = newAggregation(filterToRestaurantId, averageScoreGroupOperation);
            final AggregationResults<RestaurantAvgScore> result = mongoTemplate.aggregate(aggregation, "review", RestaurantAvgScore.class);
            final Optional<RestaurantAvgScore> uniqueResult = Optional.ofNullable(result.getUniqueMappedResult());

            if (uniqueResult.isPresent()) {
                Float averageScore = null;

                if(aggregateReviewFilter.getIncludeRating()) {
                    final Float rawScore = uniqueResult.get().getAvgScore();
                    averageScore = new BigDecimal(rawScore).setScale(1, RoundingMode.DOWN).floatValue();
                }

                restaurantIdToAggregateReviewInformation.put(restaurantId, new AggregateReviewInformation(restaurantId, averageScore));
            }
        }

        return new GetAggregateReviewInformationOutput(restaurantIdToAggregateReviewInformation);
    }

    @Override
    public Review addNewReview(@NonNull final Review review) {
        mongoTemplate.save(review);
        return review;
    }
}
