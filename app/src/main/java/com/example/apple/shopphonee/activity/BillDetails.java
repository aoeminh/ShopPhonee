package com.example.apple.shopphonee.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toolbar;

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
    TextView tvTotal;
    Toolbar toolbar;

    @Override
    void initView() {
        rvBillDetails = findViewById(R.id.rv_bill_details);
        tvTotal = findViewById(R.id.tv_total_details);
        toolbar = findViewById(R.id.tool_bar_list_details);
        toolbar.setNavigationIcon(R.mipmap.ic_action_arrow_back);
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
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
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
                   tvTotal.setText(String.valueOf(getTotalBill(billDetailList)));

               }
           }

           @Override
           public void onFailure(Call<List<BillDetail>> call, Throwable t) {
                Log.i("error",t.getMessage());
           }
       });

    }



     int getTotalBill(List<BillDetail> list){

        int totalBill = 0;
        for(int i = 0;i<list.size();i++){
            totalBill +=(list.get(i).getQuantily() * list.get(i).getProductPrice());
        }
        return totalBill;
    }


}
