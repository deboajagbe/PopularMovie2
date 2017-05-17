package com.unicornheight.popularmovie2.datamap;

import com.unicornheight.popularmovie2.data.MovieStorage;
import com.unicornheight.popularmovie2.mvp.model.Review;
import com.unicornheight.popularmovie2.mvp.model.ReviewResponse;
import com.unicornheight.popularmovie2.mvp.model.ReviewResponseResults;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by deboajagbe on 5/12/17.
 */

public class ReviewMapper {
    @Inject
    public ReviewMapper() {
    }

    public List<Review> mapMoviesReview(MovieStorage storage, ReviewResponse response) {
        List<Review> trailerList = new ArrayList<>();

        if (response != null) {
            ReviewResponseResults[] responseTrailer = response.getResults();
            if (responseTrailer != null) {
                for (ReviewResponseResults trailer : responseTrailer) {
                    Review myTrailer = new Review();
                    myTrailer.setId(trailer.getId());
                    myTrailer.setAuthor(trailer.getAuthor());
                    myTrailer.setContent(trailer.getContent());
                    myTrailer.setUrl(trailer.getUrl());
                    myTrailer.setName(trailer.getName());
                    myTrailer.setKey(trailer.getKey());
                    myTrailer.setSize(trailer.getSize());
                    myTrailer.setSite(trailer.getSite());
                    //  storage.addMovie(myMovie);
                    trailerList.add(myTrailer);
                }
            }
        }
        return trailerList;
    }
}
