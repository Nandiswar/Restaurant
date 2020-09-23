package com.example.restaurant;

import android.app.Application;

import com.example.restaurant.di.AppComponent;
import com.example.restaurant.di.DaggerAppComponent;

public class BaseApplication extends Application {

    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public AppComponent appComponent() {
        if (appComponent == null) {
            appComponent = DaggerAppComponent.create();
        }

        return appComponent;
    }
}
