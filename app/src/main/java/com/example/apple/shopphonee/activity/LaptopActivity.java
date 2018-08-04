package com.example.apple.shopphonee.activity;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
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

public class LaptopActivity extends BaseActivity {
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private List<Product> laptops  = new ArrayList<>();
    private PhoneAdapter adapter;
    private ProgressBar progressBar;
    private EditText edtSearch;
    List<Product> listSearch = new ArrayList<>();
    @Override
    void initView() {
        toolbar = this.findViewById(R.id.toolbar_laptop);
        recyclerView = this.findViewById(R.id.rv_laptop);
        progressBar = this.findViewById(R.id.progress_bar_laptop);
        toolbar.setNavigationIcon(R.mipmap.ic_action_arrow_back);

        edtSearch= findViewById(R.id.edt_search_laptop);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        adapter = new PhoneAdapter(this,laptops);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
        loadData();
    }

    private void loadData() {
        ApiUtils.getAPIService().getLapTop().enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(@NonNull Call<List<Product>> call,@NonNull  Response<List<Product>> response) {
                if(response.isSuccessful()){
                    laptops = response.body();
                    adapter.updateList(laptops);
                    adapter.notifyDataSetChanged();
                    if(laptops.size()>0){
                        recyclerView.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Product>> call,@NonNull  Throwable t) {
                Log.e("Error", t.getMessage());
            }
        });
    }

    @Override
    int getLayoutId() {
        return R.layout.activity_laptop;
    }

    @Override
    void setAction() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                listSearch.clear();
                String text = edtSearch.getText().toString();
                for (int i = 0; i< laptops.size(); i++){
                    if(laptops.get(i).getProductName().toLowerCase().contains(text.toLowerCase())){
                        listSearch.add(laptops.get(i));

                    }
                }

                adapter.updateList(listSearch);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

}