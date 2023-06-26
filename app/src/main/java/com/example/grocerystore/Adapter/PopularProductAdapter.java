package com.example.grocerystore.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.grocerystore.Activity.DetailedActivity;
import com.example.grocerystore.Models.PopularProductsModel;
import com.example.grocerystore.R;

import java.util.List;

public class PopularProductAdapter extends RecyclerView.Adapter<PopularProductAdapter.ViewHolder> {
    private Context context ;
    private List<PopularProductsModel> list;
    public PopularProductAdapter(android.content.Context context, List<PopularProductsModel> list) {
        this.context=context;
        this.list=list;
    }
    public android.content.Context getContext() {

        return context;
    }

    public void setContext(Context context) {

        this.context = context;
    }

    public List<PopularProductsModel> getList() {
        return list;
    }
    public void setList(List<PopularProductsModel> list) {
        this.list = list;
    }
    @NonNull
    @Override
    public PopularProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PopularProductAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.popular_items,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull PopularProductAdapter.ViewHolder holder, int position) {
        Glide.with(context).load(list.get(position).getImg_url()).into(holder.imageView);
        holder.name.setText(list.get(position).getName());
        holder.price.setText(String.valueOf(list.get(position).getPrice()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailedActivity.class);
                intent.putExtra("detailed",list.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView name,price,price2,all_product_name;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.veg_img);
            all_product_name = itemView.findViewById(R.id.new_product_name);
            name=itemView.findViewById(R.id.name);
            price=itemView.findViewById(R.id.dollar3);
            price2=itemView.findViewById(R.id.dollar4);
        }
    }
}
