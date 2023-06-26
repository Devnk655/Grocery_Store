package com.example.grocerystore.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.grocerystore.Activity.ShowAllActivity;
import com.example.grocerystore.Adapter.CategoryAdapter;
import com.example.grocerystore.Adapter.NewProductsAdapter;
import com.example.grocerystore.Adapter.PopularProductAdapter;
import com.example.grocerystore.Models.CategoryModel;
import com.example.grocerystore.Models.NewProductsModel;
import com.example.grocerystore.Models.PopularProductsModel;
import com.example.grocerystore.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {
    TextView catShowAll,popularShowAll,newProductsShowAll;
    //progress dialog
    ProgressDialog progressDialog;
    //LinearLayout
    LinearLayout linearlayout;
    //firebasefirestore variable
    FirebaseFirestore db;
    //category recyclerview
    RecyclerView catRecyclerView;
    //newproductrecyclerview
    RecyclerView newProductRecyclerView;
    //popularproductrecycerview
    RecyclerView popularRecyclerView;
    //category adapter
    CategoryAdapter categoryAdapter;
    List<CategoryModel> categoryModelList;
    // New Product Adapter
    NewProductsAdapter newProductsAdapter;
    List<NewProductsModel> newProductsModelList;

    //Popular Product Adapter

    PopularProductAdapter popularProductAdapter;
    List<PopularProductsModel> popularProductsModelList;



    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        //instance of firebasefirestore
        db=FirebaseFirestore.getInstance();
        catRecyclerView = root.findViewById(R.id.rec_category);
        newProductRecyclerView=root.findViewById(R.id.new_product_rec);
        popularRecyclerView=root.findViewById(R.id.popular_rec);

        catShowAll = root.findViewById(R.id.category_see_all);
        popularShowAll=root.findViewById(R.id.popular_see_all);
        newProductsShowAll=root.findViewById(R.id.newProducts_see_all);
        catShowAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ShowAllActivity.class);
                startActivity(intent);
            }
        });
        popularShowAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ShowAllActivity.class);
                startActivity(intent);
            }
        });
        newProductsShowAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ShowAllActivity.class);
                startActivity(intent);
            }
        });

        //linearlayout
       linearlayout = root.findViewById(R.id.home_layout);
       linearlayout.setVisibility(View.GONE);
       progressDialog = new ProgressDialog(getActivity());
        //Image Slider
        ImageSlider imageSlider = root.findViewById(R.id.image_slider);
        List<SlideModel> slideModels = new ArrayList<>();
        slideModels.add(new SlideModel(R.drawable.fruits2,"Fresh and Good Quality Fruits", ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel(R.drawable.fruits3,"Discount on good quality Vegetables", ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel(R.drawable.fruits,"50%Off on Flakes", ScaleTypes.CENTER_CROP));
        imageSlider.setImageList(slideModels);
        //Your dialog box when app will be started
        progressDialog.setTitle("Welcome To Our Grocery Store ");
        progressDialog.setMessage("Please wait...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        //Category Database
        catRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
        categoryModelList =  new ArrayList<>();
        categoryAdapter = new CategoryAdapter(getActivity(),categoryModelList);
        catRecyclerView.setAdapter(categoryAdapter);
         db.collection("Category")

                 .get()
                 .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                     @Override
                     public void onComplete(@NonNull Task<QuerySnapshot> task) {
                         if (task.isSuccessful()) {
                             for (QueryDocumentSnapshot document : task.getResult()) {
                                 CategoryModel categoryModel = document.toObject(CategoryModel.class);
                                 categoryModelList.add(categoryModel);
                                 categoryAdapter.notifyDataSetChanged();
                                 //this will only show the view whenever the dsilog box is opened here
                                 linearlayout.setVisibility(View.VISIBLE);
                                 //this will dismiss the dialog box whenever it is been app is open sucessfully
                                 progressDialog.dismiss();
                             }
                         } else {
                             Toast.makeText(getActivity(), ""+task.getException(), Toast.LENGTH_SHORT).show();
                         }
                     }
                 });
         //New Product Database
        newProductRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
        newProductsModelList = new ArrayList<>();
        newProductsAdapter = new NewProductsAdapter(getContext(),newProductsModelList);
        newProductRecyclerView.setAdapter(newProductsAdapter);
        db.collection("NewProducts")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                              NewProductsModel newProductsModel = document.toObject(NewProductsModel.class);
                              newProductsModelList.add(newProductsModel);
                              newProductsAdapter.notifyDataSetChanged();
                            }
                        } else {
                            Toast.makeText(getActivity(), ""+task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        //All Products Database
        popularRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
        popularProductsModelList= new ArrayList<>();
        popularProductAdapter = new PopularProductAdapter(getContext(),popularProductsModelList);
        popularRecyclerView.setAdapter(popularProductAdapter);
        db.collection("AllProducts")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                PopularProductsModel popularProductsModel= document.toObject(PopularProductsModel.class);
                                popularProductsModelList.add(popularProductsModel);
                                popularProductAdapter.notifyDataSetChanged();
                            }
                        } else {
                            Toast.makeText(getActivity(), ""+task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        return  root;
    }
}