package com.example.restaurant.di.network.api.home;

import com.example.restaurant.di.network.api.home.model.Restaurant;

import java.util.List;
import java.util.Map;

import io.reactivex.Flowable;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Provides the list of api's useful to home screen.
 */
public interface HomeApi {
    /**
     * Fetches the {@link List<Restaurant>} response for the given set of query params.
     *
     * @param queryParams {@link Map} of request params.
     * @return {@link Flowable} stream of {@link Response} objects.
     */
    @GET("/v2/restaurant/")
    Flowable<Response<List<Restaurant>>> getRestaurants(@QueryMap Map<String, String> queryParams);
}
