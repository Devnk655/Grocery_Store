package com.example.grocerystore.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grocerystore.Activity.AddressActivity;
import com.example.grocerystore.Models.AddressModel;
import com.example.grocerystore.R;

import java.util.List;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.ViewHolder> {
    List<AddressModel> addressModelList;
    Context context;
    SelectedAddress secelectedAddress;

    private RadioButton selectedRadioBtn;

    public AddressAdapter(Context context , List<AddressModel> addressModelList , SelectedAddress secelectedAddress) {
        this.addressModelList = addressModelList;
        this.context = context;
        this.secelectedAddress = secelectedAddress;
    }

    @NonNull
    @Override
    public AddressAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return  new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.address_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull AddressAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.address.setText(addressModelList.get(position).getUserAddress());
        holder.radioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(AddressModel addressModel: addressModelList){
                    addressModel.setSelected(false);
                }
                addressModelList.get(position).setSelected(true);
                if(selectedRadioBtn!=null){
                    selectedRadioBtn.setChecked(true);
                }
                selectedRadioBtn = (RadioButton)  view;
                selectedRadioBtn.setChecked(true);
                secelectedAddress.setAddress(addressModelList.get(position).getUserAddress());

            }
        });
    }

    @Override
    public int getItemCount() {
        return addressModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView address;
        RadioButton radioButton;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            address = itemView.findViewById(R.id.address_add);
            radioButton = itemView.findViewById(R.id.select_address);
        }
    }
    public interface SelectedAddress{
        void setAddress(String address);
    }
}