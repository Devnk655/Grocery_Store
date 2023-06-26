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
import com.example.grocerystore.Models.NewProductsModel;
import com.example.grocerystore.R;

import java.util.List;

public class NewProductsAdapter extends RecyclerView.Adapter<NewProductsAdapter.ViewHolder> {
    private Context context ;
    private List<NewProductsModel> list;
    public NewProductsAdapter(android.content.Context context, List<NewProductsModel> list) {
        this.context=context;
        this.list=list;
    }
    public android.content.Context getContext() {

        return context;
    }

    public void setContext(Context context) {

        this.context = context;
    }

    public List<NewProductsModel> getList() {

        return list;
    }
    public void setList(List<NewProductsModel> list) {
        this.list = list;
    }
    @NonNull
    @Override
    public NewProductsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.new_products,parent,false));
    }
    @Override
    public void onBindViewHolder(@NonNull NewProductsAdapter.ViewHolder holder, int position) {
        Glide.with(context).load(list.get(position).getImg_url()).into(holder.vegetable_img);
        holder.new_product_name.setText(list.get(position).getName());
        holder.dollar.setText(String.valueOf(list.get(position).getPrice()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,DetailedActivity.class);
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
        ImageView vegetable_img;
        TextView new_product_name,dollar,dollar1;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            vegetable_img = itemView.findViewById(R.id.vegetables_img);
            new_product_name = itemView.findViewById(R.id.new_product_name);
            dollar = itemView.findViewById(R.id.dollar3);
            dollar1 = itemView.findViewById(R.id.dollar4);
        }
    }
}
