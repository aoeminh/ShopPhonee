package com.example.apple.shopphonee.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apple.shopphonee.R;
import com.example.apple.shopphonee.model.Account;
import com.example.apple.shopphonee.model.DataLogin;
import com.example.apple.shopphonee.utils.ApiUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    Button btnSignin, btnSignup;
    FragmentManager fragmentManager = getFragmentManager();
    Toolbar toolbar;
    TextView tvNotify;
    Account account = new Account();

    @Override
    void initView() {
        btnSignin = this.findViewById(R.id.btn_signin);
        btnSignup = this.findViewById(R.id.btn_signup);
        toolbar = this.findViewById(R.id.toolbar_login);
        tvNotify = this.findViewById(R.id.tv_notify_signin);

        toolbar.setNavigationIcon(R.mipmap.ic_action_arrow_back);
    }

    @Override
    int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    void setAction() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        btnSignup.setOnClickListener(this);
        btnSignin.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        int id = v.getId();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragment = null;
        fragment = getFragmentManager().findFragmentById(R.id.frame_register);
        if (fragment != null) {
            fragmentTransaction.remove(fragment);

        }
        if (id == R.id.btn_signin) {
            fragment = new Fragsignin();
            Toast.makeText(this, "signin", Toast.LENGTH_SHORT).show();
        }
        if (id == R.id.btn_signup) {
            fragment = new Fragsignup();
            Toast.makeText(this, "signup", Toast.LENGTH_SHORT).show();
        }

        fragmentTransaction.replace(R.id.frame_register, fragment);
        fragmentTransaction.commit();
    }

    public void login(String username, String password, final DataLogin dataLogin) {

        ApiUtils.getAPIService().loginAccount(username, password).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {

                        JSONObject jsonObject = new JSONObject(response.body().string());
                        int status = jsonObject.getInt("success");
                        Log.i("status", String.valueOf(status));
                        if (status == 1) {
                            String username = jsonObject.getString("username");
                            String phone = jsonObject.getString("phone");
                            String email = jsonObject.getString("email");
                            String address = jsonObject.getString("address");

                            account.setUsername(username);
                            account.setPhoneNumber(phone);
                            account.setAddress(address);
                            account.setEmail(email);

                            dataLogin.dataLogin(true,account);

                        } else {
                            dataLogin.dataLogin(false,account);

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.i("error", t.getMessage());
            }
        });
    }

    public  void registter(String username, String password, final DataLogin dataLogin) {
        ApiUtils.getAPIService().registerAccount(username, password).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {

                        JSONObject jsonObject = new JSONObject(response.body().string());
                        int status = jsonObject.getInt("success");
                        Log.i("status", String.valueOf(status));
                        if (status == 1) {
                            String username = jsonObject.getString("username");
                            String phone = jsonObject.getString("phone");
                            String email = jsonObject.getString("email");
                            String address = jsonObject.getString("address");

                            account.setUsername(username);
                            account.setPhoneNumber(phone);
                            account.setAddress(address);
                            account.setEmail(email);
                            dataLogin.dataLogin(true,account);

                        } else {
                            Log.i("status", String.valueOf(status));

                            dataLogin.dataLogin(false,account);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }


}
