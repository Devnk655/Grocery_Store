package com.example.grocerystore.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.grocerystore.Adapter.AddressAdapter;
import com.example.grocerystore.Models.AddressModel;
import com.example.grocerystore.Models.NewProductsModel;
import com.example.grocerystore.Models.PopularProductsModel;
import com.example.grocerystore.Models.ShowAllModel;
import com.example.grocerystore.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddressActivity extends AppCompatActivity implements AddressAdapter.SelectedAddress{
    RecyclerView recyclerView;
    private List<AddressModel> addressModelList;
    private AddressAdapter addressAdapter;
    Button btnadd;
    Button paymentBtn;
    Toolbar toolbar;
    String mAddress="";
    FirebaseFirestore firestore;
    FirebaseAuth firebaseAuth;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        getSupportActionBar().hide();
        firestore=FirebaseFirestore.getInstance();
        firebaseAuth=FirebaseAuth.getInstance();
        paymentBtn=findViewById(R.id.payment_btn);
        recyclerView=findViewById(R.id.address_recycler);

        //Get Data from Detailed Activity
        Object obj = getIntent().getSerializableExtra("item");
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        addressModelList = new ArrayList<>();
        //using this it will retrive the date that is been added into the database
        addressAdapter = new AddressAdapter(getApplicationContext(),addressModelList,this::setAddress);
        recyclerView.setAdapter(addressAdapter);
        firestore.collection("CurrentUser").document(firebaseAuth.getCurrentUser().getUid())
                .collection("Address").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(DocumentSnapshot doc : task.getResult().getDocuments()) {
                                AddressModel addressModel = doc.toObject(AddressModel.class);
                                addressModelList.add(addressModel);
                                addressAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                });
        paymentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double amount =0.0;
                if(obj instanceof NewProductsModel){
                    NewProductsModel newProductModel = (NewProductsModel) obj;
                    amount = newProductModel.getPrice();
                }
                if(obj instanceof PopularProductsModel){
                    PopularProductsModel popularProductModel = (PopularProductsModel) obj;
                    amount = popularProductModel.getPrice();
                }
                if(obj instanceof ShowAllModel){
                    ShowAllModel showAllModel = (ShowAllModel) obj;
                    amount = showAllModel.getPrice();
                }
                Intent intent = new Intent(AddressActivity.this,PaymentActivity.class);
                intent.putExtra("amount",amount);
                startActivity(intent);
            }
        });

         //adding address and then moving to the add address activity
        btnadd=findViewById(R.id.add_btn);
        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddressActivity.this,AddAddressActivity.class));
            }
        });
    }
    public void setAddress(String address) {
      mAddress = address;
    }
}