package com.example.apple.shopphonee.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;

import com.example.apple.shopphonee.R;
import com.example.apple.shopphonee.adapter.BillAdapter;
import com.example.apple.shopphonee.adapter.BillDetailsAdapter;
import com.example.apple.shopphonee.model.BillDetail;
import com.example.apple.shopphonee.utils.ApiUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BillDetails extends BaseActivity{

    RecyclerView rvBillDetails;
    private List<BillDetail> billDetailList = new ArrayList<BillDetail>();
    private BillDetailsAdapter billDetailsAdapter;

    @Override
    void initView() {
        rvBillDetails = findViewById(R.id.rv_bill_details);

        billDetailsAdapter = new BillDetailsAdapter(this,billDetailList);

        LinearLayoutManager layoutManager  = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvBillDetails.setLayoutManager(layoutManager);
        rvBillDetails.setAdapter(billDetailsAdapter);
        loadData();

    }

    @Override
    int getLayoutId() {
        return R.layout.activity_bill_details;
    }

    @Override
    void setAction() {

    }

    void loadData(){
       final int billID = getIntent().getExtras().getInt("billID");
       ApiUtils.getAPIService().getBillDetails(billID).enqueue(new Callback<List<BillDetail>>() {
           @Override
           public void onResponse(Call<List<BillDetail>> call, Response<List<BillDetail>> response) {
               if(response.isSuccessful()){
                   billDetailList = response.body();
                   billDetailsAdapter.updateList(billDetailList);
                   billDetailsAdapter.notifyDataSetChanged();
                   Log.i("success",billDetailList.get(0).getProductName());
               }
           }

           @Override
           public void onFailure(Call<List<BillDetail>> call, Throwable t) {
                Log.i("error",t.getMessage());
           }
       });

    }


}
