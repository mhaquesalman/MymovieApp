package com.salman.mymovieapp.model;

import java.util.List;

public class ArtistResponseResults {
    private Double popularity;
    private Integer id;
    private String profile_path;
    private String name;
    private List<ArtistResponseResultsKnownFor> known_for;

    public ArtistResponseResults() {
    }

    public ArtistResponseResults(Double popularity, Integer id, String profile_path, String name, List<ArtistResponseResultsKnownFor> known_for) {
        this.popularity = popularity;
        this.id = id;
        this.profile_path = profile_path;
        this.name = name;
        this.known_for = known_for;
    }

    public Double getPopularity() {
        return popularity;
    }

    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProfile_path() {
        //create a base url for this poster
        String baseUrl = "https://image.tmdb.org/t/p/w500";
        return baseUrl + profile_path;
    }

    public void setProfile_path(String profile_path) {
        this.profile_path = profile_path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ArtistResponseResultsKnownFor> getKnown_for() {
        return known_for;
    }

    public void setKnown_for(List<ArtistResponseResultsKnownFor> known_for) {
        this.known_for = known_for;
    }
}
