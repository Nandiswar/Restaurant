package com.example.restaurant.ui;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

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
    public HomeComponent homeComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        homeComponent = ((BaseApplication) getApplication()).appComponent().homeComponent().create();
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Log.d(DEBUG_TAG, "Home component created: " + homeComponent);

        if (savedInstanceState == null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.restaurants_fragment, new DisplayRestaurantsFragment());
            transaction.commit();
        }
    }
}