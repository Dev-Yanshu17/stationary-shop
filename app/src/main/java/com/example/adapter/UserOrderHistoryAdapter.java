package com.example.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.model.UserOrderHistory;
import com.example.stationaryshop.BillPageActivity;
import com.example.stationaryshop.R; // Update with your stationary shop's R class


import java.util.List;

public class UserOrderHistoryAdapter extends RecyclerView.Adapter<UserOrderHistoryAdapter.OrderViewHolder>{

    private Context context;
    private List<UserOrderHistory> orderList;

    public UserOrderHistoryAdapter(Context context, List<UserOrderHistory> orderList) {
        this.context = context;
        this.orderList = orderList;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.user_order_history, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        UserOrderHistory userOrderHistory = orderList.get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the order ID and pass it to the BillActivity
                String orderId = userOrderHistory.getOrderId();
                Intent intent = new Intent(context, BillPageActivity.class);
                intent.putExtra("orderId", orderId);
                context.startActivity(intent);
            }
        });
        holder.bind(userOrderHistory);
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }
    public class OrderViewHolder extends RecyclerView.ViewHolder {

        private TextView orderIdTextView;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            orderIdTextView = itemView.findViewById(R.id.tvorderid);
        }

        public void bind(UserOrderHistory order) {
            orderIdTextView.setText(order.getOrderId());
        }
    }
}
