package com.fryrank.dal;

import com.fryrank.model.*;
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
import java.util.Optional;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@Repository
public class RestaurantInformationDALImpl implements RestaurantInformationDAL {
    @Autowired
    private MongoTemplate mongoTemplate;

    public static final String RESTAURANT_ID_KEY = "restaurantId";

    public static final String SCORE = "score";
    @Override
    public GetRestaurantInformationOutput getRestaurantInformationForRestaurants(List<String> restaurantIds, RestaurantAttributes restaurantAttributes) {
        final Query query = new Query().addCriteria(Criteria.where(RESTAURANT_ID_KEY).in(restaurantIds));
        final List<Review> reviews = mongoTemplate.find(query, Review.class);

        Map<String, RestaurantInformation> restaurantIdToRestaurantInformation = new HashMap<String, RestaurantInformation>();

        for(String restaurantId : restaurantIds) {
            final Criteria includeRestaurantIdsCriteria = Criteria.where(RESTAURANT_ID_KEY).is(restaurantId);
            final MatchOperation filterToRestaurantId = match(includeRestaurantIdsCriteria);
            final GroupOperation averageScore = group(RESTAURANT_ID_KEY).avg("score").as("avgScore");
            final Aggregation aggregation = newAggregation(filterToRestaurantId, averageScore);
            final AggregationResults<RestaurantAvgScore> result = mongoTemplate.aggregate(aggregation, "review", RestaurantAvgScore.class);
            final Optional<RestaurantAvgScore> uniqueResult = Optional.ofNullable(result.getUniqueMappedResult());

            if (uniqueResult.isPresent()) {
                final Float rawScore = uniqueResult.get().getAvgScore();
                final BigDecimal averageScoreBigDecimal = new BigDecimal(rawScore).setScale(1, RoundingMode.DOWN);

                return new GetRestaurantInformationOutput(reviews, averageScoreBigDecimal.floatValue());
            } else {
                return new GetRestaurantInformationOutput(reviews, null);
            }
        }

        return new GetRestaurantInformationOutput("hi");
    }
}
