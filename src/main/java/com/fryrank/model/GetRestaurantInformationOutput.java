package com.fryrank.model;

import lombok.Data;
import lombok.NonNull;

@Data
public class GetRestaurantInformationOutput {
    @NonNull
    private final HashMap<String, RestaurantInformation> restaurantIdToRestaurantInformation;
}
