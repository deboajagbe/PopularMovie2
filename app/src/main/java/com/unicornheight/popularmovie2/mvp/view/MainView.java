package com.unicornheight.popularmovie2.mvp.view;

import com.unicornheight.popularmovie2.mvp.model.Movie;

import java.util.List;

/**
 * Created by deboajagbe on 5/10/17.
 */

public interface MainView extends BaseView {

    void onMovieLoaded(List<Movie> movies);

    void onShowDialog(String message);

    void onHideDialog();

    void onShowToast(String message);

    void onClearItems();
}