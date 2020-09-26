package com.example.restaurant.ui.details.viewmodel;

import androidx.lifecycle.Observer;

import com.example.restaurant.di.network.api.details.DetailsApi;
import com.example.restaurant.di.network.api.details.model.RestaurantDetail;
import com.example.restaurant.ui.BaseTest;
import com.example.restaurant.ui.details.RestaurantStream;
import com.example.restaurant.ui.details.model.RestaurantDetailWrapper;
import com.example.restaurant.util.Resource;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import io.reactivex.subjects.BehaviorSubject;
import okhttp3.ResponseBody;
import retrofit2.Response;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class RestaurantDetailsViewModelTest extends BaseTest {
    private static final String TEST_RESTAURANT_NAME = "Test restaurant";

    private BehaviorSubject<Integer> restIDSubj = BehaviorSubject.create();
    private BehaviorSubject<Response<RestaurantDetail>> restDetailsSubj = BehaviorSubject.create();

    @Mock
    DetailsApi detailsApi;
    @Mock
    RestaurantStream restaurantStream;

    private RestaurantDetailsViewModel viewModel;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);

        when(restaurantStream.restaurantIDStream()).thenReturn(restIDSubj);

        viewModel = new RestaurantDetailsViewModel(detailsApi, restaurantStream);
    }

    @Test
    public void fetchRestaurants_whenHomeApiIsNull_shouldHaveObservers() {
        Observer testObserver = mock(Observer.class);

        viewModel.restaurantDetailsResource().observeForever(testObserver);

        assertNotNull(viewModel.restaurantDetailsResource());
        assertTrue(viewModel.restaurantDetailsResource().hasObservers());

        ArgumentCaptor captor = ArgumentCaptor.forClass(Resource.class);
        verify(testObserver, times(1)).onChanged(captor.capture());

        Resource value = (Resource) captor.getValue();
        assertNotNull(value);
        assertEquals(value.status, Resource.Status.LOADING);
        assertNull(value.data);

        viewModel.restaurantDetailsResource().removeObserver(testObserver);
    }

    @Test
    public void fetchRestaurants_whenHomeApiReturnsError_shouldEmitErrorResource() {
        when(detailsApi.getRestaurantDetails(anyInt())).thenReturn(restDetailsSubj);

        Observer testObserver = mock(Observer.class);
        viewModel.restaurantDetailsResource().observeForever(testObserver);

        restIDSubj.onNext(1);
        ResponseBody body = mock(ResponseBody.class);
        restDetailsSubj.onNext(Response.error(404, body));

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

        viewModel.restaurantDetailsResource().removeObserver(testObserver);
    }

    @Test
    public void fetchRestaurants_whenHomeApiReturnsSuccess_shouldEmitSuccessResource() {
        when(detailsApi.getRestaurantDetails(anyInt())).thenReturn(restDetailsSubj);

        Observer testObserver = mock(Observer.class);
        viewModel.restaurantDetailsResource().observeForever(testObserver);

        restIDSubj.onNext(1);
        RestaurantDetail body = getRestaurantDetail();
        restDetailsSubj.onNext(Response.success(body));

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

        viewModel.restaurantDetailsResource().removeObserver(testObserver);
    }

    private RestaurantDetail getRestaurantDetail() {
        RestaurantDetail restaurant = new RestaurantDetail();
        restaurant.setName(TEST_RESTAURANT_NAME);
        return restaurant;
    }
}