package com.example.apple.shopphonee.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.apple.shopphonee.R;
import com.example.apple.shopphonee.model.Account;

public class ProfileActivity extends BaseActivity {

    TextView tvName,tvPhone,tvEmail,tvAdress;
    ImageView avatar;
    Toolbar toolbar;
    Button btnSignout, btnEdit,btnHome,btnHistory;


    @Override
    void initView() {

        tvName  =this.findViewById(R.id.tv_name_profile);
        tvPhone = this.findViewById(R.id.tv_phone_number_profile);
        tvEmail = this.findViewById(R.id.tv_email_profile);
        tvAdress = this.findViewById(R.id.tv_address_profile);
        toolbar  = this.findViewById(R.id.toolbar_profile);
        btnSignout = this.findViewById(R.id.btn_signout_profile);
        btnEdit = this.findViewById(R.id.btn_edit_profile);
        btnHistory = this.findViewById(R.id.btn_history_profile);
        btnHome = this.findViewById(R.id.btn_home_profile);
        //get info
        tvName.setText(MainActivity.accountMain.getUsername());
        tvPhone.setText(MainActivity.accountMain.getPhoneNumber());
        tvAdress.setText(MainActivity.accountMain.getAddress());
        tvEmail.setText(MainActivity.accountMain.getEmail());

    }

    @Override
    int getLayoutId() {
        return R.layout.activity_profile;
    }

    @Override
    void setAction() {

        btnSignout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.checkLogin = false;
                Intent intent = new Intent(ProfileActivity.this,MainActivity.class);
                startActivity(intent);

            }
        });
    }
}
