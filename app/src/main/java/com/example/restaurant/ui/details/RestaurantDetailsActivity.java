package com.example.restaurant.ui.details;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import com.example.restaurant.BaseApplication;
import com.example.restaurant.R;
import com.example.restaurant.di.details.DetailsComponent;
import com.example.restaurant.ui.details.fragment.RestaurantDetailFragment;
import com.example.restaurant.ui.home.model.RestaurantWrapper;

import javax.inject.Inject;

public class RestaurantDetailsActivity extends AppCompatActivity {
    @Inject
    RestaurantStream restaurantStream;

    public static final String RESTAURANT_KEY = "RESTAURANT";
    public DetailsComponent detailsComponent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        // set up dagger components
        detailsComponent = ((BaseApplication) getApplication())
                .appComponent().detailsComponent().create();
        detailsComponent.inject(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        // update selected restaurant id stream
        RestaurantWrapper restaurant = getRestaurant();
        if (restaurant != null) {
            restaurantStream.restaurantID(restaurant.id());
        }

        // update toolbar title
        setupToolbar(restaurant);

        if (savedInstanceState == null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            RestaurantDetailFragment fragment = new RestaurantDetailFragment();
            transaction.replace(R.id.container_details, fragment);
            transaction.commit();
        }
    }

    private void setupToolbar(@Nullable RestaurantWrapper restaurant) {
        Toolbar toolbar = findViewById(R.id.toolbar);

        String toolbarTitle = getTitleForToolbar(restaurant);
        toolbar.setTitle(toolbarTitle);
    }

    private String getTitleForToolbar(@Nullable RestaurantWrapper restaurant) {
        int titleResId = R.string.restaurant_details;
        if (restaurant == null || restaurant.name() == null || restaurant.name().isEmpty()) {
            return getResources().getString(titleResId);
        }
        return restaurant.name();
    }

    @Nullable
    private RestaurantWrapper getRestaurant() {
        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            return null;
        }
        return (RestaurantWrapper) bundle.getSerializable(RESTAURANT_KEY);
    }
}
