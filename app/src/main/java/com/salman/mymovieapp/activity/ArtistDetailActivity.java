package com.salman.mymovieapp.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.flaviofaria.kenburnsview.KenBurnsView;
import com.salman.mymovieapp.R;
import com.salman.mymovieapp.client.RetrofitClient;
import com.salman.mymovieapp.model.ArtistDetails;
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

    AppCompatTextView artistname;
    AppCompatTextView also_knownas;
    AppCompatTextView dateofbirth;
    AppCompatTextView placeofbirth;
    AppCompatTextView dateofdeath;
    AppCompatTextView departmentdetail;
    AppCompatTextView homepagedetail;
    AppCompatTextView biographydetail;


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

        also_knownas = findViewById(R.id.also_knownas);
        dateofbirth = findViewById(R.id.dateofbirth);
        placeofbirth = findViewById(R.id.placeofbirth);
        dateofdeath = findViewById(R.id.dateofdeath);
        departmentdetail = findViewById(R.id.department);
        homepagedetail = findViewById(R.id.homepage);
        biographydetail = findViewById(R.id.biography);

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
                       ArtistDetails artistDetails = response.body();
                        Log.d(TAG, "onResponse: "+ response.toString());
                        if (artistDetails != null){
                            getArtistDetails(artistDetails);
                        } else {
                            Toast.makeText(ArtistDetailActivity.this, "any details not found", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ArtistDetails> call, Throwable t) {
                        Toast.makeText(ArtistDetailActivity.this, "something wrong", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }

    }

    public void getArtistDetails(ArtistDetails artistDetails) {

        String profilePath = artistDetails.getProfile_path();
        String name = artistDetails.getName();
        String birthday = artistDetails.getBirthday();
        String placeOfBirth = artistDetails.getPlace_of_birth();
        String dateOfDeath = artistDetails.getDeathday();
        String department = artistDetails.getKnown_for_department();
        String homepage = artistDetails.getHomepage();
        String biography = artistDetails.getBiography();
        List<String> alsoKnownAsList = artistDetails.getAlso_known_as();

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
                    departmentdetail.setText(department);
                    department_layout.setVisibility(View.VISIBLE);
                } else {
                    department_layout.setVisibility(View.GONE);
                }
            } else {
                department_layout.setVisibility(View.GONE);
            }

            if (homepage != null) {
                if (homepage.length() > 0) {
                    homepagedetail.setText(homepage);
                    homepage_layout.setVisibility(View.VISIBLE);
                } else {
                    homepage_layout.setVisibility(View.GONE);
                }
            } else {
                homepage_layout.setVisibility(View.GONE);
            }

            if (biography != null) {
                if (biography.length() > 0) {
                    biographydetail.setText(biography);
                    biography_layout.setVisibility(View.VISIBLE);
                } else {
                    biography_layout.setVisibility(View.GONE);
                }
            } else {
                biography_layout.setVisibility(View.GONE);
            }

        }
    }

    @Override
    public void finish() {
        super.finish();
        //create some animation to back to main activity
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
