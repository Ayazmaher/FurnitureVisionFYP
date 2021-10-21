package com.example.furniturevision.ui.Mycart;


import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.furniturevision.BasicDetail;
import com.example.furniturevision.Cart_item_Adapter;
import com.example.furniturevision.Cart_item_model;
import com.example.furniturevision.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class cart extends Fragment  {


    private List<Cart_item_model> cart_item_modelList;
    TextView total_price ,total_cart_items,total_label,cart_error;
    Button button1;
    GoogleSignInClient mGoogleSignInClient;
    FirebaseAuth auth ;
    Cart_item_Adapter cart_item_adapter;
    int total_bill;


    @SuppressLint("NotifyDataSetChanged")
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        View view = inflater.inflate(R.layout.fragment_mycart, container, false);

        RecyclerView rc_cart = view.findViewById(R.id.rc_mycart);
        cart_item_modelList= new ArrayList<>();
        button1=view.findViewById(R.id.proceed);
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        auth= FirebaseAuth.getInstance();
        total_price=view.findViewById(R.id.total_price);
        total_cart_items=view.findViewById(R.id.txt_cart_item);
        total_label=view.findViewById(R.id.txt_total_label);
        cart_error=view.findViewById(R.id.txt_Cart_error);
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(receiver,new IntentFilter("Total Amount of Product"));


        cart_item_adapter = new Cart_item_Adapter(cart_item_modelList,getContext());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rc_cart.setLayoutManager(layoutManager);
        rc_cart.setAdapter(cart_item_adapter);

        if(cart_item_adapter==null)
        {
            rc_cart.setVisibility(View.INVISIBLE);
            button1.setVisibility(View.INVISIBLE);
            total_price.setVisibility(View.INVISIBLE);
            total_cart_items.setVisibility(View.INVISIBLE);
            total_label.setVisibility(View.INVISIBLE);
            cart_error.setVisibility(View.VISIBLE);


        }

        else{

            rc_cart.setVisibility(View.VISIBLE);
            button1.setVisibility(View.VISIBLE);
            total_price.setVisibility(View.VISIBLE);
            total_cart_items.setVisibility(View.VISIBLE);
            total_label.setVisibility(View.VISIBLE);
            cart_error.setVisibility(View.INVISIBLE);
            if(FirebaseAuth.getInstance().getCurrentUser() !=null) {
                firebaseFirestore.collection("My Cart").document(auth.getCurrentUser().getUid()).collection("Current User").get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {


                            int a = Integer.parseInt(documentSnapshot.get("Product price").toString());
                            int b = Integer.parseInt(documentSnapshot.get("Product Quantity").toString());



                            cart_item_modelList.add(new Cart_item_model(documentSnapshot.get("Product Image").toString(), documentSnapshot.get("Product title").toString(),a, b, documentSnapshot.get("Product Desc").toString(),documentSnapshot.getId()));

                            cart_item_adapter.notifyDataSetChanged();

                        }




                    } else {
                        Toast.makeText(getContext(), "Some thing Went Wrong", Toast.LENGTH_LONG).show();
                    }

                });





            }

            else{
                GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestEmail()
                        .build();
                mGoogleSignInClient = GoogleSignIn.getClient(getContext(), gso);

                GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getContext());
                if (acct != null)
                {
                    String uid= acct.getId();

                    firebaseFirestore.collection("My Cart").document(uid).collection("Current User").get().addOnCompleteListener(task -> {
                        if(task.isSuccessful())
                        {
                            for(DocumentSnapshot documentSnapshot : task.getResult().getDocuments())
                            {


                                int a = Integer.parseInt(documentSnapshot.get("Product price").toString());
                                int b = Integer.parseInt(documentSnapshot.get("Product Quantity").toString());
                                cart_item_modelList.add(new Cart_item_model(documentSnapshot.get("Product Image").toString(),documentSnapshot.get("Product title").toString(), a ,b, documentSnapshot.get("Product Desc").toString(),documentSnapshot.getId()));

                                cart_item_adapter.notifyDataSetChanged();
                            }


                        }





                        else
                        {
                            Toast.makeText(getContext(),"Some thing Went Wrong", Toast.LENGTH_LONG).show();
                        }
                    });



                }




            }

            button1.setOnClickListener(v -> {


                Intent move_to_details = new Intent(getContext(), BasicDetail.class);
                move_to_details.putExtra("cost of Products",total_bill) ;
                startActivity(move_to_details);

            });

        }




        return view;


    }
    public BroadcastReceiver receiver = new BroadcastReceiver() {
        @SuppressLint("SetTextI18n")
        @Override
        public void onReceive(Context context, Intent intent) {
            total_bill  = intent.getIntExtra("total amount",0);

            total_price.setText("RS"  + "" +total_bill);
        }
    };
}