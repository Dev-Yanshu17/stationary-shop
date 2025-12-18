package com.example.adminAdapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.model.OrderDetails;
import com.example.stationaryshop.R;
import com.example.stationaryshop.admin_bill_page;

import java.util.List;

public class AdminOrderHistoryAdapter extends RecyclerView.Adapter<AdminOrderHistoryAdapter.OrderViewHolder> {
    private Context context;
    private List<OrderDetails> orderList;

    public AdminOrderHistoryAdapter(Context context, List<OrderDetails> orderList) {
        this.context = context;
        this.orderList = orderList;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.admin_order_history, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        OrderDetails order = orderList.get(position);
        holder.bind(order);
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public class OrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView orderIdTextView;
        private TextView userNameTextView;
        private TextView userIdTextView;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            orderIdTextView = itemView.findViewById(R.id.tvorderid);
            userNameTextView = itemView.findViewById(R.id.tvusername);
            userIdTextView = itemView.findViewById(R.id.tvuserid);
            itemView.setOnClickListener(this);
        }

        public void bind(OrderDetails order) {
            orderIdTextView.setText("Order ID: " + order.getOrderId());
            userNameTextView.setText("User: " + order.getUserName()); // Display user name
            userIdTextView.setText("User ID: " + order.getUserId()); // Display user ID
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                OrderDetails order = orderList.get(position);
                String orderId = order.getOrderId();
                Intent intent = new Intent(context, admin_bill_page.class);
                intent.putExtra("orderId", orderId);
                context.startActivity(intent);
            }
        }
    }
}
