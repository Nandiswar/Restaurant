package com.example.restaurant.di;

import com.example.restaurant.network.NetworkConstants;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetworkModule {

    /**
     * Provide single instance of {@link Retrofit} with provided base url
     * and converter factory settings.
     *
     * @return {@link Retrofit}.
     */
    @Singleton
    @Provides
    Retrofit providesRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(NetworkConstants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
