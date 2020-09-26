package com.example.restaurant.ui.home.viewmodel;

import androidx.lifecycle.Observer;

import com.example.restaurant.di.network.api.home.HomeApi;
import com.example.restaurant.di.network.api.home.model.Restaurant;
import com.example.restaurant.ui.BaseTest;
import com.example.restaurant.ui.home.model.RestaurantWrapper;
import com.example.restaurant.util.Resource;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import io.reactivex.subjects.BehaviorSubject;
import okhttp3.ResponseBody;
import retrofit2.Response;

import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class RestaurantsViewModelTest extends BaseTest {
    private static final String TEST_RESTAURANT_NAME = "Test Restaurant";
    private BehaviorSubject subj = BehaviorSubject.create();
    @Mock
    HomeApi homeApi;

    private RestaurantsViewModel viewModel;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
        viewModel = new RestaurantsViewModel(homeApi);
    }

    @Test
    public void fetchRestaurants_whenHomeApiIsNull_shouldHaveObservers() {
        when(homeApi.getRestaurants(anyMap())).thenReturn(subj);

        Observer testObserver = mock(Observer.class);
        viewModel.restaurantsResource().observeForever(testObserver);

        assertNotNull(viewModel.restaurantsResource());
        assertTrue(viewModel.restaurantsResource().hasObservers());

        ArgumentCaptor captor = ArgumentCaptor.forClass(Resource.class);
        verify(testObserver, times(1)).onChanged(captor.capture());

        Resource value = (Resource) captor.getValue();
        assertNotNull(value);
        assertEquals(value.status, Resource.Status.LOADING);
        assertNull(value.data);

        viewModel.restaurantsResource().removeObserver(testObserver);
    }

    @Test
    public void fetchRestaurants_whenHomeApiReturnsError_shouldEmitErrorResource() {
        when(homeApi.getRestaurants(anyMap())).thenReturn(subj);

        Observer testObserver = mock(Observer.class);
        viewModel.restaurantsResource().observeForever(testObserver);

        ResponseBody body = mock(ResponseBody.class);
        subj.onNext(Response.error(404, body));

        ArgumentCaptor captor = ArgumentCaptor.forClass(Resource.class);
        verify(testObserver, times(2)).onChanged(captor.capture());

        List<Resource> values = (List<Resource>) captor.getAllValues();
        assertNotNull(values);
        assertEquals(2, values.size());

        Resource value = values.get(0);
        assertNotNull(value);
        assertEquals(value.status, Resource.Status.LOADING);
        assertNull(value.data);

        Resource value2 = values.get(1);
        assertNotNull(value2);
        assertEquals(value2.status, Resource.Status.ERROR);
        assertNull(value2.data);
        assertEquals(value2.message, "error in fetching data");

        viewModel.restaurantsResource().removeObserver(testObserver);
    }

    @Test
    public void fetchRestaurants_whenHomeApiReturnsEmptySuccess_shouldEmitSuccessEmptyResource() {
        when(homeApi.getRestaurants(anyMap())).thenReturn(subj);

        Observer obs = mock(Observer.class);
        viewModel.restaurantsResource().observeForever(obs);

        List<Restaurant> body = Collections.emptyList();
        subj.onNext(Response.success(body));

        ArgumentCaptor captor = ArgumentCaptor.forClass(Resource.class);
        verify(obs, times(2)).onChanged(captor.capture());

        List<Resource> values = (List<Resource>) captor.getAllValues();
        assertNotNull(values);
        assertEquals(2, values.size());

        Resource value = values.get(0);
        assertNotNull(value);
        assertEquals(value.status, Resource.Status.LOADING);
        assertNull(value.data);

        Resource value2 = values.get(1);
        assertNotNull(value2);
        assertEquals(Resource.Status.SUCCESS, value2.status);
        assertNotNull(value2.data);
        List<RestaurantWrapper> rest = (List<RestaurantWrapper>) value2.data;
        assertEquals(0, rest.size());
        assertNull(value2.message);

        viewModel.restaurantsResource().removeObserver(obs);
    }

    @Test
    public void fetchRestaurants_whenHomeApiReturnsSuccess_shouldEmitSuccessResource() {
        when(homeApi.getRestaurants(anyMap())).thenReturn(subj);

        Observer obs = mock(Observer.class);
        viewModel.restaurantsResource().observeForever(obs);

        List<Restaurant> body = Collections.singletonList(getRestaurant());
        subj.onNext(Response.success(body));

        ArgumentCaptor captor = ArgumentCaptor.forClass(Resource.class);
        verify(obs, times(2)).onChanged(captor.capture());

        List<Resource> values = (List<Resource>) captor.getAllValues();
        assertNotNull(values);
        assertEquals(2, values.size());

        Resource value = values.get(0);
        assertEquals(value.status, Resource.Status.LOADING);
        assertNull(value.data);


        Resource value2 = values.get(1);
        assertNotNull(value2);
        assertEquals(value2.status, Resource.Status.SUCCESS);
        assertNotNull(value2.data);
        List<RestaurantWrapper> rest = (List<RestaurantWrapper>) value2.data;
        assertEquals(1, rest.size());
        assertNull(value2.message);

        RestaurantWrapper restaurantWrapper = rest.get(0);
        assertEquals(restaurantWrapper.name(), TEST_RESTAURANT_NAME);
        assertNull(restaurantWrapper.description());

        viewModel.restaurantsResource().removeObserver(obs);
    }

    private Restaurant getRestaurant() {
        Restaurant restaurant = new Restaurant();
        restaurant.setName(TEST_RESTAURANT_NAME);
        return restaurant;
    }
}