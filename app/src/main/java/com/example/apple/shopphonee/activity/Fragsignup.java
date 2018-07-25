package com.example.apple.shopphonee.activity;

import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apple.shopphonee.R;
import com.example.apple.shopphonee.model.Account;
import com.example.apple.shopphonee.model.DataLogin;
import com.example.apple.shopphonee.model.DataRegister;
import com.example.apple.shopphonee.utils.Constant;
import com.example.apple.shopphonee.utils.UtilsSharePref;

public class Fragsignup extends Fragment implements View.OnClickListener {

    Button btnSignup,btnBack;
    EditText username, password, rePassword;
    TextView noteTv;
    SharedPreferences sharedPreferences;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragsignup, container, false);
        btnSignup = view.findViewById(R.id.btn_register_signup);
        username = view.findViewById(R.id.edt_username_signup);
        password = view.findViewById(R.id.edt_password_signup);
        rePassword = view.findViewById(R.id.edt_re_password_signup);
        noteTv = view.findViewById(R.id.tv_note_signup);
        btnBack = view.findViewById(R.id.btn_back_signup);
        sharedPreferences = UtilsSharePref.getSharedPreferences(getActivity());
        btnSignup.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.btn_back_signup){
            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);

        }
        if (id == R.id.btn_register_signup){
            String name = username.getText().toString();
            String strpassword = password.getText().toString();
            String strRepassword = rePassword.getText().toString();
            boolean validation = validation(name, strpassword, strRepassword);
            if (validation) {
                LoginActivity loginActivity = new LoginActivity();

                loginActivity.registter(name, strpassword, new DataLogin() {

                    @Override
                    public void dataLogin(boolean check, Account account) {
                        if(check && account != null){
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString(Constant.NAME, account.getUsername());
                            editor.putString(Constant.PHONE_NUMBER, account.getPhoneNumber());
                            editor.putString(Constant.ADDRESS, account.getAddress());
                            editor.putString(Constant.EMAIL, account.getEmail());
                            editor.putBoolean(Constant.LOGGED_IN,true);
                            editor.apply();
                            Intent intent = new Intent(getActivity(),ProfileActivity.class);
                            startActivity(intent);
                            Log.i("check","ok");
                        }else {
                            noteTv.setTextColor(Color.RED);
                            noteTv.setText("Username has been existed");
                            noteTv.setVisibility(View.VISIBLE);
                        }
                    }
                });
            }
        }

    }

    boolean validation(String username, String password, String rePassword) {
        if (password.equals(rePassword)) {
            if (username.length() >= 6 && password.length() >= 6) {
                return true;
            } else {
                Toast.makeText(getActivity(), "Username and password must  6 characterbe or more!", Toast.LENGTH_SHORT).show();
                return false;
            }
        } else {
            Toast.makeText(getActivity(), "Password invalid", Toast.LENGTH_SHORT).show();
            return false;
        }
    }
}
