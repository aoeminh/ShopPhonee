package com.example.apple.shopphonee.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.apple.shopphonee.R;
import com.example.apple.shopphonee.adapter.BillAdapter;
import com.example.apple.shopphonee.model.Bills;
import com.example.apple.shopphonee.utils.ApiUtils;
import com.example.apple.shopphonee.utils.Constant;
import com.example.apple.shopphonee.utils.UtilsSharePref;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListBills extends BaseActivity {

    RecyclerView rvListVill;
    private List<Bills> billsList = new ArrayList<>();
    private BillAdapter adapter;
    LinearLayout layoutProgress,layoutListBill;
    SharedPreferences sharedPreferences;

    @Override
    void initView() {

        rvListVill = this.findViewById(R.id.rv_list_bills);
        layoutProgress = this.findViewById(R.id.layout_progressBar);
        layoutListBill = findViewById(R.id.layout_list_bill);
        sharedPreferences = UtilsSharePref.getSharedPreferences(this);
        loadData();
        LinearLayoutManager layoutManager = new LinearLayoutManager(ListBills.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        adapter = new BillAdapter(ListBills.this, billsList);
        rvListVill.setLayoutManager(layoutManager);
        rvListVill.setAdapter(adapter);
        loadData();

    }

    @Override
    int getLayoutId() {
        return R.layout.activity_list_bills;
    }

    @Override
    void setAction() {
        adapter.getBillID(new BillAdapter.OnBillListener() {
            @Override
            public void getBillID(int id) {
                Intent intent = new Intent(ListBills.this,BillDetails.class);
                intent.putExtra("billID",id);
                startActivity(intent);
            }
        });

    }

    void loadData() {
        String phoneNumber = sharedPreferences.getString(Constant.PHONE_NUMBER, "");
        ApiUtils.getAPIService().getListBill(phoneNumber).enqueue(new Callback<List<Bills>>() {
            @Override
            public void onResponse(@NonNull Call<List<Bills>> call, @NonNull Response<List<Bills>> response) {
                if (response.isSuccessful()) {

                    billsList = response.body();
                    if(billsList.size()>0){
                        adapter.updateList(billsList);
                        adapter.notifyDataSetChanged();
                        layoutProgress.setVisibility(View.GONE);
                        layoutListBill.setVisibility(View.VISIBLE);
                    }

                }
            }

            @Override
            public void onFailure(Call<List<Bills>> call, Throwable t) {
                Log.i("error", t.getMessage());
            }
        });

    }

}
