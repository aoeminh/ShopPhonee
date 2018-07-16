package com.example.apple.shopphonee.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.apple.shopphonee.R;
import com.example.apple.shopphonee.model.APIService;
import com.example.apple.shopphonee.model.Account;
import com.example.apple.shopphonee.model.DataLogin;
import com.example.apple.shopphonee.model.DataRegister;
import com.example.apple.shopphonee.model.RetrofitClient;
import com.example.apple.shopphonee.utils.ApiUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    Button btnSignin,btnSignup;
    FragmentManager fragmentManager = getFragmentManager();

    @Override
    void initView() {
        btnSignin = this.findViewById(R.id.btn_signin);
        btnSignup = this.findViewById(R.id.btn_signup);

    }

    @Override
    int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    void setAction() {
        btnSignup.setOnClickListener(this);
        btnSignin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragment = null;
        fragment = getFragmentManager().findFragmentById(R.id.frame_register);
        if(fragment!=null){
            fragmentTransaction.remove(fragment);

        }
        if(id== R.id.btn_signin){
            fragment = new Fragsignin();
            Toast.makeText(this,"signin",Toast.LENGTH_SHORT).show();
        }
        if (id==R.id.btn_signup){
            fragment = new Fragsignup();
            Toast.makeText(this,"signup",Toast.LENGTH_SHORT).show();
        }

        fragmentTransaction.replace(R.id.frame_register,fragment);
        fragmentTransaction.commit();
    }

    public static void login(String username, String password, final DataLogin dataLogin){

        ApiUtils.getAPIService().loginAccount(username,password).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    try {

                        JSONObject jsonObject = new JSONObject(response.body().string());
                        int status = jsonObject.getInt("success");
                        if (status == 1) {
                            Account account = new Account();
                            account.setUsername(jsonObject.getString("username"));
                            dataLogin.dataLogin(account);

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

    public static void registter(String username, String password, final DataRegister dataRegister){
        ApiUtils.getAPIService().registerAccount(username,password).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        Log.i("test",response.body().string());
                        JSONObject jsonObject = new JSONObject(response.body().string());

                        int status = jsonObject.getInt("success");
                        if (status == 1) {
                            Account account = new Account();
                            account.setUsername(jsonObject.getString("username"));
                            dataRegister.dataRegister(account,true);
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
