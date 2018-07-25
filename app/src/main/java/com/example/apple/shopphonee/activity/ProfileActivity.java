package com.example.apple.shopphonee.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.apple.shopphonee.R;
import com.example.apple.shopphonee.model.Account;
import com.example.apple.shopphonee.utils.Constant;
import com.example.apple.shopphonee.utils.UtilsSharePref;

public class ProfileActivity extends BaseActivity implements View.OnClickListener {

    TextView tvName, tvPhone, tvEmail, tvAdress;
    ImageView avatar;
    Toolbar toolbar;
    Button btnSignout, btnEdit, btnHome, btnHistory;
    SharedPreferences sharedPreferences;

    @Override
    void initView() {

        tvName = this.findViewById(R.id.tv_name_profile);
        tvPhone = this.findViewById(R.id.tv_phone_number_profile);
        tvEmail = this.findViewById(R.id.tv_email_profile);
        tvAdress = this.findViewById(R.id.tv_address_profile);
        toolbar = this.findViewById(R.id.toolbar_profile);
        btnSignout = this.findViewById(R.id.btn_signout_profile);
        btnEdit = this.findViewById(R.id.btn_edit_profile);
        btnHistory = this.findViewById(R.id.btn_history_profile);
        btnHome = this.findViewById(R.id.btn_home_profile);
        //get info
//        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreferences = UtilsSharePref.getSharedPreferences(this);

        tvName.setText( sharedPreferences.getString(Constant.NAME, ""));
        tvPhone.setText( sharedPreferences.getString(Constant.PHONE_NUMBER, ""));
        tvAdress.setText( sharedPreferences.getString(Constant.ADDRESS, ""));
        tvEmail.setText( sharedPreferences.getString(Constant.EMAIL, ""));


    }

    @Override
    int getLayoutId() {
        return R.layout.activity_profile;
    }

    @Override
    void setAction() {

        btnHome.setOnClickListener(this);
        btnEdit.setOnClickListener(this);
        btnHistory.setOnClickListener(this);
        btnSignout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        int id = v.getId();

        switch (id) {
            case R.id.btn_home_profile:
                Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_edit_profile:
                Intent intent1 = new Intent(ProfileActivity.this, EditProfile.class);
                startActivity(intent1);
                break;
            case R.id.btn_history_profile:
                 Intent intent3 = new Intent(ProfileActivity.this,ListBills.class);
                startActivity(intent3);
                break;
            case R.id.btn_signout_profile:
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.apply();
                Intent intent2 = new Intent(ProfileActivity.this, MainActivity.class);
                startActivity(intent2);
                break;
        }

    }


    void getInfo(){


    }
}
