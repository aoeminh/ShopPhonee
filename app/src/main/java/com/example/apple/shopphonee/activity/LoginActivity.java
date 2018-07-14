package com.example.apple.shopphonee.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.apple.shopphonee.R;

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
}
