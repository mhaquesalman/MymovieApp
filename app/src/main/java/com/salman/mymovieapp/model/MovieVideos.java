package com.salman.mymovieapp.model;

import java.util.List;

public class MovieVideos {
    private Integer id;
    private List<MovieVideosResults> results;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<MovieVideosResults> getResults() {
        return results;
    }

    public void setResults(List<MovieVideosResults> results) {
        this.results = results;
    }
}
