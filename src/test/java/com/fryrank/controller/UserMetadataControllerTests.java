package com.fryrank.controller;

import com.fryrank.dal.UserMetadataDAL;
import com.fryrank.model.UserMetadata;
import com.fryrank.validator.ValidatorException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static com.fryrank.TestConstants.TEST_ACCOUNT_ID;
import static com.fryrank.TestConstants.TEST_USERNAME;
import static com.fryrank.TestConstants.TEST_USER_METADATA_1;
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
        when(userMetadataDAL.upsertUserMetadata(TEST_USER_METADATA_1)).thenReturn(TEST_USER_METADATA_1);
        UserMetadata actualUserMetadata = userMetadataController.upsertUserMetadata(TEST_USER_METADATA_1);
        assertEquals(TEST_USER_METADATA_1, actualUserMetadata);
    }

    @Test(expected = ValidatorException.class)
    public void testUpsertUserMetadataForNullAccountId() throws Exception {
        UserMetadata expectedUserMetadata = new UserMetadata(null, TEST_USERNAME);
        userMetadataController.upsertUserMetadata(expectedUserMetadata);
    }

    @Test(expected = ValidatorException.class)
    public void testUpsertUserMetadataForNullUsername() throws Exception {
        UserMetadata expectedUserMetadata = new UserMetadata(TEST_ACCOUNT_ID, null);
        userMetadataController.upsertUserMetadata(expectedUserMetadata);
    }
}
