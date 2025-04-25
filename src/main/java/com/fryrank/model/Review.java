package com.fryrank.model;

import lombok.Data;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

@Document("review")
@Data
public class Review {
    @Id
    private final String reviewId;

    @NonNull
    private final String restaurantId;

    @NonNull
    private final Double score;

    @NonNull
    private final String title;

    @NonNull
    private final String body;

    private final String isoDateTime;

    private String accountId;

    private final PublicUserMetadata userMetadata;

    public Review(String reviewId, String restaurantId, Double score, String title,
                  String body, String isoDateTime, String accountId, PublicUserMetadata userMetadata) {

        Objects.requireNonNull(restaurantId, "RestaurantId cannot be null");
        Objects.requireNonNull(score, "Score cannot be null");
        Objects.requireNonNull(title, "Title cannot be null");
        Objects.requireNonNull(body, "Body cannot be null");

        this.reviewId = reviewId;
        this.restaurantId = restaurantId;
        this.score = score;
        this.title = title;
        this.body = body;
        this.isoDateTime = isoDateTime;
        this.accountId = accountId;
        this.userMetadata = userMetadata;
    }
}
