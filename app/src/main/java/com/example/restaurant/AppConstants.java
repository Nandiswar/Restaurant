package com.example.restaurant;

import com.example.restaurant.di.network.api.home.model.Location;

/**
 * Constants referenced in the application.
 */
public class AppConstants {
    public static final String DEBUG_TAG = "RestaurantLogs";
    // Location co-ordinates of Doordash HQ which is used to fetch nearby restaurants
    public static final Location DOOR_DASH_HQ = new Location("37.422740", "-122.139956");
}
