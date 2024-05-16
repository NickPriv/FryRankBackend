package com.fryrank;

import com.fryrank.model.Review;

import java.util.ArrayList;
import java.util.List;

public class TestConstants {

    public static final String TEST_RESTAURANT_ID = "1";
    public static final String TEST_REVIEW_ID_1 = "review_id_1";
    public static final String TEST_REVIEW_ID_2 = "review_id_2";
    public static final String TEST_AUTHOR_ID_1 = "author_id_1";
    public static final String TEST_AUTHOR_ID_2 = "author_id_2";
    public static final String TEST_TITLE_1 = "title_1";
    public static final String TEST_TITLE_2 = "title_2";
    public static final String TEST_BODY_1 = "body_1";
    public static final String TEST_BODY_2 = "body_2";

    public static final Review TEST_REVIEW_1 = new Review(TEST_REVIEW_ID_1, TEST_RESTAURANT_ID, TEST_AUTHOR_ID_1, 5.0 , TEST_TITLE_1, TEST_BODY_1);

    public static final List<Review> TEST_REVIEWS = new ArrayList<>() {
        {
            add(TEST_REVIEW_1);
            add(new Review(TEST_REVIEW_ID_2, TEST_RESTAURANT_ID, TEST_AUTHOR_ID_2, 7.0 , TEST_TITLE_2, TEST_BODY_2));
        }
    };
}
