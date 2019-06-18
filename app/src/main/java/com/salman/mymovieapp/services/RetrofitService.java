package com.salman.mymovieapp.services;

import com.salman.mymovieapp.model.ArtistDetails;
import com.salman.mymovieapp.model.ArtistResponse;
import com.salman.mymovieapp.model.MovieResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


//API Key (v3 auth)
//d6d5008d3dfd6ce5e18c59236b3f55d4
public interface RetrofitService {
    @GET("search/movie")
    Call<MovieResponse> getMoviesByQuery(@Query("api_key") String api_key, @Query("query") String query );

    @GET("search/person")
    Call<ArtistResponse> getArtistsByQuery(@Query("api_key") String api_key, @Query("query") String query );

    @GET("person/{person_id}")
    Call<ArtistDetails> getArtistDetailsByid(@Path("person_id") int person_id, @Query("api_key") String api_key );
}
