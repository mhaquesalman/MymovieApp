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
import com.salman.mymovieapp.model.ArtistResponseResults;
import com.salman.mymovieapp.viewholder.SearchViewHolder;

import java.util.List;

public class ArtistSearchAdapter extends RecyclerView.Adapter<SearchViewHolder> {
    private Activity activity;
    private List<ArtistResponseResults> results;

    public ArtistSearchAdapter(Activity activity, List<ArtistResponseResults> results) {
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
    public void onBindViewHolder(@NonNull SearchViewHolder searchViewHolder, int i) {
        ArtistResponseResults responseResults = results.get(i);
        searchViewHolder.setPosterImage(activity, responseResults.getProfile_path());

        String title = responseResults.getName();
        final int id = responseResults.getId();

        if (title != null){
            searchViewHolder.posterTitle.setVisibility(View.VISIBLE);
            searchViewHolder.posterTitle.setText(title);
        } else {
            searchViewHolder.posterTitle.setVisibility(View.GONE);
        }

        searchViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, ArtistDetailActivity.class);
                intent.putExtra("id", String.valueOf(id));
                activity.startActivity(intent);

                //create some animation to open the new activity
                activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
    }

    @Override
    public int getItemCount() {
        return results.size();
    }
}
