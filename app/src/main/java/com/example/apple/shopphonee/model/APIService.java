package com.example.apple.shopphonee.model;

import android.os.Message;

import java.util.Date;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

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

    //insert to bill
    @FormUrlEncoded
    @POST("insert1.php")
    Call<ResponseBody> insertBill(@Field("username") String username, @Field("phone_number") String phoneNumber,
                                  @Field("totalBill") int totalBill, @Field("address") String address,
                                  @Field("note") String note);

    //edit infomation
    @FormUrlEncoded
    @POST("update.php")
    Call<ResponseBody> updateInfo(@Field("username") String username, @Field("phone") String phoneNumber,
                                  @Field("email") String email, @Field("address") String address);

    //get list bill
    @FormUrlEncoded
    @POST("getListBill.php")
    Call<List<Bills>> getListBill(@Field("phone") String username);

    // insert bills to database
    @FormUrlEncoded
    @POST("insertBillDetail.php")
    Call<ResponseBody> inserBillDetail(@Field("billID")int billID ,@Field("productID") int id, @Field("name") String productName,
                                       @Field("productPrice") int price,@Field("quantily") int quantily,
                                       @Field("totalRow") int totalRow );

    //get billdetails
    @FormUrlEncoded
    @POST("getBillDetails.php")
    Call<List<BillDetail>> getBillDetails(@Field("billID") int billID);

    @FormUrlEncoded
    @POST("uploadimage.php")
    Call<ResponseBody> uploadImage(@Field("imageCode")String imgCode,@Field("imageName")String image ,
                                                                 @Field("userID")int userId);
}

