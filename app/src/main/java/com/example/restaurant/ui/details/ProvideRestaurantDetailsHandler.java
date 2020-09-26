package com.example.restaurant.ui.details;

import androidx.lifecycle.LiveData;

import com.example.restaurant.ui.details.model.RestaurantDetailWrapper;
import com.example.restaurant.util.Resource;

/**
 * The list of methods to be implemented by any class that would like to provide restaurant
 * details to display to the user.
 * <p>
 * Any class to replace {@link com.example.restaurant.ui.details.viewmodel.RestaurantDetailsViewModel}
 * needs to implement this.
 */
public interface ProvideRestaurantDetailsHandler {
    /**
     * Provides the stream of {@link RestaurantDetailWrapper} resource has the details of
     * the restaurant.
     *
     * @return {@link LiveData} Stream of {@link RestaurantDetailWrapper>} resources.
     */
    LiveData<Resource<RestaurantDetailWrapper>> restaurantDetailsResource();
}
