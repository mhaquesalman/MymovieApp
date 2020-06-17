package com.salman.mymovieapp.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
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
import android.widget.Toast;

import com.flaviofaria.kenburnsview.KenBurnsView;
import com.salman.mymovieapp.R;
import com.salman.mymovieapp.adapter.ProfileImagesAdapter;
import com.salman.mymovieapp.client.RetrofitClient;
import com.salman.mymovieapp.model.ArtistDetails;
import com.salman.mymovieapp.model.ArtistImages;
import com.salman.mymovieapp.model.ArtistImagesProfiles;
import com.salman.mymovieapp.services.RetrofitService;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ArtistDetailActivity extends AppCompatActivity {
    private static final String TAG = "ArtistDetailActivity";
    KenBurnsView artist_image;
    LinearLayoutCompat also_knownas_layout;
    LinearLayoutCompat dateofbirth_layout;
    LinearLayoutCompat placeofbirth_layout;
    LinearLayoutCompat dateofdeath_layout;
    LinearLayoutCompat department_layout;
    LinearLayoutCompat homepage_layout;
    LinearLayoutCompat biography_layout;
    LinearLayoutCompat profile_layout;

    AppCompatTextView artistname;
    AppCompatTextView also_knownas;
    AppCompatTextView dateofbirth;
    AppCompatTextView placeofbirth;
    AppCompatTextView dateofdeath;
    AppCompatTextView department_details;
    AppCompatTextView homepage_details;
    AppCompatTextView biography_details;
    RecyclerView profileimage_details;

    ProfileImagesAdapter profileImagesAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist_detail);

        //initialize the views
        artistname =findViewById(R.id.artistname);
        artist_image = findViewById(R.id.artist_image);

        also_knownas_layout = findViewById(R.id.also_knownas_layout);
        dateofbirth_layout = findViewById(R.id.dateofbirth_layout);
        placeofbirth_layout = findViewById(R.id.placeofbirth_layout);
        dateofdeath_layout = findViewById(R.id.dateofdeath_layout);
        department_layout = findViewById(R.id.department_layout);
        homepage_layout = findViewById(R.id.homepage_layout);
        biography_layout = findViewById(R.id.biography_layout);
        profile_layout = findViewById(R.id.profile_layout);

        also_knownas = findViewById(R.id.also_knownas);
        dateofbirth = findViewById(R.id.dateofbirth);
        placeofbirth = findViewById(R.id.placeofbirth);
        dateofdeath = findViewById(R.id.dateofdeath);
        department_details = findViewById(R.id.department_details);
        homepage_details = findViewById(R.id.homepage_details);
        biography_details = findViewById(R.id.biography_details);

        profileimage_details = findViewById(R.id.profileimage_details);
        profileimage_details.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        RetrofitService retrofitService = RetrofitClient.getClient().create(RetrofitService.class);

        Intent intent = getIntent();
        if (intent != null && intent.getExtras()!= null){

            // get the id from the previous activity
            if (intent.getExtras().getString("id") != null ){
                int id = Integer.parseInt(intent.getExtras().getString("id"));

                Call<ArtistDetails> artistDetailsCall = retrofitService.getArtistDetailsByid(id, RetrofitClient.getApiKey());
                artistDetailsCall.enqueue(new Callback<ArtistDetails>() {
                    @Override
                    public void onResponse(@NonNull Call<ArtistDetails> call, @NonNull Response<ArtistDetails> response) {
                       ArtistDetails artistDetailsResponse = response.body();
                       // Log.d(TAG, "onResponse: "+ response.body().toString());
                       // Log.d(TAG, "onResponse: Bio: "+ response.body().getBiography());
                       // Log.d(TAG, "onResponse: Dept: "+ response.body().getKnown_for_department());
                        if (artistDetailsResponse != null){
                            getArtistDetails(artistDetailsResponse);
                        } else {
                            Toast.makeText(ArtistDetailActivity.this, "any details not found", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ArtistDetails> call, Throwable t) {
                        Toast.makeText(ArtistDetailActivity.this, "something wrong", Toast.LENGTH_SHORT).show();
                    }
                });

                Call<ArtistImages> artistImagesCall = retrofitService.getArtistImagesByid(id, RetrofitClient.getApiKey());
                artistImagesCall.enqueue(new Callback<ArtistImages>() {
                    @Override
                    public void onResponse(Call<ArtistImages> call, Response<ArtistImages> response) {
                        Log.d(TAG, "onResponse:artistImagesCall " + response.body());
                        ArtistImages artistImages = response.body();
                        if (artistImages != null){
                            List<ArtistImagesProfiles> artistImagesProfilesList = artistImages.getProfiles();
                            Log.d(TAG, "onResponse:ArtistImagesProfiles " + artistImagesProfilesList);
                            if (artistImagesProfilesList != null && artistImagesProfilesList.size() > 0){
                                profile_layout.setVisibility(View.VISIBLE);
                                profileImagesAdapter = new ProfileImagesAdapter(ArtistDetailActivity.this, artistImagesProfilesList);
                                profileimage_details.setAdapter(profileImagesAdapter);

                                LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(ArtistDetailActivity.this, R.anim.layout_slide_right);
                                profileimage_details.setLayoutAnimation(controller);
                                profileimage_details.scheduleLayoutAnimation();
                            } else {
                                profileimage_details.setVisibility(View.GONE);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ArtistImages> call, Throwable t) {
                        Toast.makeText(ArtistDetailActivity.this, "Images Loading Failed ", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }

    }

    public void getArtistDetails( ArtistDetails artistDetailsResponse) {

        String profilePath = artistDetailsResponse.getProfile_path();
        String name = artistDetailsResponse.getName();
        String birthday = artistDetailsResponse.getBirthday();
        String placeOfBirth = artistDetailsResponse.getPlace_of_birth();
        String dateOfDeath = artistDetailsResponse.getDeathday();
        String department = artistDetailsResponse.getKnown_for_department();
        Log.d(TAG, "getArtistDetails: DEPARTMENT: "+department);
        String homepage = artistDetailsResponse.getHomepage();
        String biography = artistDetailsResponse.getBiography();
        Log.d(TAG, "getArtistDetails: BIO: "+biography);
        List<String> alsoKnownAsList = artistDetailsResponse.getAlso_known_as();

        Picasso.with(this).load(profilePath).into(artist_image);

        if (name != null) {
            if (name.length() > 0) {
                artistname.setText(name);
                artistname.setVisibility(View.VISIBLE);
            } else {
                artistname.setVisibility(View.GONE);
            }
        } else {
            artistname.setVisibility(View.GONE);
        }

        if (alsoKnownAsList != null) {
            if (alsoKnownAsList.size() > 0) {
                StringBuilder stringBuilder = new StringBuilder();
                for (int i = 0; i < alsoKnownAsList.size(); i++) {
                    if (i == alsoKnownAsList.size() - 1) {
                        stringBuilder.append(alsoKnownAsList.get(i));
                    } else {
                        stringBuilder.append(alsoKnownAsList.get(i)).append(", ");
                    }
                }
                also_knownas.setText(stringBuilder.toString());
                also_knownas_layout.setVisibility(View.VISIBLE);
            } else {
                also_knownas_layout.setVisibility(View.GONE);
            }
        } else {
            also_knownas_layout.setVisibility(View.GONE);
        }

        if (birthday != null) {
            if (birthday.length() > 0) {
                dateofbirth.setText(birthday);
                dateofbirth_layout.setVisibility(View.VISIBLE);
            } else {
                dateofbirth_layout.setVisibility(View.GONE);
            }
        }

        if (placeOfBirth != null) {
            if (placeOfBirth.length() > 0) {
                placeofbirth.setText(placeOfBirth);
                placeofbirth_layout.setVisibility(View.VISIBLE);
            } else {
                placeofbirth_layout.setVisibility(View.GONE);
            }
        } else {
            placeofbirth_layout.setVisibility(View.GONE);
        }
            if (dateOfDeath != null) {
                if (dateOfDeath.length() > 0) {
                    dateofdeath.setText(dateOfDeath);
                    dateofdeath_layout.setVisibility(View.VISIBLE);
                } else {
                    dateofdeath_layout.setVisibility(View.GONE);
                }
            } else {
                dateofdeath_layout.setVisibility(View.GONE);
            }

            if (department != null) {
                if (department.length() > 0) {
                    Log.d(TAG, "getArtistDetails1: "+department);
                    department_details.setText(department);
                    Log.d(TAG, "getArtistDetails: "+department);
                    department_layout.setVisibility(View.VISIBLE);
                } else {
                    department_layout.setVisibility(View.GONE);
                }
            } else {
                department_layout.setVisibility(View.GONE);
            }

            if (homepage != null) {
                if (homepage.length() > 0) {
                    homepage_details.setText(homepage);
                    homepage_layout.setVisibility(View.VISIBLE);
                } else {
                    homepage_layout.setVisibility(View.GONE);
                }
            } else {
                homepage_layout.setVisibility(View.GONE);
            }

            if (biography != null) {
                if (biography.length() > 0) {
                    biography_details.setText(biography);
                    biography_layout.setVisibility(View.VISIBLE);
                } else {
                    biography_layout.setVisibility(View.GONE);
                }
            } else {
                biography_layout.setVisibility(View.GONE);
            }

        }

    @Override
    public void finish() {
        super.finish();
        //create some animation to back to main activity
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}

