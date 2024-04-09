package com.fryrank.dal;

import com.fryrank.model.RestaurantAttributes;
import com.fryrank.model.GetRestaurantInformationOutput;

import java.util.List;

public interface RestaurantInformationDAL {
    GetRestaurantInformationOutput getRestaurantInformationForRestaurants(final List<String> restaurantIds, final RestaurantAttributes restaurantAttributes);
}
