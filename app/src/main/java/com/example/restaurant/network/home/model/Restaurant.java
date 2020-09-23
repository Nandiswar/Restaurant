package com.example.restaurant.network.home.model;

public class Restaurant {
    int id;
    boolean is_time_surging;
    String name;
    String description;
    String status;
    int number_of_ratings;
    String[] tags;
    String cover_img_url;
    String header_img_url;
    long delivery_fee;

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

    public String getCover_img_url() {
        return cover_img_url;
    }

    public void setCover_img_url(String cover_img_url) {
        this.cover_img_url = cover_img_url;
    }

    public String getHeader_img_url() {
        return header_img_url;
    }

    public void setHeader_img_url(String header_img_url) {
        this.header_img_url = header_img_url;
    }

    public long getDelivery_fee() {
        return delivery_fee;
    }

    public void setDelivery_fee(long delivery_fee) {
        this.delivery_fee = delivery_fee;
    }
}
