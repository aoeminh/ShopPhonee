package com.example.apple.shopphonee.model;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

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

    //login
    @FormUrlEncoded
    @POST("login.php")
    Call<ResponseBody> loginAccount (@Field("username") String username, @Field("password") String password);

    //register
    @FormUrlEncoded
    @POST("register.php")
    Call<ResponseBody> registerAccount (@Field("username") String username, @Field("password") String password);





}
