package com.example.apple.shopphonee.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toolbar;

import com.example.apple.shopphonee.R;

public class PayActivity extends BaseActivity {

    android.support.v7.widget.Toolbar toolbar;
    Button confirm_btn,back_btn;
    EditText name,phoneNumber,email,address;


    @Override
    void initView() {
    toolbar = this.findViewById(R.id.toolbat_pay);
    confirm_btn = this.findViewById(R.id.btn_confirm_shopping);
    back_btn = this.findViewById(R.id.btn_back_shopping);
    name = this.findViewById(R.id.edt_name_pay);
    phoneNumber = this.findViewById(R.id.edt_phone_pay);
    email = this.findViewById(R.id.edt_email_pay);
    address = this.findViewById(R.id.edt_address_pay);

    toolbar.setNavigationIcon(R.mipmap.ic_action_arrow_back);
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
    }
}
