package com.example.restaurant.ui.details.viewmodel;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.restaurant.di.network.api.details.DetailsApi;
import com.example.restaurant.di.network.api.details.model.RestaurantAddress;
import com.example.restaurant.di.network.api.details.model.RestaurantDetail;
import com.example.restaurant.ui.details.ProvideRestaurantDetailsHandler;
import com.example.restaurant.ui.details.model.RestaurantDetailWrapper;
import com.example.restaurant.util.Resource;

import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class RestaurantDetailsViewModel extends ViewModel implements ProvideRestaurantDetailsHandler {
    private final DetailsApi detailsApi;
    private final RestaurantStream restaurantStream;

    private MutableLiveData<Resource<RestaurantDetailWrapper>> liveData;
    @Nullable
    private Disposable subs;

    public RestaurantDetailsViewModel(DetailsApi detailsApi, RestaurantStream restaurantStream) {
        this.detailsApi = detailsApi;
        this.restaurantStream = restaurantStream;
    }

    /**
     * Fetch restaurant details associated with the request param #restaurantID.
     * <p>
     * //     * @param restaurantID id of the restaurant whos details has to be fetched.
     *
     * @return {@link LiveData} of {@link RestaurantDetailWrapper} resource.
     */
    @Override
    public LiveData<Resource<RestaurantDetailWrapper>> restaurantDetailsResource() {
        if (liveData == null) {
            liveData = new MutableLiveData<>();
            liveData.postValue(Resource.loading(null));

            fetchRestaurantDetails();
        }

        return liveData;
    }

    @Override
    protected void onCleared() {
        super.onCleared();

        if (subs != null && !subs.isDisposed()) {
            subs.dispose();
        }
    }

    private void fetchRestaurantDetails() {
        subs = restaurantStream.restaurantIDStream()
                .switchMap(detailsApi::getRestaurantDetails)
                .map(this::handleResponse)
                .subscribeOn(Schedulers.io())
                .subscribe(data -> liveData.postValue(data));
    }

    private Resource<RestaurantDetailWrapper> handleResponse(Response<RestaurantDetail> response) {
        if (response.code() == 200 && response.body() != null) {
            RestaurantDetailWrapper details = transform(response.body());
            return Resource.success(details);
        }

        return Resource.error("error in fetching data", null);
    }

    private RestaurantDetailWrapper transform(RestaurantDetail res) {
        return RestaurantDetailWrapper.builder()
                .bannerURL(getURL(res))
                .name(res.getName())
                .rating(res.getRatings())
                .address(getAddress(res.getAddress()))
                .description(res.getDescription())
                .status(res.getStatus())
                .tags(getTags(res.getTags()))
                .build();
    }

    @Nullable
    private String getURL(RestaurantDetail res) {
        String url = res.getHeaderImgUrl();
        return url == null ? res.getCoverImgUrl() : url;
    }

    @Nullable
    private String getAddress(RestaurantAddress address) {
        return address == null ? null : address.getAddress();
    }

    @Nullable
    private String getTags(String[] tags) {
        if (tags == null || tags.length == 0) {
            return null;
        }

        StringBuilder sb = new StringBuilder();
        for (String tag : tags) {
            sb.append(tag).append(", ");
        }

        return sb.substring(0, sb.length() - 2);
    }
}
