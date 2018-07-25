package com.example.apple.shopphonee.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.apple.shopphonee.R;
import com.example.apple.shopphonee.activity.ShoppingActivity;
import com.example.apple.shopphonee.model.Cart;
import com.example.apple.shopphonee.utils.ApiUtils;
import java.util.List;

public class ShoppingCartAdapter extends RecyclerView.Adapter<ShoppingCartAdapter.ViewHolder> {

    private List<Cart> cartlist ;
    private Context context;


    public ShoppingCartAdapter(Context context, List<Cart> list) {
        this.cartlist = list;
        this.context = context;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rowView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_shopping_cart, parent, false);

        return new ViewHolder(rowView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final Cart cart = cartlist.get(position);

        ApiUtils.loadImage(cart.getProductImage(), holder.imageView, context);
        holder.productName.setText(cart.getProductName());
        holder.productPrice.setText(String.valueOf(cart.getProductPrice()));
        holder.quantily.setText(String.valueOf(cart.getQuantily()));

        int quanlity = Integer.parseInt(holder.quantily.getText().toString());
        if (quanlity >= 10) {
            holder.increase_btn.setVisibility(View.INVISIBLE);
            holder.decrease_btn.setVisibility(View.VISIBLE);

        }else if (quanlity <= 1) {

            holder.decrease_btn.setVisibility(View.INVISIBLE);
        }else  {
            holder.decrease_btn.setVisibility(View.VISIBLE);
            holder.increase_btn.setVisibility(View.VISIBLE);
        }
        //set onClick
        holder.decrease_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                int oldQuanlity = Integer.parseInt(holder.quantily.getText().toString());
                int newQuanlity = oldQuanlity - 1;
                cart.setQuantily(newQuanlity);
                notifyDataSetChanged();
                ShoppingActivity.setTotalBill();
                if (newQuanlity < 1) {
                    holder.decrease_btn.setVisibility(View.INVISIBLE);
                    Log.i("add",String.valueOf(newQuanlity));
                }
            }

        });

        holder.increase_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int oldQuanlity = Integer.parseInt(holder.quantily.getText().toString());
                int newQuanlity = oldQuanlity + 1;
                cart.setQuantily(newQuanlity);
                notifyDataSetChanged();
                ShoppingActivity.setTotalBill();
                if(newQuanlity>9){
                    holder.increase_btn.setVisibility(View.INVISIBLE);
                    Log.i("add",String.valueOf(newQuanlity));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return cartlist.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView productName, productPrice, quantily;
        public ImageButton increase_btn, decrease_btn;
        private ViewHolder(View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.thumnail_shopping_cart);
            productName =  itemView.findViewById(R.id.name_shopping_cart);
            productPrice = itemView.findViewById(R.id.price_shopping_cart);
            quantily =  itemView.findViewById(R.id.tv_quality_shopping_cart);
            increase_btn = itemView.findViewById(R.id.btn_increase);
            decrease_btn = itemView.findViewById(R.id.btn_decrease);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Delete Product");
                    builder.setMessage("Are you sure delete this product?");
                    builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            cartlist.remove(getAdapterPosition());
                            notifyDataSetChanged();

                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    AlertDialog alertDialog =  builder.create();
                    alertDialog.show();
                    return true;
                }
            });
        }
    }

}
