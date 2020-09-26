package com.example.restaurant.ui.home.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restaurant.R;
import com.example.restaurant.ui.home.model.RestaurantWrapper;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class RestaurantsAdapter extends RecyclerView.Adapter<RestaurantsAdapter.RestaurantViewHolder> {
    private List<RestaurantWrapper> restaurants;
    private final RestaurantClickListener listener;

    RestaurantsAdapter(RestaurantClickListener listener) {
        this.listener = listener;
        restaurants = new ArrayList<>();
    }

    public void addRestaurants(List<RestaurantWrapper> data) {
        restaurants.addAll(data);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RestaurantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_restaurant_item, parent, false);
        return new RestaurantViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantViewHolder holder, int position) {
        RestaurantWrapper restaurant = restaurants.get(position);
        holder.bind(restaurant);
        holder.itemView.setOnClickListener(l -> listener.restaurantClicked(restaurant));
    }

    @Override
    public int getItemCount() {
        return restaurants.size();
    }

    static class RestaurantViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView desc;
        ImageView logo;
        TextView status;

        public RestaurantViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            desc = itemView.findViewById(R.id.desc);
            logo = itemView.findViewById(R.id.logo);
            status = itemView.findViewById(R.id.status);
        }

        void bind(RestaurantWrapper restaurant) {
            title.setText(restaurant.name());
            desc.setText(restaurant.description());
            status.setText(restaurant.status());

            String imgURL = restaurant.imgURL();
            // load logo image
            displayImage(imgURL, logo);
        }

        private void displayImage(@Nullable String imgURL, ImageView logo) {
            int placeholderDrawable = R.drawable.ic_launcher_foreground;
            if (imgURL != null) {
                Picasso.get()
                        .load(imgURL)
                        .error(placeholderDrawable)
                        .into(logo);
            } else {
                Picasso.get()
                        .load(placeholderDrawable)
                        .into(logo);
            }
        }
    }

    interface RestaurantClickListener {
        /**
         * Invoked when a restaurant is clicked.
         *
         * @param restaurant {@link RestaurantWrapper}.
         */
        void restaurantClicked(RestaurantWrapper restaurant);
    }
}
