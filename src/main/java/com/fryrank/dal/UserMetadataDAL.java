package com.fryrank.dal;

import com.fryrank.model.UserMetadata;

public interface UserMetadataDAL {
    UserMetadata getUserMetadataForAccountId(final String accountId);

    UserMetadata upsertUserMetadata(final UserMetadata userMetadata);
}
