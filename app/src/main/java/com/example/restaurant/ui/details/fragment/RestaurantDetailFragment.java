package com.example.restaurant.ui.details.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.restaurant.R;
import com.example.restaurant.ui.details.ProvideRestaurantDetailsHandler;
import com.example.restaurant.ui.details.RestaurantDetailsActivity;
import com.example.restaurant.ui.details.model.RestaurantDetailWrapper;
import com.example.restaurant.ui.details.viewmodel.RestaurantDetailsViewModel;
import com.example.restaurant.ui.details.viewmodel.RestaurantDetailsViewModelFactory;
import com.example.restaurant.util.Resource;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import static com.example.restaurant.AppConstants.DEBUG_TAG;

public class RestaurantDetailFragment extends Fragment {

    @Inject
    RestaurantDetailsViewModelFactory factory;

    private TextView name;
    private TextView desc;
    private TextView address;
    private TextView status;
    private TextView tags;
    private TextView rating;
    private ImageView bannerImg;
    private ImageView loadingImg;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_restaurant_details, container, false);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (getActivity() == null) {
            Log.d(DEBUG_TAG, "Unable to attach details fragment");
        }

        ((RestaurantDetailsActivity) getActivity()).detailsComponent.inject(this);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews(view);

        ProvideRestaurantDetailsHandler viewModel = new ViewModelProvider(requireActivity(), factory)
                .get(RestaurantDetailsViewModel.class);

        // subscribe for restaurant details and display to the user
        viewModel.restaurantDetailsResource()
                .observe(getViewLifecycleOwner(), this::handleResource);
    }

    private void initViews(View view) {
        loadingImg = view.findViewById(R.id.loading_img);
        bannerImg = view.findViewById(R.id.banner);
        name = view.findViewById(R.id.title);
        rating = view.findViewById(R.id.rating);
        address = view.findViewById(R.id.address);
        desc = view.findViewById(R.id.description);
        status = view.findViewById(R.id.status);
        tags = view.findViewById(R.id.tags);
    }

    private void handleResource(Resource<RestaurantDetailWrapper> restaurantResource) {
        switch (restaurantResource.status) {
            case LOADING:
                loadingImg.setVisibility(View.VISIBLE);
                break;
            case ERROR:
                loadingImg.setVisibility(View.GONE);
                Toast.makeText(getActivity(), R.string.fetching_restaurant_details_failed, Toast.LENGTH_SHORT).show();
                break;
            case SUCCESS:
                loadingImg.setVisibility(View.GONE);
                handleRestaurantDetails(restaurantResource.data);
                break;
        }
    }

    private void handleRestaurantDetails(@Nullable RestaurantDetailWrapper data) {
        if (data == null) {
            return;
        }

        setText(name, data.name());
        setText(desc, data.description());
        setText(rating, getRatingString(data.rating()));
        setText(address, data.address());
        setText(tags, getTags(data.tags()));
        setText(status, data.status());

        String bannerURL = data.bannerURL();
        if (bannerURL != null) {
            Picasso.get()
                    .load(data.bannerURL())
                    .error(R.drawable.ic_launcher_foreground)
                    .into(bannerImg);
        } else {
            Picasso.get()
                    .load(R.drawable.ic_launcher_foreground)
                    .into(bannerImg);
        }
    }

    private String getTags(String tags) {
        return String.format(getResources().getString(R.string.tags), tags);
    }

    private String getRatingString(Double rating) {
        return String.format(getResources().getString(R.string.ratings), rating);
    }

    private void setText(TextView textView, @Nullable String text) {
        if (text == null) {
            textView.setVisibility(View.GONE);
            return;
        }

        textView.setVisibility(View.VISIBLE);
        textView.setText(text);
    }
}
