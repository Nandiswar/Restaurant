package com.example.restaurant.ui.details.viewmodel;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;

/**
 * This class has the selected restaurant information stream.
 */
public class RestaurantStream {
    private BehaviorSubject<Integer> subj = BehaviorSubject.create();

    public void restaurantID(int id) {
        subj.onNext(id);
    }

    public Observable<Integer> restaurantIDStream() {
        return subj.hide();
    }
}
