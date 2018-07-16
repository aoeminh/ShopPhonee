package com.example.apple.shopphonee.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.apple.shopphonee.activity.DetailProduct;
import com.example.apple.shopphonee.model.APIService;
import com.example.apple.shopphonee.model.Account;
import com.example.apple.shopphonee.model.Product;
import com.example.apple.shopphonee.model.RetrofitClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
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
    private final static String BASE_URL ="http://192.168.43.218/server/";
    public static APIService getAPIService(){
        return RetrofitClient.getRetrofitClient(BASE_URL).create(APIService.class);
    }

    public ArrayList<Integer> getArrQuantily(){
        ArrayList<Integer> integers = new ArrayList<Integer>();
        for (int i = 1; i<=10; i++){
            integers.add(i);
        }
        return integers;
    }

    public static  void loadImage(String url, ImageView imageView, Context context){
        Glide.with(context).load(url).into(imageView);
    }

    public static Product getBundle (Bundle bundle, Product product){
        String id = bundle.getString("id");
       String name = bundle.getString("name");
      int  price = bundle.getInt("price");
        String image = bundle.getString("image");
       String description = bundle.getString("description");
      String  category = bundle.getString("category");

        product = new Product(id,name,price,image,description,category);
        return product;
    }

    public static void login(String username, String password){

        ApiUtils.getAPIService().loginAccount(username,password).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    try {

                        JSONObject jsonObject = new JSONObject(response.body().string());
                        Log.i("test",jsonObject.toString());
                        int status = jsonObject.getInt("success");
                        if (status == 1) {
                            Account account = new Account();
                            account.setUsername(jsonObject.getString("username"));
                            Log.i("username",account.getUsername());

                        } else {
                            Log.d("error", "onResponse: " + jsonObject.toString());
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }




}
