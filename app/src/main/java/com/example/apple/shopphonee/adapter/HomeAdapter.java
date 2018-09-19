package com.example.apple.shopphonee.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
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
import com.example.apple.shopphonee.model.OnPosListener;
import com.example.apple.shopphonee.model.Product;
import com.example.apple.shopphonee.utils.ApiUtils;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {
    private List<Product> phoneList;
    private Context context;
     private OnPosListener onPosListener;

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
        NumberFormat format = NumberFormat.getCurrencyInstance(Locale.CANADA);
        String currency = format.format(phone.getProductPrice());
        holder.phonePrice.setText(String.valueOf(currency));

        holder.phonePrice.setTextColor(Color.RED);
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
                    onPosListener.getPositioin(position);
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
                        Product product = phoneList.get(position);

                        ApiUtils.addItemToCart(context,product);
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

    public void getPositionHome(OnPosListener onPosListener){
        this.onPosListener = onPosListener;

    }
}
