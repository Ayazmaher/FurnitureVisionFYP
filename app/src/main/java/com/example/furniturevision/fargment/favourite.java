package com.example.furniturevision.fargment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.furniturevision.R;
import com.example.furniturevision.Wishlist_prdouct_adapter;
import com.example.furniturevision.Wishlist_product_model;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;


public class favourite extends Fragment {



    public favourite() {
        // Required empty public constructor
    }


    private List <Wishlist_product_model>wishlistProductModelList;
    FirebaseAuth auth;
    GoogleSignInClient mGoogleSignInClient;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_favourite, container, false);
        RecyclerView rc_wishlist_product = view.findViewById(R.id.rc_wishlist_product);
        wishlistProductModelList= new ArrayList<>();
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        auth= FirebaseAuth.getInstance();
        Wishlist_prdouct_adapter wishlist_prdouct_adapter = new Wishlist_prdouct_adapter(wishlistProductModelList,getContext());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rc_wishlist_product.setLayoutManager(layoutManager);
        rc_wishlist_product.setAdapter(wishlist_prdouct_adapter);






        if(FirebaseAuth.getInstance().getCurrentUser() !=null)
        {
            firebaseFirestore.collection("My Wishlist").document(auth.getCurrentUser().getUid()).collection("Current User").get().addOnCompleteListener(task -> {
                if(task.isSuccessful())
                {
                    for( DocumentSnapshot documentSnapshot : task.getResult().getDocuments())

                    {

                        wishlistProductModelList.add(new Wishlist_product_model(documentSnapshot.get("Product Image").toString(),documentSnapshot.get("Product title").toString(),"RS" + "" + documentSnapshot.get("Product price").toString() + "/-" ,documentSnapshot.get("Product Desc").toString(),documentSnapshot.getId()));
                    }
                    wishlist_prdouct_adapter.notifyDataSetChanged();
                }

                else{
                    Toast.makeText(getContext(),"Some thing Went Wrong", Toast.LENGTH_LONG).show();
                }
            });

        }
        else
            {
            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestEmail()
                    .build();
            mGoogleSignInClient = GoogleSignIn.getClient(getContext(), gso);

            GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getContext());

            if (acct != null)
            {
                String uid= acct.getId();
                firebaseFirestore.collection("My Wishlist").document(uid).collection("Current User").get().addOnCompleteListener(task -> {
                    if(task.isSuccessful())
                    {
                        for(DocumentSnapshot documentSnapshot : task.getResult().getDocuments())
                        {
                            wishlistProductModelList.add(new Wishlist_product_model(documentSnapshot.get("Product Image").toString(),documentSnapshot.get("Product title").toString(),"RS" + "" + documentSnapshot.get("Product price").toString() + "/-" ,documentSnapshot.get("Product Desc").toString(),documentSnapshot.getId()));
                        }
                        wishlist_prdouct_adapter.notifyDataSetChanged();
                    }

                    else{
                        Toast.makeText(getContext(),"Some thing Went Wrong", Toast.LENGTH_LONG).show();
                    }
                });

            }



        }



        return view;
    }
}