package com.unicornheight.popularmovie2.mvp.model;

import java.io.Serializable;

/**
 * Created by deboajagbe on 5/10/17.
 */

public class Movie implements Serializable {
    private int Id;
    private String overview;
    private String original_title;
    private boolean video;
    private String poster_path;
    private String release_date;
    private double popularity;
    private double vote_average;
    private int vote_count;
    private int favorite;


    public boolean isFavorite() {
        return favorite() > 0;
    }

    public int favorite() {
        return 0;
    }

    public int getId() {
        return Id;
    }

    public int getFavorite() {
        return favorite;
    }

    public void setFavorite(int favorite) {
        this.favorite = favorite;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public boolean isVideo() {
        return video;
    }

    public void setVideo(boolean video) {
        this.video = video;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public double getVote_average() {
        return vote_average;
    }

    public void setVote_average(double vote_average) {
        this.vote_average = vote_average;
    }

    public int getVote_count() {
        return vote_count;
    }

    public void setVote_count(int vote_count) {
        this.vote_count = vote_count;
    }
}
