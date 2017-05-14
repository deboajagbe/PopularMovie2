package com.unicornheight.popularmovie2.api;



import com.unicornheight.popularmovie2.BuildConfig;
import com.unicornheight.popularmovie2.mvp.model.MovieResponse;
import com.unicornheight.popularmovie2.mvp.model.ReviewResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by deboajagbe on 5/10/17.
 */

public interface MovieApiService {

    String API_PARAM = "api_key";
    String TOKEN_API_KEY = BuildConfig.TOKEN_API_KEY;

    @GET("discover/movie?" + API_PARAM + "=" + TOKEN_API_KEY)
    Observable<MovieResponse> getMovies();

    @GET("discover/movie?" + API_PARAM + "=" + TOKEN_API_KEY)
    Call<MovieResponse> getTheMovies();

    @GET("movie/popular?" + API_PARAM + "=" + TOKEN_API_KEY)
    Observable<MovieResponse> getPopularMovies();

//    @GET("movie/popular?" + API_PARAM + "=" + TOKEN_API_KEY)
//    Call<MovieResponse> getThePopularMovies();

    @GET("movie/top_rated?" + API_PARAM + "=" + TOKEN_API_KEY)
    Observable<MovieResponse> getHighRatedMovies();

//    @GET("movie/top_rated?" + API_PARAM + "=" + TOKEN_API_KEY)
//    Call<MovieResponse> getTheHighRatedMovies();

    @GET("movie/{movie_id}/videos?" + API_PARAM + "=" + TOKEN_API_KEY)
    Observable<ReviewResponse> getMovieTrailers(@Path("movie_id") long movieId);

//    @GET("movie/{movie_id}/videos?" + API_PARAM + "=" + TOKEN_API_KEY)
//    Call<ReviewResponse> getTheMovieTrailers(@Path("movie_id") long movieId);

    @GET("movie/{movie_id}/reviews?"+ API_PARAM + "=" + TOKEN_API_KEY)
    Observable<ReviewResponse> getMovieReviews(@Path("movie_id") long movieId);

//    @GET("movie/{movie_id}/reviews?")
//    Call<ReviewResponse> getTheMovieReviews(@Path("movie_id") long movieId);



}
