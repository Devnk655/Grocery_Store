package com.example.grocerystore.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.grocerystore.Adapter.ShowAllAdapter;
import com.example.grocerystore.Models.ShowAllModel;
import com.example.grocerystore.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ShowAllActivity extends AppCompatActivity {
    RecyclerView mRecyclerView;
    ShowAllAdapter showAllAdapter;
    List<ShowAllModel> showAllModellist;
    FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all2);
        getSupportActionBar().hide();
        String type = getIntent().getStringExtra("type");
        firestore = FirebaseFirestore.getInstance();
        mRecyclerView = findViewById(R.id.show_all_rec);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        showAllModellist = new ArrayList<>();
        showAllAdapter = new ShowAllAdapter(this, showAllModellist);
        mRecyclerView.setAdapter(showAllAdapter);
        if (type == null || type.isEmpty()) {
            firestore.collection("ShowAll")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (DocumentSnapshot document : task.getResult().getDocuments()) {
                                    ShowAllModel showAllModel = document.toObject(ShowAllModel.class);
                                    showAllModellist.add(showAllModel);
                                    showAllAdapter.notifyDataSetChanged();

                                }
                            } else {
                                Toast.makeText(ShowAllActivity.this, "" + task.getException(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
              }
            }
        }