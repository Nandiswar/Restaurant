package com.example.restaurant.ui;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.restaurant.R;
import com.example.restaurant.network.home.HomeApi;
import com.example.restaurant.util.Resource;

import java.util.List;

import javax.inject.Inject;

import static com.example.restaurant.AppConstants.DEBUG_TAG;

public class DisplayRestaurantsFragment extends Fragment {

    @Inject
    HomeApi homeApi;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.restaurants_fragment, container, false);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (getActivity() == null) {
            Log.w(DEBUG_TAG, "Unable to attach DisplayRestaurantsFragment to MainActivity");
        }
        ((MainActivity) getActivity()).homeComponent.inject(this);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MainViewModelFactory factory = new MainViewModelFactory(homeApi);
        MainViewModel mainViewModel = new ViewModelProvider(requireActivity(), factory).get(MainViewModel.class);
        mainViewModel.fetchRestaurantsV2().observe(getViewLifecycleOwner(), this::handleData);
    }

    private void handleData(Resource<List<RestaurantWrapper>> listResource) {
        switch (listResource.status) {
            case LOADING:
                // handle loading state
                break;
            case ERROR:
                // handle error case
                break;
            case SUCCESS:
                // handle success state
                showRestaurants(listResource.data);
                break;
        }
    }

    private void showRestaurants(@Nullable List<RestaurantWrapper> data) {
        if (data == null || data.isEmpty()) {
            return;
        }
    }
}
