package com.salman.mymovieapp.model;

import java.util.List;

public class ArtistImages {
    private List<ArtistImagesProfiles> profile;
    private Integer id;

    public ArtistImages() {
    }

    public ArtistImages(List<ArtistImagesProfiles> profile, Integer id) {
        this.profile = profile;
        this.id = id;
    }

    public List<ArtistImagesProfiles> getProfile() {
        return profile;
    }

    public void setProfile(List<ArtistImagesProfiles> profile) {
        this.profile = profile;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
