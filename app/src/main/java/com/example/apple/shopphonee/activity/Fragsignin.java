package com.example.apple.shopphonee.activity;

import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
import com.example.apple.shopphonee.utils.ApiUtils;
import com.example.apple.shopphonee.utils.Constant;
import com.example.apple.shopphonee.utils.UtilsSharePref;

import org.w3c.dom.Text;

import java.io.Serializable;

public class Fragsignin extends Fragment implements View.OnClickListener {

    EditText edtUsername, edtPassword;
    DataLogin dataLogin;
    TextView tvNotify;
    Button btnBack;
    SharedPreferences sharedPreferences;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragsignin, container, false);
        Button btnLogin = view.findViewById(R.id.btn_login);
        btnBack = view.findViewById(R.id.btn_back_signin);
        edtUsername = view.findViewById(R.id.edt_username_signin);
        edtPassword = view.findViewById(R.id.edt_password_signin);
        tvNotify = view.findViewById(R.id.tv_notify_signin);
        btnLogin.setOnClickListener(this);
//        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        sharedPreferences = UtilsSharePref.getSharedPreferences(getActivity());
        btnBack.setOnClickListener(this);

        return view;
    }


    @Override
    public void onClick(View v) {

        final String username = edtUsername.getText().toString();
        final String password = edtPassword.getText().toString();
        int id = v.getId();

        if (id == R.id.btn_back_signin) {
            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
        }
        if (id == R.id.btn_login) {


            boolean validation = validation(username, password);
            if (validation) {
                LoginActivity loginActivity = new LoginActivity();
                loginActivity.login(username, password, new DataLogin() {

                    @Override
                    public void dataLogin(boolean check, Account account) {
                        if (account != null && check) {
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString(Constant.NAME, account.getUsername());
                            editor.putString(Constant.PHONE_NUMBER, account.getPhoneNumber());
                            editor.putString(Constant.ADDRESS, account.getAddress());
                            editor.putString(Constant.EMAIL, account.getEmail());
                            editor.putBoolean(Constant.LOGGED_IN,true);
                            editor.apply();
                            Intent intent = new Intent(getActivity(), ProfileActivity.class);
                            getActivity().startActivity(intent);
                        } else {
                            Log.i("fail", "login fail");
                            tvNotify.setVisibility(View.VISIBLE);
                            tvNotify.setText(R.string.notify_signin);
                            tvNotify.setTextColor(Color.RED);
                        }
                    }
                });
            }
        }

    }

    boolean validation(String username, String password) {

        if (username.length() >= 6 && password.length() >= 6) {
            return true;
        } else {
            Toast.makeText(getActivity(), "Username and password must  6 characterbe or more!", Toast.LENGTH_SHORT).show();
            return false;
        }
    }


}

