package com.example.restaurant.ui;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.restaurant.BaseApplication;
import com.example.restaurant.R;
import com.example.restaurant.di.home.HomeComponent;
import com.example.restaurant.network.home.HomeApi;
import com.example.restaurant.network.home.model.GetRestaurantsRequest;
import com.example.restaurant.network.home.model.Restaurant;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.restaurant.AppConstants.DEBUG_TAG;
import static com.example.restaurant.AppConstants.DOOR_DASH_HQ;

public class MainActivity extends AppCompatActivity {

    @Inject
    HomeApi homeApi;

    HomeComponent homeComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        homeComponent = ((BaseApplication) getApplication()).appComponent().homeComponent().create();
        homeComponent.inject(this);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Log.d(DEBUG_TAG, "Home component created: " + homeComponent);

        GetRestaurantsRequest req = new GetRestaurantsRequest(DOOR_DASH_HQ);
        Map<String, String> queryParams = getDefaultQueryParams(req);
        homeApi.getRestaurants(queryParams).enqueue(new Callback<List<Restaurant>>() {
            @Override
            public void onResponse(Call<List<Restaurant>> call, Response<List<Restaurant>> response) {
                Log.d(DEBUG_TAG, "Received restaurants: " + response);
            }

            @Override
            public void onFailure(Call<List<Restaurant>> call, Throwable t) {
                Log.d(DEBUG_TAG, "Error fetching restaurants");
            }
        });
    }

    private Map<String, String> getDefaultQueryParams(GetRestaurantsRequest req) {
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("lat", req.getLocation().getLat());
        queryParams.put("lng", req.getLocation().getLng());
        return queryParams;
    }
}