package com.example.restaurant.ui.details.model;

import androidx.annotation.Nullable;

import com.google.auto.value.AutoValue;

/**
 * This class abstracts the information from {@link com.example.restaurant.di.network.api.details.model.RestaurantDetail}
 * response providing only the properties necessary for driving user experiences
 * in restaurant details screen.
 */
@AutoValue
public abstract class RestaurantDetailWrapper {

    @Nullable
    public abstract String name();

    @Nullable
    public abstract String bannerURL();

    @Nullable
    public abstract Double rating();

    @Nullable
    public abstract String address();

    @Nullable
    public abstract String description();

    @Nullable
    public abstract String status();

    @Nullable
    public abstract String tags();

    public static Builder builder() {
        return new AutoValue_RestaurantDetailWrapper.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder bannerURL(@Nullable String bannerURL);

        public abstract Builder name(@Nullable String name);

        public abstract Builder rating(@Nullable Double rating);

        public abstract Builder address(@Nullable String address);

        public abstract Builder description(@Nullable String description);

        public abstract Builder status(@Nullable String status);

        public abstract Builder tags(@Nullable String tags);

        public abstract RestaurantDetailWrapper build();
    }
}
