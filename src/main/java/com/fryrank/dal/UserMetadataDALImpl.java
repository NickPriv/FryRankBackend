package com.fryrank.dal;

import com.fryrank.model.PublicUserMetadata;
import com.fryrank.model.PublicUserMetadataOutput;
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
    public PublicUserMetadataOutput putPublicUserMetadataForAccountId(String accountId, String defaultUserName) {
        final Query query = new Query();
        final Criteria equalToUserIdCriteria = Criteria.where(ACCOUNT_ID_KEY).is(accountId);
        query.addCriteria(equalToUserIdCriteria);
        final List<PublicUserMetadata> publicUserMetadata = mongoTemplate.find(query, PublicUserMetadata.class);
        if(!publicUserMetadata.isEmpty()) {
            if(publicUserMetadata.size() > 1) {
                // TODO(https://github.com/NickPriv/FryRankBackend/issues/76): Add warning logging statement here.
            }
            return new PublicUserMetadataOutput(publicUserMetadata.get(0).getUsername());
        }
        else {
            PublicUserMetadata newUserMetadata = new PublicUserMetadata(
                    accountId,
                    defaultUserName
            );
            return upsertPublicUserMetadata(newUserMetadata);
        }
    }

    @Override
    public PublicUserMetadataOutput getPublicUserMetadataForAccountId(final String accountId) {
        final Query query = new Query();
        final Criteria equalToUserIdCriteria = Criteria.where(ACCOUNT_ID_KEY).is(accountId);
        query.addCriteria(equalToUserIdCriteria);
        final List<PublicUserMetadata> publicUserMetadata = mongoTemplate.find(query, PublicUserMetadata.class);
        if(!publicUserMetadata.isEmpty()) {
            if(publicUserMetadata.size() > 1) {
                // TODO(https://github.com/NickPriv/FryRankBackend/issues/76): Add warning logging statement here.
            }
            return new PublicUserMetadataOutput(publicUserMetadata.get(0).getUsername());
        }
        else {
            return new PublicUserMetadataOutput(null);
        }
    }

    @Override
    public PublicUserMetadataOutput upsertPublicUserMetadata(PublicUserMetadata userMetadata) {
        final Query query = new Query().addCriteria(Criteria.where("_id").is(userMetadata.getAccountId()));
        final FindAndReplaceOptions options = new FindAndReplaceOptions();
        options.upsert();
        options.returnNew();

        PublicUserMetadata mongodbRecord =  mongoTemplate.findAndReplace(query, userMetadata, options);
        if(mongodbRecord == null) {
            return new PublicUserMetadataOutput(userMetadata.getUsername());
        }
        return new PublicUserMetadataOutput(mongodbRecord.getUsername());

    }
}
