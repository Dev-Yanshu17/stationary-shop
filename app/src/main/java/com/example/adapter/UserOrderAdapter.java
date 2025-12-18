package com.example.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.model.UserOrder;
import com.example.stationaryshop.R;

import java.util.ArrayList;

public class UserOrderAdapter extends RecyclerView.Adapter<UserOrderAdapter.ViewHolder> {
    Context context;
    ArrayList<UserOrder> userOrders;
    public UserOrderAdapter(Context context, ArrayList<UserOrder>userOrders){
        this.context=context;
        this.userOrders=userOrders;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,int viewType){
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.user_order_item,parent,false));

    }
    @Override
    public void  onBindViewHolder(@NonNull ViewHolder holder,int position){
        UserOrder userOrder=userOrders.get(position);
        Glide.with(context).load(userOrders.get(position).getProductImage()).into(holder.stationaryProductImage);
        holder.stationaryProductName.setText(userOrders.get(position).getProductName());
        holder.stationaryProductPrice.setText("MRP : ₹"+ userOrder.getProductPrice()+" x"+userOrder.getProductQuantity()+"="+userOrder.getProductTotalPrice());

    }
    @Override
    public int getItemCount(){return userOrders.size();}

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView stationaryProductImage;
        TextView stationaryProductName;
        TextView stationaryProductPrice;
        public ViewHolder (@NonNull View itemView){
            super(itemView);
            stationaryProductImage=itemView.findViewById(R.id.stationaryProductImage);
            stationaryProductName=itemView.findViewById(R.id.stationaryProductName);
            stationaryProductPrice=itemView.findViewById(R.id.stationaryProductprice);
        }
    }
}
