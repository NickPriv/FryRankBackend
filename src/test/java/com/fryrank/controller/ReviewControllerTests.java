package com.fryrank.controller;

import com.fryrank.dal.ReviewDAL;
import com.fryrank.model.Review;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class ReviewControllerTests {
    @Mock
    ReviewDAL reviewDAL;

    @InjectMocks
    ReviewController controller;

    @Test
    public void testGetAllReviewsForRestaurant() throws Exception {
        List<Review> expectedReviews = new ArrayList<>() {
                {
                    add(new Review("review_id_1", "restaurant_id_1", "author_id_1", 5.0, "title_1", "body_1"));
                    add(new Review("review_id_2", "restaurant_id_2", "author_id_2", 7.0, "title_2", "body_2"));
                }
        };
        when(reviewDAL.getAllReviewsByRestaurantId("1")).thenReturn(expectedReviews);

        List<Review> actualReviews = controller.getAllReviewsForRestaurant("1");

        assertEquals(actualReviews, expectedReviews);
    }

    @Test
    public void testAddNewReviewForRestaurant() throws Exception {
        Review expectedReview = new Review("review_id_1", "restaurant_id_1", "author_id_1", 5.0, "title_1", "body_1");

        when(reviewDAL.addNewReview(expectedReview)).thenReturn(expectedReview);

        Review actualReview = controller.addNewReviewForRestaurant(expectedReview);

        assertEquals(expectedReview, actualReview);
    }

    @Test(expected = NullPointerException.class)
    public void testAddNewReviewForNullRestaurantID() throws Exception {
        controller.addNewReviewForRestaurant(null);
    }

    @Test(expected = NullPointerException.class)
    public void testAddNewReviewNullReviewID() throws Exception {
        Review expectedReview = new Review(null, "restaurant_id_1", "author_id_1", 5.0, "title_1", "body_1");

        controller.addNewReviewForRestaurant(expectedReview);
    }

    @Test(expected = NullPointerException.class)
    public void testAddNewReviewNullRestaurantID() throws Exception {
        Review expectedReview = new Review("review_id_1", null, "author_id_1", 5.0, "title_1", "body_1");

        controller.addNewReviewForRestaurant(expectedReview);
    }

    @Test(expected = NullPointerException.class)
    public void testAddNewReviewNullAuthorID() throws Exception {
        Review expectedReview = new Review("review_id_1", "restaurant_id_1", null, 5.0, "title_1", "body_1");

        controller.addNewReviewForRestaurant(expectedReview);
    }

    @Test(expected = NullPointerException.class)
    public void testAddNewReviewNullScore() throws Exception {
        Review expectedReview = new Review("review_id_1", "restaurant_id_1", "author_id_1", null, "title_1", "body_1");

        controller.addNewReviewForRestaurant(expectedReview);
    }

    @Test(expected = NullPointerException.class)
    public void testAddNewReviewNullTitle() throws Exception {
        Review expectedReview = new Review("review_id_1", "restaurant_id_1", "author_id_1", 5.0, null, "body_1");

        controller.addNewReviewForRestaurant(expectedReview);
    }

    @Test(expected = NullPointerException.class)
    public void testAddNewReviewNullBody() throws Exception {
        Review expectedReview = new Review("review_id_1", "restaurant_id_1", "author_id_1", 5.0, "title_1", null);

        controller.addNewReviewForRestaurant(expectedReview);
    }
}
