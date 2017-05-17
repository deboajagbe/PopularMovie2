package com.unicornheight.popularmovie2.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by deboajagbe on 5/14/17.
 */

public class MovieContract {

    public static final String CONSTITUTED_AUTHORITY = "com.unicornheight.popularmovie2";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONSTITUTED_AUTHORITY);
    public static final String MOVIE_PATH = "movies";


    public static final class MovieEntry implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI
                .buildUpon().appendPath(MOVIE_PATH).build();

        public static final String ORIGINAL_TITLE = "title";
        public static final String MOVIE_ID = "_id";
        public static final String FAVORITE = "favourite";
        public static final String POPULARITY = "popularity";
        public static final String RATING = "ratings";
        public static final String OVERVIEW = "overview";
        public static final String RELEASE_DATE = "releaseDate";
        public static final String POSTER_PATH = "posterPath";
        public static final String TABLE_NAME = "movies";

    }
}
