package com.unicornheight.popularmovie2.modules.details;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.unicornheight.popularmovie2.R;
import com.unicornheight.popularmovie2.api.MovieApiService;
import com.unicornheight.popularmovie2.base.BaseActivity;
import com.unicornheight.popularmovie2.data.MovieStorage;
import com.unicornheight.popularmovie2.di.components.DaggerDetailComponent;
import com.unicornheight.popularmovie2.di.modules.DetailModule;
import com.unicornheight.popularmovie2.helper.ImageHandler;
import com.unicornheight.popularmovie2.mvp.model.Movie;
import com.unicornheight.popularmovie2.mvp.model.Review;
import com.unicornheight.popularmovie2.mvp.presenter.DetailPresenter;
import com.unicornheight.popularmovie2.mvp.view.DetailView;
import com.unicornheight.popularmovie2.utilities.NetworkUtils;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;

/**
 * Created by deboajagbe on 5/10/17.
 */

public class DetailActivity extends BaseActivity implements DetailView {

    public static final String MOVIE = "movie";
    private static final String BASE_URL = "http://www.youtube.com/watch?v=";
    @Inject
    protected DetailPresenter mPresenter;
    @Inject
    protected MovieApiService mApiService;
    @Inject
    protected MovieStorage mStorage;
    @Inject
    Context context;

    @Bind(R.id.movieImage) protected ImageView mMovieImage;
    @Bind(R.id.movieTitle) protected TextView mMovieTitle;
    @Bind(R.id.movieDescription) protected TextView mMovieDescription;
    @Bind(R.id.releaseDate) protected TextView mMovieReleaseDate;
    @Bind(R.id.fab_like) protected FloatingActionButton mMovieLike;
    @Bind(R.id.trailerPlay) protected ImageButton mPlay;
    @Bind(R.id.reviewAuthor) protected TextView mReviewAuthor;
    @Bind(R.id.reviewContent) protected TextView mReviewContent;
    @Bind(R.id.ratings) protected TextView mRating;
    @Bind(R.id.webView) protected WebView webView;


    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState, intent);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mMovieImage.setTransitionName("movieImageAnimation");
        }
        final Movie movie = (Movie) intent.getSerializableExtra(MOVIE);
        mMovieTitle.setText(movie.getOriginal_title());
        mMovieDescription.setText(movie.getOverview());
        mMovieReleaseDate.setText(movie.getRelease_date());
        mRating.setText("Ratings - " + String.valueOf(movie.getVote_average()) +" /10");
        loadTrailerAndReview(movie.getId());
        checkIfFavorite(movie.getId());

        Glide.with(this).load("http://image.tmdb.org/t/p/w500" + movie.getPoster_path())
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(new ImageHandler(mMovieImage));
        mMovieLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateFavorite(movie.getId());
            }
        });
    }

    private void updateFavorite(long movie_id){
        MovieStorage storage = new MovieStorage(this);
        int value = storage.UpdateFavourite(movie_id);
        if(value == 1){
            mMovieLike.setImageResource(R.mipmap.ic_toolbar_like_p);
        } else {
            mMovieLike.setImageResource(R.mipmap.ic_toolbar_like_n);
        }
    }
    private void checkIfFavorite(long movie_id) {
        MovieStorage storage = new MovieStorage(this);
        long result = storage.checkFavouriteStatus(movie_id);
        if (result == 1) {
            mMovieLike.setImageResource(R.mipmap.ic_toolbar_like_p);
        } else {
            mMovieLike.setImageResource(R.mipmap.ic_toolbar_like_n);
        }
    }
    private void loadTrailerAndReview(long movie_id) {
        if(NetworkUtils.isNetAvailable(this)) {
              mPresenter.getTrailers(movie_id);
              mPresenter.getReviews(movie_id);
        } else {
           // mPresenter.getMoviesFromDatabase();
        }
    }
    @Override
    protected int getContentView() {
        return R.layout.activity_detail;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onReviewLoaded(List<Review> reviews, List<Review> videoTrailers) {

            for (Review review : reviews) {
                if(review.getContent() == null){
                    mReviewAuthor.setText(getString(R.string.empty));
                }else{
                    mReviewAuthor.setText(review.getAuthor());
                    mReviewAuthor.setText(review.getContent());
                }

            }
            for (final Review video : videoTrailers) {
                mPlay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String setKey = video.getKey();
                        //startWebView(setKey);
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(BASE_URL + setKey)));
                    }
                });
            }
    }

    private void startWebView(String setKey) {
        webView.setVisibility(View.VISIBLE);
        mMovieImage.setVisibility(View.GONE);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }
        });
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.loadUrl( BASE_URL + setKey);
    }

    @Override
    public void onShowToast(String message) {
        //Toast.makeText(DetailActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setLikeButtonState(boolean isLiked) {
        mMovieLike.setSelected(isLiked);
    }

    @Override
    public void insertLikeData() {

    }

    @Override
    public void queryLikeData(int id) {

    }

    @Override
    protected void resolveDaggerDependency() {
        DaggerDetailComponent.builder()
                .applicationComponent(getApplicationComponent())
                .detailModule(new DetailModule(this))
                .build().inject(this);
    }
}
