package com.example.restaurant.di.home;

import com.example.restaurant.ui.home.HomeActivity;
import com.example.restaurant.ui.home.fragment.RestaurantsFragment;

import dagger.Subcomponent;

/**
 * {@link HomeComponent} is a Dagger Subcomponent responsible for providing the dependencies for the
 * Activities and Fragments being inject. This component is confined to {@link HomeScope}.
 */
@HomeScope
@Subcomponent(modules = HomeModule.class)
public interface HomeComponent {

    /**
     * Factory that is used to create instances of this subcomponent.
     */
    @Subcomponent.Factory
    interface Factory {
        HomeComponent create();
    }

    /**
     * This tells Dagger that HomeActivity requests injection from HomeComponent
     * so that this subcomponent graph needs to satisfy all the dependencies of the
     * fields that HomeActivity is injecting
     */
    void inject(HomeActivity mainActivity);

    /**
     * This tells Dagger that RestaurantsFragment requests injection from HomeComponent
     * so that this subcomponent graph needs to satisfy all the dependencies of the
     * fields that RestaurantsFragment is injecting
     */
    void inject(RestaurantsFragment restaurantsFragment);
}
