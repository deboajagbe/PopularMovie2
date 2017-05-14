package com.unicornheight.popularmovie2.mvp.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by deboajagbe on 5/10/17.
 */

public class Storage extends SQLiteOpenHelper {

    private static final String TAG = Storage.class.getSimpleName();

    @Inject
    public Storage(Context context) {
        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(CREATE_TABLE);
        } catch(SQLException e) {
            Log.d(TAG, e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE);
        onCreate(db);
    }

    public void addMovie(Movie movie) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(MOVIE_ID, movie.getId());
        values.put(ORIGINAL_TITLE, movie.getOriginal_title());
        values.put(OVERVIEW, movie.getOverview());
        values.put(POPULARITY, movie.getPopularity());
        values.put(RELEASE_DATE, movie.getRelease_date());
        values.put(POSTER_PATH, movie.getPoster_path());
        values.put(RATING, movie.getVote_average());
        values.put(FAVORITE, movie.getFavorite());
        try {
            db.insertWithOnConflict(TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_IGNORE);
        } catch(SQLException e) {
            Log.d(TAG, e.getMessage());
        }
        db.close();
    }
    public int checkFavouriteStatus(long id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE "+MOVIE_ID+"='"+ id + "'", null);
        cursor.moveToFirst();
        int value = cursor.getInt(cursor.getColumnIndex(FAVORITE));
        cursor.close();
        db.close();
        return value;
    }
    public List<Movie> getFavourite(){
        List<Movie> favouriteList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE "+FAVORITE+"='"+ 1 + "'", null);
            Log.d("Main", String.valueOf(cursor));
            if (cursor != null) {
                if (cursor.getCount() > 0) {
                    if (cursor.moveToFirst()) {
                        do {
                            Movie movie = new Movie();
                            movie.setId(cursor.getInt(cursor.getColumnIndex(MOVIE_ID)));
                            movie.setOriginal_title(cursor.getString(cursor.getColumnIndex(ORIGINAL_TITLE)));
                            movie.setOverview(cursor.getString(cursor.getColumnIndex(OVERVIEW)));
                            movie.setPopularity(cursor.getDouble(cursor.getColumnIndex(POPULARITY)));
                            movie.setVote_average(cursor.getDouble(cursor.getColumnIndex(RATING)));
                            movie.setRelease_date(cursor.getString(cursor.getColumnIndex(RELEASE_DATE)));
                            movie.setFavorite(cursor.getInt(cursor.getColumnIndex(FAVORITE)));
                            movie.setPoster_path(cursor.getString(cursor.getColumnIndex(POSTER_PATH)));
                            favouriteList.add(movie);

                        } while (cursor.moveToNext());
                    }
                }
            }
        } catch (SQLException e) {
            Log.d(TAG, e.getMessage());
        }
        return favouriteList;
    }

    public int updateFavourite(long id){
        Movie movie = new Movie();
       // int getValue = checkFavouriteStatus(id);
        SQLiteDatabase db = this.getWritableDatabase();
        // Toggle favorite
        int newValue = movie.isFavorite() ? 0 : 1;
        ContentValues contentValues = new ContentValues();
        contentValues.put(FAVORITE, newValue);
        db.update(TABLE_NAME, contentValues, "movieId="+id, null);
        db.close();
      //  Log.d(TAG, "Favourite is now " + contentValues);
        return newValue;
    }
    public List<Movie> getSavedMovies() {
        List<Movie> movieList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            Cursor cursor = db.rawQuery(SELECT_QUERY, null);
            if (cursor != null) {
                if (cursor.getCount() > 0) {
                    if (cursor.moveToFirst()) {
                        do {
                            Movie movie = new Movie();
                            movie.setId(cursor.getInt(cursor.getColumnIndex(MOVIE_ID)));
                            movie.setOriginal_title(cursor.getString(cursor.getColumnIndex(ORIGINAL_TITLE)));
                            movie.setOverview(cursor.getString(cursor.getColumnIndex(OVERVIEW)));
                            movie.setPopularity(cursor.getDouble(cursor.getColumnIndex(POPULARITY)));
                            movie.setVote_average(cursor.getDouble(cursor.getColumnIndex(RATING)));
                            movie.setRelease_date(cursor.getString(cursor.getColumnIndex(RELEASE_DATE)));
                            movie.setFavorite(cursor.getInt(cursor.getColumnIndex(FAVORITE)));
                            movie.setPoster_path(cursor.getString(cursor.getColumnIndex(POSTER_PATH)));

                            movieList.add(movie);

                        } while (cursor.moveToNext());
                    }
                }
            }
        } catch (SQLException e) {
            Log.d(TAG, e.getMessage());
        }
        return movieList;
    }

    private static final String ORIGINAL_TITLE = "title";
    private static final String MOVIE_ID = "movieId";
    private static final String FAVORITE = "favourite";
    private static final String POPULARITY = "popularity";
    private static final String RATING = "ratings";
    private static final String OVERVIEW = "overview";
    private static final String RELEASE_DATE = "releaseDate";
    private static final String POSTER_PATH = "posterPath";
    private static final String TABLE_NAME = "movies";
    private static final String DATABASE_NAME = "movies.db";
    private static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    private static final String SELECT_QUERY = "SELECT * FROM " + TABLE_NAME;

    public static final String CREATE_TABLE = "create table " + TABLE_NAME + "(" +
            MOVIE_ID + " INTEGER NOT NULL PRIMARY KEY," +
            ORIGINAL_TITLE +  " TEXT NOT NULL," +
            OVERVIEW + " TEXT NOT NULL," +
            RELEASE_DATE + " TEXT NOT NULL," +
            POSTER_PATH + " TEXT NOT NULL," +
            POPULARITY + " REAL NOT NULL," +
            RATING + " REAL NOT NULL," +
            FAVORITE + "  INTEGER NOT NULL DEFAULT 0)";
}
