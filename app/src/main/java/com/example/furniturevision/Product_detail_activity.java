package com.example.furniturevision;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class Product_detail_activity extends AppCompatActivity {

    private FloatingActionButton activity_wishlist;

    GoogleSignInClient mGoogleSignInClient;
    FirebaseAuth auth;
   FirebaseFirestore firestore;


    ProductsModel productsModel =null;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail_activity);

        ImageView activty_productimage = findViewById(R.id.activity_product_image);

        TextView activity_productname = findViewById(R.id.activity_product_name);

        TextView activity_productprice = findViewById(R.id.activity_product_price);

        TextView activity_productdesc = findViewById(R.id.activity_product_desc);

        activity_wishlist = findViewById(R.id.activity_wishlist);

        Button addtocart = findViewById(R.id.btn_addto_cart);

        Button viewCamera = findViewById(R.id.btn_ARcamera);

        activty_productimage.setImageResource(R.drawable.sofa);

        auth= FirebaseAuth.getInstance();
        firestore=FirebaseFirestore.getInstance();

        final Object object = getIntent().getSerializableExtra("detail");

        if (object instanceof   ProductsModel )
        {
            productsModel = (ProductsModel) object;
        }

        if(productsModel !=null)
        {
            Glide.with(Product_detail_activity.this).load(productsModel.getProduct_img()).into(activty_productimage);
            activity_productname.setText(productsModel.getProduct_title());
            activity_productprice.setText(" RS " + "" + productsModel.getProduct_price() + "/-");
            activity_productdesc.setText(productsModel.getProduct_desc());


        }




        activity_wishlist.setOnClickListener(v -> {


            Map<String,Object> WishlistMap = new HashMap<>();
            WishlistMap.put("Product Image",productsModel.getProduct_img());
            WishlistMap.put("Product title",productsModel.getProduct_title());
            WishlistMap.put("Product price",productsModel.getProduct_price());
            WishlistMap.put("Product Desc",productsModel.getProduct_desc());

            if(FirebaseAuth.getInstance().getCurrentUser() !=null)
            {
                firestore.collection("My Wishlist").document(auth.getCurrentUser().getUid()).collection("Current User").add(WishlistMap).addOnCompleteListener(task -> {

                    if (task.isSuccessful())
                    {
                        Toast.makeText(Product_detail_activity.this,"SuccessFully Product Added in the Wishlist", Toast.LENGTH_LONG).show();
                        activity_wishlist.setBackgroundColor(Color.RED);
                    }
                    else
                    {
                        Toast.makeText(Product_detail_activity.this,"Some thing Went Wrong", Toast.LENGTH_LONG).show();
                    }

                });
            }

            else
                {
                    GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                            .requestEmail()
                            .build();
                    mGoogleSignInClient = GoogleSignIn.getClient(Product_detail_activity.this, gso);

                    GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(Product_detail_activity.this);

                    if (acct != null)
                    {
                        String uid= acct.getId();
                        firestore.collection("My Wishlist").document(uid).collection("Current User").add(WishlistMap).addOnCompleteListener(task -> {

                            if (task.isSuccessful())
                            {
                                Toast.makeText(Product_detail_activity.this,"SuccessFully Product Added in the Wishlist", Toast.LENGTH_LONG).show();
                                activity_wishlist.setBackgroundColor(Color.RED);
                            }
                            else
                            {
                                Toast.makeText(Product_detail_activity.this,"Some thing Went Wrong", Toast.LENGTH_LONG).show();
                            }

                        });


                    }

            }


        });


        addtocart.setOnClickListener(v -> {

            int Quantity =1 ;
            Map<String,Object> cartMap = new HashMap<>();

            cartMap.put("Product Image",productsModel.getProduct_img());
            cartMap.put("Product title",productsModel.getProduct_title());
            cartMap.put("Product price",productsModel.getProduct_price());
            cartMap.put("Product Desc",productsModel.getProduct_desc());
            cartMap.put("Product Quantity",Quantity);


            if(FirebaseAuth.getInstance().getCurrentUser() !=null)
            {
                firestore.collection("My Cart").document(auth.getCurrentUser().getUid()).collection("Current User").add(cartMap).addOnCompleteListener(task -> {

                    if (task.isSuccessful())
                    {
                        Toast.makeText(Product_detail_activity.this,"SuccessFully Product Added in the Cart", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(Product_detail_activity.this,navdrawer.class));
                    }
                    else
                    {
                        Toast.makeText(Product_detail_activity.this,"Some thing Went Wrong", Toast.LENGTH_LONG).show();
                    }

                });

            }

            else
                {
                    GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                            .requestEmail()
                            .build();
                    mGoogleSignInClient = GoogleSignIn.getClient(Product_detail_activity.this, gso);

                    GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(Product_detail_activity.this);

                    if (acct != null)
                    {
                        String uid= acct.getId();
                        firestore.collection("My Cart").document(uid).collection("Current User").add(cartMap).addOnCompleteListener(task -> {

                            if (task.isSuccessful())
                            {
                                Toast.makeText(Product_detail_activity.this,"SuccessFully Product Added in the Cart", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(Product_detail_activity.this,navdrawer.class));
                            }
                            else
                            {
                                Toast.makeText(Product_detail_activity.this,"Some thing Went Wrong", Toast.LENGTH_LONG).show();
                            }

                        });
                    }


            }










        });

        viewCamera.setOnClickListener(v -> {
            try{
                startActivity(new Intent(MediaStore.ACTION_IMAGE_CAPTURE));
            }
            catch (Exception e)
            {
                Toast.makeText(Product_detail_activity.this,"Some thing went Wrong"+e.getMessage(),Toast.LENGTH_LONG).show();
            }
        });







    }
}