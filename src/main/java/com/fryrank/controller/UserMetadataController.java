package com.fryrank.controller;

import com.fryrank.dal.UserMetadataDAL;
import com.fryrank.model.PublicUserMetadata;
import com.fryrank.model.PublicUserMetadataOutput;
import com.fryrank.validator.UserMetadataValidator;
import com.fryrank.validator.ValidatorException;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.fryrank.Constants.API_PATH;
import static com.fryrank.Constants.GENERIC_VALIDATOR_ERROR_MESSAGE;
import static com.fryrank.Constants.USER_METADATA_VALIDATOR_ERRORS_OBJECT_NAME;

@RestController
public class UserMetadataController {
    private static final String USER_METADATA_URI = API_PATH + "/userMetadata";

    @Autowired
    private UserMetadataDAL userMetadataDAL;

    @PostMapping(value=USER_METADATA_URI)
    public PublicUserMetadataOutput upsertPublicUserMetadata(@RequestBody @NonNull final PublicUserMetadata userMetadata) throws ValidatorException {
        BindingResult bindingResult = new BeanPropertyBindingResult(userMetadata, USER_METADATA_VALIDATOR_ERRORS_OBJECT_NAME);
        UserMetadataValidator validator = new UserMetadataValidator();
        validator.validate(userMetadata, bindingResult);

        if(bindingResult.hasErrors()) {
            throw new ValidatorException(bindingResult.getAllErrors(), GENERIC_VALIDATOR_ERROR_MESSAGE);
        }
        return userMetadataDAL.upsertPublicUserMetadata(userMetadata);
    }

    @PutMapping(value=USER_METADATA_URI)
    public PublicUserMetadataOutput putPublicUserMetadata(@RequestParam final String accountId, @RequestParam @NonNull final String defaultUsername) {
        return userMetadataDAL.putPublicUserMetadataForAccountId(accountId, defaultUsername);
    }

    @GetMapping(value=USER_METADATA_URI)
    public PublicUserMetadataOutput getPublicUserMetadata(@RequestParam final String accountId) {
        return userMetadataDAL.getPublicUserMetadataForAccountId(accountId);
    }
}
