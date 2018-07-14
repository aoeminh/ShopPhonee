package com.example.apple.shopphonee.model;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APIService {

    //get data for navigation bar
    @GET("getdata.php")
    Call<List<Category>> getCategory();

    //get data for phone activity
    @GET("getphone.php")
    Call<List<Product>> getPhone();

    //get data for  home activity
    @GET("latestproduct.php")
    Call<List<Product>> getLatest();

    //get data for laptop activity
    @GET("getlaptop.php")
    Call<List<Product>> getLapTop();




}
