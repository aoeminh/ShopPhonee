package com.example.apple.shopphonee.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apple.shopphonee.R;
import com.example.apple.shopphonee.activity.LoginActivity;
import com.example.apple.shopphonee.activity.MainActivity;
import com.example.apple.shopphonee.activity.ProfileActivity;
import com.example.apple.shopphonee.model.Account;
import com.example.apple.shopphonee.model.DataLogin;
import com.example.apple.shopphonee.utils.Constant;
import com.example.apple.shopphonee.utils.UtilsSharePref;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpFragment extends Fragment implements View.OnClickListener {

    Button btnSignup, btnBack;
    EditText username, password, rePassword;
    TextView noteTv;
    SharedPreferences sharedPreferences;
    TextInputLayout tilUsername, tilPassword, tilsRePassword;
    private Pattern pattern = Pattern.compile(EMAIL_PATTERN);
    Matcher matcher;
    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9#_~!$&'()*+,;=:.\"(),:;<>@\\[\\]\\\\]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*$";

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
        tilUsername = view.findViewById(R.id.til_username);
        tilPassword = view.findViewById(R.id.til_password);
        tilsRePassword = view.findViewById(R.id.til_re_password);
        sharedPreferences = UtilsSharePref.getSharedPreferences(getActivity());
        btnSignup.setOnClickListener(this);
        btnBack.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_back_signup) {
            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);

        }
        if (id == R.id.btn_register_signup) {
            String name = username.getText().toString().trim();
            String strpassword = password.getText().toString().trim();
            String strRepassword = rePassword.getText().toString().trim();
            // boolean validation = validation(name, strpassword, strRepassword);
            if (!validateEmail(name)) {
                Toast.makeText(getActivity(), "username is not valid", Toast.LENGTH_SHORT).show();

                tilUsername.setError("Not a valid email address!");
            } else if (!validatePassword(strpassword)) {
                Toast.makeText(getActivity(), "Password is not valid", Toast.LENGTH_SHORT).show();
                tilPassword.setError("Not a valid password!");
            } else if (!strpassword.equals(strRepassword)) {
                Toast.makeText(getActivity(), "repassword is not valid", Toast.LENGTH_SHORT).show();
                tilsRePassword.setError("Your password and confirmation password do not match");
            } else {
                tilUsername.setErrorEnabled(false);
                tilPassword.setErrorEnabled(false);
                tilsRePassword.setErrorEnabled(false);
                LoginActivity loginActivity = new LoginActivity();
                loginActivity.registter(name, strpassword, new DataLogin() {

                    @Override
                    public void dataLogin(boolean check, Account account) {
                        if (check && account != null) {
                            SharedPreferences.Editor editor = sharedPreferences.edit();

                            editor.putInt(Constant.USER_ID,account.getId());
                            editor.putString(Constant.NAME, account.getUsername());
                            editor.putString(Constant.PHONE_NUMBER, account.getPhoneNumber());
                            editor.putString(Constant.ADDRESS, account.getAddress());
                            editor.putString(Constant.EMAIL, account.getEmail());
                            editor.putBoolean(Constant.LOGGED_IN, true);
                            editor.apply();
                            Intent intent = new Intent(getActivity(), ProfileActivity.class);
                            startActivity(intent);
                            getActivity().finish();
                            Log.i("check", "ok");
                        } else {
                            noteTv.setTextColor(Color.RED);
                            noteTv.setText("Username has been existed");
                            noteTv.setVisibility(View.VISIBLE);
                        }
                    }
                });
            }
        }
    }

    public boolean validateEmail(String email) {
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public boolean validatePassword(String password) {
        return password.length() > 5;
    }

}
