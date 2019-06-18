package com.salman.mymovieapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.AdapterView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.salman.mymovieapp.adapter.ArtistSearchAdapter;
import com.salman.mymovieapp.adapter.MovieSearchAdapter;
import com.salman.mymovieapp.client.RetrofitClient;
import com.salman.mymovieapp.model.ArtistResponse;
import com.salman.mymovieapp.model.ArtistResponseResults;
import com.salman.mymovieapp.model.MovieResponse;
import com.salman.mymovieapp.model.MovieResponseResults;
import com.salman.mymovieapp.services.RetrofitService;

import org.angmarch.views.NiceSpinner;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    NiceSpinner sourceSpinner;
    AppCompatEditText queryEditText;
    AppCompatButton querySearchButton;
    RecyclerView resultsRecyclerView;
    String movie = "By Movie Title";
    String artist = "By Artist Name";
    RetrofitService retrofitService;
    MovieSearchAdapter movieSearchAdapter;
    ArtistSearchAdapter artistSearchAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // disable the keyword on start
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        sourceSpinner = findViewById(R.id.source_spinner);
        queryEditText = findViewById(R.id.edit_query);
        querySearchButton = findViewById(R.id.query_searchbtn);

        resultsRecyclerView = findViewById(R.id.result_recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        resultsRecyclerView.setLayoutManager(linearLayoutManager);

        Paper.init(this);

        retrofitService = RetrofitClient.getClient().create(RetrofitService.class);

        final ArrayList<String> category = new ArrayList<>();

        //set list for sourceSpinner
        category.add(movie);
        category.add(artist);
        sourceSpinner.attachDataSource(category);

        //retrieve the position at start and set the spinner
        if (Paper.book().read("position") != null){
            int position = Paper.book().read("position");
            sourceSpinner.setSelectedIndex(position);
        }


        // set the text on edit text onCreate
        int position = sourceSpinner.getSelectedIndex();
        if (position == 0){
            queryEditText.setHint("Enter any movie title...");
        } else {
            queryEditText.setHint("Enter any artist name...");
        }

        sourceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                // when sourceSpinner is clicked change the text of the edittext
                if (position == 0){
                    queryEditText.setHint("Enter any movie title...");
                } else {
                    queryEditText.setHint("Enter any artist name...");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //retrieve the result from paper db
        if (Paper.book().read("cache") != null){
            String result = Paper.book().read("cache");
            if (Paper.book().read("source") != null){
                String source = Paper.book().read("cache");
                if (source.equals("movie")){
                    //convert the string cache to model MovieResponse class using gson
                    MovieResponse movieResponse = new Gson().fromJson(result, MovieResponse.class);
                    if (movieResponse != null){
                        List<MovieResponseResults> movieResponseResults = movieResponse.getResults();

                        movieSearchAdapter = new MovieSearchAdapter(MainActivity.this, movieResponseResults);

                        resultsRecyclerView.setAdapter(movieSearchAdapter);

                        //create some animations to recycler view
                        LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(MainActivity.this,R.anim.layout_slide_right);
                        resultsRecyclerView.setLayoutAnimation(controller);
                        resultsRecyclerView.scheduleLayoutAnimation();

                        //now store the result in paper db to access offline
                        Paper.book().write("cache", new Gson().toJson(movieResponse));
                        //store also the category to set the spinner at app start
                        Paper.book().write("source","movie");

                    }
                } else {
                    //convert the string cache to model ArtistResponse class using gson
                    ArtistResponse artistResponse = new Gson().fromJson(result, ArtistResponse.class);
                    if (artistResponse != null){
                        List<ArtistResponseResults> artistResponseResults = artistResponse.getResults();

                        artistSearchAdapter = new ArtistSearchAdapter(MainActivity.this, artistResponseResults);

                        resultsRecyclerView.setAdapter(artistSearchAdapter);

                        //create some animations to recycler view
                        LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(MainActivity.this,R.anim.layout_slide_right);
                        resultsRecyclerView.setLayoutAnimation(controller);
                        resultsRecyclerView.scheduleLayoutAnimation();

                        //now store the result in paper db to access offline
                        Paper.book().write("cache", new Gson().toJson(artistResponse));
                        //store also the category to set the spinner at app start
                        Paper.book().write("source","artist");

                    }
                }
            }
        }


        // get the query from user
        querySearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (queryEditText.getText() != null){
                    String query = queryEditText.getText().toString();

                    if (query.equals("") || query.equals(" ")){
                        Toast.makeText(MainActivity.this, "please enter any text..", Toast.LENGTH_SHORT).show();
                    } else {
                        queryEditText.setText("");
                        // get the category to search the query for movie or artist
                        String finalQuery = query.replaceAll(" ", "+");
                        if (category.size() > 0){
                            String categoryName = category.get(sourceSpinner.getSelectedIndex());
                            if (categoryName.equals(movie)){
                                Call<MovieResponse> movieResponseCall = retrofitService.getMoviesByQuery(RetrofitClient.getApiKey(),finalQuery);
                                movieResponseCall.enqueue(new Callback<MovieResponse>() {
                                    @Override
                                    public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                                        MovieResponse movieResponse = response.body();
                                        if (movieResponse != null){
                                            List<MovieResponseResults> movieResponseResults = movieResponse.getResults();

                                            movieSearchAdapter = new MovieSearchAdapter(MainActivity.this, movieResponseResults);
                                            resultsRecyclerView.setAdapter(movieSearchAdapter);

                                            //create some animations to recycler view
                                            LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(MainActivity.this,R.anim.layout_slide_right);
                                            resultsRecyclerView.setLayoutAnimation(controller);
                                            resultsRecyclerView.scheduleLayoutAnimation();

                                            //now store the result in paper db to access offline
                                            Paper.book().write("cache", new Gson().toJson(movieResponse));
                                            //store also the category to set the spinner at app start
                                            Paper.book().write("source","movie");

                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<MovieResponse> call, Throwable t) {
                                        Toast.makeText(MainActivity.this, "something wrong", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }else {

                                Call<ArtistResponse> artistResponseCall = retrofitService.getArtistsByQuery(RetrofitClient.getApiKey(),finalQuery);
                                artistResponseCall.enqueue(new Callback<ArtistResponse>() {
                                    @Override
                                    public void onResponse(Call<ArtistResponse> call, Response<ArtistResponse> response) {
                                        ArtistResponse artistResponse = response.body();
                                        if (artistResponse != null){
                                            List<ArtistResponseResults> artistResponseResults = artistResponse.getResults();

                                            artistSearchAdapter = new ArtistSearchAdapter(MainActivity.this, artistResponseResults);
                                            resultsRecyclerView.setAdapter(artistSearchAdapter);

                                            //create some animations to recycler view
                                            LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(MainActivity.this,R.anim.layout_slide_right);
                                            resultsRecyclerView.setLayoutAnimation(controller);
                                            resultsRecyclerView.scheduleLayoutAnimation();

                                            //now store the result in paper db to access offline
                                            Paper.book().write("cache", new Gson().toJson(artistResponse));
                                            //store also the category to set the spinner at app start
                                            Paper.book().write("source","artist");

                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<ArtistResponse> call, Throwable t) {
                                        Toast.makeText(MainActivity.this, "something wrong", Toast.LENGTH_SHORT).show();
                                    }
                                });


                            }
                        }
                    }
                }
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        //set the position of spinner in offline to retrieve at start
        Paper.book().write("position", sourceSpinner.getSelectedIndex());
    }
}
