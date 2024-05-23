package com.fryrank.model;

import lombok.Data;
import lombok.NonNull;

import java.util.List;

@Data
public class GetAllReviewsOutput {

    @NonNull
    private final List<Review> reviews;
}
