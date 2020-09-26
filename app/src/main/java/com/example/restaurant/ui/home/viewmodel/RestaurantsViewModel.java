package com.example.restaurant.ui.home.viewmodel;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import com.example.restaurant.di.network.api.home.HomeApi;
import com.example.restaurant.di.network.api.home.model.GetRestaurantsRequest;
import com.example.restaurant.di.network.api.home.model.Restaurant;
import com.example.restaurant.ui.home.ProvideRestaurantsHandler;
import com.example.restaurant.ui.home.model.RestaurantWrapper;
import com.example.restaurant.util.Resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

import static com.example.restaurant.AppConstants.DOOR_DASH_HQ;

public class RestaurantsViewModel extends ViewModel implements ProvideRestaurantsHandler {
    private HomeApi homeApi;
    private MediatorLiveData<Resource<List<RestaurantWrapper>>> restaurantsLiveData;

    @Nullable
    private Disposable subs;

    public RestaurantsViewModel(HomeApi homeApi) {
        this.homeApi = homeApi;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (subs != null && !subs.isDisposed()) {
            subs.dispose();
        }
    }

    @Override
    public LiveData<Resource<List<RestaurantWrapper>>> restaurantsResource() {
        if (restaurantsLiveData == null) {
            restaurantsLiveData = new MediatorLiveData<>();
            restaurantsLiveData.postValue(Resource.loading(null));

            fetchRestaurants();
        }
        return restaurantsLiveData;
    }

    /**
     * Fetch restaurants associated with the location in {@link GetRestaurantsRequest}
     * using {@link HomeApi#getRestaurants(Map)}.
     */
    private void fetchRestaurants() {
        GetRestaurantsRequest req = new GetRestaurantsRequest(DOOR_DASH_HQ);
        Map<String, String> queryParams = getDefaultQueryParams(req);

        subs = homeApi.getRestaurants(queryParams)
                .map(this::handleResponse)
                .subscribeOn(Schedulers.io())
                .subscribe(data -> restaurantsLiveData.postValue(data));
    }

    private Resource<List<RestaurantWrapper>> handleResponse(@Nullable Response<List<Restaurant>> response) {
        if (response != null && response.code() == 200 && response.body() != null) {
            List<RestaurantWrapper> restaurantWrapperList = transformedResponse(response.body());
            return Resource.success(restaurantWrapperList);
        }

        return Resource.error("error in fetching data", null);
    }

    private List<RestaurantWrapper> transformedResponse(List<Restaurant> response) {
        List<RestaurantWrapper> restaurantWrapperList = new ArrayList<>();
        for (Restaurant restaurant : response) {
            restaurantWrapperList.add(getRestaurantWrapper(restaurant));
        }
        return restaurantWrapperList;
    }

    private RestaurantWrapper getRestaurantWrapper(Restaurant restaurant) {
        return RestaurantWrapper.builder()
                .id(restaurant.getId())
                .name(restaurant.getName())
                .description(restaurant.getDescription())
                .imgURL(restaurant.getCoverImgUrl())
                .status(restaurant.getStatus())
                .build();
    }

    /**
     * Create {@link Map} of queryParams using properties in the {@link GetRestaurantsRequest}.
     *
     * @param req {@link GetRestaurantsRequest}
     * @return {@link Map} of queryParams used in {@link HomeApi#getRestaurants(Map)}
     */
    private Map<String, String> getDefaultQueryParams(GetRestaurantsRequest req) {
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("lat", req.getLocation().getLat());
        queryParams.put("lng", req.getLocation().getLng());
        return queryParams;
    }
}
