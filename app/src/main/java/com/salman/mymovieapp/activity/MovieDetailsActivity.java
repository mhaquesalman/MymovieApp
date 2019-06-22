package com.salman.mymovieapp.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.View;

import com.flaviofaria.kenburnsview.KenBurnsView;
import com.github.lzyzsd.circleprogress.ArcProgress;
import com.salman.mymovieapp.R;
import com.salman.mymovieapp.client.RetrofitClient;
import com.salman.mymovieapp.model.MovieDetails;
import com.salman.mymovieapp.model.MovieDetailsGenres;
import com.salman.mymovieapp.model.MovieDetailsProductionCountries;
import com.salman.mymovieapp.services.RetrofitService;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailsActivity extends AppCompatActivity {

    private KenBurnsView movie_details_poster;
    private CircleImageView movie_details_poster_cv;
    private ArcProgress movie_details_ratingbar;

    private LinearLayoutCompat original_title_layout;
    private LinearLayoutCompat original_language_layout;
    private LinearLayoutCompat adult_layout;
    private LinearLayoutCompat status_layout;
    private LinearLayoutCompat runtime_layout;
    private LinearLayoutCompat budget_layout;
    private LinearLayoutCompat revenue_layout;
    private LinearLayoutCompat genre_layout;
    private LinearLayoutCompat production_countires_layout;
    private LinearLayoutCompat release_date_layout;
    private LinearLayoutCompat homepage_layout;
    private LinearLayoutCompat overview_layout;

    private AppCompatTextView movie_details_title;
    private AppCompatTextView original_title;
    private AppCompatTextView original_language;
    private AppCompatTextView adult_details;
    private AppCompatTextView status_details;
    private AppCompatTextView runtime_details;
    private AppCompatTextView budget_details;
    private AppCompatTextView revenue_details;
    private AppCompatTextView genre_details;
    private AppCompatTextView production_countires;
    private AppCompatTextView release_date;
    private AppCompatTextView homepage_details;
    private AppCompatTextView overview_details;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        Intent intent = getIntent();
        RetrofitService retrofitService = RetrofitClient.getClient().create(RetrofitService.class);

        movie_details_poster = findViewById(R.id.movie_details_poster);
        movie_details_poster_cv = findViewById(R.id.movie_details_poster_cv);
        movie_details_ratingbar = findViewById(R.id.movie_details_ratingbar);

        original_title_layout = findViewById(R.id.original_title_layout);
        original_language_layout = findViewById(R.id.original_language_layout);
        adult_layout = findViewById(R.id.adult_layout);
        status_layout = findViewById(R.id.status_layout);
        runtime_layout = findViewById(R.id.runtime_layout);
        budget_layout = findViewById(R.id.budget_layout);
        revenue_layout = findViewById(R.id.revenue_layout);
        genre_layout = findViewById(R.id.genre_layout);
        production_countires_layout = findViewById(R.id.production_countires_layout);
        release_date_layout = findViewById(R.id.release_date_layout);
        homepage_layout = findViewById(R.id.homepage_layout);
        overview_layout = findViewById(R.id.overview_layout);

        movie_details_title = findViewById(R.id.movie_details_title);
        original_title = findViewById(R.id.original_title);
        original_language = findViewById(R.id.original_language);
        adult_details = findViewById(R.id.adult);
        status_details = findViewById(R.id.status);
        runtime_details = findViewById(R.id.runtime);
        budget_details = findViewById(R.id.budget);
        revenue_details = findViewById(R.id.revenue);
        genre_details = findViewById(R.id.genre);
        production_countires = findViewById(R.id.production_countires);
        release_date = findViewById(R.id.release_date);
        homepage_details = findViewById(R.id.homepage);
        overview_details = findViewById(R.id.overview);

        if (intent != null && intent.getExtras() != null){
            if (intent.getExtras().getString("id") != null){
                int id = Integer.parseInt(intent.getExtras().getString("id"));
                Call<MovieDetails> movieDetailsCall = retrofitService.getMovieDetailsByid(id, RetrofitClient.getApiKey());
                movieDetailsCall.enqueue(new Callback<MovieDetails>() {
                    @Override
                    public void onResponse(Call<MovieDetails> call, Response<MovieDetails> response) {
                        MovieDetails movieDetailsResponse = response.body();
                        if (movieDetailsResponse != null){
                            getMovieDetails(movieDetailsResponse);
                        }
                    }

                    @Override
                    public void onFailure(Call<MovieDetails> call, Throwable t) {

                    }
                });
            }
        }
    }

    public void getMovieDetails(MovieDetails movieDetailsResponse) {
        String posterPath = movieDetailsResponse.getPoster_path();
        String backDropPath = movieDetailsResponse.getBackdrop_path();
        Double voteAverage = movieDetailsResponse.getVote_average() * 10;
        String title = movieDetailsResponse.getTitle();
        String originalTitle = movieDetailsResponse.getOriginal_title();
        String originalLanguage = movieDetailsResponse.getOriginal_language();
        boolean adult = movieDetailsResponse.isAdult();
        String status = movieDetailsResponse.getStatus();
        Integer runtime = movieDetailsResponse.getRuntime();
        Integer budget = movieDetailsResponse.getBudget();
        Integer revenue = movieDetailsResponse.getRevenue();
        List<MovieDetailsGenres> movieDetailsGenresList = movieDetailsResponse.getGenres();
        List<MovieDetailsProductionCountries> movieDetailsProductionCountriesList = movieDetailsResponse.getProduction_countires();
        String releaseDate = movieDetailsResponse.getRelease_date();
        String homepage = movieDetailsResponse.getHomepage();
        String overview = movieDetailsResponse.getOverview();
        int rating = voteAverage.intValue();
        movie_details_ratingbar.setProgress(rating);

        Picasso.with(this).load(posterPath).into(movie_details_poster);
        Picasso.with(this).load(backDropPath).into(movie_details_poster_cv);

        movie_details_title.setText(title);

        if (originalTitle != null){
            if (originalTitle.length() > 0){
                original_title.setText(originalTitle);
                original_title_layout.setVisibility(View.VISIBLE);
            } else {
                original_title_layout.setVisibility(View.GONE);
            }
        } else {
            original_title_layout.setVisibility(View.GONE);
        }

        if (originalLanguage != null){
            if (originalLanguage.length() > 0){
                original_language.setText(originalLanguage);
                original_language_layout.setVisibility(View.VISIBLE);
            } else {
                original_language_layout.setVisibility(View.GONE);
            }
        } else {
            original_language_layout.setVisibility(View.GONE);
        }

        if (adult){
            adult_details.setText("Yes");
            adult_layout.setVisibility(View.VISIBLE);
        } else {
            adult_details.setText("No");
            adult_layout.setVisibility(View.VISIBLE);
        }

        if (status != null){
            if (status.length() > 0){
                status_details.setText(status);
                status_layout.setVisibility(View.VISIBLE);
            } else {
                status_layout.setVisibility(View.GONE);
            }
        } else {
            status_layout.setVisibility(View.GONE);
        }

        if (runtime != null){
            if (runtime != 0){
                runtime_details.setText(runtime);
                runtime_layout.setVisibility(View.VISIBLE);
            } else {
                runtime_layout.setVisibility(View.GONE);
            }
        } else {
            runtime_layout.setVisibility(View.GONE);
        }

        if (budget != null){
            if (budget != 0){
                budget_details.setText(budget);
                budget_layout.setVisibility(View.VISIBLE);
            } else {
                budget_layout.setVisibility(View.GONE);
            }
        } else {
            budget_layout.setVisibility(View.GONE);
        }

        if (revenue != null){
            if (revenue != 0){
                revenue_details.setText(revenue);
                revenue_layout.setVisibility(View.VISIBLE);
            } else {
                revenue_layout.setVisibility(View.GONE);
            }
        } else {
            revenue_layout.setVisibility(View.GONE);
        }

        if (movieDetailsGenresList != null && movieDetailsGenresList.size() > 0){
            StringBuilder stringBuilder = new StringBuilder();
            for (int i=0; i < movieDetailsGenresList.size(); i++){
                if (i == movieDetailsGenresList.size() -1 ) {
                    stringBuilder.append(movieDetailsGenresList.get(i).getName());
                } else {
                    stringBuilder.append(movieDetailsGenresList.get(i).getName()).append(", ");
                }
            }
            genre_details.setText(stringBuilder.toString());
            genre_layout.setVisibility(View.VISIBLE);
        } else {
            genre_layout.setVisibility(View.GONE);
        }

        if (movieDetailsProductionCountriesList != null && movieDetailsProductionCountriesList.size() > 0){
            StringBuilder stringBuilder = new StringBuilder();
            for (int i=0; i < movieDetailsProductionCountriesList.size(); i++){
                if (i == movieDetailsProductionCountriesList.size() -1 ) {
                    stringBuilder.append(movieDetailsProductionCountriesList.get(i).getName());
                } else {
                    stringBuilder.append(movieDetailsProductionCountriesList.get(i).getName()).append(", ");
                }
            }
            production_countires.setText(stringBuilder.toString());
            production_countires_layout.setVisibility(View.VISIBLE);
        } else {
            production_countires_layout.setVisibility(View.GONE);
        }

        if (releaseDate != null){
            if (releaseDate.length() > 0){
                release_date.setText(releaseDate);
                release_date_layout.setVisibility(View.VISIBLE);
            } else {
                release_date_layout.setVisibility(View.GONE);
            }
        } else {
            release_date_layout.setVisibility(View.GONE);
        }

        if (homepage != null){
            if (homepage.length() > 0){
                homepage_details.setText(homepage);
                homepage_layout.setVisibility(View.VISIBLE);
            } else {
                homepage_layout.setVisibility(View.GONE);
            }
        } else {
            homepage_layout.setVisibility(View.GONE);
        }

        if (overview != null){
            if (overview.length() > 0){
                overview_details.setText(overview);
                overview_layout.setVisibility(View.VISIBLE);
            } else {
                overview_layout.setVisibility(View.GONE);
            }
        } else {
            overview_layout.setVisibility(View.GONE);
        }
        
    }

    @Override
    public void finish() {
        super.finish();
        //create some animation to back to main activity
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
