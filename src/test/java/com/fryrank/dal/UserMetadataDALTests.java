package com.fryrank.dal;
import com.fryrank.model.UserMetadata;
import com.fryrank.model.UserMetadataOutput;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.mongodb.core.FindAndReplaceOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

import static com.fryrank.Constants.ACCOUNT_ID_KEY;
import static com.fryrank.TestConstants.TEST_ACCOUNT_ID;
import static com.fryrank.TestConstants.TEST_ACCOUNT_ID_NO_USER_METADATA;
import static com.fryrank.TestConstants.TEST_DEFAULT_NAME;
import static com.fryrank.TestConstants.TEST_USER_METADATA_1;
import static com.fryrank.TestConstants.TEST_USER_METADATA_LIST;
import static com.fryrank.TestConstants.TEST_USER_METADATA_OUTPUT_1;
import static com.fryrank.TestConstants.TEST_USER_METADATA_OUTPUT_EMPTY;
import static com.fryrank.TestConstants.TEST_USER_METADATA_OUTPUT_WITH_DEFAULT_NAME;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.data.mongodb.core.query.Criteria.where;

@RunWith(MockitoJUnitRunner.class)
public class UserMetadataDALTests {
    @Mock
    MongoTemplate mongoTemplate;

    @InjectMocks
    UserMetadataDALImpl userMetadataDAL;

    @Test
    public void testPutUserMetadataForAccountId_happyPath() throws Exception {
        final Query query = new Query();
        query.addCriteria(where(ACCOUNT_ID_KEY).is(TEST_ACCOUNT_ID));

        when(mongoTemplate.find(query, UserMetadata.class)).thenReturn(TEST_USER_METADATA_LIST);

        final UserMetadataOutput actualOutput = userMetadataDAL.putUserMetadataForAccountId(TEST_ACCOUNT_ID, TEST_DEFAULT_NAME);
        assertEquals(actualOutput, TEST_USER_METADATA_OUTPUT_1);
    }

    @Test
    public void testPutUserMetadataForAccountId_noUserMetadata() throws Exception {
        final Query query = new Query();
        query.addCriteria(where(ACCOUNT_ID_KEY).is(TEST_ACCOUNT_ID_NO_USER_METADATA));

        final List<UserMetadata> expectedUserMetadata = List.of();
        when(mongoTemplate.find(query, UserMetadata.class)).thenReturn(expectedUserMetadata);

        final UserMetadataOutput actualOutput = userMetadataDAL.putUserMetadataForAccountId(TEST_ACCOUNT_ID_NO_USER_METADATA, TEST_DEFAULT_NAME);
        assertEquals(actualOutput, TEST_USER_METADATA_OUTPUT_WITH_DEFAULT_NAME);
    }

    @Test
    public void testPutUserMetadataForAccountId_nullAccountId() throws Exception {
        final Query query = new Query();
        query.addCriteria(where(ACCOUNT_ID_KEY).is(null));

        final UserMetadataOutput actualOutput = userMetadataDAL.putUserMetadataForAccountId(null, TEST_DEFAULT_NAME);
        assertEquals(actualOutput, TEST_USER_METADATA_OUTPUT_WITH_DEFAULT_NAME);
    }

    @Test
    public void testGetUserMetadataForAccountId_happyPath() throws Exception {
        final Query query = new Query();
        query.addCriteria(where(ACCOUNT_ID_KEY).is(TEST_ACCOUNT_ID));

        when(mongoTemplate.find(query, UserMetadata.class)).thenReturn(TEST_USER_METADATA_LIST);

        final UserMetadataOutput actualOutput = userMetadataDAL.getUserMetadataForAccountId(TEST_ACCOUNT_ID);
        assertEquals(actualOutput, TEST_USER_METADATA_OUTPUT_1);
    }

    @Test
    public void testGetUserMetadataForAccountId_noUserMetadata() throws Exception {
        final Query query = new Query();
        query.addCriteria(where(ACCOUNT_ID_KEY).is(TEST_ACCOUNT_ID_NO_USER_METADATA));

        final List<UserMetadata> expectedUserMetadata = List.of();
        when(mongoTemplate.find(query, UserMetadata.class)).thenReturn(expectedUserMetadata);

        final UserMetadataOutput actualOutput = userMetadataDAL.getUserMetadataForAccountId(TEST_ACCOUNT_ID_NO_USER_METADATA);
        assertEquals(actualOutput, TEST_USER_METADATA_OUTPUT_EMPTY);
    }

    @Test
    public void testGetUserMetadataForAccountId_nullAccountId() throws Exception {
        final Query query = new Query();
        query.addCriteria(where(ACCOUNT_ID_KEY).is(null));

        final UserMetadataOutput actualOutput = userMetadataDAL.getUserMetadataForAccountId(null);
        assertEquals(actualOutput, TEST_USER_METADATA_OUTPUT_EMPTY);
    }

    @Test
    public void testUpsertUserMetadata() throws Exception {
        when(mongoTemplate.findAndReplace(any(Query.class), eq(TEST_USER_METADATA_1), any(FindAndReplaceOptions.class))).thenReturn(TEST_USER_METADATA_1);

        final UserMetadataOutput actualUserMetadata = userMetadataDAL.upsertUserMetadata(TEST_USER_METADATA_1);
        assertEquals(TEST_USER_METADATA_OUTPUT_1, actualUserMetadata);
    }
}
