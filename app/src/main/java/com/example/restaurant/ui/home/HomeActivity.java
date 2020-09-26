package com.example.restaurant.ui.home;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import com.example.restaurant.BaseApplication;
import com.example.restaurant.R;
import com.example.restaurant.di.home.HomeComponent;
import com.example.restaurant.ui.home.fragment.RestaurantsFragment;

public class HomeActivity extends AppCompatActivity {
    public HomeComponent homeComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // set up dagger components
        homeComponent = ((BaseApplication) getApplication())
                .appComponent().homeComponent().create();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupToolbar();

        if (savedInstanceState == null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.container_home, new RestaurantsFragment());
            transaction.commit();
        }
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(R.string.home_activity_title);
    }
}