package com.example.apple.shopphonee.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.apple.shopphonee.R;
import com.example.apple.shopphonee.activity.DetailProduct;
import com.example.apple.shopphonee.model.OnPosListener;
import com.example.apple.shopphonee.model.Product;
import com.example.apple.shopphonee.utils.ApiUtils;
import java.util.List;

public class PhoneAdapter extends RecyclerView.Adapter<PhoneAdapter.ViewHolder>  {

    private List<Product> phones ;
    private Context context;

    private OnPosListener onPosListener;
    public PhoneAdapter(Context context, List<Product> list){
        this.context  = context;
         this.phones = list;

    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View rowView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_item_phone,parent,false);

        return new ViewHolder(rowView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product  product = phones.get(position);
        ApiUtils.loadImage(product.getProductImage(),holder.phoneThumnail,context);
        holder.phoneName.setText(product.getProductName());
        holder.phonePrice.setText(String.valueOf(product.getProductPrice()));
        holder.phoneDescription.setText(product.getProductDescription());

    }

    @Override
    public int getItemCount() {
        return phones.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
         TextView phoneName, phonePrice, phoneDescription;
         ImageView phoneThumnail;

         ViewHolder(View itemView) {
            super(itemView);

            phoneName = itemView.findViewById(R.id.name_item_phone);
            phonePrice = itemView.findViewById(R.id.price_item_phone);
            phoneDescription = itemView.findViewById(R.id.description_item_phone);
            phoneThumnail = itemView.findViewById(R.id.thumnail_phone);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int position = getAdapterPosition();
                    onPosListener.getPositioin(position);

                }
            });
        }
    }

    public void updateList(List<Product> list){
        this.phones = list;
        notifyDataSetChanged();
    }

    public void getPosition(OnPosListener onPosListener){
        this.onPosListener = onPosListener;


    }


}
