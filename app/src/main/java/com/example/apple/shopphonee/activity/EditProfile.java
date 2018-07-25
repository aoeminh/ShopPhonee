package com.example.apple.shopphonee.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.apple.shopphonee.R;
import com.example.apple.shopphonee.utils.ApiUtils;
import com.example.apple.shopphonee.utils.Constant;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfile extends BaseActivity implements View.OnClickListener {

    EditText edtName,edtEmail,edtAddress;
    Button btnSave, btnBack;
SharedPreferences sharedPreferences;
    @Override
    void initView() {
        edtName = this.findViewById(R.id.edt_name_edit);
        edtAddress = this.findViewById(R.id.edt_address_edit);
        edtEmail = this.findViewById(R.id.edt_email_edit);
        btnBack = this.findViewById(R.id.btn_back_edit);
        btnSave =this.findViewById(R.id.btn_save_edit);


        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        edtName.setText( sharedPreferences.getString(Constant.NAME, ""));
        edtAddress.setText( sharedPreferences.getString(Constant.ADDRESS, ""));
        edtEmail.setText( sharedPreferences.getString(Constant.EMAIL, ""));
    }

    @Override
    int getLayoutId() {
        return R.layout.activity_edit_profile;
    }

    @Override
    void setAction() {
        btnSave.setOnClickListener(this);
        btnBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int  id = v.getId();
        final String name = edtName.getText().toString();
        final String email = edtEmail.getText().toString();
        final String adress=edtAddress.getText().toString();
        final String phone = sharedPreferences.getString("phone","");
        if(id== R.id.btn_save_edit) {
            ApiUtils.getAPIService().updateInfo(name,phone,email,adress).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if(response.isSuccessful()){
                        try{
                            JSONObject jsonObject  = new JSONObject(response.body().string());
                            int status = jsonObject.getInt("success");
                            Log.i("status", String.valueOf(status));

                            if(status ==1){
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString(Constant.NAME, name);
                                editor.putString(Constant.PHONE_NUMBER,phone);
                                editor.putString(Constant.EMAIL,email);
                                editor.putString(Constant.ADDRESS,adress);
                                editor.apply();
                                Intent intent = new Intent(EditProfile.this,ProfileActivity.class);
                                startActivity(intent);
                            }
                            else {
                                Toast.makeText(EditProfile.this,"Edit fail! Please check information again!",Toast.LENGTH_SHORT).show();
                            }

                        }catch (IOException e ){
                                Log.i("error1",e.getMessage());
                        }catch (JSONException  ex){
                            Log.i("error",ex.getMessage());
                        }
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                }
            });
        }
        if(id==R.id.btn_back_edit){
            onBackPressed();
        }

    }

}
