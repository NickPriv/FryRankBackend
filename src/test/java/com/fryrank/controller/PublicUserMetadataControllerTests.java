package com.fryrank.controller;

import com.fryrank.dal.UserMetadataDAL;
import com.fryrank.model.PublicUserMetadata;
import com.fryrank.model.PublicUserMetadataOutput;
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
import static com.fryrank.TestConstants.TEST_PUBLIC_USER_METADATA_OUTPUT_EMPTY;
import static com.fryrank.TestConstants.TEST_PUBLIC_USER_METADATA_OUTPUT_WITH_DEFAULT_NAME;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PublicUserMetadataControllerTests {
    @Mock
    UserMetadataDAL userMetadataDAL;

    @InjectMocks
    UserMetadataController userMetadataController;

    @Test
    public void testUpsertUserMetadata() throws Exception {
        when(userMetadataDAL.upsertPublicUserMetadata(TEST_USER_METADATA_1)).thenReturn(TEST_USER_METADATA_OUTPUT_1);
        final PublicUserMetadataOutput actualUserMetadata = userMetadataController.upsertPublicUserMetadata(TEST_USER_METADATA_1);
        assertEquals(TEST_USER_METADATA_OUTPUT_1, actualUserMetadata);
    }

    @Test(expected = ValidatorException.class)
    public void testUpsertUserMetadataForNullAccountId() throws Exception {
        final PublicUserMetadata expectedPublicUserMetadata = new PublicUserMetadata(null, TEST_USERNAME);
        userMetadataController.upsertPublicUserMetadata(expectedPublicUserMetadata);
    }

    @Test(expected = ValidatorException.class)
    public void testUpsertUserMetadataForNullUsername() throws Exception {
        final PublicUserMetadata expectedPublicUserMetadata = new PublicUserMetadata(TEST_ACCOUNT_ID, null);
        userMetadataController.upsertPublicUserMetadata(expectedPublicUserMetadata);
    }

    @Test
    public void testPutUserMetadata() throws Exception {
        when(userMetadataDAL.putPublicUserMetadataForAccountId(TEST_ACCOUNT_ID, TEST_DEFAULT_NAME)).thenReturn(TEST_USER_METADATA_OUTPUT_1);

        final PublicUserMetadataOutput actualUserMetadata = userMetadataController.putPublicUserMetadata(TEST_ACCOUNT_ID, TEST_DEFAULT_NAME);
        assertEquals(TEST_USER_METADATA_OUTPUT_1, actualUserMetadata);
    }

    @Test
    public void testPutUserMetadataNoAccountIdParameter() throws Exception {
        when(userMetadataDAL.putPublicUserMetadataForAccountId(null, TEST_DEFAULT_NAME)).thenReturn(TEST_PUBLIC_USER_METADATA_OUTPUT_WITH_DEFAULT_NAME);

        final PublicUserMetadataOutput actualUserMetadata = userMetadataController.putPublicUserMetadata(null, TEST_DEFAULT_NAME);
        assertEquals(TEST_PUBLIC_USER_METADATA_OUTPUT_WITH_DEFAULT_NAME, actualUserMetadata);
    }

    @Test
    public void testGetUserMetadata() throws Exception {
        when(userMetadataDAL.getPublicUserMetadataForAccountId(TEST_ACCOUNT_ID)).thenReturn(TEST_USER_METADATA_OUTPUT_1);

        final PublicUserMetadataOutput actualUserMetadata = userMetadataController.getPublicUserMetadata(TEST_ACCOUNT_ID);
        assertEquals(TEST_USER_METADATA_OUTPUT_1, actualUserMetadata);
    }

    @Test
    public void testGetUserMetadataNoAccountIdParameter() throws Exception {
        when(userMetadataDAL.getPublicUserMetadataForAccountId(null)).thenReturn(TEST_PUBLIC_USER_METADATA_OUTPUT_EMPTY);

        final PublicUserMetadataOutput actualUserMetadata = userMetadataController.getPublicUserMetadata(null);
        assertEquals(TEST_PUBLIC_USER_METADATA_OUTPUT_EMPTY, actualUserMetadata);
    }
}
