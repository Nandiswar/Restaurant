package com.example.restaurant.di.network.api.home.model;

/**
 * This class contains the properties used for making the api call to fetch restaurants.
 */
public class GetRestaurantsRequest {

    private Location location;

    public GetRestaurantsRequest(Location location) {
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
