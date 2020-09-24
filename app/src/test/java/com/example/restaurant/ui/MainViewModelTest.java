package com.example.restaurant.ui;

import androidx.lifecycle.Observer;

import com.example.restaurant.network.home.HomeApi;
import com.example.restaurant.util.Resource;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.Mockito.when;

public class MainViewModelTest {
    @Mock
    HomeApi homeApi;
    @Mock
    Observer<Resource<List<RestaurantWrapper>>> observer;

    private MainViewModel viewModel;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);

        viewModel = new MainViewModel(homeApi);
    }

    @Test
    public void fetchRestaurants_whenHomeApiIsNull_shouldHaveObservers() {
        when(homeApi.getRestaurants(anyMap())).thenReturn(null);
        viewModel.fetchRestaurants().observeForever(observer);

        assertNotNull(viewModel.fetchRestaurants());
        assertTrue(viewModel.fetchRestaurants().hasObservers());
    }

}