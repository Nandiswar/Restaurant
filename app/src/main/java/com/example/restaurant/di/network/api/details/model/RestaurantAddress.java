package com.example.restaurant.di.network.api.details.model;

import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class RestaurantAddress implements Serializable {
    @SerializedName("printable_address")
    @Nullable
    String address;

    @Nullable
    public String getAddress() {
        return address;
    }

    public void setAddress(@Nullable String address) {
        this.address = address;
    }
}
