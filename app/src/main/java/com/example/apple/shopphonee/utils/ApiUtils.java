package com.example.apple.shopphonee.utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.apple.shopphonee.activity.DetailProduct;
import com.example.apple.shopphonee.activity.MainActivity;
import com.example.apple.shopphonee.model.APIService;
import com.example.apple.shopphonee.model.Account;
import com.example.apple.shopphonee.model.DataLogin;
import com.example.apple.shopphonee.model.Product;
import com.example.apple.shopphonee.model.RetrofitClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ApiUtils {
    //192.168.100.31
//
//192.168.0.104
    //10.22.185.162
    private final static String BASE_URL = "http://192.168.0.115/server/";

    public static APIService getAPIService() {
        return RetrofitClient.getRetrofitClient(BASE_URL).create(APIService.class);
    }

    public static void loadImage(String url, ImageView imageView, Context context) {
        Glide.with(context).load(url).into(imageView);
    }

    public static Product getBundle(Bundle bundle, Product product) {
        String id = bundle.getString("id");
        String name = bundle.getString("name");
        int price = bundle.getInt("price");
        String image = bundle.getString("image");
        String description = bundle.getString("description");
        String category = bundle.getString("category");

        product = new Product(id, name, price, image, description, category);
        return product;
    }

}



