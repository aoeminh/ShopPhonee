package com.example.apple.shopphonee.activity;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.example.apple.shopphonee.R;
import com.example.apple.shopphonee.adapter.PhoneAdapter;
import com.example.apple.shopphonee.model.Product;
import com.example.apple.shopphonee.utils.ApiUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PhoneActivity extends BaseActivity {

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private List<Product>  phones  = new ArrayList<>();
    private PhoneAdapter adapter;
    private ProgressBar progressBar;
    @Override
    void initView() {
        toolbar = this.findViewById(R.id.toolbar_phone);
        recyclerView = this.findViewById(R.id.rv_phone);
        progressBar = this.findViewById(R.id.progress_bar_phone);
        toolbar.setNavigationIcon(R.mipmap.ic_action_arrow_back);


        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        adapter = new PhoneAdapter(this,phones);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
        loadData();


    }

    @Override
    int getLayoutId() {
        return R.layout.activity_phone;
    }

    @Override
    void setAction() {

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    void loadData(){
        ApiUtils.getAPIService().getPhone().enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(@NonNull Call<List<Product>> call,@NonNull  Response<List<Product>> response) {
                if(response.isSuccessful()){
                    phones = response.body();
                    adapter.updateList(phones);
                    adapter.notifyDataSetChanged();
                    if(phones.size()>0){
                        recyclerView.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);

                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Product>> call,@NonNull  Throwable t) {
                Log.e("Error",t.getMessage());
            }
        });
    }
}