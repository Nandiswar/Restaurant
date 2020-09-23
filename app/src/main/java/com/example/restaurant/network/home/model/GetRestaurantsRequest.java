package com.example.restaurant.network.home.model;

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
