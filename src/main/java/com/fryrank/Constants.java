package com.fryrank;

public class Constants {

    public static final String API_PATH = "/api";
    public static final String ISO_DATE_TIME = "isoDateTime";

    // Input Validator
    public static final String GENERIC_VALIDATOR_ERROR_MESSAGE = "Encountered error while validating API input.";
    public static final String REVIEW_VALIDATOR_ERRORS_OBJECT_NAME = "review";
    public static final String USER_METADATA_VALIDATOR_ERRORS_OBJECT_NAME = "userMetadata";
    public static final String REJECTION_REQUIRED_CODE = "field.required";
    public static final String REJECTION_FORMAT_CODE = "field.invalidFormat";

    // Output field names
    public static final String USER_METADATA_OUTPUT_FIELD_NAME = "userMetadata";

    // MongoDB keys
    public static final String ACCOUNT_ID_KEY = "accountId";
    public static final String PRIMARY_KEY = "_id";

    // MongoDB collection names
    public static final String REVIEW_COLLECTION_NAME = "review";
    public static final String USER_METADATA_COLLECTION_NAME = "user-metadata";
}
