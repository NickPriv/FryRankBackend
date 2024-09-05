package com.fryrank.dal;

import com.fryrank.model.UserMetadata;
import com.fryrank.model.UserMetadataOutput;
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
    public UserMetadataOutput getUserMetadataForAccountId(String accountId) {
        final Query query = new Query();
        final Criteria equalToUserIdCriteria = Criteria.where(ACCOUNT_ID_KEY).is(accountId);
        query.addCriteria(equalToUserIdCriteria);
        final List<UserMetadata> userMetadata = mongoTemplate.find(query, UserMetadata.class);
        if(!userMetadata.isEmpty()) {
            if(userMetadata.size() > 1) {
                // TODO(https://github.com/NickPriv/FryRankBackend/issues/76): Add warning logging statement here.
            }
            return new UserMetadataOutput(userMetadata.get(0).getUsername());
        }
        else {
            return new UserMetadataOutput(null);
        }
    }

    @Override
    public UserMetadataOutput upsertUserMetadata(UserMetadata userMetadata) {
        final Query query = new Query().addCriteria(Criteria.where("_id").is(userMetadata.getAccountId()));
        final FindAndReplaceOptions options = new FindAndReplaceOptions();
        options.upsert();

        UserMetadata mongodbRecord =  mongoTemplate.findAndReplace(query, userMetadata, options);
        if(mongodbRecord == null) {
            return new UserMetadataOutput(userMetadata.getUsername());
        }
        return new UserMetadataOutput(mongodbRecord.getUsername());

    }
}
