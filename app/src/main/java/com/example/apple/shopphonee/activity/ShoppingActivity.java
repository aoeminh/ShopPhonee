package com.example.apple.shopphonee.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apple.shopphonee.R;
import com.example.apple.shopphonee.adapter.ShoppingCartAdapter;
import com.example.apple.shopphonee.model.Cart;
import com.example.apple.shopphonee.model.Product;

import java.util.ArrayList;
import java.util.List;

public class ShoppingActivity extends BaseActivity implements View.OnClickListener {

    private Product product = new Product();
    ShoppingCartAdapter adapter;
    RecyclerView listShopping_rv;
    public static TextView totalBill;
    Button backHome, payBtn;
    TextView empty_text;
    private Toolbar toolbar;


    @Override
    void initView() {

        listShopping_rv =  this.findViewById(R.id.rv_list_shopping);
        totalBill = this.findViewById(R.id.tv_total_bill);
        backHome =  this.findViewById(R.id.btn_back_home);
        payBtn =  this.findViewById(R.id.btn_pay);
        toolbar =  this.findViewById(R.id.toolbar_shopping);
        empty_text = this.findViewById(R.id.tv_empty_shopping);
        toolbar.setNavigationIcon(R.mipmap.ic_action_arrow_back);
        if (MainActivity.cartList.size() > 0) {
            listShopping_rv.setVisibility(View.VISIBLE);
            empty_text.setVisibility(View.GONE);
        }
        adapter = new ShoppingCartAdapter(this, MainActivity.cartList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        listShopping_rv.setLayoutManager(layoutManager);
        listShopping_rv.setAdapter(adapter);
        setTotalBill();


    }

    @Override
    int getLayoutId() {
        return R.layout.activity_shopping;
    }

    @Override
    void setAction() {
        backHome.setOnClickListener(this);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        payBtn.setOnClickListener(this);
    }

    public static void setTotalBill() {
        int total = 0;
        for (int i = 0; i < MainActivity.cartList.size(); i++) {
            Cart cart = MainActivity.cartList.get(i);
            total += (cart.getQuantily() * cart.getProductPrice());
        }
        totalBill.setText(String.valueOf(total));


    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_back_home) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        if(id==R.id.btn_pay){
            if(MainActivity.checkLogin){
                Intent intent = new Intent(this,PayActivity.class);
                startActivity(intent);
            }else{
                Intent intent = new Intent(this,LoginActivity.class);
                startActivity(intent);
            }
        }
    }
}
