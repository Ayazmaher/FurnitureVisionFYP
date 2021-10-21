package com.example.furniturevision;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;


import java.util.List;



public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ViewHolder> {

private  List <ProductsModel> productsModelList;
Context context ;

    public ProductsAdapter(List<ProductsModel> productsModelList,Context context) {
        this.productsModelList = productsModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.displayproduct,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, @SuppressLint("RecyclerView") int position) {


        Glide.with(viewHolder.productimage.getContext()).load(productsModelList.get(position).getProduct_img()).apply(new RequestOptions().placeholder(R.drawable.ic_google)).into(viewHolder.productimage);
        viewHolder.producttitle.setText(productsModelList.get(position).getProduct_title());
        viewHolder.productprice.setText(String.valueOf(productsModelList.get(position).getProduct_price()));
        viewHolder.productdesc.setText(productsModelList.get(position).getProduct_desc());



        viewHolder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context,Product_detail_activity.class);
            intent.putExtra("detail",productsModelList.get(position));
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
       return productsModelList.size();

    }

    public void filterlist(List<ProductsModel> list) {

        productsModelList = list;
        notifyDataSetChanged();
    }


    public static class ViewHolder extends  RecyclerView.ViewHolder {

        private final ImageView productimage ;
        private final TextView producttitle;
        private final TextView productprice;
        private final TextView productdesc;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            productimage=itemView.findViewById(R.id.product_img);
            producttitle=itemView.findViewById(R.id.prdouct_name);
            productprice=itemView.findViewById(R.id.product_price);
            productdesc=itemView.findViewById(R.id.product_desc);



        }



    }
}
