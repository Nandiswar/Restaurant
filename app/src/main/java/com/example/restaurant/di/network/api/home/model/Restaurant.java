package com.example.restaurant.di.network.api.home.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Restaurant implements Serializable {
    int id;
    boolean is_time_surging;
    String name;
    String description;
    String status;
    int number_of_ratings;
    String[] tags;
    @SerializedName("cover_img_url")
    String coverImgUrl;
    long delivery_fee;
    String status_type;

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

    public int getNumber_of_ratings() {
        return number_of_ratings;
    }

    public void setNumber_of_ratings(int number_of_ratings) {
        this.number_of_ratings = number_of_ratings;
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

    public long getDelivery_fee() {
        return delivery_fee;
    }

    public void setDelivery_fee(long delivery_fee) {
        this.delivery_fee = delivery_fee;
    }
}
