package com.example.apple.shopphonee.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apple.shopphonee.R;
import com.example.apple.shopphonee.model.Cart;
import com.example.apple.shopphonee.model.Product;
import com.example.apple.shopphonee.utils.ApiUtils;


public class DetailProduct extends BaseActivity implements View.OnClickListener {

     ImageView thumnail;
     TextView nameProduct, decriptionProduct, priceProduct;
    private Spinner quantily_spn;
    private Button buy_btn;
    private  Product product = new Product();
    private android.support.v7.widget.Toolbar toolbar;


    @Override
    void initView() {
        thumnail = this.findViewById(R.id.thumnail_detail);
        nameProduct = this.findViewById(R.id.name_details);
        decriptionProduct = this.findViewById(R.id.tv_descripton_details);
        priceProduct = this.findViewById(R.id.price_details);
        quantily_spn = this.findViewById(R.id.spn_quantily_details);
        buy_btn = this.findViewById(R.id.btn_buy_details);
        toolbar = (android.support.v7.widget.Toolbar) this.findViewById(R.id.toolbar_detail);

        Bundle bundle = getIntent().getExtras();
        product = ApiUtils.getBundle(bundle, product);

        //pass data for detailActivity
        ApiUtils.loadImage(product.getProductImage(), thumnail, this);
        nameProduct.setText(product.getProductName());
        priceProduct.setText(String.valueOf(product.getProductPrice()));
        decriptionProduct.setText(product.getProductDescription());
        toolbar.setNavigationIcon(R.mipmap.ic_action_arrow_back);

    }

    @Override
    int getLayoutId() {
        return R.layout.activity_detail_product;
    }

    @Override
    void setAction() {
        buy_btn.setOnClickListener(this);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }


    @Override
    public void onClick(View v) {

        if(MainActivity.cartList.size()<10){
            int quantily = Integer.parseInt(quantily_spn.getSelectedItem().toString());
            if (MainActivity.cartList.size() > 0) {
                boolean exist = false;
                for (int i = 0; i < MainActivity.cartList.size(); i++) {
                    Cart c ;
                    c = MainActivity.cartList.get(i);
                    Log.i("product",product.getId());
                    Log.i("c",c.getProductId());

                    if (product.getId().equals(c.getProductId()))  {
                        c.setQuantily(c.getQuantily() + quantily);
                        if (c.getQuantily() > 10) {
                            c.setQuantily(10);
                        }
                        Log.i("1", "1");
                        Log.i("i", String.valueOf(i));
                        exist = true;
                    }

                }
                if (!exist) {
                    addToList(quantily,product);
                    Log.i("2", String.valueOf(MainActivity.cartList.size()));

                }
            } else {

                addToList(quantily,product);
                Log.i("3", "3");

            }
            Intent intent = new Intent(getApplicationContext(), ShoppingActivity.class);
            startActivity(intent);

        }else {
            Toast.makeText(this,"Cart has been full",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), ShoppingActivity.class);
            startActivity(intent);
        }



    }

   public static void addToList(int quantily,Product product) {
        Cart cart = new Cart();
        cart.setProductId(product.getId());
        cart.setProductName(product.getProductName());
        cart.setProductPrice(product.getProductPrice());
        cart.setProductImage(product.getProductImage());
        cart.setQuantily(quantily);
        MainActivity.cartList.add(cart);
    }

}
