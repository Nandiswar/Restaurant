package com.example.restaurant.ui.home.model;


import androidx.annotation.Nullable;

import com.google.auto.value.AutoValue;

import java.io.Serializable;

/**
 * Model class that abstracts the actual {@link com.example.restaurant.di.network.api.home.model.Restaurant} class
 * and provides only the properties that are relevant to user experience.
 */
@AutoValue
public abstract class RestaurantWrapper implements Serializable {
    public abstract int id();

    @Nullable
    public abstract String name();

    @Nullable
    public abstract String imgURL();

    @Nullable
    public abstract String description();

    @Nullable
    public abstract String status();

    public static Builder builder() {
        return new AutoValue_RestaurantWrapper.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder id(int id);

        public abstract Builder name(@Nullable String name);

        public abstract Builder imgURL(@Nullable String imgURL);

        public abstract Builder description(@Nullable String description);

        public abstract Builder status(@Nullable String status);

        public abstract RestaurantWrapper build();
    }

}
