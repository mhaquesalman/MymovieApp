package com.salman.mymovieapp.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import com.codewaves.youtubethumbnailview.ThumbnailLoader;
import com.flaviofaria.kenburnsview.KenBurnsView;
import com.github.lzyzsd.circleprogress.ArcProgress;
import com.salman.mymovieapp.R;
import com.salman.mymovieapp.adapter.MovieCreditsCastAdapter;
import com.salman.mymovieapp.adapter.MovieCreditsCrewAdapter;
import com.salman.mymovieapp.adapter.MoviePosterImagesAdapter;
import com.salman.mymovieapp.adapter.MovieProductionCompaniesAdapter;
import com.salman.mymovieapp.adapter.MovieVideosAdapter;
import com.salman.mymovieapp.client.RetrofitClient;
import com.salman.mymovieapp.model.MovieCredits;
import com.salman.mymovieapp.model.MovieCreditsCast;
import com.salman.mymovieapp.model.MovieCreditsCrew;
import com.salman.mymovieapp.model.MovieDetails;
import com.salman.mymovieapp.model.MovieDetailsGenres;
import com.salman.mymovieapp.model.MovieDetailsProductionCompanies;
import com.salman.mymovieapp.model.MovieDetailsProductionCountries;
import com.salman.mymovieapp.model.MovieImages;
import com.salman.mymovieapp.model.MovieImagesBackDropsAndPosters;
import com.salman.mymovieapp.model.MovieVideos;
import com.salman.mymovieapp.model.MovieVideosResults;
import com.salman.mymovieapp.services.RetrofitService;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailsActivity extends AppCompatActivity {
    private static final String API = "AIzaSyBkTuTV_NaS0gx2ob7eKRdFDh599oEdhMo";

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
    private LinearLayoutCompat movieDetailCastLayout;
    private LinearLayoutCompat movieDetailCrewLayout;
    private LinearLayoutCompat movieDetailProductionCompaniesLayout;
    private LinearLayoutCompat movieDetailImagesLayout;
    private LinearLayoutCompat movieDetailVideosLayout;

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

    private RecyclerView movieDetailsCastRecyclerView;
    private RecyclerView movieDetailsCrewRecyclerView;
    private RecyclerView movieDetailProductionCompaniesRecyclerView;
    private RecyclerView movieDetailImagesRecyclerView;
    private RecyclerView movieDetailVideosRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        Intent intent = getIntent();
        RetrofitService retrofitService = RetrofitClient.getClient().create(RetrofitService.class);

        ThumbnailLoader.initialize(API);

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
        movieDetailCastLayout = findViewById(R.id.movie_detail_cast_layout);
        movieDetailCrewLayout = findViewById(R.id.movie_detail_crew_layout);
        movieDetailProductionCompaniesLayout = findViewById(R.id.movie_detail_production_companies_layout);
        movieDetailImagesLayout = findViewById(R.id.movie_detail_images_layout);
        movieDetailVideosLayout = findViewById(R.id.movie_detail_videos_layout);


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

        movieDetailsCastRecyclerView = findViewById(R.id.movie_detail_cast_RV);
        movieDetailsCastRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        movieDetailsCrewRecyclerView = findViewById(R.id.movie_detail_crew_RV);
        movieDetailsCrewRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        movieDetailProductionCompaniesRecyclerView = findViewById(R.id.movie_detail_production_companies_RV);
        movieDetailProductionCompaniesRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        movieDetailImagesRecyclerView = findViewById(R.id.movie_detail_images_RV);
        movieDetailImagesRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        movieDetailVideosRecyclerView = findViewById(R.id.movie_detail_videos_RV);
        movieDetailVideosRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

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

                Call<MovieCredits> movieCreditsCall = retrofitService.getMovieCreditsByid(id, RetrofitClient.getApiKey());
                movieCreditsCall.enqueue(new Callback<MovieCredits>() {
                    @Override
                    public void onResponse(Call<MovieCredits> call, Response<MovieCredits> response) {
                        MovieCredits movieCreditsResponse = response.body();
                        if (movieCreditsResponse != null){
                            List<MovieCreditsCast> movieCreditsCastList = movieCreditsResponse.getCast();
                            if (movieCreditsCastList != null && movieCreditsCastList.size() > 0){
                                MovieCreditsCastAdapter movieCreditsCastAdapter = new MovieCreditsCastAdapter(MovieDetailsActivity.this, movieCreditsCastList);
                                movieDetailsCastRecyclerView.setAdapter(movieCreditsCastAdapter);
                                movieDetailCastLayout.setVisibility(View.VISIBLE);

                                LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(MovieDetailsActivity.this, R.anim.layout_slide_right);
                                movieDetailsCastRecyclerView.setLayoutAnimation(controller);
                                movieDetailsCastRecyclerView.scheduleLayoutAnimation();
                            } else {
                                movieDetailCastLayout.setVisibility(View.GONE);
                            }

                            List<MovieCreditsCrew> movieCreditsCrewList = movieCreditsResponse.getCrew();
                            if (movieCreditsCrewList != null && movieCreditsCrewList.size() > 0){
                                MovieCreditsCrewAdapter movieCreditsCrewAdapter = new MovieCreditsCrewAdapter(MovieDetailsActivity.this, movieCreditsCrewList);
                                movieDetailsCrewRecyclerView.setAdapter(movieCreditsCrewAdapter);
                                movieDetailCrewLayout.setVisibility(View.VISIBLE);

                                LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(MovieDetailsActivity.this, R.anim.layout_slide_right);
                                movieDetailsCrewRecyclerView.setLayoutAnimation(controller);
                                movieDetailsCrewRecyclerView.scheduleLayoutAnimation();
                            } else {
                                movieDetailCrewLayout.setVisibility(View.GONE);
                            }

                        } else {
                            movieDetailCastLayout.setVisibility(View.GONE);
                            movieDetailCrewLayout.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onFailure(Call<MovieCredits> call, Throwable t) {

                    }
                });

                Call<MovieImages> movieImagesCall = retrofitService.getMovieImagesByid(id, RetrofitClient.getApiKey());
                movieImagesCall.enqueue(new Callback<MovieImages>() {
                    @Override
                    public void onResponse(Call<MovieImages> call, Response<MovieImages> response) {
                        MovieImages movieImagesResponse = response.body();
                        if (movieImagesResponse != null) {
                            ArrayList<MovieImagesBackDropsAndPosters> movieImagesBackDropsAndPostersArrayList = new ArrayList<>();
                            List<MovieImagesBackDropsAndPosters> movieImagesBackDropsList = movieImagesResponse.getBackdrops();
                            List<MovieImagesBackDropsAndPosters> movieImagesPosterList = movieImagesResponse.getPosters();
                            if (movieImagesBackDropsList != null && movieImagesBackDropsList.size() > 0){
                                if (movieImagesPosterList != null && movieImagesPosterList.size() >0){
                                    movieImagesBackDropsAndPostersArrayList.addAll(movieImagesBackDropsList);
                                    movieImagesBackDropsAndPostersArrayList.addAll(movieImagesPosterList);
                                } else {
                                    movieImagesBackDropsAndPostersArrayList.addAll(movieImagesBackDropsList);
                                }
                            } else if (movieImagesPosterList != null && movieImagesPosterList.size() > 0) {
                                movieImagesBackDropsAndPostersArrayList.addAll(movieImagesPosterList);
                            } else {
                                movieImagesBackDropsAndPostersArrayList.clear();
                                movieDetailImagesLayout.setVisibility(View.GONE);
                            }
                            if (movieImagesBackDropsAndPostersArrayList.size() > 0){
                                MoviePosterImagesAdapter moviePosterImagesAdapter = new MoviePosterImagesAdapter(MovieDetailsActivity.this, movieImagesBackDropsAndPostersArrayList);
                                movieDetailImagesRecyclerView.setAdapter(moviePosterImagesAdapter);
                                movieDetailImagesRecyclerView.setVisibility(View.VISIBLE);

                                LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(MovieDetailsActivity.this, R.anim.layout_slide_right);
                                movieDetailImagesRecyclerView.setLayoutAnimation(controller);
                                movieDetailImagesRecyclerView.scheduleLayoutAnimation();

                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<MovieImages> call, Throwable t) {
                        Log.e("MovieDetailsActivity", "onFailure:movieImagesCall "+ t.getMessage());
                    }
                });

                Call<MovieVideos> movieVideosCall = retrofitService.getMovieVideosByid(id, RetrofitClient.getApiKey());
                movieVideosCall.enqueue(new Callback<MovieVideos>() {
                    @Override
                    public void onResponse(Call<MovieVideos> call, Response<MovieVideos> response) {
                        MovieVideos movieVideosResponse = response.body();
                        if (movieVideosResponse != null) {
                            List<MovieVideosResults> movieVideosResultsList = movieVideosResponse.getResults();
                            if (movieVideosResultsList != null && movieVideosResultsList.size() > 0) {
                                movieDetailVideosLayout.setVisibility(View.VISIBLE);
                                MovieVideosAdapter movieVideosAdapter = new MovieVideosAdapter(MovieDetailsActivity.this, movieVideosResultsList);
                                movieDetailVideosRecyclerView.setAdapter(movieVideosAdapter);

                                LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(MovieDetailsActivity.this, R.anim.layout_slide_right);
                                movieDetailVideosRecyclerView.setLayoutAnimation(controller);
                                movieDetailVideosRecyclerView.scheduleLayoutAnimation();
                            } else {
                                movieDetailVideosLayout.setVisibility(View.GONE);
                            }
                        } else {
                            movieDetailVideosLayout.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onFailure(Call<MovieVideos> call, Throwable t) {

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
        List<MovieDetailsProductionCompanies> movieDetailsProductionCompaniesList = movieDetailsResponse.getProduction_companies();
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
                runtime_details.setText(Integer.toString(runtime));
                runtime_layout.setVisibility(View.VISIBLE);
            } else {
                runtime_layout.setVisibility(View.GONE);
            }
        } else {
            runtime_layout.setVisibility(View.GONE);
        }

        if (budget != null){
            if (budget != 0){
                budget_details.setText(Integer.toString(budget));
                budget_layout.setVisibility(View.VISIBLE);
            } else {
                budget_layout.setVisibility(View.GONE);
            }
        } else {
            budget_layout.setVisibility(View.GONE);
        }

        if (revenue != null){
            if (revenue != 0){
                revenue_details.setText(Integer.toString(revenue));
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

        if (movieDetailsProductionCompaniesList != null && movieDetailsProductionCompaniesList.size() > 0){
            MovieProductionCompaniesAdapter movieProductionCompaniesAdapter = new MovieProductionCompaniesAdapter(MovieDetailsActivity.this, movieDetailsProductionCompaniesList);
            movieDetailProductionCompaniesRecyclerView.setAdapter(movieProductionCompaniesAdapter);
            movieDetailProductionCompaniesLayout.setVisibility(View.VISIBLE);

            LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(MovieDetailsActivity.this, R.anim.layout_slide_right);
            movieDetailProductionCompaniesRecyclerView.setLayoutAnimation(controller);
            movieDetailProductionCompaniesRecyclerView.scheduleLayoutAnimation();
        } else {
            movieDetailProductionCompaniesLayout.setVisibility(View.GONE);
        }

    }

    @Override
    public void finish() {
        super.finish();
        //create some animation to back to main activity
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
