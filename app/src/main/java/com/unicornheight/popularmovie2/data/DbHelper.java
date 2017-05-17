package com.unicornheight.popularmovie2.data;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.unicornheight.popularmovie2.data.MovieContract.MovieEntry;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;

/**
 * Created by deboajagbe on 5/14/17.
 */

public class DbHelper extends SQLiteOpenHelper {


    public static final String DATABASE_NAME = "movies.db";
    private static final int VERSION = 1;


    DbHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(CREATE_TABLE);
        } catch (SQLException e) {
            Log.d(TAG, e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + MovieEntry.TABLE_NAME);
        onCreate(db);
    }

    final String CREATE_TABLE = "create table " + MovieEntry.TABLE_NAME + "(" +
            MovieEntry.MOVIE_ID + " INTEGER NOT NULL PRIMARY KEY," +
            MovieEntry.ORIGINAL_TITLE + " TEXT NOT NULL," +
            MovieEntry.OVERVIEW + " TEXT NOT NULL," +
            MovieEntry.RELEASE_DATE + " TEXT NOT NULL," +
            MovieEntry.POSTER_PATH + " TEXT NOT NULL," +
            MovieEntry.POPULARITY + " REAL NOT NULL," +
            MovieEntry.RATING + " REAL NOT NULL," +
            MovieEntry.FAVORITE + "  INTEGER NOT NULL DEFAULT 0)";
}
