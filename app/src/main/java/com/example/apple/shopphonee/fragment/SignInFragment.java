package com.example.apple.shopphonee.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
import com.example.apple.shopphonee.utils.ApiUtils;
import com.example.apple.shopphonee.utils.Constant;
import com.example.apple.shopphonee.utils.UtilsSharePref;

import org.w3c.dom.Text;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignInFragment extends Fragment implements View.OnClickListener {

    EditText edtUsername, edtPassword;
    DataLogin dataLogin;
    TextView tvNotify;
    Button btnBack;
    SharedPreferences sharedPreferences;
    private Pattern pattern = Pattern.compile(EMAIL_PATTERN);
    private Matcher matcher;
    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9#_~!$&'()*+,;=:.\"(),:;<>@\\[\\]\\\\]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*$";
    TextInputLayout tilUsername,tilPassword;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragsignin, container, false);
        Button btnLogin = view.findViewById(R.id.btn_login);
        btnBack = view.findViewById(R.id.btn_back_signin);
        edtUsername = view.findViewById(R.id.edt_username_signin);
        edtPassword = view.findViewById(R.id.edt_password_signin);
        tvNotify = view.findViewById(R.id.tv_notify_signin);
        tilPassword = view.findViewById(R.id.sign_in_til_password);
        tilUsername = view.findViewById(R.id.sign_in_til_username);
        btnLogin.setOnClickListener(this);
        sharedPreferences = UtilsSharePref.getSharedPreferences(getActivity());
        btnBack.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {

        final String username = edtUsername.getText().toString().trim();
        final String password = edtPassword.getText().toString().trim();
        int id = v.getId();

        if (id == R.id.btn_back_signin) {
            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
        }
        if (id == R.id.btn_login) {

            if (!validateEmail(username)) {
                tilUsername.setError("Not a valid email address!");

            }else if(!validatePassword(password)){
                tilPassword.setError("Not a valid password!");

            }else{

                LoginActivity loginActivity = new LoginActivity();
                loginActivity.login(username, password, new DataLogin() {

                    @Override
                    public void dataLogin(boolean check, Account account) {
                        if (account != null && check) {
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putInt(Constant.USER_ID,account.getId());
                            editor.putString(Constant.NAME, account.getUsername());
                            editor.putString(Constant.PHONE_NUMBER, account.getPhoneNumber());
                            editor.putString(Constant.ADDRESS, account.getAddress());
                            editor.putString(Constant.EMAIL, account.getEmail());
                            editor.putBoolean(Constant.LOGGED_IN,true);
                            editor.putString(Constant.IMAGE,account.getImage());
                            editor.apply();
                            Intent intent = new Intent(getActivity(), ProfileActivity.class);
                            startActivity(intent);
                            getActivity().finish();
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

    public boolean validateEmail(String email) {
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public boolean validatePassword(String password) {
        return password.length() > 5;
    }
}

