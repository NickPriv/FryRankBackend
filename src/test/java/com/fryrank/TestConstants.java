package com.fryrank;

import com.fryrank.model.Review;
import com.fryrank.model.PublicUserMetadata;
import com.fryrank.model.PublicUserMetadataOutput;

import java.util.ArrayList;
import java.util.List;

public class TestConstants {

    public static final String TEST_RESTAURANT_ID = "1";
    public static final String TEST_RESTAURANT_ID_1 = "ChIJl8BSSgfsj4ARi9qijghUAH0";
    public static final String TEST_RESTAURANT_ID_2 = "ChIJ1wHcROHNj4ARmNwmP2PcUWw";
    public static final String TEST_REVIEW_ID_1 = "review_id_1";
    public static final String TEST_REVIEW_ID_2 = "review_id_2";
    public static final String TEST_TITLE_1 = "title_1";
    public static final String TEST_TITLE_2 = "title_2";
    public static final String TEST_BODY_1 = "body_1";
    public static final String TEST_BODY_2 = "body_2";
    public static final String TEST_ISO_DATE_TIME_1 = "1970-01-01T00:00:00Z";
    public static final String TEST_ACCOUNT_ID = "1234567890";
    public static final String TEST_ACCOUNT_ID_NO_USER_METADATA = "test_account_id_no_user_metadata";
    public static final String TEST_USERNAME = "testflush";

    public static final String TEST_TOKEN = "3c2353bc79ff762690f24ea376b4eb940f1db01427b39a65fb9153d59f011e46";

    public static final String ACCOUNT_JWT = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwiaWF0IjoxNzQ1MzgzODcwLCJ1c2VySWQiOiIxMjM0NTY3ODkwIn0.-VUzBtKs_3puwIGLBEA2l2lBoAGTD7FunzViENIwA3M";

    public static final PublicUserMetadata TEST_USER_METADATA_1 = new PublicUserMetadata(
        TEST_ACCOUNT_ID,
        TEST_USERNAME
    );

    public static final PublicUserMetadataOutput TEST_USER_METADATA_OUTPUT_1 = new PublicUserMetadataOutput(
        TEST_USERNAME
    );

    public static final Review TEST_REVIEW_JWT = new Review(
            TEST_REVIEW_ID_1,
            TEST_RESTAURANT_ID,
            5.0 ,
            TEST_TITLE_1,
            TEST_BODY_1,
            TEST_ISO_DATE_TIME_1,
            ACCOUNT_JWT,
            null
    );

    public static final Review TEST_REVIEW_1 = new Review(
        TEST_REVIEW_ID_1,
        TEST_RESTAURANT_ID,
        5.0 ,
        TEST_TITLE_1,
        TEST_BODY_1,
        TEST_ISO_DATE_TIME_1,
        TEST_ACCOUNT_ID,
        null
    );

    public static final Review TEST_REVIEW_NULL_ISO_DATETIME = new Review(
        TEST_REVIEW_ID_1,
        TEST_RESTAURANT_ID_1,
        5.0,
        TEST_TITLE_1,
        TEST_BODY_1,
        null,
            TEST_ACCOUNT_ID,
        TEST_USER_METADATA_1
    );

    public static final Review TEST_REVIEW_BAD_ISO_DATETIME = new Review(
        TEST_REVIEW_ID_1,
        TEST_RESTAURANT_ID_1,
        5.0,
        TEST_TITLE_1,
        TEST_BODY_1,
        "not-a-real-date",
            TEST_ACCOUNT_ID,
        TEST_USER_METADATA_1
    );

    public static final Review TEST_REVIEW_NULL_ACCOUNT_ID = new Review(
        TEST_REVIEW_ID_1,
        TEST_RESTAURANT_ID,
        5.0 ,
        TEST_TITLE_1,
        TEST_BODY_1,
        TEST_ISO_DATE_TIME_1,
        null,
        TEST_USER_METADATA_1
    );

    public static final List<Review> TEST_REVIEWS = new ArrayList<>() {
        {
            add(TEST_REVIEW_1);
            add(new Review(
                TEST_REVIEW_ID_2,
                TEST_RESTAURANT_ID,
                7.0 ,
                TEST_TITLE_2,
                TEST_BODY_2,
                TEST_ISO_DATE_TIME_1,
                TEST_ACCOUNT_ID,
                TEST_USER_METADATA_1)
            );
        }
    };

    public static final String TEST_DEFAULT_NAME = "test user name";
    public static final PublicUserMetadataOutput TEST_PUBLIC_USER_METADATA_OUTPUT_WITH_DEFAULT_NAME = new PublicUserMetadataOutput(TEST_DEFAULT_NAME);

    public static final PublicUserMetadataOutput TEST_PUBLIC_USER_METADATA_OUTPUT_EMPTY = new PublicUserMetadataOutput(null);

    public static final List<PublicUserMetadata> TEST_PUBLIC_USER_METADATA_LIST = new ArrayList<>() {
        {
            add(new PublicUserMetadata(TEST_ACCOUNT_ID, TEST_USERNAME));
        }
    };
}
