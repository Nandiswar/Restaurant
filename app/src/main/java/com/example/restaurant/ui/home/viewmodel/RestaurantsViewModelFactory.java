package com.example.restaurant.ui.home.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.restaurant.di.network.api.home.HomeApi;

/**
 * {@link RestaurantsViewModelFactory} is used to provide dependencies for {@link RestaurantsViewModel}.
 */
public class RestaurantsViewModelFactory implements ViewModelProvider.Factory {
    private final HomeApi homeApi;

    public RestaurantsViewModelFactory(HomeApi homeApi) {
        this.homeApi = homeApi;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new RestaurantsViewModel(homeApi);
    }
}
