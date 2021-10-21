package com.example.furniturevision;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cart_item_Adapter extends RecyclerView.Adapter<Cart_item_Adapter.cartItemBeholder> {

    private final List <Cart_item_model> cart_item_modelList;
    GoogleSignInClient mGoogleSignInClient;
    FirebaseAuth auth ;
    FirebaseFirestore firebaseFirestore;
    Context context;
    int total = 0;
    int i=1;
    int price_product;
    public Cart_item_Adapter(List<Cart_item_model> cart_item_modelList, Context context) {
        this.cart_item_modelList = cart_item_modelList;
        this.context=context;
        auth= FirebaseAuth.getInstance();
        firebaseFirestore  = FirebaseFirestore.getInstance();
    }

    @NonNull
    @Override
    public cartItemBeholder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mycart_layout,parent,false);
        return new cartItemBeholder(view);

    }




    @SuppressLint({"SetTextI18n", "NotifyDataSetChanged"})
    @Override
    public void onBindViewHolder(@NonNull cartItemBeholder holder, @SuppressLint("RecyclerView") int position) {


        Glide.with(holder.cart_img.getContext()).load(cart_item_modelList.get(position).getCart_Image()).into(holder.cart_img);
        holder.cart_title.setText(cart_item_modelList.get(position).getCart_product_title());
        holder.cart_desc.setText(cart_item_modelList.get(position).getCart_product_desc());
        holder.cart_qty.setText(String.valueOf(cart_item_modelList.get(position).getCart_product_qty()));
        holder.cart_price.setText(String.valueOf(cart_item_modelList.get(position).getCart_product_price()));




        // Calculate Total Price
        price_product = cart_item_modelList.get(position).getCart_product_price() * cart_item_modelList.get(position).getCart_product_qty();
        total = total+ price_product;
        Intent intent = new Intent("Total Amount of Product");
        intent.putExtra("total amount",total);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);





        holder.delete.setOnClickListener(v -> {
            if(FirebaseAuth.getInstance().getCurrentUser() !=null)
            {
                firebaseFirestore.collection("My Cart").document(auth.getCurrentUser().getUid()).collection("Current User").document(cart_item_modelList.get(position).getUUID()).delete().addOnCompleteListener(task -> {
                    if(task.isSuccessful())
                    {
                        cart_item_modelList.remove(cart_item_modelList.get(position));
                        notifyDataSetChanged();
                        Toast.makeText(context,"This Item is Deleted Succesfully" , Toast.LENGTH_LONG).show();
                        total = total- price_product;



                    }
                    else{
                        Toast.makeText(context,"Some thing went Wrong"+ task.getException() , Toast.LENGTH_LONG).show();
                    }
                });
            }
            else{
                GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestEmail()
                        .build();
                mGoogleSignInClient = GoogleSignIn.getClient(context, gso);

                GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(context);

                if (acct != null)
                {
                    String uid= acct.getId();

                    firebaseFirestore.collection("My Cart").document(uid).collection("Current User")
                            .document(cart_item_modelList.get(position).getUUID()).delete().addOnCompleteListener(task -> {
                                if(task.isSuccessful())
                                {
                                    cart_item_modelList.remove(cart_item_modelList.get(position));
                                    notifyDataSetChanged();

                                    total = total- price_product;
                                    Toast.makeText(context,"This Item is Deleted Succesfully" , Toast.LENGTH_LONG).show();

                                }
                                else{
                                    Toast.makeText(context,"Some thing went Wrong"+ task.getException() , Toast.LENGTH_LONG).show();
                                }
                            });



                }
            }
        });

        holder.increment.setOnClickListener(v -> {


            i++;
            Map<String,Object> cartMap_increment = new HashMap<>();
            cartMap_increment.put("Product Quantity",i);

            if(auth.getCurrentUser()!=null)
            {

                firebaseFirestore.collection("My Cart").document(auth.getCurrentUser().getUid()).collection("Current User").document(cart_item_modelList.get(position).getUUID()).update(cartMap_increment).addOnCompleteListener(task -> {

                    if(task.isSuccessful())
                    {
                        holder.cart_qty.setText(String.valueOf(i ));
                        total = total+ price_product;

                        notifyItemChanged(position);
                    }
                });
            }
            else{
                GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestEmail()
                        .build();
                mGoogleSignInClient = GoogleSignIn.getClient(context, gso);

                GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(context);

                firebaseFirestore.collection("My Cart").document(acct.getId()).collection("Current User").document(cart_item_modelList.get(position).getUUID()).update(cartMap_increment).addOnCompleteListener(task -> {

                    if(task.isSuccessful())
                    {
                        holder.cart_qty.setText(String.valueOf(i ));

                        total = total+ price_product;
                        notifyItemChanged(position);
                    }
                });
            }
        });

        holder.decrement.setOnClickListener(v -> {


            i--;
            Map<String,Object> cartMap_increment = new HashMap<>();
            cartMap_increment.put("Product Quantity",i);

            if(auth.getCurrentUser()!=null)
            {

                firebaseFirestore.collection("My Cart").document(auth.getCurrentUser().getUid()).collection("Current User").document(cart_item_modelList.get(position).getUUID()).update(cartMap_increment).addOnCompleteListener(task -> {

                    if(task.isSuccessful())
                    {
                        holder.cart_qty.setText(String.valueOf(i) );
                        total = total- price_product;
                        notifyItemChanged(position);
                    }
                });
            }
            else{
                GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestEmail()
                        .build();
                mGoogleSignInClient = GoogleSignIn.getClient(context, gso);

                GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(context);

                firebaseFirestore.collection("My Cart").document(acct.getId()).collection("Current User").document(cart_item_modelList.get(position).getUUID()).update(cartMap_increment).addOnCompleteListener(task -> {

                    if(task.isSuccessful())
                    {
                        holder.cart_qty.setText(String.valueOf( i));
                        total = total- price_product;
                        notifyItemChanged(position);
                    }
                });
            }
        });




    }




    @Override

    public int getItemCount() {
        return cart_item_modelList.size();
    }

    static class cartItemBeholder extends  RecyclerView.ViewHolder
    {
        private final ImageView cart_img;
        private final TextView  cart_title;
        private final TextView  cart_price;
        private final TextView  cart_qty;
        private final TextView  cart_desc;
        ImageView increment,decrement,delete;

        public cartItemBeholder(@NonNull @NotNull View itemView) {
            super(itemView);

            cart_img =itemView.findViewById(R.id.cart_img);
            cart_title =itemView.findViewById(R.id.cart_title);
            cart_price =itemView.findViewById(R.id.cart_price);
            cart_desc =itemView.findViewById(R.id.cart_desc);
            cart_qty =itemView.findViewById(R.id.cart_qty);
            increment = itemView.findViewById(R.id.cart_add);
            decrement = itemView.findViewById(R.id.cart_minus);
            delete = itemView.findViewById(R.id.cart_delete);

        }






    }


}


