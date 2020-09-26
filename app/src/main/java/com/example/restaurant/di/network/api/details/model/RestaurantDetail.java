package com.example.restaurant.di.network.api.details.model;

import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class RestaurantDetail implements Serializable {
    int id;
    boolean is_time_surging;
    String name;
    String description;
    String status;
    @SerializedName("average_rating")
    Double ratings;
    String[] tags;
    @SerializedName("cover_img_url")
    String coverImgUrl;
    @SerializedName("header_img_url")
    String headerImgUrl;
    long delivery_fee;
    @SerializedName("status_type")
    String statusType;
    @Nullable RestaurantAddress address;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isIs_time_surging() {
        return is_time_surging;
    }

    public void setIs_time_surging(boolean is_time_surging) {
        this.is_time_surging = is_time_surging;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getRatings() {
        return ratings;
    }

    public void setRatings(Double ratings) {
        this.ratings = ratings;
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    public String getCoverImgUrl() {
        return coverImgUrl;
    }

    public void setCoverImgUrl(String coverImgUrl) {
        this.coverImgUrl = coverImgUrl;
    }

    public String getHeaderImgUrl() {
        return headerImgUrl;
    }

    public void setHeaderImgUrl(String headerImgUrl) {
        this.headerImgUrl = headerImgUrl;
    }

    public long getDelivery_fee() {
        return delivery_fee;
    }

    public void setDelivery_fee(long delivery_fee) {
        this.delivery_fee = delivery_fee;
    }

    public String getStatusType() {
        return statusType;
    }

    public void setStatusType(String statusType) {
        this.statusType = statusType;
    }

    public RestaurantAddress getAddress() {
        return address;
    }

    public void setAddress(RestaurantAddress address) {
        this.address = address;
    }
}
