package com.salman.mymovieapp.model;

import java.util.List;

public class ArtistImages {
    private List<ArtistImagesProfiles> profiles;
    private Integer id;

    public ArtistImages() {
    }

    public ArtistImages(List<ArtistImagesProfiles> profile, Integer id) {
        this.profiles = profile;
        this.id = id;
    }

    public List<ArtistImagesProfiles> getProfiles() {
        return profiles;
    }

    public void setProfiles(List<ArtistImagesProfiles> profiles) {
        this.profiles = profiles;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
