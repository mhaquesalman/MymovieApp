package com.salman.mymovieapp.client;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static final String BASE_URL = "http://api.themoviedb.org/3/";
    private static final String API_KEY = "d6d5008d3dfd6ce5e18c59236b3f55d4";

    private static Retrofit retrofit = null;

    public static Retrofit getClient(){
        if (retrofit == null){
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return  retrofit;
    }

    public static String getApiKey() {
        return API_KEY;
    }
}
