package com.fryrank.model;

import lombok.Data;
import lombok.NonNull;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("user-metadata")
@Data
public class UserMetadataOutput {
    private final String username;
}
