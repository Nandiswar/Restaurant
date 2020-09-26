package com.example.restaurant.ui.home.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restaurant.R;
import com.example.restaurant.ui.details.RestaurantDetailsActivity;
import com.example.restaurant.ui.home.HomeActivity;
import com.example.restaurant.ui.home.ProvideRestaurantsHandler;
import com.example.restaurant.ui.home.model.RestaurantWrapper;
import com.example.restaurant.ui.home.viewmodel.RestaurantsViewModel;
import com.example.restaurant.ui.home.viewmodel.RestaurantsViewModelFactory;
import com.example.restaurant.util.Resource;

import java.util.List;

import javax.inject.Inject;

import static com.example.restaurant.AppConstants.DEBUG_TAG;

public class RestaurantsFragment extends Fragment implements RestaurantsAdapter.RestaurantClickListener {

    @Inject
    RestaurantsViewModelFactory factory;

    private ConstraintLayout loadingContainer;
    private RecyclerView recyclerView;
    private RestaurantsAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_restaurants, container, false);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (getActivity() == null) {
            Log.w(DEBUG_TAG, "Unable to attach DisplayRestaurantsFragment to MainActivity");
        }
        ((HomeActivity) getActivity()).homeComponent.inject(this);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // placeholder view shown to the user when fetching restaurants
        loadingContainer = view.findViewById(R.id.loading_container);

        recyclerView = view.findViewById(R.id.recycler_view);
        setupRecyclerView();

        ProvideRestaurantsHandler viewModel = new ViewModelProvider(requireActivity(), factory)
                .get(RestaurantsViewModel.class);

        // subscribe for restaurants data and display to the user
        viewModel.restaurantsResource()
                .observe(getViewLifecycleOwner(), this::handleData);
    }

    @Override
    public void restaurantClicked(RestaurantWrapper restaurant) {
        Intent intent = new Intent(getActivity(), RestaurantDetailsActivity.class);
        intent.putExtra(RestaurantDetailsActivity.RESTAURANT_KEY, restaurant);
        startActivity(intent);
    }

    private void setupRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                layoutManager.getOrientation());
        recyclerView.addItemDecoration(itemDecoration);

        adapter = new RestaurantsAdapter(this);
        recyclerView.setAdapter(adapter);
    }

    private void handleData(Resource<List<RestaurantWrapper>> listResource) {
        switch (listResource.status) {
            case LOADING:
                // handle loading state
                loadingContainer.setVisibility(View.VISIBLE);
                break;
            case ERROR:
                // handle error case
                loadingContainer.setVisibility(View.GONE);
                break;
            case SUCCESS:
                // handle success state
                loadingContainer.setVisibility(View.GONE);
                showRestaurants(listResource.data);
                break;
        }
    }

    private void showRestaurants(@Nullable List<RestaurantWrapper> data) {
        if (data == null || data.isEmpty()) {
            return;
        }

        recyclerView.setVisibility(View.VISIBLE);
        adapter.addRestaurants(data);
    }
}
