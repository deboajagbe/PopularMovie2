package com.unicornheight.popularmovie2.mvp.model;

public class MovieResponse implements java.io.Serializable {
    private static final long serialVersionUID = -248919118664396367L;
    private int page;
    private int total_pages;
    private MovieResponseResults[] results;
    private int total_results;

    public int getPage() {
        return this.page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotal_pages() {
        return this.total_pages;
    }

    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }

    public MovieResponseResults[] getResults() {
        return this.results;
    }

    public void setResults(MovieResponseResults[] results) {
        this.results = results;
    }

    public int getTotal_results() {
        return this.total_results;
    }

    public void setTotal_results(int total_results) {
        this.total_results = total_results;
    }
}
