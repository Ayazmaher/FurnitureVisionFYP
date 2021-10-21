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
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class FinalConfirmationAdapter extends RecyclerView.Adapter<FinalConfirmationAdapter.FooViewHolder>{
    @NonNull

    FirebaseFirestore firebaseFirestore;
    FirebaseAuth auth;
    private final List<FinalConfirmationModel> finalConfirmationModelList;
    Context context;

    public FinalConfirmationAdapter(@NonNull List<FinalConfirmationModel> finalConfirmationModelList, Context context) {
        this.finalConfirmationModelList = finalConfirmationModelList;
        this.context = context;
        firebaseFirestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
    }

    @NonNull
    public FooViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.myfinal_layout,parent,false);
        return new FinalConfirmationAdapter.FooViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FooViewHolder holder, int position) {
        Glide.with(holder.final_img.getContext()).load(finalConfirmationModelList.get(position).getCart_Image()).into(holder.final_img);
        holder.final_title.setText(finalConfirmationModelList.get(position).getCart_product_title());
        holder.final_desc.setText(finalConfirmationModelList.get(position).getCart_product_desc());
        holder.final_qty.setText(String.valueOf(finalConfirmationModelList.get(position).getCart_product_qty()));
        holder.final_price.setText(String.valueOf(finalConfirmationModelList.get(position).getCart_product_price()));

//         order Place Functionality


        FinalConfirmation.order_now.setOnClickListener(v1 ->
        {


            for(FinalConfirmationModel model_cart:finalConfirmationModelList)
            {
                Calendar calendar = Calendar.getInstance();

                @SuppressLint("SimpleDateFormat") SimpleDateFormat reformat = new SimpleDateFormat("HH:mm:ss");

                String strDate = reformat.format(calendar.getTime());

                HashMap<String, Object> orders = new HashMap<>();

                orders.put("Product title", model_cart.getCart_product_title());

                orders.put("Product Image", model_cart.getCart_Image());

                orders.put("Product desc", model_cart.getCart_product_desc());

                orders.put("Product Price", model_cart.getCart_product_price());

                orders.put("Product Quantity", model_cart.getCart_product_qty());

                orders.put("Name",model_cart.getName());

                orders.put("Email",model_cart.getEmail());

                orders.put("Phone",model_cart.getNumber());

                orders.put("Status","Ongoing");

                orders.put("Address",model_cart.getAddress());

                orders.put("City",model_cart.getCity());

                orders.put("Current Time",strDate);

                orders.put("Payment Type",model_cart.getPayment_type());

                if(FirebaseAuth.getInstance().getCurrentUser() !=null)
                {

                 if(model_cart.getPayment_type().equals("JazzCash"))
                 {

                 }
                 else{
                     firebaseFirestore.collection("My Orders").document(auth.getCurrentUser().getUid()).
                             collection("Order Details").add(orders).addOnCompleteListener(task -> {
                         if(task.isSuccessful())
                         {
                             firebaseFirestore.collection("My Cart").document(auth.getCurrentUser().getUid()).collection("Current User").document(finalConfirmationModelList.get(position).getUUId()).delete().
                                     addOnCompleteListener(task1 -> {

                                         if(task1.isSuccessful())
                                         {
                                             context.startActivity(new Intent(context,navdrawer.class));

                                         }

                                         else{
                                             Toast.makeText(context, "Error"+ task1.getException().toString(), Toast.LENGTH_SHORT).show();

                                         }

                                     });

                         }
                     });
                 }






                }

                else{
                    GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                            .requestEmail()
                            .build();
                    GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(context, gso);

                    GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(context);

                    if(model_cart.getPayment_type().equals("JazzCash"))
                    {

                    }

                    else{
                        firebaseFirestore.collection("My Orders").document(acct.getId()).
                                collection("Order Details").add(orders).addOnCompleteListener(task -> {
                            if(task.isSuccessful())
                            {
                                firebaseFirestore.collection("My Cart").document(acct.getId()).collection("Current User").document(finalConfirmationModelList.get(position).getUUId()).delete().
                                        addOnCompleteListener(task12 -> {

                                            if(task12.isSuccessful())
                                            {
                                                context.startActivity(new Intent(context,navdrawer.class));

                                            }

                                            else{
                                                Toast.makeText(context, "Error"+ task12.getException().toString(), Toast.LENGTH_SHORT).show();

                                            }

                                        });
                            }
                        });
                    }






                }




            }
        });


    }



    @Override
    public int getItemCount() {
        return finalConfirmationModelList.size();
    }

    static class FooViewHolder extends RecyclerView.ViewHolder {

        private final ImageView final_img;
        private final TextView final_title;
        private final TextView  final_price;
        private final TextView  final_qty;
        private final TextView  final_desc;


        public FooViewHolder(@NonNull View itemView) {
            super(itemView);

            final_img =itemView.findViewById(R.id.final_cart_img);
            final_title =itemView.findViewById(R.id.final_cart_title);
            final_price =itemView.findViewById(R.id.final_cart_price);
            final_desc =itemView.findViewById(R.id.final_cart_desc);
            final_qty =itemView.findViewById(R.id.final_cart_qty);

        }


    }

}
