package com.example.furniturevision;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.List;

public class Order_adapter extends RecyclerView.Adapter<Order_adapter.ViewHolder> {



    private final List <Myorder_model> my_order_modelList;
    Context context;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth auth;

    public Order_adapter(List<Myorder_model> my_order_modelList, Context context) {
        this.my_order_modelList = my_order_modelList;
        this.context = context;
        firebaseFirestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
    }


    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.myorder_product_layout,parent,false);
        return new ViewHolder(view);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onBindViewHolder(@NonNull Order_adapter.ViewHolder beholder, @SuppressLint("RecyclerView") int position) {

        Glide.with(context).load(my_order_modelList.get(position).getMyorder_product_img()).into(beholder.my_order_productimage);
        beholder.my_order_producttitle.setText(my_order_modelList.get(position).getMyorder_product_title());
        beholder.my_order_productprice.setText(String.valueOf(my_order_modelList.get(position).getMyorder_product_price()));
        beholder.my_order_productdesc.setText(my_order_modelList.get(position).getMyorder_product_desc());
        beholder.my_order_qty.setText( String.valueOf(my_order_modelList.get(position).getMyorder_qty()));
        beholder.my_order_product_date.setText(my_order_modelList.get(position).getMyorder_date());

        beholder.my_order_cancel.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Cancel Order Confirmation");
            builder.setMessage("Are you Want to Cancel this Order");
            builder.setIcon(R.drawable.ic_baseline_warning_24);
            builder.setPositiveButton("YES", (dialog, which) -> {

                HashMap<String, Object> cancel = new HashMap<>();
                cancel.put("Status","Cancel");

                if(FirebaseAuth.getInstance().getCurrentUser() !=null)
                {
                    firebaseFirestore.collection("My Orders").document(auth.getCurrentUser().getUid()).
                            collection("Order Details").document(my_order_modelList.get(position).getUUID())
                            .update(cancel).addOnCompleteListener(task -> {
                                    if (task.isSuccessful())
                                    {
                                        builder.setTitle("Cancel Order Confirmation");
                                        builder.setMessage("Your order has been SuccessFully Cancel");
                                        builder.setPositiveButton("OK", (dialog12, which12) -> dialog12.dismiss());

                                        notifyDataSetChanged();
                                        notifyItemChanged(position);
                                    }
                                    else{
                                        builder.setTitle("Error");
                                        builder.setMessage("Some thing Went Wrong");
                                        builder.setPositiveButton("OK", (dialog1, which1) -> dialog1.dismiss());
                                    }
                            });
                }
                else{
                    GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                            .requestEmail()
                            .build();
                    GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(context, gso);

                    GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(context);


                    firebaseFirestore.collection("My Orders").document(acct.getId()).
                            collection("Order Details").document(my_order_modelList.get(position).getUUID())
                            .update(cancel).addOnCompleteListener(task -> {
                        if (task.isSuccessful())
                        {
                            builder.setTitle("Cancel Order Confirmation");
                            builder.setMessage("Your order has been SuccessFully Cancel");
                            builder.setPositiveButton("OK", (dialog14, which14) -> dialog14.dismiss());
                            notifyDataSetChanged();
                            notifyItemChanged(position);
                        }
                        else{
                            builder.setTitle("Error");
                            builder.setMessage("Some thing Went Wrong");
                            builder.setPositiveButton("OK", (dialog13, which13) -> dialog13.dismiss());
                        }
                    });
                }



            });
            builder.setNegativeButton("NO", (dialog, which) -> dialog.dismiss());

            builder.show();
        });


    }


    @Override
    public int getItemCount() {
        return my_order_modelList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        private final ImageView my_order_productimage;
        private final Button my_order_cancel;
        private final TextView my_order_product_date;
        private final TextView my_order_producttitle;
        private final TextView my_order_productprice;
        private final TextView my_order_productdesc;
        private final TextView my_order_qty;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            my_order_productimage=itemView.findViewById(R.id.myorder_img);
            my_order_producttitle=itemView.findViewById(R.id.myorder_title);
            my_order_product_date =itemView.findViewById(R.id.myorder_date);
            my_order_productprice=itemView.findViewById(R.id.myorder_price);
            my_order_productdesc=itemView.findViewById(R.id.myorder_desc);
            my_order_qty=itemView.findViewById(R.id.myorder_QTY);
            my_order_cancel=itemView.findViewById(R.id.cancel_order);



        }


    }
}
