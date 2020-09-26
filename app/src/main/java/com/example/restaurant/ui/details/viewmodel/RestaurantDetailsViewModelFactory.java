package com.example.restaurant.ui.details.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.restaurant.di.network.api.details.DetailsApi;

/**
 * This class is used to provide dependencies for {@link RestaurantDetailsViewModel}.
 */
public class RestaurantDetailsViewModelFactory implements ViewModelProvider.Factory {
    private final DetailsApi detailsApi;
    private final RestaurantStream restaurantStream;

    public RestaurantDetailsViewModelFactory(DetailsApi detailsApi, RestaurantStream restaurantStream) {
        this.detailsApi = detailsApi;
        this.restaurantStream = restaurantStream;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new RestaurantDetailsViewModel(detailsApi, restaurantStream);
    }
}
