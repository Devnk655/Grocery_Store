package com.example.grocerystore.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toolbar;

import com.example.grocerystore.Adapter.MyCartAdapter;
import com.example.grocerystore.Models.MyCartModel;
import com.example.grocerystore.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {
    int overAllTotalAmount;
    Toolbar toolbar;
    RecyclerView recyclerView;
    TextView overAllAmount;
    List<MyCartModel> cardModellist;
    MyCartAdapter cartAdapter;
    private FirebaseAuth auth;
    private FirebaseFirestore firebaseFirestore;
    //This activity is for the Data That we select to purchase for the cart
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        overAllAmount=findViewById(R.id.textView3);
        auth=FirebaseAuth.getInstance();
        getSupportActionBar().hide();
        firebaseFirestore = FirebaseFirestore.getInstance();
        //get data from my cart adapter
        // and localbrodcast manager will able to listen the data from the another activity
        LocalBroadcastManager.getInstance(this)
                .registerReceiver(mMessageReceiver,new IntentFilter("MyTotalAmount"));

        recyclerView = findViewById(R.id.cart_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        cardModellist =  new ArrayList<>();
        cartAdapter = new MyCartAdapter(this,cardModellist);
        recyclerView.setAdapter(cartAdapter);
        firebaseFirestore.collection("AddToCart")
                .document(auth.getCurrentUser().getUid())
                .collection("User")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()) {
                            for (DocumentSnapshot document : task.getResult().getDocuments()) {
                                MyCartModel myCartModel = document.toObject(MyCartModel.class);
                                cardModellist.add(myCartModel);
                                cartAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                });
    }
    public BroadcastReceiver mMessageReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //here it will calulate the total price and show on the result that is been there in add to cart
            int totalBill=intent.getIntExtra("totalAmount",0);
            overAllAmount.setText("Total Amount:"+totalBill+"Rs");
        }
    };
}
