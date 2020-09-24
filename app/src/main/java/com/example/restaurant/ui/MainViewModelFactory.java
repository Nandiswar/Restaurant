package com.example.restaurant.ui;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.restaurant.network.home.HomeApi;

public class MainViewModelFactory implements ViewModelProvider.Factory {
    private final HomeApi homeApi;

    MainViewModelFactory(HomeApi homeApi) {
        this.homeApi = homeApi;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MainViewModel.class)) {
            return (T) new MainViewModel(homeApi);
        }

        return null;
    }
}
