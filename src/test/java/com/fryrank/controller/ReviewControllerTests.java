package com.fryrank.controller;

import com.fryrank.dal.ReviewDAL;
import com.fryrank.model.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class ReviewControllerTests {
    @Mock
    ReviewDAL reviewDAL;

    @InjectMocks
    ReviewController controller;

    // /api/reviews endpoint tests
    @Test
    public void testGetAllReviewsForRestaurant() throws Exception {
        final List<Review> expectedReviews = new ArrayList<>() {
                {
                    add(new Review("review_id_1", "restaurant_id_1", "author_id_1", 5.0, "title_1", "body_1"));
                    add(new Review("review_id_2", "restaurant_id_2", "author_id_2", 7.0, "title_2", "body_2"));
                }
        };
        final GetAllReviewsOutput expectedOutput = new GetAllReviewsOutput(expectedReviews);
        when(reviewDAL.getAllReviewsByRestaurantId("1")).thenReturn(expectedOutput);

        final GetAllReviewsOutput actualOutput = controller.getAllReviewsForRestaurant("1");
        assertEquals(actualOutput, expectedOutput);
    }

    @Test
    public void testAddNewReviewForRestaurant() throws Exception {
        Review expectedReview = new Review("review_id_1", "restaurant_id_1", "author_id_1", 5.0, "title_1", "body_1");

        when(reviewDAL.addNewReview(expectedReview)).thenReturn(expectedReview);

        Review actualReview = controller.addNewReviewForRestaurant(expectedReview);

        assertEquals(expectedReview, actualReview);
    }

    @Test(expected = NullPointerException.class)
    public void testAddNewReviewForNullRestaurant() throws Exception {
        controller.addNewReviewForRestaurant(null);
    }

    @Test
    public void testAddNewReviewNullReviewID() throws Exception {
        Review expectedReview = new Review(null, "restaurant_id_1", "author_id_1", 5.0, "title_1", "body_1");

        when(reviewDAL.addNewReview(expectedReview)).thenReturn(expectedReview);

        Review actualReview = controller.addNewReviewForRestaurant(expectedReview);

        assertEquals(expectedReview, actualReview);
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

    // /api/reviews/aggregateInformation endpoint tests
    @Test
    public void testGetSingleRestaurantAllAggregateInformation() throws Exception {
        final Map<String, AggregateReviewInformation> expectedAggregateReviewInformation = Map.of(
                "ChIJl8BSSgfsj4ARi9qijghUAH0", new AggregateReviewInformation("ChIJl8BSSgfsj4ARi9qijghUAH0", 5.0F)
        );
        final GetAggregateReviewInformationOutput expectedOutput = new GetAggregateReviewInformationOutput(expectedAggregateReviewInformation);
        final List<String> restaurantIds = new ArrayList<String>(){
            {
                add("ChIJl8BSSgfsj4ARi9qijghUAH0");
            }
        };
        final AggregateReviewFilter aggregateReviewFilter = new AggregateReviewFilter(true);

        when(reviewDAL.getAggregateReviewInformationForRestaurants(restaurantIds, aggregateReviewFilter)).thenReturn(expectedOutput);

        final GetAggregateReviewInformationOutput actualOutput = controller.getAggregateReviewInformationForRestaurants("ChIJl8BSSgfsj4ARi9qijghUAH0", true);
        assertEquals(actualOutput, expectedOutput);
    }

    @Test
    public void testGetMultipleRestaurantAllAggregateInformation() throws Exception {
        final Map<String, AggregateReviewInformation> expectedAggregateReviewInformation = Map.of(
                "ChIJl8BSSgfsj4ARi9qijghUAH0", new AggregateReviewInformation("ChIJl8BSSgfsj4ARi9qijghUAH0", 5.0F),
                "ChIJ1wHcROHNj4ARmNwmP2PcUWw", new AggregateReviewInformation("ChIJ1wHcROHNj4ARmNwmP2PcUWw", 7.0F)
        );
        final GetAggregateReviewInformationOutput expectedOutput = new GetAggregateReviewInformationOutput(expectedAggregateReviewInformation);
        final List<String> restaurantIds = new ArrayList<String>(){
            {
                add("ChIJl8BSSgfsj4ARi9qijghUAH0");
                add("ChIJ1wHcROHNj4ARmNwmP2PcUWw");
            }
        };
        final AggregateReviewFilter aggregateReviewFilter = new AggregateReviewFilter(true);

        when(reviewDAL.getAggregateReviewInformationForRestaurants(restaurantIds.stream().sorted().collect(Collectors.toList()), aggregateReviewFilter)).thenReturn(expectedOutput);

        final GetAggregateReviewInformationOutput actualOutput = controller.getAggregateReviewInformationForRestaurants("ChIJl8BSSgfsj4ARi9qijghUAH0,ChIJ1wHcROHNj4ARmNwmP2PcUWw", true);
        assertEquals(actualOutput, expectedOutput);
    }

    @Test
    public void testGetMultipleRestaurantAllAggregateInformationReverseOrdering() throws Exception {
        final Map<String, AggregateReviewInformation> expectedAggregateReviewInformation = Map.of(
                "ChIJl8BSSgfsj4ARi9qijghUAH0", new AggregateReviewInformation("ChIJl8BSSgfsj4ARi9qijghUAH0", 5.0F),
                "ChIJ1wHcROHNj4ARmNwmP2PcUWw", new AggregateReviewInformation("ChIJ1wHcROHNj4ARmNwmP2PcUWw", 7.0F)
        );
        final GetAggregateReviewInformationOutput expectedOutput = new GetAggregateReviewInformationOutput(expectedAggregateReviewInformation);
        final List<String> restaurantIds = new ArrayList<String>(){
            {
                add("ChIJl8BSSgfsj4ARi9qijghUAH0");
                add("ChIJ1wHcROHNj4ARmNwmP2PcUWw");
            }
        };
        final AggregateReviewFilter aggregateReviewFilter = new AggregateReviewFilter(true);

        when(reviewDAL.getAggregateReviewInformationForRestaurants(restaurantIds.stream().sorted().collect(Collectors.toList()), aggregateReviewFilter)).thenReturn(expectedOutput);

        final GetAggregateReviewInformationOutput actualOutput = controller.getAggregateReviewInformationForRestaurants("ChIJ1wHcROHNj4ARmNwmP2PcUWw,ChIJl8BSSgfsj4ARi9qijghUAH0", true);
        assertEquals(actualOutput, expectedOutput);
    }

    @Test
    public void testGetSingleRestaurantNoAggregateInformation() throws Exception {
        final Map<String, AggregateReviewInformation> expectedAggregateReviewInformation = Map.of(
                "ChIJl8BSSgfsj4ARi9qijghUAH0", new AggregateReviewInformation("ChIJl8BSSgfsj4ARi9qijghUAH0", null)
        );
        final GetAggregateReviewInformationOutput expectedOutput = new GetAggregateReviewInformationOutput(expectedAggregateReviewInformation);
        final List<String> restaurantIds = new ArrayList<String>(){
            {
                add("ChIJl8BSSgfsj4ARi9qijghUAH0");
            }
        };
        final AggregateReviewFilter aggregateReviewFilter = new AggregateReviewFilter(false);

        when(reviewDAL.getAggregateReviewInformationForRestaurants(restaurantIds, aggregateReviewFilter)).thenReturn(expectedOutput);

        final GetAggregateReviewInformationOutput actualOutput = controller.getAggregateReviewInformationForRestaurants("ChIJl8BSSgfsj4ARi9qijghUAH0", false);
        assertEquals(actualOutput, expectedOutput);
    }

    @Test
    public void testGetMultipleRestaurantNoAggregateInformationReverseOrdering() throws Exception {
        final Map<String, AggregateReviewInformation> expectedAggregateReviewInformation = Map.of(
                "ChIJl8BSSgfsj4ARi9qijghUAH0", new AggregateReviewInformation("ChIJl8BSSgfsj4ARi9qijghUAH0", null),
                "ChIJ1wHcROHNj4ARmNwmP2PcUWw", new AggregateReviewInformation("ChIJ1wHcROHNj4ARmNwmP2PcUWw", null)
        );
        final GetAggregateReviewInformationOutput expectedOutput = new GetAggregateReviewInformationOutput(expectedAggregateReviewInformation);
        final List<String> restaurantIds = new ArrayList<String>(){
            {
                add("ChIJl8BSSgfsj4ARi9qijghUAH0");
                add("ChIJ1wHcROHNj4ARmNwmP2PcUWw");
            }
        };
        final AggregateReviewFilter aggregateReviewFilter = new AggregateReviewFilter(false);

        when(reviewDAL.getAggregateReviewInformationForRestaurants(restaurantIds.stream().sorted().collect(Collectors.toList()), aggregateReviewFilter)).thenReturn(expectedOutput);

        final GetAggregateReviewInformationOutput actualOutput = controller.getAggregateReviewInformationForRestaurants("ChIJ1wHcROHNj4ARmNwmP2PcUWw,ChIJl8BSSgfsj4ARi9qijghUAH0", false);
        assertEquals(actualOutput, expectedOutput);
    }
}
