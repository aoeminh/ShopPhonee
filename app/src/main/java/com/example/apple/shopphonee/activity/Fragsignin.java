package com.example.apple.shopphonee.activity;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.apple.shopphonee.R;
import com.example.apple.shopphonee.model.Account;
import com.example.apple.shopphonee.model.DataLogin;

import java.io.Serializable;

public class Fragsignin extends Fragment implements View.OnClickListener {

    EditText edtUsername, edtPassword;
    DataLogin dataLogin;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragsignin, container, false);
        Button btnLogin = view.findViewById(R.id.btn_login);
        edtUsername = view.findViewById(R.id.edt_username_signin);
        edtPassword = view.findViewById(R.id.edt_password_signin);
        btnLogin.setOnClickListener(this);

        return view;
    }


    @Override
    public void onClick(View v) {
        final String username = edtUsername.getText().toString();
        final String password = edtPassword.getText().toString();

        boolean validation = validation(username, password);
        if (validation) {
            LoginActivity.login(username, password, new DataLogin() {
                @Override
                public void dataLogin(Account account) {

                    Bundle bundle = new Bundle();
                    bundle.putSerializable("account", account);
                    Intent intent = new Intent(getActivity(), ProfileActivity.class);
                    intent.putExtras(bundle);
                    getActivity().startActivity(intent);
                }

            });
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
