package com.unicornheight.popularmovie2.modules.home;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.unicornheight.popularmovie2.R;
import com.unicornheight.popularmovie2.base.BaseActivity;
import com.unicornheight.popularmovie2.di.components.DaggerMovieComponent;
import com.unicornheight.popularmovie2.di.modules.MoviesModule;
import com.unicornheight.popularmovie2.modules.details.DetailActivity;
import com.unicornheight.popularmovie2.modules.home.adapter.MovieAdapter;
import com.unicornheight.popularmovie2.mvp.model.Movie;
import com.unicornheight.popularmovie2.mvp.presenter.MoviePresenter;
import com.unicornheight.popularmovie2.mvp.view.MainView;
import com.unicornheight.popularmovie2.utilities.NetworkUtils;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;

public class MainActivity extends BaseActivity implements MainView{


    @Bind(R.id.movie_list) protected RecyclerView mMovieList;
    @Bind(R.id.errorText) protected TextView mError;
    @Inject
    protected MoviePresenter mPresenter;
    private MovieAdapter mMovieAdapter;


    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState, intent);
        initializeList();
        loadMovies();
    }

    private void loadMovies() {
        if(NetworkUtils.isNetAvailable(this)) {
            mPresenter.getMovies();
        } else {
            mPresenter.getMoviesFromDatabase();
        }
    }


    private int numberOfColumns() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        // You can change this divider to adjust the size of the poster
        int widthDivider = 400;
        int width = displayMetrics.widthPixels;
        int nColumns = width / widthDivider;
        if (nColumns < 2) return 2;
        return nColumns;
    }
    private void initializeList() {
        mMovieList.setHasFixedSize(true);
        GridLayoutManager layoutManager = new GridLayoutManager(this, numberOfColumns());
        mMovieList.setLayoutManager(layoutManager);
        mMovieAdapter = new MovieAdapter(getLayoutInflater());
        mMovieAdapter.setMovieClickListener(mMovieClickListener);
        mMovieList.setAdapter(mMovieAdapter);
    }
    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }


    @Override
    public void onMovieLoaded(List<Movie> movies) {
        if(movies != null) {
            mMovieAdapter.addMovies(movies);
        }else{
            mError.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onShowDialog(String message) {
        showDialog(message);
    }

    @Override
    public void onHideDialog() {
        hideDialog();
    }

    @Override
    public void onShowToast(String message) {
        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClearItems() {
        mMovieAdapter.clearMovies();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sort_by_popular_action:
                mPresenter.getPopularMovies();
                return true;
            case R.id.sort_by_highest_rated_action:
                 mPresenter.getHighMovies();
                return true;
            case R.id.save_movies_action:
                mPresenter.getFavourite();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void resolveDaggerDependency() {
        DaggerMovieComponent.builder()
                .applicationComponent(getApplicationComponent())
                .moviesModule(new MoviesModule(this))
                .build().inject(this);
    }
    private MovieAdapter.OnMovieClickListener mMovieClickListener = new MovieAdapter.OnMovieClickListener() {
        @Override
        public void onClick(View v, Movie movie, int position) {
            Intent intent = new Intent(MainActivity.this, DetailActivity.class);
            intent.putExtra(DetailActivity.MOVIE, movie);

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this, v, "movieImageAnimation");
                startActivity(intent, options.toBundle());
            } else {
                startActivity(intent);
            }
        }
    };

}
