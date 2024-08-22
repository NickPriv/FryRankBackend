package com.fryrank.dal;

import com.fryrank.model.UserMetadata;
import com.fryrank.model.UserMetadataOutput;

public interface UserMetadataDAL {
    UserMetadataOutput getUserMetadataForAccountId(final String accountId);

    UserMetadataOutput upsertUserMetadata(final UserMetadata userMetadata);
}
