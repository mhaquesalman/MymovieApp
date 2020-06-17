package com.salman.mymovieapp.viewholder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.flaviofaria.kenburnsview.KenBurnsView;
import com.salman.mymovieapp.R;
import com.squareup.picasso.Picasso;

public class MovieProductionCompaniesViewHolder extends RecyclerView.ViewHolder {
    private KenBurnsView productionCompanyImage;
    public AppCompatTextView productionCompanyName;

    public MovieProductionCompaniesViewHolder(@NonNull View itemView) {
        super(itemView);

        productionCompanyImage = itemView.findViewById(R.id.production_company_image);
        productionCompanyName = itemView.findViewById(R.id.production_company_name);

    }

    public void setProductionCompanyImage(Context context, String imageUrl) {
        Picasso.with(context).load(imageUrl).into(productionCompanyImage);
    }
}
