package com.fryrank.controller;

import com.fryrank.dal.UserMetadataDAL;
import com.fryrank.model.PublicUserMetadata;
import com.fryrank.model.PublicUserMetadataOutput;
import com.fryrank.validator.UserMetadataValidator;
import com.fryrank.validator.ValidatorException;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

import static com.fryrank.Constants.API_PATH;
import static com.fryrank.Constants.GENERIC_VALIDATOR_ERROR_MESSAGE;
import static com.fryrank.Constants.USER_METADATA_VALIDATOR_ERRORS_OBJECT_NAME;
import static com.fryrank.utils.TokenUtils.decodeToken;

@RestController
public class UserMetadataController {
    private static final String USER_METADATA_URI = API_PATH + "/userMetadata";

    @Value("${TOKEN_KEY}")
    private String token_key;

    @Autowired
    private UserMetadataDAL userMetadataDAL;

    @PostMapping(value=USER_METADATA_URI)
    public PublicUserMetadataOutput upsertPublicUserMetadata(@RequestBody @NonNull final PublicUserMetadata userMetadata) throws ValidatorException {
        if (userMetadata.getAccountId() == null || userMetadata.getAccountId().isEmpty() || userMetadata.getUsername() == null || userMetadata.getUsername().isEmpty()) {
            throw new ValidatorException(Collections.emptyList(), GENERIC_VALIDATOR_ERROR_MESSAGE);
        }

        String decodeAccount = decodeToken(userMetadata.getAccountId(), token_key);
        PublicUserMetadata updatedUserMetadata = new PublicUserMetadata(decodeAccount, userMetadata.getUsername());
        BindingResult bindingResult = new BeanPropertyBindingResult(updatedUserMetadata, USER_METADATA_VALIDATOR_ERRORS_OBJECT_NAME);
        UserMetadataValidator validator = new UserMetadataValidator();
        validator.validate(updatedUserMetadata, bindingResult);

        if(bindingResult.hasErrors()) {
            throw new ValidatorException(bindingResult.getAllErrors(), GENERIC_VALIDATOR_ERROR_MESSAGE);
        }
        return userMetadataDAL.upsertPublicUserMetadata(updatedUserMetadata);
    }

    @PutMapping(value=USER_METADATA_URI)
    public PublicUserMetadataOutput putPublicUserMetadata(@RequestParam final String accountId, @RequestParam @NonNull final String defaultUsername) {
        if (accountId == null || accountId.isEmpty()){
            return userMetadataDAL.putPublicUserMetadataForAccountId(null, defaultUsername);
        }
        String decodeAccount = decodeToken(accountId, token_key);
        return userMetadataDAL.putPublicUserMetadataForAccountId(decodeAccount, defaultUsername);
    }

    @GetMapping(value=USER_METADATA_URI)
    public PublicUserMetadataOutput getPublicUserMetadata(@RequestParam final String accountId) {
        if (accountId ==null || accountId.isEmpty()){
            return userMetadataDAL.getPublicUserMetadataForAccountId(null);
        }
        String decodeAccount = decodeToken(accountId, token_key);
        return userMetadataDAL.getPublicUserMetadataForAccountId(decodeAccount);
    }
}
