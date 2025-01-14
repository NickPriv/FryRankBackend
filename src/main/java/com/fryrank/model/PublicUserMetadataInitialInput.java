package com.fryrank.model;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("public-user-metadata")
@Data
public class PublicUserMetadataInitialInput {
    private final String username;
}