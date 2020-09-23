package com.example.restaurant.di.home;

import com.example.restaurant.network.home.HomeApi;
import com.example.restaurant.ui.MainViewModel;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class HomeModule {
    /**
     * Provide {@link HomeApi} obj to get the api's to be invoked from Home Screen.
     *
     * @param retrofit {@link Retrofit}.
     * @return {@link HomeApi}.
     */
    @Provides
    HomeApi provideHomeApi(Retrofit retrofit) {
        return retrofit.create(HomeApi.class);
    }

    @Provides
    MainViewModel provideMainViewModel(HomeApi homeApi) {
        return new MainViewModel(homeApi);
    }
}
