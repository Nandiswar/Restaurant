package com.example.restaurant.di;

import com.example.restaurant.di.home.HomeComponent;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {NetworkModule.class, SubcomponentsModule.class})
public interface AppComponent {
    /**
     * Use this to obtain new instances of HomeComponent.
     *
     * @return {@link HomeComponent}.
     */
    HomeComponent.Factory homeComponent();
}
