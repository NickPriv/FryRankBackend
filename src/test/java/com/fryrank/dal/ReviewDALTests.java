package com.fryrank.dal;

import com.fryrank.model.GetAllReviewsOutput;
import com.fryrank.model.RestaurantAvgScore;
import com.fryrank.model.Review;
import org.bson.Document;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.ArrayList;
import java.util.List;

import static com.fryrank.dal.ReviewDALImpl.RESTAURANT_ID_KEY;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.data.mongodb.core.query.Criteria.where;

@RunWith(MockitoJUnitRunner.class)
public class ReviewDALTests {
    @Mock
    MongoTemplate mongoTemplate;

    @InjectMocks
    ReviewDALImpl reviewDAL;

    private static final String TEST_RESTAURANT_ID = "1";

    @Test
    public void testGetAllReviewsByRestaurantId_happyPath() throws Exception {
        final Query query = new Query();
        query.addCriteria(where(RESTAURANT_ID_KEY).is(TEST_RESTAURANT_ID));

        final List<Review> expectedReviews = new ArrayList<>() {
            {
                add(new Review("review_id_1", "restaurant_id_1", "author_id_1", 5.0 , "title_1", "body_1"));
                add(new Review("review_id_2", "restaurant_id_2", "author_id_2", 7.0 , "title_2", "body_2"));
            }
        };
        when(mongoTemplate.find(query, Review.class)).thenReturn(expectedReviews);

        final AggregationResults<RestaurantAvgScore> aggregationResults = new AggregationResults<>(List.of(
                new RestaurantAvgScore(RESTAURANT_ID_KEY, 6f)), new Document());
        when(mongoTemplate.aggregate(any(Aggregation.class), anyString(), eq(RestaurantAvgScore.class))).thenReturn(aggregationResults);

        final GetAllReviewsOutput expectedOutput = new GetAllReviewsOutput(expectedReviews, 6f);
        final GetAllReviewsOutput actualOutput = reviewDAL.getAllReviewsByRestaurantId(TEST_RESTAURANT_ID);
        assertEquals(actualOutput, expectedOutput);
    }

    @Test
    public void testGetAllReviewsByRestaurantId_noReviews() throws Exception {
        final Query query = new Query();
        query.addCriteria(where(RESTAURANT_ID_KEY).is(TEST_RESTAURANT_ID));

        final List<Review> expectedReviews = List.of();
        when(mongoTemplate.find(query, Review.class)).thenReturn(expectedReviews);

        final AggregationResults<RestaurantAvgScore> aggregationResults = new AggregationResults<>(List.of(), new Document());
        when(mongoTemplate.aggregate(any(Aggregation.class), anyString(), eq(RestaurantAvgScore.class))).thenReturn(aggregationResults);

        final GetAllReviewsOutput expectedOutput = new GetAllReviewsOutput(expectedReviews, null);
        final GetAllReviewsOutput actualOutput = reviewDAL.getAllReviewsByRestaurantId(TEST_RESTAURANT_ID);
        assertEquals(actualOutput, expectedOutput);
    }

    @Test
    public void testGetAllReviewsByRestaurantId_nullRestaurantId() {
        assertThrows(NullPointerException.class, () -> reviewDAL.getAllReviewsByRestaurantId(null));
    }

    @Test
    public void testAddNewReview() throws Exception {
        Review expectedReview = new Review("review_id_1", "restaurant_id_1", "author_id_1", 5.0 , "title_1", "body_1");

        Review actualReview = reviewDAL.addNewReview(expectedReview);

        assertEquals(expectedReview, actualReview);
    }
}
