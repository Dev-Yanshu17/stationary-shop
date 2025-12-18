package com.example.adapter;

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
import com.example.model.StationaryProduct;
import com.example.stationaryshop.AllProductPage;
import com.example.stationaryshop.R;
import com.example.stationaryshop.StationaryProductDetails;

import java.util.ArrayList;

public class StationaryProductAdapter extends RecyclerView.Adapter<StationaryProductAdapter.StationaryViewHolder> {
    Context context;
    ArrayList<StationaryProduct> stationaryProductArrayList;
    public StationaryProductAdapter(Context context,ArrayList<StationaryProduct>stationaryProductArrayList){
        this.context=context;
        this.stationaryProductArrayList=stationaryProductArrayList;
    }
    public void setFilter(ArrayList<StationaryProduct>newList){
        this.stationaryProductArrayList=newList;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public StationaryViewHolder onCreateViewHolder(@NonNull ViewGroup parent,int viewType){
        return new StationaryViewHolder(LayoutInflater.from(context).inflate(R.layout.stationary_product_item,parent,false));

    }
    @Override
    public void onBindViewHolder(@NonNull StationaryViewHolder holder, @SuppressLint("Recyclerview") int position){
        holder.stationary_product_name.setText(stationaryProductArrayList.get(position).getPname());
        holder.stationary_product_name.setSelected(true);
        holder.stationary_price.setText("MRP : ₹ "+ stationaryProductArrayList.get(position).getPprice());
        Glide.with(context).load(stationaryProductArrayList.get(position).getPimage()).into(holder.stationary_product_image);

        holder.itemView.setOnClickListener(v -> {
            Intent intent;
            intent=new Intent(context, StationaryProductDetails.class);
            intent.putExtra("model",stationaryProductArrayList.get(position));
            context.startActivity(intent);
        });
        holder.statonary_product_share.setOnClickListener(v -> {
           Intent intentt=new Intent(Intent.ACTION_SEND);
           intentt.setType("text/plain");
           intentt.putExtra(Intent.EXTRA_SUBJECT,"Subject here");
           intentt.putExtra(Intent.EXTRA_TEXT,stationaryProductArrayList.get(position).getPname());
           intentt.putExtra(Intent.EXTRA_TEXT,
                   "Product Name : "+ stationaryProductArrayList.get(position).getPname()+
                   "MRP : ₹ "+stationaryProductArrayList.get(position).getPprice()+
                   "\n\nApp Download Link :\n"+
                   "https://play.google.com/store/apps/details?id="+context.getPackageName());
           context.startActivity(Intent.createChooser(intentt,"Share via"));

        });
    }

    @Override
    public int getItemCount(){return stationaryProductArrayList.size();}

    public class StationaryViewHolder extends RecyclerView.ViewHolder{
        TextView stationary_product_name,stationary_price;
        ImageView stationary_product_image;
        ImageView statonary_product_share;
        public StationaryViewHolder(@NonNull View itemView){
            super(itemView);
            stationary_product_name=itemView.findViewById(R.id.stationary_product_name);
            stationary_price=itemView.findViewById(R.id.stationary_price);
            stationary_product_image=itemView.findViewById(R.id.stationary_product_image);
            statonary_product_share=itemView.findViewById(R.id.stationary_product_share);
        }
    }
}
