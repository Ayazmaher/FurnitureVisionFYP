package com.example.furniturevision;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FinalConfirmation extends AppCompatActivity {

      RecyclerView rc_final_confirm;
      List<FinalConfirmationModel> finalConfirmationModelList;
      String final_email, final_name,final_add,final_city,payment_type ;
      static  Button order_now;
      int phone11;
      TextView total_price;
      String document_id;



    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_confirmation);

        // Firebase code
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        FirebaseAuth auth = FirebaseAuth.getInstance();


        //  Code for Layout
        View includedLayout = findViewById(R.id.include_card_details);
        TextView user_email1 = includedLayout.findViewById(R.id.final_user_email);
        TextView user_add1 = includedLayout.findViewById(R.id.final_user_address);
        TextView user_city1 = includedLayout.findViewById(R.id.final_user_city);
        TextView user_name1 = includedLayout.findViewById(R.id.final_user_name);
        total_price = findViewById(R.id.total_cost);
        order_now = findViewById(R.id.place_order);



        // RC_Code
        rc_final_confirm= findViewById(R.id.recyclerView_final);
        finalConfirmationModelList= new ArrayList<>();
        FinalConfirmationAdapter finalConfrimationAdapter = new FinalConfirmationAdapter(finalConfirmationModelList,FinalConfirmation.this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(FinalConfirmation.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rc_final_confirm.setLayoutManager(layoutManager);
        rc_final_confirm.setAdapter(finalConfrimationAdapter);



        // Conditions to Fetch data from Database

        if(FirebaseAuth.getInstance().getCurrentUser() !=null) {
            firebaseFirestore.collection("My Cart").document(auth.getCurrentUser().getUid()).collection("Current User").get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {


                        int a = Integer.parseInt(documentSnapshot.get("Product price").toString());

                     int b=  Integer.parseInt(  documentSnapshot.get("Product Quantity").toString());

                            document_id=documentSnapshot.getId();
                        finalConfirmationModelList.add(new FinalConfirmationModel(documentSnapshot.get("Product Image").toString(), documentSnapshot.get("Product title").toString(),a, b, documentSnapshot.get("Product Desc").toString(), document_id,final_name,final_email,final_add,phone11,payment_type,final_city));
                    }
                    finalConfrimationAdapter.notifyDataSetChanged();



                } else {
                    Toast.makeText(FinalConfirmation.this, "Some thing Went Wrong", Toast.LENGTH_LONG).show();
                }

            });





        }

        else{
            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestEmail()
                    .build();
            GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(FinalConfirmation.this, gso);

            GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(FinalConfirmation.this);
            if (acct != null)
            {
                String uid= acct.getId();

                firebaseFirestore.collection("My Cart").document(uid).collection("Current User").get().addOnCompleteListener(task -> {
                    if(task.isSuccessful())
                    {
                        for(DocumentSnapshot documentSnapshot : task.getResult().getDocuments())
                        {

                            document_id=documentSnapshot.getId();
                            int a = Integer.parseInt(documentSnapshot.get("Product price").toString());
                            int b=  Integer.parseInt(  documentSnapshot.get("Product Quantity").toString());
                            finalConfirmationModelList.add(new FinalConfirmationModel(documentSnapshot.get("Product Image").toString(),documentSnapshot.get("Product title").toString(), a ,b, documentSnapshot.get("Product Desc").toString(),document_id,final_name,final_email,final_add,phone11,payment_type,final_city));
                        }
                        finalConfrimationAdapter.notifyDataSetChanged();

                    }





                    else
                    {
                        Toast.makeText(FinalConfirmation.this,"Some thing Went Wrong", Toast.LENGTH_LONG).show();
                    }
                });



            }




        }


        // Use of Intent to get Data

        Intent final_intent = getIntent();
        final_name  =  final_intent.getStringExtra("Final_user_name");
        final_email =  final_intent.getStringExtra("Final_user_email");
        final_city  =  final_intent.getStringExtra("Final_user_city");
        final_add   =  final_intent.getStringExtra("Final_user_add");
        phone11     =  final_intent.getIntExtra("Final_user_phone",0);
        payment_type=  final_intent.getStringExtra("Final_user_payment_type");
        total_price.setText( "RS" + final_intent.getIntExtra("Final_user_total",0));
        user_email1.setText(final_email);
        user_city1.setText(final_city);
        user_add1.setText(final_add);
        user_name1.setText(final_name);




    }


}