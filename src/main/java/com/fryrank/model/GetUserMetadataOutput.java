package com.fryrank.model;

import lombok.Data;
import lombok.NonNull;

import java.util.List;

@Data
public class GetUserMetadataOutput {
    @NonNull
    private final List<UserMetadata> userMetadata;
}
