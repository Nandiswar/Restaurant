package com.example.restaurant.di.network.api.details;

import com.example.restaurant.di.network.api.details.model.RestaurantDetail;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Provides the list of api's useful for {@link com.example.restaurant.ui.details.fragment.RestaurantDetailFragment}.
 */
public interface DetailsApi {
    /**
     * Fetches the {@link RestaurantDetail} response for the given restaurant id.
     *
     * @param restaurantID the ID of the restaurant used to fetch complete details.
     * @return {@link Flowable} stream of {@link Response} objects.
     */
    @GET("/v2/restaurant/{restaurant_id}")
    Observable<Response<RestaurantDetail>> getRestaurantDetails(@Path("restaurant_id") int restaurantID);
}
