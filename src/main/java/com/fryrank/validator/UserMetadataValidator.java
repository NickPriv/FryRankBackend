package com.fryrank.validator;

import com.fryrank.model.UserMetadata;
import lombok.NonNull;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import static com.fryrank.Constants.REJECTION_REQUIRED_CODE;

public class UserMetadataValidator implements Validator {
    public static final String USERNAME = "username";
    public static final String ACCOUNT_ID = "accountId";
    @Override
    public boolean supports(@NonNull Class clazz) {
        return UserMetadata.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(@NonNull Object target, @NonNull Errors errors) {
        UserMetadata userMetadata = (UserMetadata) target;
        String username = userMetadata.getUsername();
        if(username == null) {
            errors.rejectValue(USERNAME, REJECTION_REQUIRED_CODE);
        }

        final String accountId = userMetadata.getAccountId();
        if (accountId == null) {
            errors.rejectValue(ACCOUNT_ID, REJECTION_REQUIRED_CODE);
        }
    }

}
