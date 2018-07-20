package com.example.apple.shopphonee.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toolbar;

import com.example.apple.shopphonee.R;
import com.example.apple.shopphonee.utils.ApiUtils;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PayActivity extends BaseActivity {

    android.support.v7.widget.Toolbar toolbar;
    Button confirm_btn, back_btn;
    TextView name, phone;
    EditText address , note;

    @Override
    void initView() {
        toolbar = this.findViewById(R.id.toolbar_pay);
        confirm_btn = this.findViewById(R.id.btn_confirm_pay);
        name = this.findViewById(R.id.tv_name_pay);
        phone = this.findViewById(R.id.tv_phone_number_pay);
        address = this.findViewById(R.id.edt_address_pay);
        note = this.findViewById(R.id.edt_note_pay);
        back_btn = this.findViewById(R.id.btn_back_pay);


        toolbar.setNavigationIcon(R.mipmap.ic_action_arrow_back);

        name.setText(MainActivity.accountMain.getUsername());
        phone.setText(MainActivity.accountMain.getPhoneNumber());
        address.setText(MainActivity.accountMain.getAddress());

    }

    @Override
    int getLayoutId() {
        return R.layout.activity_pay;
    }

    @Override
    void setAction() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        confirm_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = name.getText().toString();
                String phoneNumber = phone.getText().toString();
                String addres = address.getText().toString();
                String notes = note.getText().toString();
                int totalBill = Integer.parseInt(ShoppingActivity.totalBill.getText().toString());
                ApiUtils.getAPIService().insertBill(username,phoneNumber,totalBill,addres,notes).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()){
                            try{
                                JSONObject jsonObject = new JSONObject(response.body().string());

                                Log.i("test",jsonObject.toString());
                                int status = jsonObject.getInt("success");
                                Log.i("status",String.valueOf(status));
                                if(status==1){
                                    final AlertDialog.Builder builder = new AlertDialog.Builder(PayActivity.this);
                                    builder.setTitle("Success! Thank for watching!");
                                    builder.setMessage("");
                                    builder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                                MainActivity.cartList.clear();
                                                Intent intent  = new Intent(PayActivity.this, MainActivity.class);
                                                startActivity(intent);

                                        }
                                    });
                                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            MainActivity.cartList.clear();

                                            finish();
                                        }
                                    });
                                    AlertDialog alertDialog =  builder.create();
                                    alertDialog.show();
                                }
                            }catch (IOException ex){
                                Log.i("error",ex.getMessage());

                            }catch (JSONException ex){
                                Log.i("error1",ex.getMessage());
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });
            }
        });

    }
}
