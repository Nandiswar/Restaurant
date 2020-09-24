package com.example.restaurant.ui;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import com.example.restaurant.network.home.HomeApi;
import com.example.restaurant.network.home.model.GetRestaurantsRequest;
import com.example.restaurant.network.home.model.Restaurant;
import com.example.restaurant.util.Resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.annotations.Nullable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.functions.Function;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.restaurant.AppConstants.DEBUG_TAG;
import static com.example.restaurant.AppConstants.DOOR_DASH_HQ;

public class MainViewModel extends ViewModel {
    private final HomeApi homeApi;

    private MediatorLiveData<Resource<List<RestaurantWrapper>>> restaurantsLiveData;

    public MainViewModel(HomeApi homeApi) {
        this.homeApi = homeApi;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }

    public LiveData<Resource<List<RestaurantWrapper>>> fetchRestaurantsV2() {
        if (restaurantsLiveData == null) {
            restaurantsLiveData = new MediatorLiveData<>();
            fetchRestaurants();
        }
        return restaurantsLiveData;
    }

    public LiveData<Resource<List<RestaurantWrapper>>> fetchRestaurants() {
        // loading state
        restaurantsLiveData.postValue(Resource.loading(null));

        GetRestaurantsRequest req = new GetRestaurantsRequest(DOOR_DASH_HQ);
        Map<String, String> queryParams = getDefaultQueryParams(req);

        homeApi.getRestaurantsV2(queryParams)
                .map(resp -> {
                  restaurantsLiveData.postValue(handleRespoonse(resp));
                    return null;
                })
                .subscribe();

        return restaurantsLiveData;

//        homeApi.getRestaurants(queryParams).enqueue(new Callback<List<Restaurant>>() {
//            @Override
//            public void onResponse(Call<List<Restaurant>> call, Response<List<Restaurant>> response) {
//                Log.d(DEBUG_TAG, "Fetch restaurants success ");
//                if (response.code() == 200 && response.body() != null) {
//                    List<RestaurantWrapper> restaurantWrapperList = transformedResponse(response.body());
//                    restaurantsLiveData.postValue(Resource.success(restaurantWrapperList));
//                    return;
//                }
//
//                restaurantsLiveData.postValue(Resource.success(null));
//            }
//
//            @Override
//            public void onFailure(Call<List<Restaurant>> call, Throwable t) {
//                Log.d(DEBUG_TAG, "Error fetching restaurants");
//                restaurantsLiveData.postValue(Resource.error("Error fetching restaurants...", null));
//            }
//        });
    }

    private Resource<List<RestaurantWrapper>> handleRespoonse(Response<List<Restaurant>> response) {
        Log.d(DEBUG_TAG, "Fetch restaurants success ");
        if (response.code() == 200 && response.body() != null) {
            List<RestaurantWrapper> restaurantWrapperList = transformedResponse(response.body());
            return Resource.success(restaurantWrapperList);
        }

        return Resource.success(null);
    }

    private void handleRespoonse(List<Restaurant> restaurants) {
        Log.d(DEBUG_TAG, "Fetch restaurants success ");
//        if (response.code() == 200 && response.body() != null) {
//            List<RestaurantWrapper> restaurantWrapperList = transformedResponse(response.body());
//            restaurantsLiveData.postValue(Resource.success(restaurantWrapperList));
//            return;
//        }

        restaurantsLiveData.postValue(Resource.success(null));
    }

    private List<Restaurant> handleError(Throwable throwable) {
        Log.d(DEBUG_TAG, "Error fetching restaurants");
//        restaurantsLiveData.postValue(Resource.error("Error fetching restaurants...", null));
        return null;
    }

    private List<RestaurantWrapper> transformedResponse(@Nullable List<Restaurant> response) {
        List<RestaurantWrapper> restaurantWrapperList = new ArrayList<>();
        for (Restaurant restaurant : response) {
            restaurantWrapperList.add(getRestaurantWrapper(restaurant));
        }
        return restaurantWrapperList;
    }

    private RestaurantWrapper getRestaurantWrapper(Restaurant restaurant) {
        return RestaurantWrapper.builder()
                .name(restaurant.getName())
                .description(restaurant.getDescription())
                .imgURL(restaurant.getCoverImgUrl())
                .status(restaurant.getStatus())
                .build();
    }

    private Map<String, String> getDefaultQueryParams(GetRestaurantsRequest req) {
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("lat", req.getLocation().getLat());
        queryParams.put("lng", req.getLocation().getLng());
        return queryParams;
    }

}
