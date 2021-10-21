package com.example.furniturevision;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class Wishlist_prdouct_adapter extends RecyclerView.Adapter<Wishlist_prdouct_adapter.ViewHolder> {

    private final List<Wishlist_product_model> wishlistProductModelList;
    private final FirebaseFirestore firebaseFirestore ;
    FirebaseAuth auth ;
    Context context;
    GoogleSignInClient mGoogleSignInClient;


    public Wishlist_prdouct_adapter(List<Wishlist_product_model> wishlistProductModelList, Context context) {
        this.wishlistProductModelList = wishlistProductModelList;
        this.context=context;
        firebaseFirestore = FirebaseFirestore.getInstance();
        auth= FirebaseAuth.getInstance();
    }

    @NonNull
    @Override
    public Wishlist_prdouct_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_wishlist_product_item,parent,false);
        return new ViewHolder(view);
    }



    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onBindViewHolder(@NonNull Wishlist_prdouct_adapter.ViewHolder viewHolder, @SuppressLint("RecyclerView") int position) {



        Glide.with(context).load(wishlistProductModelList.get(position).getWishlist_product_img()).into(viewHolder.wishlist_productimage);
        viewHolder.wishlist_producttitle.setText(wishlistProductModelList.get(position).getWishlist_product_title());
        viewHolder.wishlist_productprice.setText(wishlistProductModelList.get(position).getWishlist_product_price());
        viewHolder.wishlist_productdesc.setText(wishlistProductModelList.get(position).getWishlist_product_desc());

        viewHolder.delete.setOnClickListener(v -> {
            if(FirebaseAuth.getInstance().getCurrentUser()!=null)
            {
                firebaseFirestore.collection("My Wishlist").document(auth.getCurrentUser().getUid()).collection("Current User").document(wishlistProductModelList.get(position).getUUId()).delete().addOnCompleteListener(task -> {
                    wishlistProductModelList.remove(wishlistProductModelList.get(position));
                    notifyDataSetChanged();
                    Toast.makeText(context,"This Item is Deleted Succesfully" , Toast.LENGTH_LONG).show();
                });
            }
            else{

                GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestEmail()
                        .build();
                mGoogleSignInClient = GoogleSignIn.getClient(context, gso);

                GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(context);


                firebaseFirestore.collection("My Wishlist").document(acct.getId()).collection("Current User").document(wishlistProductModelList.get(position).getUUId()).delete().addOnCompleteListener(task -> {
                    wishlistProductModelList.remove(wishlistProductModelList.get(position));
                    notifyDataSetChanged();
                    Toast.makeText(context,"This Item is Deleted Succesfully" , Toast.LENGTH_LONG).show();
                });

            }
        });



    }

    @Override
    public int getItemCount() {
     return wishlistProductModelList.size();

    }

    public static class ViewHolder extends  RecyclerView.ViewHolder
    {
        private final ImageView wishlist_productimage ;
        private final TextView wishlist_producttitle;
        private final TextView wishlist_productprice;
        private final TextView wishlist_productdesc;
        ImageView delete;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            wishlist_productimage=itemView.findViewById(R.id.wishlist_product_img);
            wishlist_producttitle=itemView.findViewById(R.id.wishlist_prdouct_name);
            wishlist_productprice=itemView.findViewById(R.id.wishlist_product_price);
            wishlist_productdesc=itemView.findViewById(R.id.wishlist_product_desc);
            delete = itemView.findViewById(R.id.wishlist_delete);
        }






    }
}

