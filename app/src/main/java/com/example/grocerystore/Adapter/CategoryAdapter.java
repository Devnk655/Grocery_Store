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
import com.example.grocerystore.Activity.ShowAllActivity;
import com.example.grocerystore.Models.CategoryModel;
import com.example.grocerystore.Models.ShowAllModel;
import com.example.grocerystore.R;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    private Context context ;
    private List<CategoryModel> list;

    public CategoryAdapter(Context context, List<CategoryModel>list) {
        this.context=context;
        this.list=list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.category_list,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.ViewHolder holder, int position) {
        Glide.with(context).load(list.get(position).getImg_url()).into(holder.cat_img);
        holder.catName.setText(list.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView cat_img;
        TextView catName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cat_img = itemView.findViewById(R.id.cat_img);
            catName=itemView.findViewById(R.id.cat_name);
        }
    }
}
