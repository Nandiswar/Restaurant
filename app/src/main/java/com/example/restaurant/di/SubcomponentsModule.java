package com.example.restaurant.di;

import com.example.restaurant.di.details.DetailsComponent;
import com.example.restaurant.di.home.HomeComponent;

import dagger.Module;
/**
 * The "subcomponents" attribute in the @Module annotation tells Dagger what
 * Subcomponents are children of the Component this module is included in.
 */
@Module(subcomponents = {HomeComponent.class, DetailsComponent.class})
public class SubcomponentsModule {
}
