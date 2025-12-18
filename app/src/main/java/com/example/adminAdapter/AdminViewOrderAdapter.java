package com.example.adminAdapter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.model.UserOrder;
import com.example.stationaryshop.AdminOrderDetailsActivity;
import com.example.stationaryshop.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class AdminViewOrderAdapter extends FirebaseRecyclerAdapter<UserOrder,AdminViewOrderAdapter.myViewHolder> {
    public AdminViewOrderAdapter(@NonNull FirebaseRecyclerOptions<UserOrder>options){
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, @SuppressLint("RecyclerView")int position,@NonNull UserOrder model){
        Glide.with(holder.itemView.getContext()).load(model.getProductImage()).into(holder.stationaryProductImage);
        holder.stationaryProductName.setText(model.getProductName());
        holder.stationaryProductPrice.setText("MRP : ₹"+model.getProductPrice()+" x "+model.getProductQuantity() + " = "+model.getProductTotalPrice());
        holder.itemView.setOnClickListener(v ->{
            Intent intent;
            intent=new Intent(holder.itemView.getContext(), AdminOrderDetailsActivity.class);
            intent.putExtra("stationaryProductImage",model.getProductImage());
            intent.putExtra("stationaryProductName",model.getProductName());
            intent.putExtra("stationaryProductPrice",model.getProductPrice());
            intent.putExtra("user_first_name",model.getFirstname());
            intent.putExtra("user_last_name",model.getLastname());
            intent.putExtra("user_mobile_number",model.getMobile());
            intent.putExtra("user_zip_code",model.getZipCode());
            intent.putExtra("user_address",model.getAddress());
            intent.putExtra("user_landmark",model.getLandmark());
            intent.putExtra("user_product_quantity",model.getProductQuantity());
            intent.putExtra("user_product_price",model.getProductPrice());
            intent.putExtra("user_product_price_total",model.getProductTotalPrice());
            holder.itemView.getContext().startActivity(intent);
        } );
    }
    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent,int viewType){
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.user_order_item,parent,false);
        return new myViewHolder(view);

    }
    public class myViewHolder extends RecyclerView.ViewHolder{
        ImageView stationaryProductImage;
        TextView stationaryProductName;
        TextView stationaryProductPrice;

        public myViewHolder(@NonNull View itemView){
            super(itemView);
            stationaryProductImage=itemView.findViewById(R.id.stationaryProductImage);
            stationaryProductName=itemView.findViewById(R.id.stationaryProductName);
            stationaryProductPrice=itemView.findViewById(R.id.stationaryProductprice);
        }
    }
}
