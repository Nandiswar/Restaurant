package com.example.restaurant.di.home;

import androidx.lifecycle.ViewModelProvider;

import com.example.restaurant.di.network.api.home.HomeApi;
import com.example.restaurant.ui.home.viewmodel.RestaurantsViewModelFactory;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Provide the dependencies for the upstream component that this module is linked to, in this case,
 * {@link HomeComponent}
 */
@Module
public class HomeModule {
    /**
     * Provide {@link HomeApi} obj to get the api's to be invoked from Home Screen.
     *
     * @param retrofit {@link Retrofit}.
     * @return {@link HomeApi}.
     */
    @HomeScope
    @Provides
    HomeApi provideHomeApi(Retrofit retrofit) {
        return retrofit.create(HomeApi.class);
    }

    /**
     * Provide the {@link ViewModelProvider.Factory} that has the dependencies to create
     * {@link com.example.restaurant.ui.home.viewmodel.RestaurantsViewModel}.
     *
     * @param homeApi {@link HomeApi}.
     * @return {@link ViewModelProvider.Factory}.
     */
    @HomeScope
    @Provides
    RestaurantsViewModelFactory provideFactory(HomeApi homeApi) {
        return new RestaurantsViewModelFactory(homeApi);
    }
}
