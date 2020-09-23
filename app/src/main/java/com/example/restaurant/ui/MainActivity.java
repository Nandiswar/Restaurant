package com.example.restaurant.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.restaurant.AppConstants;
import com.example.restaurant.BaseApplication;
import com.example.restaurant.R;
import com.example.restaurant.di.home.HomeComponent;

public class MainActivity extends AppCompatActivity {

    HomeComponent homeComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        homeComponent = ((BaseApplication) getApplication()).appComponent().homeComponent().create();
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Log.d(AppConstants.DEBUG_TAG, "Home component created: " + homeComponent);
    }
}