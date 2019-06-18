package com.salman.mymovieapp.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.salman.mymovieapp.R;
import com.salman.mymovieapp.model.MovieResponseResults;
import com.salman.mymovieapp.viewholder.SearchViewHolder;

import java.util.List;

public class MovieSearchAdapter extends RecyclerView.Adapter<SearchViewHolder> {
    Activity activity;
    List<MovieResponseResults> results;

    public MovieSearchAdapter(Activity activity, List<MovieResponseResults> results) {
        this.activity = activity;
        this.results = results;
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(activity).inflate(R.layout.search__items, viewGroup, false);
        return new SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder movieSearchViewHolder, int i) {
        MovieResponseResults responseResults = results.get(i);
        movieSearchViewHolder.setPosterImage(activity, responseResults.getPoster_path());

        String title = responseResults.getTitle();
        if (title != null){
            movieSearchViewHolder.posterTitle.setVisibility(View.VISIBLE);
            movieSearchViewHolder.posterTitle.setText(title);
        } else {
            movieSearchViewHolder.posterTitle.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return results.size();
    }
}
