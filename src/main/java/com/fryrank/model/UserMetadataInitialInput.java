package com.fryrank.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("user-metadata")
@Data
public class UserMetadataInitialInput {
    private final String username;
}
