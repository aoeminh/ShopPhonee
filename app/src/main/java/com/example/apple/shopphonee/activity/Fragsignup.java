package com.example.apple.shopphonee.activity;

import android.app.Fragment;
import android.content.Intent;
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
import com.example.apple.shopphonee.model.DataRegister;

public class Fragsignup extends Fragment implements View.OnClickListener {

    Button btnSignup;
    EditText username, password, rePassword;
    TextView noteTv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragsignup, container, false);
        btnSignup = view.findViewById(R.id.btn_register_signup);
        username = view.findViewById(R.id.edt_username_signup);
        password = view.findViewById(R.id.edt_password_signup);
        rePassword = view.findViewById(R.id.edt_re_password_signup);
        noteTv = view.findViewById(R.id.tv_note_signup);
        btnSignup.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        String name = username.getText().toString();
        String strpassword = password.getText().toString();
        String strRepassword = rePassword.getText().toString();
        boolean validation = validation(name, strpassword, strRepassword);
        if (validation) {
            LoginActivity.registter(name, strpassword, new DataRegister() {
                @Override
                public void dataRegister(Account account, boolean flag) {
                    if (flag) {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("account", account);
                        Intent intent = new Intent(getActivity(), ProfileActivity.class);
                        intent.putExtras(bundle);
                        getActivity().startActivity(intent);
                        Toast.makeText(getActivity(),"Register success!",Toast.LENGTH_SHORT).show();
                    } else {
                        noteTv.setText("Register Fail! Please check username or password again!");

                    }


                }
            });
        } else {


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
        }else{
            Toast.makeText(getActivity(), "Password invalid", Toast.LENGTH_SHORT).show();
            return false;
        }

    }
}
