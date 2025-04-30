package com.fryrank.controller;

import com.fryrank.dal.UserMetadataDAL;
import com.fryrank.model.PublicUserMetadata;
import com.fryrank.model.PublicUserMetadataOutput;
import com.fryrank.validator.ValidatorException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Date;

import static com.fryrank.TestConstants.TEST_ACCOUNT_ID;
import static com.fryrank.TestConstants.TEST_DEFAULT_NAME;
import static com.fryrank.TestConstants.TEST_USERNAME;
import static com.fryrank.TestConstants.TEST_USER_METADATA_1;
import static com.fryrank.TestConstants.TEST_USER_METADATA_OUTPUT_1;
import static com.fryrank.TestConstants.TEST_PUBLIC_USER_METADATA_OUTPUT_EMPTY;
import static com.fryrank.TestConstants.TEST_PUBLIC_USER_METADATA_OUTPUT_WITH_DEFAULT_NAME;
import static com.fryrank.TestConstants.TEST_TOKEN;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PublicUserMetadataControllerTests {
    @Mock
    UserMetadataDAL userMetadataDAL;

    @InjectMocks
    UserMetadataController userMetadataController;

    @Before
    public void setUp() {
        ReflectionTestUtils.setField(userMetadataController, "token_key", TEST_TOKEN);
    }

    private String generateTestToken() {
        return Jwts.builder()
                .setSubject(TEST_ACCOUNT_ID) // Example user ID
                .setIssuedAt(new Date()) // Issue time
                .claim("userId", TEST_ACCOUNT_ID) // Add claims
                .signWith(SignatureAlgorithm.HS256, TEST_TOKEN.getBytes()) // Your TOKEN_KEY
                .compact();
    }

    @Test
    public void testUpsertUserMetadata() throws Exception {
        String testToken = generateTestToken();
        PublicUserMetadata userMetadata = new PublicUserMetadata(testToken, TEST_USERNAME);

        when(userMetadataDAL.upsertPublicUserMetadata(TEST_USER_METADATA_1)).thenReturn(TEST_USER_METADATA_OUTPUT_1);
        final PublicUserMetadataOutput actualUserMetadata = userMetadataController.upsertPublicUserMetadata(userMetadata);
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
        // Mock a valid token and decoded accountId
        String testToken = generateTestToken();
        when(userMetadataDAL.putPublicUserMetadataForAccountId(TEST_ACCOUNT_ID, TEST_DEFAULT_NAME)).thenReturn(TEST_USER_METADATA_OUTPUT_1);
        final PublicUserMetadataOutput actualUserMetadata = userMetadataController.putPublicUserMetadata(testToken, TEST_DEFAULT_NAME);
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
        String testToken = generateTestToken();
        when(userMetadataDAL.getPublicUserMetadataForAccountId(TEST_ACCOUNT_ID)).thenReturn(TEST_USER_METADATA_OUTPUT_1);

        final PublicUserMetadataOutput actualUserMetadata = userMetadataController.getPublicUserMetadata(testToken);
        assertEquals(TEST_USER_METADATA_OUTPUT_1, actualUserMetadata);
    }

    @Test
    public void testGetUserMetadataNoAccountIdParameter() throws Exception {
        when(userMetadataDAL.getPublicUserMetadataForAccountId(null)).thenReturn(TEST_PUBLIC_USER_METADATA_OUTPUT_EMPTY);

        final PublicUserMetadataOutput actualUserMetadata = userMetadataController.getPublicUserMetadata(null);
        assertEquals(TEST_PUBLIC_USER_METADATA_OUTPUT_EMPTY, actualUserMetadata);
    }
}
