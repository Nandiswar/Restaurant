package com.example.restaurant.di.home;

import com.example.restaurant.ui.MainActivity;

import dagger.Subcomponent;

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
     * This subcomponent graph needs to satisfy all the dependencies of the
     * fields that MainActivity is injecting.
     */
    void inject(MainActivity mainActivity);
}
