package com.example.restaurant.ui.details.viewmodel;

import androidx.lifecycle.Observer;

import com.example.restaurant.di.network.api.details.DetailsApi;
import com.example.restaurant.di.network.api.details.model.RestaurantDetail;
import com.example.restaurant.di.network.api.home.model.Restaurant;
import com.example.restaurant.ui.BaseTest;
import com.example.restaurant.ui.details.model.RestaurantDetailWrapper;
import com.example.restaurant.util.Resource;
import com.jakewharton.rxrelay2.PublishRelay;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import okhttp3.ResponseBody;
import retrofit2.Response;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class RestaurantDetailsViewModelTest extends BaseTest {

    private static final String TEST_RESTAURANT_NAME = "Test restaurant";
    private static final int ID = 1;

    @Mock
    DetailsApi detailsApi;

    private PublishRelay relay;
    private RestaurantDetailsViewModel viewModel;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);

        relay = PublishRelay.create();
        viewModel = new RestaurantDetailsViewModel(detailsApi, restaurantStream);
    }

    @Test
    public void fetchRestaurants_whenHomeApiIsNull_shouldHaveObservers() {
        Flowable flowable = relay.toFlowable(BackpressureStrategy.LATEST);
        when(detailsApi.getRestaurantDetails(ID)).thenReturn(flowable);

        Observer testObserver = mock(Observer.class);

        viewModel.restaurantDetailsResource(ID).observeForever(testObserver);

        assertNotNull(viewModel.restaurantDetailsResource(ID));
        assertTrue(viewModel.restaurantDetailsResource(ID).hasObservers());

        ArgumentCaptor captor = ArgumentCaptor.forClass(Resource.class);
        verify(testObserver, times(1)).onChanged(captor.capture());

        Resource value = (Resource) captor.getValue();
        assertNotNull(value);
        assertEquals(value.status, Resource.Status.LOADING);
        assertNull(value.data);

        viewModel.restaurantDetailsResource(ID).removeObserver(testObserver);
    }

    @Test
    public void fetchRestaurants_whenHomeApiReturnsError_shouldEmitErrorResource() {
        Flowable flowable = relay.toFlowable(BackpressureStrategy.LATEST);
        when(detailsApi.getRestaurantDetails(ID)).thenReturn(flowable);

        Observer testObserver = mock(Observer.class);
        viewModel.restaurantDetailsResource(ID).observeForever(testObserver);

        ResponseBody body = mock(ResponseBody.class);
        relay.accept(Response.error(404, body));

        ArgumentCaptor captor = ArgumentCaptor.forClass(Resource.class);
        verify(testObserver, times(2)).onChanged(captor.capture());

        List<Resource> values = (List<Resource>) captor.getAllValues();
        assertNotNull(values);
        assertEquals(2, values.size());

        Resource value = (Resource) values.get(0);
        assertNotNull(value);
        assertEquals(value.status, Resource.Status.LOADING);
        assertNull(value.data);

        Resource value2 = (Resource) values.get(1);
        assertNotNull(value2);
        assertEquals(value2.status, Resource.Status.ERROR);
        assertNull(value2.data);
        assertEquals(value2.message, "error in fetching data");

        viewModel.restaurantDetailsResource(ID).removeObserver(testObserver);
    }

    @Test
    public void fetchRestaurants_whenHomeApiReturnsSuccess_shouldEmitSuccessResource() {
        Flowable flowable = relay.toFlowable(BackpressureStrategy.LATEST);
        when(detailsApi.getRestaurantDetails(ID)).thenReturn(flowable);

        Observer testObserver = mock(Observer.class);
        viewModel.restaurantDetailsResource(ID).observeForever(testObserver);

        RestaurantDetail body = getRestaurantDetail();
        relay.accept(Response.success(body));

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
        assertEquals(Resource.Status.SUCCESS, value2.status);
        assertNotNull(value2.data);
        RestaurantDetailWrapper rest = (RestaurantDetailWrapper) value2.data;
        assertEquals(rest.name(), TEST_RESTAURANT_NAME);
        assertNull(rest.description());

        viewModel.restaurantDetailsResource(ID).removeObserver(testObserver);
    }

    private RestaurantDetail getRestaurantDetail() {
        RestaurantDetail restaurant = new RestaurantDetail();
        restaurant.setName(TEST_RESTAURANT_NAME);
        return restaurant;
    }

    private Flowable<Response<List<Restaurant>>> getFlowable(PublishRelay<Response<List<Restaurant>>> relay) {
        return relay.toFlowable(BackpressureStrategy.LATEST);
    }
}