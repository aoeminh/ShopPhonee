package com.example.apple.shopphonee.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.apple.shopphonee.R;
import com.example.apple.shopphonee.database.SQLiteUtils;
import com.example.apple.shopphonee.model.BillDetail;
import com.example.apple.shopphonee.model.Cart;
import com.example.apple.shopphonee.model.GetBillsID;
import com.example.apple.shopphonee.utils.ApiUtils;
import com.example.apple.shopphonee.utils.Constant;
import com.example.apple.shopphonee.utils.UtilsSharePref;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Calendar;
import java.util.Date;

import okhttp3.ResponseBody;
import okhttp3.internal.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PayActivity extends BaseActivity {

    android.support.v7.widget.Toolbar toolbar;
    Button confirm_btn, back_btn;
    TextView name, phone;
    EditText address, note;
    SharedPreferences sharedPreferences;
    SQLiteUtils sqLiteDatabase = new SQLiteUtils(this);
    private  int id;

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
        //open database
        sqLiteUtils.open();
        sharedPreferences = UtilsSharePref.getSharedPreferences(this);

        name.setText(sharedPreferences.getString(Constant.NAME, ""));
        phone.setText(sharedPreferences.getString(Constant.PHONE_NUMBER, ""));
        address.setText(sharedPreferences.getString(Constant.ADDRESS, ""));
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
                confirm_btn.setEnabled(false);
                String username = name.getText().toString();
                String phoneNumber = phone.getText().toString();
                String addres = address.getText().toString();
                String notes = note.getText().toString();
                int totalBill = Integer.parseInt(ShoppingActivity.totalBill.getText().toString());
                ApiUtils.getAPIService().insertBill(username, phoneNumber, totalBill, addres, notes).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            try {
                                JSONObject jsonObject = new JSONObject(response.body().string());
                                int status = jsonObject.getInt("success");
                                if (status == 1) {
                                    id = Integer.parseInt(jsonObject.getString("id"));
                                    Log.i("id",String.valueOf(id));

                                    //add to bill details
                                    if (id != 0) {
                                        for (int i = 0; i < MainActivity.cartList.size(); i++) {

                                            Cart cart = MainActivity.cartList.get(i);
                                            int productID = Integer.parseInt(cart.getProductId());
                                            String productName = cart.getProductName();
                                            int productPrice = cart.getProductPrice();
                                            int quantily = cart.getQuantily();
                                            int totalRow = (quantily * productPrice);
                                            ApiUtils.getAPIService().inserBillDetail(id, productID, productName, productPrice, quantily, totalRow).enqueue(new Callback<ResponseBody>() {
                                                @Override
                                                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                                    if (response.isSuccessful()) {
                                                        try {
                                                            JSONObject jsonObject = new JSONObject(response.body().string());
                                                            int status = jsonObject.getInt("success");

                                                                final AlertDialog.Builder builder = new AlertDialog.Builder(PayActivity.this);
                                                                builder.setTitle("Success! Thank for shopping!");
                                                                builder.setMessage("");
                                                                builder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                                                                    @Override
                                                                    public void onClick(DialogInterface dialog, int which) {
                                                                        sqLiteUtils.deleteAll();
                                                                        sqLiteUtils.close();
                                                                        MainActivity.cartList.clear();

                                                                        Intent intent = new Intent(PayActivity.this, MainActivity.class);
                                                                        startActivity(intent);
                                                                        finish();

                                                                    }
                                                                });
                                                                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                                                    @Override
                                                                    public void onClick(DialogInterface dialog, int which) {
                                                                        MainActivity.cartList.clear();

                                                                        Intent intent = new Intent(PayActivity.this, ShoppingActivity.class);
                                                                        startActivity(intent);
                                                                        finish();
                                                                    }
                                                                });
                                                                AlertDialog alertDialog = builder.create();
                                                                alertDialog.show();


                                                        } catch (Exception ex) {
                                                            ex.printStackTrace();
                                                        }
                                                    } else {
                                                        Toast.makeText(PayActivity.this, "Insert fail", Toast.LENGTH_SHORT).show();
                                                    }
                                                }

                                                @Override
                                                public void onFailure(Call<ResponseBody> call, Throwable t) {
                                                    Log.e("error", t.getMessage());
                                                }
                                            });

                                        }
                                    }
                                }
                            } catch (IOException ex) {
                                Log.i("error", ex.getMessage());

                            } catch (JSONException ex) {
                                Log.i("error1", ex.getMessage());
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

    void insertBillDetail(int id){

    }
}
