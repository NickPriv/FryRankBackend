package com.fryrank.dal;

import com.fryrank.model.GetAllReviewsOutput;
import com.fryrank.model.GetUserMetadataOutput;
import com.fryrank.model.UserMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndReplaceOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;


import java.util.List;

import static com.fryrank.Constants.ACCOUNT_ID_KEY;

@Repository
public class UserMetadataDALImpl implements UserMetadataDAL {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public GetUserMetadataOutput getUserMetadataForAccountId(String accountId) {
        final Query query = new Query();
        final Criteria equalToUserIdCriteria = Criteria.where(ACCOUNT_ID_KEY).is(accountId);
        query.addCriteria(equalToUserIdCriteria);
        final List<UserMetadata> userMetadata = mongoTemplate.find(query, UserMetadata.class);

        return new GetUserMetadataOutput(userMetadata);
    }

    @Override
    public UserMetadata upsertUserMetadata(UserMetadata userMetadata) {
        final Query query = new Query().addCriteria(Criteria.where("_id").is(userMetadata.getAccountId()));
        final FindAndReplaceOptions options = new FindAndReplaceOptions();
        options.upsert();

        return mongoTemplate.findAndReplace(query, userMetadata, options);
    }
}
