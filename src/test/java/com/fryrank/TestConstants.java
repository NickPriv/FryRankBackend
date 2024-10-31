package com.fryrank;

import com.fryrank.model.Review;
import com.fryrank.model.UserMetadata;
import com.fryrank.model.UserMetadataOutput;

import java.util.ArrayList;
import java.util.List;

public class TestConstants {

    public static final String TEST_RESTAURANT_ID = "1";
    public static final String TEST_RESTAURANT_ID_1 = "ChIJl8BSSgfsj4ARi9qijghUAH0";
    public static final String TEST_RESTAURANT_ID_2 = "ChIJ1wHcROHNj4ARmNwmP2PcUWw";
    public static final String TEST_REVIEW_ID_1 = "review_id_1";
    public static final String TEST_REVIEW_ID_2 = "review_id_2";
    public static final String TEST_AUTHOR_ID_1 = "author_id_1";
    public static final String TEST_AUTHOR_ID_2 = "author_id_2";
    public static final String TEST_TITLE_1 = "title_1";
    public static final String TEST_TITLE_2 = "title_2";
    public static final String TEST_BODY_1 = "body_1";
    public static final String TEST_BODY_2 = "body_2";
    public static final String TEST_ISO_DATE_TIME_1 = "1970-01-01T00:00:00Z";
    public static final String TEST_ACCOUNT_ID = "test_account_id";
    public static final String TEST_ACCOUNT_ID_NO_USER_METADATA = "test_account_id_no_user_metadata";
    public static final String TEST_USERNAME = "testflush";

    public static final Review TEST_REVIEW_1 = new Review(
        TEST_REVIEW_ID_1,
        TEST_RESTAURANT_ID,
        TEST_AUTHOR_ID_1,
        5.0 ,
        TEST_TITLE_1,
        TEST_BODY_1,
        TEST_ISO_DATE_TIME_1,
        TEST_ACCOUNT_ID
    );

    public static final Review TEST_REVIEW_NULL_ISO_DATETIME = new Review(
        TEST_REVIEW_ID_1,
        TEST_RESTAURANT_ID_1,
        TEST_AUTHOR_ID_1,
        5.0,
        TEST_TITLE_1,
        TEST_BODY_1,
        null,
        TEST_ACCOUNT_ID
    );

    public static final Review TEST_REVIEW_BAD_ISO_DATETIME = new Review(
        TEST_REVIEW_ID_1,
        TEST_RESTAURANT_ID_1,
        TEST_AUTHOR_ID_1,
        5.0,
        TEST_TITLE_1,
        TEST_BODY_1,
        "not-a-real-date",
        TEST_ACCOUNT_ID
    );

    public static final Review TEST_REVIEW_NULL_ACCOUNT_ID = new Review(
        TEST_REVIEW_ID_1,
        TEST_RESTAURANT_ID,
        TEST_AUTHOR_ID_1,
        5.0 ,
        TEST_TITLE_1,
        TEST_BODY_1,
        TEST_ISO_DATE_TIME_1,
        null
    );

    public static final List<Review> TEST_REVIEWS = new ArrayList<>() {
        {
            add(TEST_REVIEW_1);
            add(new Review(
                TEST_REVIEW_ID_2,
                TEST_RESTAURANT_ID,
                TEST_AUTHOR_ID_2,
                7.0 ,
                TEST_TITLE_2,
                TEST_BODY_2,
                TEST_ISO_DATE_TIME_1,
                TEST_ACCOUNT_ID)
            );
        }
    };

    public static final UserMetadata TEST_USER_METADATA_1 = new UserMetadata(
        TEST_ACCOUNT_ID,
        TEST_USERNAME
    );

    public static final UserMetadataOutput TEST_USER_METADATA_OUTPUT_1 = new UserMetadataOutput(
            TEST_USERNAME
    );

    public static final UserMetadataOutput TEST_USER_METADATA_OUTPUT_EMPTY = new UserMetadataOutput(null);

    public static final List<UserMetadata> TEST_USER_METADATA_LIST = new ArrayList<>() {
        {
            add(new UserMetadata(TEST_ACCOUNT_ID, TEST_USERNAME));
        }
    };
}
