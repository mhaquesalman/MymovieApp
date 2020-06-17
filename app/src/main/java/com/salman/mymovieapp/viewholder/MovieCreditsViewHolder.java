package com.salman.mymovieapp.viewholder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.flaviofaria.kenburnsview.KenBurnsView;
import com.salman.mymovieapp.R;
import com.squareup.picasso.Picasso;

public class MovieCreditsViewHolder extends RecyclerView.ViewHolder {
    private KenBurnsView movieCreditsImage;
    public AppCompatTextView movieCreditsName;
    public AppCompatTextView getMovieCreditsCharacter;
    public MovieCreditsViewHolder(@NonNull View itemView) {
        super(itemView);

        movieCreditsImage = itemView.findViewById(R.id.movie_credits_image);
        movieCreditsName = itemView.findViewById(R.id.movie_credits_name);
        getMovieCreditsCharacter = itemView.findViewById(R.id.movie_credits_character);
    }

    public void setMovieCreditsImage(Context context, String imageUrl) {
        Picasso.with(context).load(imageUrl).into(movieCreditsImage);
    }
}
