package com.example.apple.shopphonee.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apple.shopphonee.R;
import com.example.apple.shopphonee.adapter.ShoppingCartAdapter;
import com.example.apple.shopphonee.database.SQLiteUtils;
import com.example.apple.shopphonee.model.Cart;
import com.example.apple.shopphonee.model.OnPosListener;
import com.example.apple.shopphonee.model.Product;
import com.example.apple.shopphonee.utils.Constant;
import com.example.apple.shopphonee.utils.UtilsSharePref;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public class ShoppingActivity extends BaseActivity implements View.OnClickListener {

    private Product product = new Product();
    ShoppingCartAdapter adapter;
    RecyclerView listShopping_rv;
    public static TextView tvTotalBill;
    Button backHome, payBtn, deleteBtn;
    TextView empty_text;
    private Toolbar toolbar;
    SharedPreferences sharedPreerences;
    CheckBox deleteChk;
    LinearLayout checkAllLay;
    static int totalBill = 0;

    @Override
    void initView() {

        listShopping_rv = this.findViewById(R.id.rv_list_shopping);
        tvTotalBill = this.findViewById(R.id.tv_total_bill);
        backHome = this.findViewById(R.id.btn_back_home);
        payBtn = this.findViewById(R.id.btn_pay);
        toolbar = this.findViewById(R.id.toolbar_shopping);
        empty_text = this.findViewById(R.id.tv_empty_shopping);
        toolbar.setNavigationIcon(R.mipmap.ic_action_arrow_back);
        deleteChk = findViewById(R.id.cb_delete);
        deleteBtn = findViewById(R.id.btn_delete_all);
        checkAllLay = findViewById(R.id.layout_check_all);

        checkEmptyList();
        sqLiteUtils.open();

        adapter = new ShoppingCartAdapter(this, MainActivity.cartList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        listShopping_rv.setLayoutManager(layoutManager);
        listShopping_rv.setAdapter(adapter);
        setTotalBill();
        sharedPreerences = UtilsSharePref.getSharedPreferences(this);
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
        deleteChk.setOnClickListener(this);
        deleteBtn.setOnClickListener(this);

        adapter.OnRemoveItem(new OnPosListener() {
            @Override
            public void getPositioin(int position) {
                MainActivity.cartList.remove(position);
                adapter.notifyDataSetChanged();
                adapter.updateList(MainActivity.cartList);
                checkEmptyList();
            }
        });
    }

    public static int setTotalBill() {
        int total = 0;
        for (int i = 0; i < MainActivity.cartList.size(); i++) {
            Cart cart = MainActivity.cartList.get(i);
            total += (cart.getQuantily() * cart.getProductPrice());
        }
        NumberFormat format = NumberFormat.getCurrencyInstance(Locale.CANADA);

        String currency = format.format(total);
        tvTotalBill.setText(currency);
        return total;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {

            case R.id.btn_back_home:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_pay:
                if (MainActivity.cartList.size() > 0) {

                    if (sharedPreerences.getBoolean(Constant.LOGGED_IN, false)) {
                        Intent intent2 = new Intent(this, PayActivity.class);
                        startActivity(intent2);
                    } else {
                        Intent intent1 = new Intent(this, LoginActivity.class);
                        startActivity(intent1);
                    }
                } else {
                    Toast.makeText(ShoppingActivity.this, "Cart is empty!", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.cb_delete:
                if (deleteChk.isChecked()) {
                    for (Cart c : MainActivity.cartList) {
                        c.setSelected(true);
                    }
                } else {
                    for (Cart c : MainActivity.cartList) {
                        c.setSelected(false);
                    }
                }
                adapter.notifyDataSetChanged();
                break;
            case R.id.btn_delete_all:
                Log.i("size", MainActivity.cartList.size() + "");
                if (deleteChk.isChecked()) {
                    MainActivity.cartList.clear();
                    checkEmptyList();
                } else {
                    for (int i=MainActivity.cartList.size() -1; i>=0; i--) {
                        if(MainActivity.cartList.size()>0){
                            Cart c = MainActivity.cartList.get(i);
                            if (c.isSelected()) {
                                MainActivity.cartList.remove(c);
                                checkEmptyList();
                            }
                        }else {
                            Toast.makeText(ShoppingActivity.this,"Delete all product",Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                adapter.notifyDataSetChanged();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        sqLiteUtils.open();

        sqLiteUtils.deleteAll();
        for (int i = 0; i < MainActivity.cartList.size(); i++) {
            Cart cart = MainActivity.cartList.get(i);
            sqLiteUtils.addCart(cart);
            Log.i("test", "ok");
        }
        sqLiteUtils.close();
    }

    public void checkEmptyList() {
        if (MainActivity.cartList.size() > 0) {
            listShopping_rv.setVisibility(View.VISIBLE);
            checkAllLay.setVisibility(View.VISIBLE);
            empty_text.setVisibility(View.GONE);
            setTotalBill();
        } else {
            listShopping_rv.setVisibility(View.GONE);
            checkAllLay.setVisibility(View.GONE);
            empty_text.setVisibility(View.VISIBLE);
            setTotalBill();
        }
    }

}
