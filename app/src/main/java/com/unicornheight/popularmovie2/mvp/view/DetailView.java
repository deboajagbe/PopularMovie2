package com.unicornheight.popularmovie2.mvp.view;

import com.unicornheight.popularmovie2.mvp.model.Review;

import java.util.List;

/**
 * Created by deboajagbe on 5/12/17.
 */

public interface DetailView extends BaseView {

    void onReviewLoaded(List<Review> reviews, List<Review> videoTrailers);

    void onShowToast(String message);

    void setLikeButtonState(boolean isLiked);

    void insertLikeData();

    void queryLikeData(int id);
}
