package com.salman.mymovieapp.model;

import java.util.List;

class ArtistResponseResultsKnownFor {
    private float vote_average;
    private Integer vote_count;
    private Integer id;
    private boolean video;
    private String media_type;
    private String title;
    private String popularity;
    private String poster_path;
    private String original_language;
    private String original_title;
    private List<Integer> genre;
    private String backdrop_path;
    private String overview;
    private String release_date;

    public ArtistResponseResultsKnownFor() {
    }

    public ArtistResponseResultsKnownFor(float vote_average, Integer vote_count, Integer id, boolean video, String media_type, String title, String popularity, String poster_path, String original_language, String original_title, List<Integer> genre, String backdrop_path, String overview, String release_date) {
        this.vote_average = vote_average;
        this.vote_count = vote_count;
        this.id = id;
        this.video = video;
        this.media_type = media_type;
        this.title = title;
        this.popularity = popularity;
        this.poster_path = poster_path;
        this.original_language = original_language;
        this.original_title = original_title;
        this.genre = genre;
        this.backdrop_path = backdrop_path;
        this.overview = overview;
        this.release_date = release_date;
    }

    public float getVote_average() {
        return vote_average;
    }

    public void setVote_average(float vote_average) {
        this.vote_average = vote_average;
    }

    public Integer getVote_count() {
        return vote_count;
    }

    public void setVote_count(Integer vote_count) {
        this.vote_count = vote_count;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean isVideo() {
        return video;
    }

    public void setVideo(boolean video) {
        this.video = video;
    }

    public String getMedia_type() {
        return media_type;
    }

    public void setMedia_type(String media_type) {
        this.media_type = media_type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPopularity() {
        return popularity;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    public String getPoster_path() {
        //create a base url for this poster
        String baseUrl = "https://image.tmdb.org/t/p/w500";
        return baseUrl + poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public void setOriginal_language(String original_language) {
        this.original_language = original_language;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public List<Integer> getGenre() {
        return genre;
    }

    public void setGenre(List<Integer> genre) {
        this.genre = genre;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }
}
