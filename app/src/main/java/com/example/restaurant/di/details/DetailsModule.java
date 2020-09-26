package com.example.restaurant.di.details;

import androidx.lifecycle.ViewModelProvider;

import com.example.restaurant.di.network.api.details.DetailsApi;
import com.example.restaurant.ui.details.viewmodel.RestaurantDetailsViewModelFactory;
import com.example.restaurant.ui.details.RestaurantStream;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Provide the dependencies for the upstream component that this module is linked to, in this case,
 * {@link DetailsComponent}
 */
@Module
public class DetailsModule {
    /**
     * Provide {@link DetailsApi} obj to get the api's to be invoked from Restaurant Details Screen.
     *
     * @param retrofit {@link Retrofit}.
     * @return {@link DetailsApi}.
     */
    @DetailsScope
    @Provides
    DetailsApi providesDetailApi(Retrofit retrofit) {
        return retrofit.create(DetailsApi.class);
    }

    /**
     * Provides {@link RestaurantStream} with details on selected restaurant.
     *
     * @return {@link RestaurantStream}.
     */
    @DetailsScope
    @Provides
    RestaurantStream providesRestaurantStream() {
        return new RestaurantStream();
    }

    /**
     * Provide the {@link ViewModelProvider.Factory} that has the dependencies to create
     * {@link com.example.restaurant.ui.details.viewmodel.RestaurantDetailsViewModel}.
     *
     * @param detailsApi       {@link DetailsApi}.
     * @param restaurantStream {@link RestaurantStream}.
     * @return {@link ViewModelProvider.Factory}.
     */
    @DetailsScope
    @Provides
    RestaurantDetailsViewModelFactory provideFactory(DetailsApi detailsApi, RestaurantStream restaurantStream) {
        return new RestaurantDetailsViewModelFactory(detailsApi, restaurantStream);
    }
}
