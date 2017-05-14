package com.unicornheight.popularmovie2.mvp.model;

public class ReviewResponse implements java.io.Serializable {
    private static final long serialVersionUID = -9134990261719052876L;
    private int id;
    private int page;
    private int total_pages;
    private ReviewResponseResults[] results;
    private int total_results;

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public ReviewResponseResults[] getResults() {
        return this.results;
    }

    public void setResults(ReviewResponseResults[] results) {
        this.results = results;
    }

    public int getTotal_results() {
        return this.total_results;
    }

    public void setTotal_results(int total_results) {
        this.total_results = total_results;
    }
}
