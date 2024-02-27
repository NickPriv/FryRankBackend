package com.fryrank.model;

import lombok.Data;
import lombok.NonNull;
import org.springframework.data.annotation.Id;

@Data
public class RestaurantAvgScore {

    @Id
    @NonNull
    private final String restaurantId;

    @NonNull
    private final Float avgScore;

}
