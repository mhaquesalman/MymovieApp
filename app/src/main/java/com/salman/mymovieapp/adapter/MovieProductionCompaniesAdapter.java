package com.salman.mymovieapp.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.salman.mymovieapp.R;
import com.salman.mymovieapp.activity.ArtistDetailActivity;
import com.salman.mymovieapp.model.MovieCreditsCast;
import com.salman.mymovieapp.model.MovieDetailsProductionCompanies;
import com.salman.mymovieapp.viewholder.MovieCreditsViewHolder;
import com.salman.mymovieapp.viewholder.MovieProductionCompaniesViewHolder;

import java.util.List;

public class MovieProductionCompaniesAdapter extends RecyclerView.Adapter<MovieProductionCompaniesViewHolder> {
    private Activity activity;
    List<MovieDetailsProductionCompanies> movieDetailsProductionCompaniesList;

    public MovieProductionCompaniesAdapter(Activity activity, List<MovieDetailsProductionCompanies> movieDetailsProductionCompaniesList) {
        this.activity = activity;
        this.movieDetailsProductionCompaniesList = movieDetailsProductionCompaniesList;
    }

    @NonNull
    @Override
    public MovieProductionCompaniesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(activity).inflate(R.layout.production_company_layout, viewGroup, false);
        return new MovieProductionCompaniesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieProductionCompaniesViewHolder movieProductionCompaniesViewHolder, int i) {
        final MovieDetailsProductionCompanies movieDetailsProductionCompanies = movieDetailsProductionCompaniesList.get(i);
        movieProductionCompaniesViewHolder.setProductionCompanyImage(activity, movieDetailsProductionCompanies.getLogo_path());
        movieProductionCompaniesViewHolder.productionCompanyName.setText(movieDetailsProductionCompanies.getName());

    }

    @Override
    public int getItemCount() {
        return movieDetailsProductionCompaniesList.size();
    }
}
