package com.fryrank.dal;
import com.fryrank.model.UserMetadata;
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
import static com.fryrank.TestConstants.TEST_USER_METADATA_1;
import static com.fryrank.TestConstants.TEST_USER_METADATA_EMPTY;
import static com.fryrank.TestConstants.TEST_USER_METADATA_LIST;
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
    public void testGetUserMetadataForAccountId_happyPath() throws Exception {
        final Query query = new Query();
        query.addCriteria(where(ACCOUNT_ID_KEY).is(TEST_ACCOUNT_ID));

        when(mongoTemplate.find(query, UserMetadata.class)).thenReturn(TEST_USER_METADATA_LIST);

        final UserMetadata actualOutput = userMetadataDAL.getUserMetadataForAccountId(TEST_ACCOUNT_ID);
        assertEquals(actualOutput, TEST_USER_METADATA_1);
    }

    @Test
    public void testGetUserMetadataForAccountId_noUserMetadata() throws Exception {
        final Query query = new Query();
        query.addCriteria(where(ACCOUNT_ID_KEY).is(TEST_ACCOUNT_ID_NO_USER_METADATA));

        final List<UserMetadata> expectedUserMetadata = List.of();
        when(mongoTemplate.find(query, UserMetadata.class)).thenReturn(expectedUserMetadata);

        final UserMetadata actualOutput = userMetadataDAL.getUserMetadataForAccountId(TEST_ACCOUNT_ID_NO_USER_METADATA);
        assertEquals(actualOutput, TEST_USER_METADATA_EMPTY);
    }

    @Test
    public void testGetUserMetadataForAccountId_nullAccountId() throws Exception {
        final Query query = new Query();
        query.addCriteria(where(ACCOUNT_ID_KEY).is(null));

        final UserMetadata actualOutput = userMetadataDAL.getUserMetadataForAccountId(null);
        assertEquals(actualOutput, TEST_USER_METADATA_EMPTY);
    }

    @Test
    public void testUpsertUserMetadata() throws Exception {
        when(mongoTemplate.findAndReplace(any(Query.class), eq(TEST_USER_METADATA_1), any(FindAndReplaceOptions.class))).thenReturn(TEST_USER_METADATA_1);

        final UserMetadata expectedUserMetadata = TEST_USER_METADATA_1;
        final UserMetadata actualUserMetadata = userMetadataDAL.upsertUserMetadata(expectedUserMetadata);
        assertEquals(expectedUserMetadata, actualUserMetadata);
    }
}
