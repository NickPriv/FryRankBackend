package com.fryrank.controller;

import com.fryrank.dal.RestaurantInformationDAL;
import com.fryrank.model.GetRestaurantInformationOutput;
import com.fryrank.model.RestaurantAttributes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.fryrank.Constants.API_PATH;

@RestController
public class RestaurantInformationController {
    private static final String RESTAURANT_INFORMATION_URI = API_PATH + "/restaurants";

    @Autowired
    private RestaurantInformationDAL restaurantInformationDAL;

    @GetMapping(value = RESTAURANT_INFORMATION_URI)
    public GetRestaurantInformationOutput getRestaurantInformationForRestaurants(
            @RequestParam(name = "ids") String ids,
            @RequestParam(name = "rating", defaultValue = "false") boolean includeRating
    ) {
        List<String> parsedIDs = Arrays.asList(ids.split(","));
        RestaurantAttributes attributes = new RestaurantAttributes(includeRating);
        return restaurantInformationDAL.getRestaurantInformationForRestaurants(parsedIDs, attributes);
    }

}
