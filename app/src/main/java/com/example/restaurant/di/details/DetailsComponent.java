package com.example.restaurant.di.details;

import com.example.restaurant.ui.details.RestaurantDetailsActivity;
import com.example.restaurant.ui.details.fragment.RestaurantDetailFragment;

import dagger.Subcomponent;

/**
 * {@link DetailsComponent} is a Dagger Subcomponent responsible for providing the dependencies for
 * the Activities and Fragments being inject. This component is confined to {@link DetailsScope}.
 */
@DetailsScope
@Subcomponent(modules = DetailsModule.class)
public interface DetailsComponent {
    /**
     * Factory that is used to create instances of this subcomponent.
     */
    @Subcomponent.Factory
    interface Factory {
        DetailsComponent create();
    }

    /**
     * This tells Dagger that RestaurantDetailsActivity requests injection from HomeComponent
     * so that this subcomponent graph needs to satisfy all the dependencies of the
     * fields that RestaurantDetailsActivity is injecting
     */
    void inject(RestaurantDetailsActivity restaurantDetailsActivity);

    /**
     * This tells Dagger that RestaurantDetailFragment requests injection from HomeComponent
     * so that this subcomponent graph needs to satisfy all the dependencies of the
     * fields that RestaurantDetailFragment is injecting
     */
    void inject(RestaurantDetailFragment restaurantDetailFragment);
}
