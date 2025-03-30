package com.example.labtestappfront;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static final String BASE_URL = "https://ead2caapi-akcqhmexhbfpb3an.ukwest-01.azurewebsites.net/api/";
    private static Retrofit retrofit = null;

    public static Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd")  // adjust format to match your API
            .create();
    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }
}
