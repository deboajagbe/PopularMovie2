package com.unicornheight.popularmovie2.mvp.presenter;

import com.unicornheight.popularmovie2.api.MovieApiService;
import com.unicornheight.popularmovie2.base.BasePresenter;
import com.unicornheight.popularmovie2.datamap.MovieMapper;
import com.unicornheight.popularmovie2.mvp.model.Movie;
import com.unicornheight.popularmovie2.mvp.model.MovieResponse;
import com.unicornheight.popularmovie2.mvp.model.Storage;
import com.unicornheight.popularmovie2.mvp.view.MainView;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Observer;

/**
 * Created by deboajagbe on 5/10/17.
 */

public class MoviePresenter extends BasePresenter<MainView> implements Observer<MovieResponse> {

    @Inject
    protected MovieApiService mApiService;
    @Inject protected MovieMapper mMovieMapper;
    @Inject protected Storage mStorage;

    @Inject
    public MoviePresenter() {
    }

    public void getMovies() {
        getView().onShowDialog("Loading movies....");
        Observable<MovieResponse> moviesResponseObservable = mApiService.getMovies();
        subscribe(moviesResponseObservable, this);
    }

    public void getHighMovies() {
        getView().onShowDialog("Loading movies....");
        Observable<MovieResponse> moviesResponseObservable = mApiService.getHighRatedMovies();
        subscribe(moviesResponseObservable, this);
    }

    public void getPopularMovies() {
        getView().onShowDialog("Loading movies....");
        Observable<MovieResponse> moviesResponseObservable = mApiService.getPopularMovies();
        subscribe(moviesResponseObservable, this);
    }
    @Override
    public void onCompleted() {
        getView().onHideDialog();
        getView().onShowToast("Movie loading completed!");
    }

    @Override
    public void onError(Throwable e) {
        getView().onHideDialog();
        getView().onShowToast("Error loading movies " + e.getMessage());
    }

    @Override
    public void onNext(MovieResponse movieResults) {
        List<Movie> movies = mMovieMapper.mapMovies(mStorage, movieResults);
        getView().onClearItems();
        getView().onMovieLoaded(movies);
    }

    public void getMoviesFromDatabase() {
        List<Movie> movies = mStorage.getSavedMovies();
        getView().onClearItems();
        getView().onMovieLoaded(movies);
    }
    public void getFavourite() {
        List<Movie> movies = mStorage.getFavourite();
        getView().onClearItems();
        getView().onMovieLoaded(movies);
    }
}
