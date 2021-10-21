package com.example.furniturevision.fargment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.furniturevision.ProductsAdapter;
import com.example.furniturevision.ProductsModel;
import com.example.furniturevision.R;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;


public class Products extends Fragment {

    public Products() {
        // Required empty public constructor
    }


    private List <ProductsModel> productsModelList;
    EditText search_products;
    ProductsAdapter productsAdapter;

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view =  inflater.inflate(R.layout.fragment_products, container, false);
        RecyclerView rc_productshow = view.findViewById(R.id.rc_productsdisplay);
        search_products=view.findViewById(R.id.search_product);
        productsModelList = new ArrayList<>();
        productsAdapter  = new ProductsAdapter(productsModelList,getContext());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rc_productshow.setLayoutManager(layoutManager);
        rc_productshow.setAdapter(productsAdapter);


        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("Products").get().addOnCompleteListener(task -> {

            if (task.isSuccessful())
            {
                for (QueryDocumentSnapshot documentSnapshot : task.getResult())
                {

                    int price = Integer.parseInt(documentSnapshot.get("Productprice").toString());


                 productsModelList.add(new ProductsModel(documentSnapshot.get("Productimage").toString(),documentSnapshot.get("Productname").toString(),price, documentSnapshot.get("Productdesc").toString() ));
                }
                productsAdapter.notifyDataSetChanged();
            }
            else{
                Toast.makeText(getContext(),"Error is"+task.getException(),Toast.LENGTH_LONG).show();
            }

        });


        search_products.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {



            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });

        return view;




    }

    private void filter(String text) {

        List <ProductsModel> list = new ArrayList<>();

        for(ProductsModel product : productsModelList)
        {
            if(product.getProduct_title().toLowerCase().contains(text.toLowerCase()))
            {
                list.add(product);
            }

        }
        productsAdapter.filterlist(list);
    }


}