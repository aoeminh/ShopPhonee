package com.example.apple.shopphonee.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.apple.shopphonee.R;

public class ProfileActivity extends BaseActivity {

    TextView tvName,tvPhone,tvGender,tvEmail,tvAdress;
    ImageView avatar;



    @Override
    void initView() {

        tvName  =this.findViewById(R.id.tv_name_profile);
        tvPhone = this.findViewById(R.id.tv_phone_number_profile);
        tvGender = this.findViewById(R.id.tv_gender_profile);
        tvEmail = this.findViewById(R.id.tv_email_profile);
        tvAdress = this.findViewById(R.id.tv_address_profile);



    }

    @Override
    int getLayoutId() {
        return R.layout.activity_profile;
    }

    @Override
    void setAction() {

    }
}
