package com.fryrank.dal;

import com.fryrank.model.GetUserMetadataOutput;
import com.fryrank.model.UserMetadata;

public interface UserMetadataDAL {
    GetUserMetadataOutput getUserMetadataForAccountId(final String accountId);

    UserMetadata upsertUserMetadata(final UserMetadata userMetadata);
}
