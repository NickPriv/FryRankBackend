package com.fryrank.controller;

import com.fryrank.dal.UserMetadataDAL;
import com.fryrank.model.UserMetadata;
import com.fryrank.model.UserMetadataOutput;
import com.fryrank.validator.ValidatorException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static com.fryrank.TestConstants.TEST_ACCOUNT_ID;
import static com.fryrank.TestConstants.TEST_DEFAULT_NAME;
import static com.fryrank.TestConstants.TEST_USERNAME;
import static com.fryrank.TestConstants.TEST_USER_METADATA_1;
import static com.fryrank.TestConstants.TEST_USER_METADATA_OUTPUT_1;
import static com.fryrank.TestConstants.TEST_USER_METADATA_OUTPUT_EMPTY;
import static com.fryrank.TestConstants.TEST_USER_METADATA_OUTPUT_WITH_DEFAULT_NAME;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserMetadataControllerTests {
    @Mock
    UserMetadataDAL userMetadataDAL;

    @InjectMocks
    UserMetadataController userMetadataController;

    @Test
    public void testUpsertUserMetadata() throws Exception {
        when(userMetadataDAL.upsertUserMetadata(TEST_USER_METADATA_1)).thenReturn(TEST_USER_METADATA_OUTPUT_1);
        final UserMetadataOutput actualUserMetadata = userMetadataController.upsertUserMetadata(TEST_USER_METADATA_1);
        assertEquals(TEST_USER_METADATA_OUTPUT_1, actualUserMetadata);
    }

    @Test(expected = ValidatorException.class)
    public void testUpsertUserMetadataForNullAccountId() throws Exception {
        final UserMetadata expectedUserMetadata = new UserMetadata(null, TEST_USERNAME);
        userMetadataController.upsertUserMetadata(expectedUserMetadata);
    }

    @Test(expected = ValidatorException.class)
    public void testUpsertUserMetadataForNullUsername() throws Exception {
        final UserMetadata expectedUserMetadata = new UserMetadata(TEST_ACCOUNT_ID, null);
        userMetadataController.upsertUserMetadata(expectedUserMetadata);
    }

    @Test
    public void testPutUserMetadata() throws Exception {
        when(userMetadataDAL.putUserMetadataForAccountId(TEST_ACCOUNT_ID, TEST_DEFAULT_NAME)).thenReturn(TEST_USER_METADATA_OUTPUT_1);

        final UserMetadataOutput actualUserMetadata = userMetadataController.putUserMetadata(TEST_ACCOUNT_ID, TEST_DEFAULT_NAME);
        assertEquals(TEST_USER_METADATA_OUTPUT_1, actualUserMetadata);
    }

    @Test
    public void testPutUserMetadataNoAccountIdParameter() throws Exception {
        when(userMetadataDAL.putUserMetadataForAccountId(null, TEST_DEFAULT_NAME)).thenReturn(TEST_USER_METADATA_OUTPUT_WITH_DEFAULT_NAME);

        final UserMetadataOutput actualUserMetadata = userMetadataController.putUserMetadata(null, TEST_DEFAULT_NAME);
        assertEquals(TEST_USER_METADATA_OUTPUT_WITH_DEFAULT_NAME, actualUserMetadata);
    }

    @Test
    public void testGetUserMetadata() throws Exception {
        when(userMetadataDAL.getUserMetadataForAccountId(TEST_ACCOUNT_ID)).thenReturn(TEST_USER_METADATA_OUTPUT_1);

        final UserMetadataOutput actualUserMetadata = userMetadataController.getUserMetadata(TEST_ACCOUNT_ID);
        assertEquals(TEST_USER_METADATA_OUTPUT_1, actualUserMetadata);
    }

    @Test
    public void testGetUserMetadataNoAccountIdParameter() throws Exception {
        when(userMetadataDAL.getUserMetadataForAccountId(null)).thenReturn(TEST_USER_METADATA_OUTPUT_EMPTY);

        final UserMetadataOutput actualUserMetadata = userMetadataController.getUserMetadata(null);
        assertEquals(TEST_USER_METADATA_OUTPUT_EMPTY, actualUserMetadata);
    }
}
