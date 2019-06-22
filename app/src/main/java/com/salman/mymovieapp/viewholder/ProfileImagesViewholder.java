package com.salman.mymovieapp.viewholder;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.salman.mymovieapp.R;
import com.squareup.picasso.Picasso;

public class ProfileImagesViewholder extends RecyclerView.ViewHolder {
     public AppCompatImageView profileImage;

    public ProfileImagesViewholder(@NonNull View itemView) {
        super(itemView);
        profileImage = itemView.findViewById(R.id.profile_image);
    }

    public void setProfileImage(Activity activity, String profilePath){
        Picasso.with(activity).load(profilePath).into(profileImage);
    }
}
