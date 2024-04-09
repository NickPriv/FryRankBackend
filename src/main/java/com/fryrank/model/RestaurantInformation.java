package com.fryrank.model;

import lombok.Data;
import lombok.NonNull;
import org.springframework.data.annotation.Id;

@Data
public class RestaurantInformation {
    @Id
    @NonNull
    private final String restaurantId;

    private final Float avgScore;
}
