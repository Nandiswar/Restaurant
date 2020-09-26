package com.example.restaurant.ui.home;

import androidx.lifecycle.LiveData;

import com.example.restaurant.ui.home.model.RestaurantWrapper;
import com.example.restaurant.util.Resource;

import java.util.List;

/**
 * The list of methods to be implemented by any class that would like to provide list of restaurants
 * to Activity or Fragment to display to the user.
 * <p>
 * that is, to replace {@link com.example.restaurant.ui.home.viewmodel.RestaurantsViewModel}.
 */
public interface ProvideRestaurantsHandler {
    /**
     * Provides the stream of {@link List<RestaurantWrapper>} resources that can be used by consumers
     * to display to users.
     *
     * @return {@link LiveData} Stream of {@link List<RestaurantWrapper>} resources.
     */
    LiveData<Resource<List<RestaurantWrapper>>> restaurantsResource();
}
