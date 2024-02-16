package com.fryrank.dal;

import com.fryrank.model.Review;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class ReviewDALTests {
    @Mock
    MongoTemplate mongoTemplate;

    @InjectMocks
    ReviewDALImpl reviewDAL;

    @Test
    public void testGetAllReviewsByRestaurantId() throws Exception {
        Query query = new Query();
        query.addCriteria(Criteria.where("restaurantId").is("1"));
        List<Review> expectedReviews = new ArrayList<>() {
            {
                add(new Review("review_id_1", "restaurant_id_1", "author_id_1", 5.0 , "title_1", "body_1"));
                add(new Review("review_id_2", "restaurant_id_2", "author_id_2", 7.0 , "title_2", "body_2"));
            }
        };
        Mockito.when(mongoTemplate.find(query, Review.class)).thenReturn(expectedReviews);

        List<Review> actualReviews = reviewDAL.getAllReviewsByRestaurantId("1");

        Assert.assertEquals(actualReviews, expectedReviews);
    }

    @Test
    public void testAddNewReview() throws Exception {
        Review expectedReview = new Review("review_id_1", "restaurant_id_1", "author_id_1", 5.0 , "title_1", "body_1");

        Review actualReview = reviewDAL.addNewReview(expectedReview);

        Assert.assertEquals(expectedReview, actualReview);
    }
}
