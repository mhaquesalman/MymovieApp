package com.salman.mymovieapp.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.salman.mymovieapp.R;
import com.salman.mymovieapp.activity.ImageViewerActivity;
import com.salman.mymovieapp.model.ArtistImagesProfiles;
import com.salman.mymovieapp.model.MovieImagesBackDropsAndPosters;
import com.salman.mymovieapp.viewholder.ImagesViewHolder;

import java.util.List;

public class MoviePosterImagesAdapter extends RecyclerView.Adapter<ImagesViewHolder> {

    private Activity activity;
    private List<MovieImagesBackDropsAndPosters> movieImagesBackDropsAndPostersList;

    public MoviePosterImagesAdapter(Activity activity, List<MovieImagesBackDropsAndPosters> movieImagesBackDropsAndPostersList) {
        this.activity = activity;
        this.movieImagesBackDropsAndPostersList = movieImagesBackDropsAndPostersList;
    }

    @NonNull
    @Override
    public ImagesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(activity).inflate(R.layout.profile_images_layout, viewGroup, false);
        return new ImagesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ImagesViewHolder imagesViewHolder, int i) {
        final MovieImagesBackDropsAndPosters movieImagesBackDropsAndPosters = movieImagesBackDropsAndPostersList.get(i);
        imagesViewHolder.setProfileImage(activity, movieImagesBackDropsAndPosters.getFile_path());

        imagesViewHolder.profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, ImageViewerActivity.class);
                ActivityOptionsCompat compat = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, imagesViewHolder.profileImage, ViewCompat.getTransitionName(imagesViewHolder.profileImage));
                intent.putExtra("imageUrl", movieImagesBackDropsAndPosters.getFile_path());
                activity.startActivity(intent, compat.toBundle());

            }
        });
    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
