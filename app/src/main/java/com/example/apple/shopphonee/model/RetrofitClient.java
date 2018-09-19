package com.example.apple.shopphonee.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static Retrofit retrofit =null;
   static Gson gson = new GsonBuilder()
            .setLenient()
            .create();
    public static Retrofit getRetrofitClient(String baseUrl){
        if(retrofit==null){
            retrofit= new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }
}
