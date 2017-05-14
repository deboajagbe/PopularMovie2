package com.unicornheight.popularmovie2.datamap;

import com.unicornheight.popularmovie2.mvp.model.Movie;
import com.unicornheight.popularmovie2.mvp.model.MovieResponse;
import com.unicornheight.popularmovie2.mvp.model.MovieResponseResults;
import com.unicornheight.popularmovie2.mvp.model.Storage;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by deboajagbe on 5/10/17.
 */

public class MovieMapper {
    @Inject
    public MovieMapper() {
    }
    public List<Movie> mapMovies(Storage storage, MovieResponse response) {
        List<Movie> movieList = new ArrayList<>();

        if (response != null) {
            MovieResponseResults[] responseMovies = response.getResults();
            if (responseMovies != null) {
                for (MovieResponseResults movie : responseMovies) {
                    Movie myMovie = new Movie();
                    myMovie.setId(movie.getId());
                    myMovie.setOverview(movie.getOverview());
                    myMovie.setRelease_date(movie.getRelease_date());
                    myMovie.setOriginal_title(movie.getOriginal_title() );
                    myMovie.setPopularity(movie.getPopularity());
                    myMovie.setVote_average(movie.getVote_average());
                    myMovie.setPoster_path(movie.getPoster_path());
                    storage.addMovie(myMovie);
                    movieList.add(myMovie);
                }
            }
        }
        return movieList;
    }

}