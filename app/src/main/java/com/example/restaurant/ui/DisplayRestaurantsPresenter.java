package com.example.restaurant.ui;

import java.util.List;

public interface DisplayRestaurantsPresenter {
    void handleLoadingState();

    void displayRestaurants(List<RestaurantWrapper> restaurantWrapperList);
}
