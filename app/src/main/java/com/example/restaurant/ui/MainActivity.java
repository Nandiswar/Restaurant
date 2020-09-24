package com.example.restaurant.ui;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.restaurant.BaseApplication;
import com.example.restaurant.R;
import com.example.restaurant.di.home.HomeComponent;

import static com.example.restaurant.AppConstants.DEBUG_TAG;

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