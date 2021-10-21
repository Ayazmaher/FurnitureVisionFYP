package com.example.furniturevision.ui.orderhistory;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.furniturevision.Order_adapter;
import com.example.furniturevision.Myorder_model;

import com.example.furniturevision.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class Orderhistory extends Fragment {


    Order_adapter myorderAdapter;
    TextView error_text;


    @SuppressLint("NotifyDataSetChanged")
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        View view = inflater.inflate(R.layout.fragment_orderhistory, container, false);

        RecyclerView rc_myorder = view.findViewById(R.id.rc_myorder);
        List<Myorder_model> myorder_modelList = new ArrayList<>();
        error_text=view.findViewById(R.id.error_order_history);

        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        myorderAdapter  = new Order_adapter(myorder_modelList,getContext());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rc_myorder.setLayoutManager(layoutManager);
        rc_myorder.setAdapter(myorderAdapter);

        if(myorderAdapter==null)
        {
            error_text.setText("Sorry you have not any place an Order Yet");
            error_text.setVisibility(View.VISIBLE);
            rc_myorder.setVisibility(View.INVISIBLE);
        }

       else{
            error_text.setText("");
            error_text.setVisibility(View.INVISIBLE);
            rc_myorder.setVisibility(View.VISIBLE);
            if(FirebaseAuth.getInstance().getCurrentUser() !=null)
            {
                firebaseFirestore.collection("My Orders").document(auth.getCurrentUser().getUid()).
                        collection("Order Details").get().addOnCompleteListener(task -> {
                            if(task.isSuccessful())
                            {
                                for(QueryDocumentSnapshot documentSnapshot : task.getResult())
                                {

                                    int a = Integer.parseInt(documentSnapshot.get("Product Price").toString());
                                    int b = Integer.parseInt(documentSnapshot.get("Product Quantity").toString());
                                    myorder_modelList.add( new Myorder_model(documentSnapshot.get("Product Image").toString(), documentSnapshot.get("Status").toString(), documentSnapshot.get("Product title").toString(), a,documentSnapshot.get("Product desc").toString(),b, documentSnapshot.getId()));
                                }
                                myorderAdapter.notifyDataSetChanged();

                            }else{
                                Toast.makeText(getContext(), "Error"+task.getException(), Toast.LENGTH_SHORT).show();
                            }
                        });

            }
            else{

                GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestEmail()
                        .build();
                GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(getContext(), gso);

                GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getContext());

                firebaseFirestore.collection("My Orders").document(acct.getId()).
                        collection("Order Details").get().addOnCompleteListener(task -> {
                            if(task.isSuccessful())
                            {
                                for(QueryDocumentSnapshot documentSnapshot : task.getResult())
                                {
                                    int a = Integer.parseInt(documentSnapshot.get("Product Price").toString());
                                    int b = Integer.parseInt(documentSnapshot.get("Product Quantity").toString());
                                    myorder_modelList.add( new Myorder_model(documentSnapshot.get("Product Image").toString(), documentSnapshot.get("Status").toString(), documentSnapshot.get("Product title").toString(), a,documentSnapshot.get("Product desc").toString(),b, documentSnapshot.getId()));
                                }
                                myorderAdapter.notifyDataSetChanged();

                            }else{
                                Toast.makeText(getContext(), "Error"+task.getException(), Toast.LENGTH_SHORT).show();
                            }
                        });


            }
        }








        return view;
    }
}