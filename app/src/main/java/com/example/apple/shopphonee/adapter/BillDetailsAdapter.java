package com.example.apple.shopphonee.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.apple.shopphonee.R;
import com.example.apple.shopphonee.model.BillDetail;

import java.util.ArrayList;
import java.util.List;

public class BillDetailsAdapter extends RecyclerView.Adapter<BillDetailsAdapter.ViewHolder> {
    private List<BillDetail> list = new ArrayList<>();
    private Context context;

    public BillDetailsAdapter(Context context, List<BillDetail> list){
        this.context = context;
        this.list = list;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rowView = LayoutInflater.from(context).inflate(R.layout.item_bill_details,parent,false);

        return new ViewHolder(rowView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BillDetail billDetail = list.get(position);

        holder.name.setText(billDetail.getProductName());
        holder.price.setText(String.valueOf(billDetail.getProductPrice()));
        holder.quantily.setText(String.valueOf(billDetail.getQuantily()));
        holder.totalRow.setText(String.valueOf(billDetail.getTotalRow()));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name,price,quantily,totalRow;
        public ViewHolder(View itemView) {
            super(itemView);

            name= itemView.findViewById(R.id.tv_name_details);
            price = itemView.findViewById(R.id.tv_price_details);
            quantily = itemView.findViewById(R.id.tv_quantily_details);
            totalRow = itemView.findViewById(R.id.total_details);
        }
    }

    public void updateList(List<BillDetail> detailList){
        this.list = detailList;

    }
}
