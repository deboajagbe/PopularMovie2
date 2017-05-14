package com.unicornheight.popularmovie2.mvp.presenter;

import com.unicornheight.popularmovie2.api.MovieApiService;
import com.unicornheight.popularmovie2.base.BasePresenter;
import com.unicornheight.popularmovie2.datamap.ReviewMapper;
import com.unicornheight.popularmovie2.mvp.model.Review;
import com.unicornheight.popularmovie2.mvp.model.ReviewResponse;
import com.unicornheight.popularmovie2.mvp.model.Storage;
import com.unicornheight.popularmovie2.mvp.view.DetailView;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Observer;



/**
 * Created by deboajagbe on 5/13/17.
 */

public class DetailPresenter extends BasePresenter<DetailView> implements Observer<ReviewResponse>{

    @Inject
    protected MovieApiService mApiService;
    @Inject protected ReviewMapper mReviewMapper;
    @Inject protected Storage mStorage;


    @Inject
    public DetailPresenter() {
    }

    public void getTrailers(long movie_id) {
        Observable<ReviewResponse> videoResponseObservable = mApiService.getMovieTrailers(movie_id);
        subscribe(videoResponseObservable, this);
    }

    public void getReviews(long movie_id) {
        Observable<ReviewResponse> reviewResponseObservable = mApiService.getMovieReviews(movie_id);
        subscribe(reviewResponseObservable, this);
    }

    @Override
    public void onCompleted() {
        getView().onShowToast("Trailer and Review loading completed!");
    }

    @Override
    public void onError(Throwable e) {
        getView().onShowToast("Error loading movies " + e.getMessage());
    }

    @Override
    public void onNext(ReviewResponse reviewResponse) {
        List<Review> trailers = mReviewMapper.mapMoviesReview(mStorage, reviewResponse);
        List<Review> reviews = mReviewMapper.mapMoviesReview(mStorage, reviewResponse);
        getView().onReviewLoaded(trailers, reviews);
    }



}
