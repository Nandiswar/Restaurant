package com.example.restaurant.di.network;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetworkModule {

    /**
     * Provides {@link Retrofit} instance to handle network interactions with doordash
     * 1. using base url - {@link NetworkConstants#BASE_URL}
     * 2. using {@link GsonConverterFactory} and
     * 2. RxJavaCallAdapterFactory {@link RxJava2CallAdapterFactory}.
     *
     * @return {@link Retrofit}.
     */
    @Singleton
    @Provides
    Retrofit providesRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(NetworkConstants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }
}
