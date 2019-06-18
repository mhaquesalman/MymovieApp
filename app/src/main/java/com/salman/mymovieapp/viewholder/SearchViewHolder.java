package com.salman.mymovieapp.viewholder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import com.flaviofaria.kenburnsview.KenBurnsView;
import com.flaviofaria.kenburnsview.RandomTransitionGenerator;
import com.salman.mymovieapp.R;
import com.squareup.picasso.Picasso;

public class SearchViewHolder extends RecyclerView.ViewHolder {
    private KenBurnsView posterImage;
    public AppCompatTextView posterTitle;

    public SearchViewHolder(@NonNull View itemView) {
        super(itemView);

        posterImage = itemView.findViewById(R.id.poster_image);
        posterTitle = itemView.findViewById(R.id.poster_title);

        RandomTransitionGenerator generator = new RandomTransitionGenerator(1000, new DecelerateInterpolator());
        posterImage.setTransitionGenerator(generator);
    }

    public void setPosterImage(Context context, String posterUrl){
        Picasso.with(context).load(posterUrl).into(posterImage);
    }
}
