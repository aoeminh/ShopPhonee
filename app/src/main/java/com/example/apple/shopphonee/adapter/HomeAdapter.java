package com.example.apple.shopphonee.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.apple.shopphonee.R;
import com.example.apple.shopphonee.activity.DetailProduct;
import com.example.apple.shopphonee.activity.MainActivity;
import com.example.apple.shopphonee.activity.ShoppingActivity;
import com.example.apple.shopphonee.model.Cart;
import com.example.apple.shopphonee.model.Product;

import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {
    private List<Product> phoneList;
    private Context context;

    public HomeAdapter(List<Product> list, Context context) {
        this.context = context;
        this.phoneList = list;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View rowView = LayoutInflater.from(context)
                .inflate(R.layout.item_card_view, parent, false);

        return new ViewHolder(rowView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product phone = phoneList.get(position);
        Glide.with(context).load(phone.getProductImage()).into(holder.thumnail);
        holder.phoneName.setText(phone.getProductName());
        holder.phonePrice.setText(String.valueOf(phone.getProductPrice()));
    }

    @Override
    public int getItemCount() {
        return phoneList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView thumnail, overflow;
        TextView phoneName, phonePrice;

        private ViewHolder(View itemView) {
            super(itemView);

            thumnail =itemView.findViewById(R.id.thumnail);
            overflow =  itemView.findViewById(R.id.overflow);
            phoneName =  itemView.findViewById(R.id.tv_phone_name);
            phonePrice =  itemView.findViewById(R.id.tv_phone_price);
            overflow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showPopup(context, v,getAdapterPosition());
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();

                    //jump to Detail activity
                    Intent intent = new Intent(context,DetailProduct.class);
                    Product product = phoneList.get(position);
                    intent.putExtra("id",product.getId());
                    intent.putExtra("name",product.getProductName());
                    intent.putExtra("price",product.getProductPrice());
                    intent.putExtra("image",product.getProductImage());
                    intent.putExtra("description",product.getProductDescription());
                    intent.putExtra("category",product.getProductId());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);


                }
            });

        }
    }

    public void updateList(List<Product> list) {
        phoneList = list;
        notifyDataSetChanged();
    }

    private void showPopup(final Context context, View v, final int position) {
        final PopupMenu popupMenu = new PopupMenu(context, v);
        popupMenu.inflate(R.menu.popup_menu);

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                int id = item.getItemId();
                switch (id) {
                    case R.id.action_add:

                        if(MainActivity.cartList.size()<10){
                           Product product = phoneList.get(position);
                            if (MainActivity.cartList.size() > 0) {
                                boolean exist =false ;
                                for (int i = 0; i < MainActivity.cartList.size(); i++) {
                                    Cart c ;
                                    c = MainActivity.cartList.get(i);
                                    if (product.getId().equals(c.getProductId()))  {
                                        c.setQuantily(c.getQuantily() + 1);
                                        if (c.getQuantily() > 10) {
                                            c.setQuantily(10);
                                        }
                                        Log.i("1", "1");
                                        Log.i("i", String.valueOf(i));
                                        exist = true;
                                    }

                                }
                                if (!exist) {
                                    DetailProduct.addToList(1,product);
                                    Log.i("2", String.valueOf(MainActivity.cartList.size()));

                                }
                            } else {

                                DetailProduct.addToList(1,product);
                                Log.i("3", "3");

                            }
                            Intent intent = new Intent(context, ShoppingActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                            context.startActivity(intent);

                        }else {
                            Toast.makeText(context,"Cart has been full",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(context, ShoppingActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intent);
                        }


                        return true;
                    case R.id.action_favorite:
                        Toast.makeText(context, "add favorite", Toast.LENGTH_SHORT).show();
                        return true;
                    default:
                        popupMenu.dismiss();
                        return true;

                }

            }
        });

        popupMenu.show();
    }

}
