package com.example.restaurant.network.home;

import com.example.restaurant.network.home.model.Restaurant;

import java.util.List;
import java.util.Map;

import io.reactivex.rxjava3.core.Flowable;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Tis interface provides the api's useful for home screen.
 */
public interface HomeApi {
    /**
     * Api to provide list of restaurants.
     * TODO add response object instead of generic {@link Call} return type.
     *
     * @param queryParams query params to be included in the api request.
     * @return {@link Call} list of restaurants if api is success.
     */
    @GET("/v2/restaurant/")
    Call<List<Restaurant>> getRestaurants(@QueryMap Map<String, String> queryParams);

    @GET("/v2/restaurant/")
    Flowable<Response<List<Restaurant>>> getRestaurantsV2(@QueryMap Map<String, String> queryParams);
}
