package com.example.restaurant.di;

import com.example.restaurant.di.details.DetailsComponent;
import com.example.restaurant.di.home.HomeComponent;
import com.example.restaurant.di.network.NetworkModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * This is an application level scoped component.
 */
@Singleton
@Component(modules = {NetworkModule.class, SubcomponentsModule.class})
public interface AppComponent {
    /**
     * This function exposes the HomeComponent Factory out of the graph so consumers
     * can use it to obtain new instances of HomeComponent.
     *
     * @return {@link HomeComponent.Factory}
     */
    HomeComponent.Factory homeComponent();

    /**
     * This function exposes the DetailsComponent Factory out of the graph so consumers
     * can use it to obtain new instances of DetailsComponent.
     *
     * @return {@link DetailsComponent.Factory}
     */
    DetailsComponent.Factory detailsComponent();
}
