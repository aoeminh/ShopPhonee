package com.example.apple.shopphonee.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.apple.shopphonee.R;
import com.example.apple.shopphonee.model.Bills;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class BillAdapter extends RecyclerView.Adapter<BillAdapter.ViewHolder> {

    private List<Bills> billsList = new ArrayList<>();
    private Context context;
    private OnBillListener onBillListener;

    public BillAdapter(Context context, List<Bills> list){
        this.context = context;
        this.billsList = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_list_bill,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Bills bills = billsList.get(position);
        holder.tvName.setText(bills.getCustomerName());
        holder.tvPhone.setText(bills.getCustomerPhone());
        holder.tvAddress.setText(bills.getCustomerAddress());
        holder.tvNote.setText(bills.getNote());
        holder.tvTotalBill.setText(String.valueOf(bills.getTotalBill()));

        holder.tvDate.setText(bills.getDate());
    }

    @Override
    public int getItemCount() {
        return billsList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView tvName,tvPhone,tvAddress,tvTotalBill,tvNote,tvDate;
        public ViewHolder(View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tv_name_list_bill);
            tvPhone = itemView.findViewById(R.id.tv_phone_list_bill);
            tvAddress = itemView.findViewById(R.id.address_list_bill);
            tvTotalBill = itemView.findViewById(R.id.total_list_bill);
            tvNote = itemView.findViewById(R.id.note_list_bill);
            tvDate = itemView.findViewById(R.id.date_list_bill);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bills bills = billsList.get(getAdapterPosition());
                    onBillListener.getBillID(bills.getId());
                }
            });

        }
    }
        public void updateList(List<Bills> list){
        billsList = list;
        notifyDataSetChanged();
    }

    public interface OnBillListener{
        void getBillID(int id);
    }

    public void getBillID( OnBillListener onBillListener){
        this.onBillListener = onBillListener;

    }

}
