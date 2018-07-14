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
import android.widget.Toast;

import com.example.apple.shopphonee.R;
import com.example.apple.shopphonee.activity.LaptopActivity;
import com.example.apple.shopphonee.activity.PhoneActivity;
import com.example.apple.shopphonee.model.Category;
import com.example.apple.shopphonee.utils.ApiUtils;

import java.util.List;

public class NavigationAdapter extends RecyclerView.Adapter<NavigationAdapter.ViewHolder> {

    private List<Category> listCategory ;
    private Context context;

    public NavigationAdapter (List<Category> list,Context context){
        this.listCategory = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View rowView = LayoutInflater.from(context)
                .inflate(R.layout.row_item_list_category,parent,false);

        return new ViewHolder(rowView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Category category = listCategory.get(position);
        ApiUtils.loadImage(category.getCategoryImage(),holder.imageView,context);
        holder.textView.setText(category.getCategoryName());
    }

    @Override
    public int getItemCount() {
        return listCategory.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

         TextView textView;
         ImageView imageView;
         ViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.text_row_category);
            imageView =itemView.findViewById(R.id.image_row_category);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String checkStr = textView.getText().toString();

                    switch (checkStr){
                        case "Phone":
                            Intent intent = new Intent(context,PhoneActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intent);
                            break;
                        case "Laptop":
                            Intent intent1 = new Intent(context,LaptopActivity.class);
                            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intent1);
                            break;
                         default:
                             Toast.makeText(context,"Choose item",Toast.LENGTH_SHORT).show();
                             break;

                    }


                }
            });
        }
    }
    public void updateList(List<Category> listCategory){
        this.listCategory = listCategory;
        notifyDataSetChanged();
    }




}

