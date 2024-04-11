package com.fryrank.model;

import lombok.Data;
import lombok.NonNull;

import java.util.Map;

@Data
public class GetAggregateReviewInformationOutput {
    @NonNull
    private final Map<String, AggregateReviewInformation> restaurantIdToRestaurantInformation;
}
