package com.unicornheight.popularmovie2.data;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.unicornheight.popularmovie2.mvp.model.Movie;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import static com.unicornheight.popularmovie2.data.MovieContract.MovieEntry;

/**
 * Created by deboajagbe on 5/14/17.
 */

public class MovieStorage {

    Context mContext;

    @Inject
    public MovieStorage(Context context) {
        this.mContext = context;
    }

    public final static String TAG = "MainActivity";

    public void addMovie(Movie movie) {
        ContentValues values = new ContentValues();
        values.put(MovieEntry.MOVIE_ID, movie.getId());
        values.put(MovieEntry.ORIGINAL_TITLE, movie.getOriginal_title());
        values.put(MovieEntry.OVERVIEW, movie.getOverview());
        values.put(MovieEntry.POPULARITY, movie.getPopularity());
        values.put(MovieEntry.RELEASE_DATE, movie.getRelease_date());
        values.put(MovieEntry.POSTER_PATH, movie.getPoster_path());
        values.put(MovieEntry.RATING, movie.getVote_average());
        values.put(MovieEntry.FAVORITE, movie.getFavorite());
        Uri uri = mContext.getContentResolver().insert(MovieContract.MovieEntry.CONTENT_URI, values);
        if (uri != null) {
            Toast.makeText(mContext.getApplicationContext(), uri.toString(), Toast.LENGTH_LONG).show();
        }
    }

    public List<Movie> getSavedMovies() {
        List<Movie> movieList = new ArrayList<>();
        Cursor cursor = mContext.getContentResolver().query(MovieContract.MovieEntry.CONTENT_URI,
                null,
                null,
                null,
                MovieContract.MovieEntry.MOVIE_ID);
        if (cursor != null) {
            if (cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        Movie movie = new Movie();
                        movie.setId(cursor.getInt(cursor.getColumnIndex(MovieEntry.MOVIE_ID)));
                        movie.setOriginal_title(cursor.getString(cursor.getColumnIndex(MovieEntry.ORIGINAL_TITLE)));
                        movie.setOverview(cursor.getString(cursor.getColumnIndex(MovieEntry.OVERVIEW)));
                        movie.setPopularity(cursor.getDouble(cursor.getColumnIndex(MovieEntry.POPULARITY)));
                        movie.setVote_average(cursor.getDouble(cursor.getColumnIndex(MovieEntry.RATING)));
                        movie.setRelease_date(cursor.getString(cursor.getColumnIndex(MovieEntry.RELEASE_DATE)));
                        movie.setFavorite(cursor.getInt(cursor.getColumnIndex(MovieEntry.FAVORITE)));
                        movie.setPoster_path(cursor.getString(cursor.getColumnIndex(MovieEntry.POSTER_PATH)));
                        movieList.add(movie);
                    } while (cursor.moveToNext());
                }
            }

            return movieList;
        } else {
            Log.e(TAG, "Failed to load data");
            return null;
        }
    }

    public List<Movie> getFavourite() {
        List<Movie> favouriteList = new ArrayList<>();
        Cursor cursor = mContext.getContentResolver().query(MovieContract.MovieEntry.CONTENT_URI,
                null,
                "favourite= 1",
                null,
                MovieContract.MovieEntry.MOVIE_ID);
        if (cursor != null) {
            if (cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        Movie movie = new Movie();
                        movie.setId(cursor.getInt(cursor.getColumnIndex(MovieEntry.MOVIE_ID)));
                        movie.setOriginal_title(cursor.getString(cursor.getColumnIndex(MovieEntry.ORIGINAL_TITLE)));
                        movie.setOverview(cursor.getString(cursor.getColumnIndex(MovieEntry.OVERVIEW)));
                        movie.setPopularity(cursor.getDouble(cursor.getColumnIndex(MovieEntry.POPULARITY)));
                        movie.setVote_average(cursor.getDouble(cursor.getColumnIndex(MovieEntry.RATING)));
                        movie.setRelease_date(cursor.getString(cursor.getColumnIndex(MovieEntry.RELEASE_DATE)));
                        movie.setFavorite(cursor.getInt(cursor.getColumnIndex(MovieEntry.FAVORITE)));
                        movie.setPoster_path(cursor.getString(cursor.getColumnIndex(MovieEntry.POSTER_PATH)));
                        favouriteList.add(movie);
                    } while (cursor.moveToNext());
                }
            }
        } else {
            Log.e(TAG, "Failed to load data");
            return null;
        }
        return favouriteList;
    }

    public int UpdateFavourite(long id) {
        Movie movie = new Movie();
        // Toggle favorite
        int newValue = movie.isFavorite() ? 0 : 1;
        ContentValues contentValues = new ContentValues();
        contentValues.put(MovieEntry.FAVORITE, newValue);
        Uri returnUri = ContentUris.withAppendedId(MovieContract.MovieEntry.CONTENT_URI, id);
        int update = mContext.getContentResolver().update(returnUri, contentValues, "_id=" + id, null);
        if (update < 1) {
            Toast.makeText(mContext.getApplicationContext(), update, Toast.LENGTH_LONG).show();
        }
        return update;

    }

    public int checkFavouriteStatus(long id) {
        Cursor cursor = mContext.getContentResolver().query(MovieContract.MovieEntry.CONTENT_URI,
                null,
                "_id=" + id,
                null,
                MovieContract.MovieEntry.MOVIE_ID);
        cursor.moveToFirst();
        int value = cursor.getInt(cursor.getColumnIndex(MovieEntry.FAVORITE));
        return value;
    }
}