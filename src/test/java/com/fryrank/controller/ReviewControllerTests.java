package com.fryrank.controller;

import com.fryrank.dal.ReviewDAL;
import com.fryrank.model.*;
import com.fryrank.validator.ValidatorException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.fryrank.TestConstants.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ReviewControllerTests {
    @Mock
    ReviewDAL reviewDAL;

    @InjectMocks
    ReviewController controller;

    @Before
    public void setUp() {
        ReflectionTestUtils.setField(controller, "token_key", TEST_TOKEN);
    }

    private String generateTestToken() {
        return Jwts.builder()
                .setSubject(TEST_ACCOUNT_ID) // Example user ID
                .setIssuedAt(new Date()) // Issue time
                .claim("userId", TEST_ACCOUNT_ID) // Add claims
                .signWith(SignatureAlgorithm.HS256, TEST_TOKEN.getBytes())
                .compact();
    }

    // /api/reviews endpoint tests
    @Test
    public void testGetAllReviewsForRestaurant() throws Exception {
        final GetAllReviewsOutput expectedOutput = new GetAllReviewsOutput(TEST_REVIEWS);
        when(reviewDAL.getAllReviewsByRestaurantId(TEST_RESTAURANT_ID)).thenReturn(expectedOutput);

        final GetAllReviewsOutput actualOutput = controller.getAllReviews(TEST_RESTAURANT_ID, null);
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void testGetAllReviewsForAccount() throws Exception {
        String testToken = generateTestToken();
        final GetAllReviewsOutput expectedOutput = new GetAllReviewsOutput(TEST_REVIEWS);
        when(reviewDAL.getAllReviewsByAccountId(TEST_ACCOUNT_ID)).thenReturn(expectedOutput);

        final GetAllReviewsOutput actualOutput = controller.getAllReviews(null, testToken);
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void testGetTopReviews() throws Exception {
        final GetAllReviewsOutput expectedOutput = new GetAllReviewsOutput(TEST_REVIEWS);
        when(reviewDAL.getTopMostRecentReviews(TEST_REVIEWS.size())).thenReturn(expectedOutput);

        final GetAllReviewsOutput actualOutput = controller.getTopReviews(TEST_REVIEWS.size());
        assertEquals(expectedOutput.getReviews().size(), actualOutput.getReviews().size());
    }

    @Test
    public void testGetAllReviewsNoParameter() throws Exception {
        assertThrows(NullPointerException.class, () -> controller.getAllReviews(null, null));
    }

    @Test
    public void testAddNewReviewForRestaurant() throws Exception {
        String testToken = generateTestToken();
        when(reviewDAL.addNewReview(TEST_REVIEW_1)).thenReturn(TEST_REVIEW_1);

        Review actualReview = controller.addNewReviewForRestaurant(testToken, TEST_REVIEW_1);
        assertEquals(TEST_REVIEW_1, actualReview);
    }

    @Test(expected = NullPointerException.class)
    public void testAddNewReviewForNullRestaurant() throws Exception {
        String testToken = generateTestToken();
        controller.addNewReviewForRestaurant(testToken,null);
    }

    @Test
    public void testAddNewReviewNullReviewID() throws Exception {
        String testToken = generateTestToken();
        Review expectedReview = new Review(null, TEST_RESTAURANT_ID_1, 5.0, TEST_TITLE_1, TEST_BODY_1, TEST_ISO_DATE_TIME_1, TEST_ACCOUNT_ID, null);
        when(reviewDAL.addNewReview(expectedReview)).thenReturn(expectedReview);

        Review actualReview = controller.addNewReviewForRestaurant(testToken, expectedReview);
        assertEquals(expectedReview, actualReview);
    }

    @Test(expected = NullPointerException.class)
    public void testAddNewReviewNullRestaurantID() throws Exception {
        String testToken = generateTestToken();
        Review expectedReview = new Review(TEST_REVIEW_ID_1, null, 5.0, TEST_TITLE_1, TEST_BODY_1, TEST_ISO_DATE_TIME_1, TEST_ACCOUNT_ID, null);

        controller.addNewReviewForRestaurant(testToken, expectedReview);
    }

    @Test(expected = NullPointerException.class)
    public void testAddNewReviewNullScore() throws Exception {
        String testToken = generateTestToken();
        Review expectedReview = new Review(TEST_REVIEW_ID_1, TEST_RESTAURANT_ID_1, null, TEST_TITLE_1, TEST_BODY_1, TEST_ISO_DATE_TIME_1, TEST_ACCOUNT_ID, null);

        controller.addNewReviewForRestaurant(testToken, expectedReview);
    }

    @Test(expected = NullPointerException.class)
    public void testAddNewReviewNullTitle() throws Exception {
        String testToken = generateTestToken();
        Review expectedReview = new Review(TEST_REVIEW_ID_1, TEST_RESTAURANT_ID_1, 5.0, null, TEST_BODY_1, TEST_ISO_DATE_TIME_1, TEST_ACCOUNT_ID, null);

        controller.addNewReviewForRestaurant(testToken, expectedReview);
    }

    @Test(expected = NullPointerException.class)
    public void testAddNewReviewNullBody() throws Exception {
        String testToken = generateTestToken();
        Review expectedReview = new Review(TEST_REVIEW_ID_1, TEST_RESTAURANT_ID_1, 5.0, TEST_TITLE_1, null, TEST_ISO_DATE_TIME_1, TEST_ACCOUNT_ID, null);

        controller.addNewReviewForRestaurant(testToken, expectedReview);
    }

    @Test(expected = ValidatorException.class)
    public void testAddNewReviewNullISODateTime() throws Exception {
        String testToken = generateTestToken();
        controller.addNewReviewForRestaurant(testToken, TEST_REVIEW_NULL_ISO_DATETIME);
    }

    @Test(expected = ValidatorException.class)
    public void testAddNewBadFormatISODateTime() throws Exception {
        String testToken = generateTestToken();
        controller.addNewReviewForRestaurant(testToken, TEST_REVIEW_BAD_ISO_DATETIME);
    }

    @Test(expected = ValidatorException.class)
    public void testAddNewReviewNullAccountId() throws Exception {
        String testToken = generateTestToken();
        controller.addNewReviewForRestaurant(testToken, TEST_REVIEW_NULL_ACCOUNT_ID);
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

