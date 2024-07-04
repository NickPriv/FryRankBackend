package com.fryrank.controller;

import com.fryrank.dal.ReviewDAL;
import com.fryrank.model.*;
import com.fryrank.validator.ValidatorException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.fryrank.TestConstants.TEST_AUTHOR_ID_1;
import static com.fryrank.TestConstants.TEST_BODY_1;
import static com.fryrank.TestConstants.TEST_RESTAURANT_ID;
import static com.fryrank.TestConstants.TEST_REVIEWS;
import static com.fryrank.TestConstants.TEST_REVIEW_1;
import static com.fryrank.TestConstants.TEST_REVIEW_ID_1;
import static com.fryrank.TestConstants.TEST_TITLE_1;
import static com.fryrank.TestConstants.TEST_ISO_DATE_TIME_1;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class ReviewControllerTests {
    @Mock
    ReviewDAL reviewDAL;

    @InjectMocks
    ReviewController controller;

    private static final String TEST_RESTAURANT_ID_1 = "ChIJl8BSSgfsj4ARi9qijghUAH0";
    private static final String TEST_RESTAURANT_ID_2 = "ChIJ1wHcROHNj4ARmNwmP2PcUWw";

    // /api/reviews endpoint tests
    @Test
    public void testGetAllReviewsForRestaurant() throws Exception {
        final GetAllReviewsOutput expectedOutput = new GetAllReviewsOutput(TEST_REVIEWS);
        when(reviewDAL.getAllReviewsByRestaurantId(TEST_RESTAURANT_ID)).thenReturn(expectedOutput);

        final GetAllReviewsOutput actualOutput = controller.getAllReviewsForRestaurant(TEST_RESTAURANT_ID);
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void testAddNewReviewForRestaurant() throws Exception {
        when(reviewDAL.addNewReview(TEST_REVIEW_1)).thenReturn(TEST_REVIEW_1);

        Review actualReview = controller.addNewReviewForRestaurant(TEST_REVIEW_1);

        assertEquals(TEST_REVIEW_1, actualReview);
    }

    @Test(expected = NullPointerException.class)
    public void testAddNewReviewForNullRestaurant() throws Exception {
        controller.addNewReviewForRestaurant(null);
    }

    @Test
    public void testAddNewReviewNullReviewID() throws Exception {
        Review expectedReview = new Review(null, TEST_RESTAURANT_ID_1, TEST_AUTHOR_ID_1, 5.0, TEST_TITLE_1, TEST_BODY_1, TEST_ISO_DATE_TIME_1);

        when(reviewDAL.addNewReview(expectedReview)).thenReturn(expectedReview);

        Review actualReview = controller.addNewReviewForRestaurant(expectedReview);

        assertEquals(expectedReview, actualReview);
    }

    @Test(expected = NullPointerException.class)
    public void testAddNewReviewNullRestaurantID() throws Exception {
        Review expectedReview = new Review(TEST_REVIEW_ID_1, null, TEST_AUTHOR_ID_1, 5.0, TEST_TITLE_1, TEST_BODY_1, TEST_ISO_DATE_TIME_1);

        controller.addNewReviewForRestaurant(expectedReview);
    }

    @Test(expected = NullPointerException.class)
    public void testAddNewReviewNullAuthorID() throws Exception {
        Review expectedReview = new Review(TEST_REVIEW_ID_1, TEST_RESTAURANT_ID_1, null, 5.0, TEST_TITLE_1, TEST_BODY_1, TEST_ISO_DATE_TIME_1);

        controller.addNewReviewForRestaurant(expectedReview);
    }

    @Test(expected = NullPointerException.class)
    public void testAddNewReviewNullScore() throws Exception {
        Review expectedReview = new Review(TEST_REVIEW_ID_1, TEST_RESTAURANT_ID_1, TEST_AUTHOR_ID_1, null, TEST_TITLE_1, TEST_BODY_1, TEST_ISO_DATE_TIME_1);

        controller.addNewReviewForRestaurant(expectedReview);
    }

    @Test(expected = NullPointerException.class)
    public void testAddNewReviewNullTitle() throws Exception {
        Review expectedReview = new Review(TEST_REVIEW_ID_1, TEST_RESTAURANT_ID_1, TEST_AUTHOR_ID_1, 5.0, null, TEST_BODY_1, TEST_ISO_DATE_TIME_1);

        controller.addNewReviewForRestaurant(expectedReview);
    }

    @Test(expected = NullPointerException.class)
    public void testAddNewReviewNullBody() throws Exception {
        Review expectedReview = new Review(TEST_REVIEW_ID_1, TEST_RESTAURANT_ID_1, TEST_AUTHOR_ID_1, 5.0, TEST_TITLE_1, null, TEST_ISO_DATE_TIME_1);

        controller.addNewReviewForRestaurant(expectedReview);
    }

    @Test
    public void testAddNewReviewNullISODateTime() {
        Review expectedReview = new Review(TEST_REVIEW_ID_1, TEST_RESTAURANT_ID_1, TEST_AUTHOR_ID_1, 5.0, TEST_TITLE_1, TEST_BODY_1, null);

        ValidatorException exception = assertThrows(
                ValidatorException.class, () -> controller.addNewReviewForRestaurant(expectedReview));

        assertEquals("Encountered error while validating API input.\n" +
                    "Errors:\n\tField error in object 'review' on field 'isoDateTime': rejected value [null]; codes [field.required.review.isoDateTime,field.required.isoDateTime,field.required.java.lang.String,field.required]; arguments []; default message [null]\n",
                exception.getErrorsString()
        );
        assertEquals("Encountered error while validating API input.",
                exception.getMessage());
    }

    @Test
    public void testAddNewBadFormatISODateTime() {
        Review expectedReview = new Review(TEST_REVIEW_ID_1, TEST_RESTAURANT_ID_1, TEST_AUTHOR_ID_1, 5.0, TEST_TITLE_1, TEST_BODY_1, "not-a-real-date");

        ValidatorException exception = assertThrows(
                ValidatorException.class, () -> controller.addNewReviewForRestaurant(expectedReview));

        assertEquals("Encountered error while validating API input.\n" +
                        "Errors:\n" +
                        "\tField error in object 'review' on field 'isoDateTime': rejected value [not-a-real-date]; codes [field.invalidFormat.review.isoDateTime,field.invalidFormat.isoDateTime,field.invalidFormat.java.lang.String,field.invalidFormat]; arguments []; default message [The provided isoDateTime is not in ISO format.]\n",
                exception.getErrorsString()
        );
        assertEquals("Encountered error while validating API input.",
                exception.getMessage());
    }

    // /api/reviews/aggregateInformation endpoint tests
    @Test
    public void testGetSingleRestaurantAllAggregateInformation() throws Exception {
        final Map<String, AggregateReviewInformation> expectedAggregateReviewInformation = Map.of(
            TEST_RESTAURANT_ID_1, new AggregateReviewInformation(TEST_RESTAURANT_ID_1, 5.0F)
        );
        final GetAggregateReviewInformationOutput expectedOutput = new GetAggregateReviewInformationOutput(expectedAggregateReviewInformation);
        final List<String> restaurantIds = new ArrayList<String>(){
            {
                add(TEST_RESTAURANT_ID_1);
            }
        };
        final AggregateReviewFilter aggregateReviewFilter = new AggregateReviewFilter(true);

        when(reviewDAL.getAggregateReviewInformationForRestaurants(restaurantIds, aggregateReviewFilter)).thenReturn(expectedOutput);

        final GetAggregateReviewInformationOutput actualOutput = controller.getAggregateReviewInformationForRestaurants(TEST_RESTAURANT_ID_1, true);
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void testGetMultipleRestaurantAllAggregateInformation() throws Exception {
        final Map<String, AggregateReviewInformation> expectedAggregateReviewInformation = Map.of(
            TEST_RESTAURANT_ID_1, new AggregateReviewInformation(TEST_RESTAURANT_ID_1, 5.0F),
                TEST_RESTAURANT_ID_2, new AggregateReviewInformation(TEST_RESTAURANT_ID_2, 7.0F)
        );
        final GetAggregateReviewInformationOutput expectedOutput = new GetAggregateReviewInformationOutput(expectedAggregateReviewInformation);
        final List<String> restaurantIds = new ArrayList<String>(){
            {
                add(TEST_RESTAURANT_ID_1);
                add(TEST_RESTAURANT_ID_2);
            }
        };
        final AggregateReviewFilter aggregateReviewFilter = new AggregateReviewFilter(true);

        when(reviewDAL.getAggregateReviewInformationForRestaurants(restaurantIds.stream().sorted().collect(Collectors.toList()), aggregateReviewFilter)).thenReturn(expectedOutput);

        final GetAggregateReviewInformationOutput inOrderOutput = controller.getAggregateReviewInformationForRestaurants(TEST_RESTAURANT_ID_1 + "," + TEST_RESTAURANT_ID_2, true);
        assertEquals(expectedOutput, inOrderOutput);

        final GetAggregateReviewInformationOutput reversedOutput = controller.getAggregateReviewInformationForRestaurants(TEST_RESTAURANT_ID_2 + "," + TEST_RESTAURANT_ID_1, true);
        assertEquals(expectedOutput, reversedOutput);
    }

    @Test
    public void testGetSingleRestaurantNoAggregateInformation() throws Exception {
        final Map<String, AggregateReviewInformation> expectedAggregateReviewInformation = Map.of(
            TEST_RESTAURANT_ID_1, new AggregateReviewInformation(TEST_RESTAURANT_ID_1, null)
        );
        final GetAggregateReviewInformationOutput expectedOutput = new GetAggregateReviewInformationOutput(expectedAggregateReviewInformation);
        final List<String> restaurantIds = new ArrayList<String>(){
            {
                add(TEST_RESTAURANT_ID_1);
            }
        };
        final AggregateReviewFilter aggregateReviewFilter = new AggregateReviewFilter(false);

        when(reviewDAL.getAggregateReviewInformationForRestaurants(restaurantIds, aggregateReviewFilter)).thenReturn(expectedOutput);

        final GetAggregateReviewInformationOutput actualOutput = controller.getAggregateReviewInformationForRestaurants(TEST_RESTAURANT_ID_1, false);
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void testGetMultipleRestaurantNoAggregateInformationReverseOrdering() throws Exception {
        final Map<String, AggregateReviewInformation> expectedAggregateReviewInformation = Map.of(
            TEST_RESTAURANT_ID_1, new AggregateReviewInformation(TEST_RESTAURANT_ID_1, null),
                TEST_RESTAURANT_ID_2, new AggregateReviewInformation(TEST_RESTAURANT_ID_2, null)
        );
        final GetAggregateReviewInformationOutput expectedOutput = new GetAggregateReviewInformationOutput(expectedAggregateReviewInformation);
        final List<String> restaurantIds = new ArrayList<String>(){
            {
                add(TEST_RESTAURANT_ID_1);
                add(TEST_RESTAURANT_ID_2);
            }
        };
        final AggregateReviewFilter aggregateReviewFilter = new AggregateReviewFilter(false);

        when(reviewDAL.getAggregateReviewInformationForRestaurants(restaurantIds.stream().sorted().collect(Collectors.toList()), aggregateReviewFilter)).thenReturn(expectedOutput);

        final GetAggregateReviewInformationOutput actualOutput = controller.getAggregateReviewInformationForRestaurants(TEST_RESTAURANT_ID_2 + "," + TEST_RESTAURANT_ID_1, false);
        assertEquals(expectedOutput, actualOutput);
    }
}
