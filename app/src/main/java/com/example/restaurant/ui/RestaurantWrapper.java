package com.example.restaurant.ui;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class RestaurantWrapper {
    public abstract String name();

    public abstract String imgURL();

    public abstract String description();

    public abstract String status();

    static Builder builder() {
        return new AutoValue_RestaurantWrapper.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder name(String name);

        public abstract Builder imgURL(String imgURL);

        public abstract Builder description(String description);

        public abstract Builder status(String status);

        public abstract RestaurantWrapper build();
    }
}
