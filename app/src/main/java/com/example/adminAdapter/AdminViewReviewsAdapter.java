package com.example.adminAdapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.model.Review;
import com.example.stationaryshop.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class AdminViewReviewsAdapter extends FirebaseRecyclerAdapter<Review, AdminViewReviewsAdapter.myViewHolder> {
    public AdminViewReviewsAdapter(@NonNull FirebaseRecyclerOptions<Review> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, @SuppressLint("RecyclerView") int position, @NonNull Review model) {
        holder.review_product_name.setText("Product Name: " + model.getPname());
        holder.review_product_review.setText("Review: " + model.getPreview());
        holder.username.setText("User Name: " + model.getUsername());
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.review_product_name.getContext());
                builder.setTitle("Are You Sure ?");
                builder.setMessage("Deleted data can't be Undo.");

                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseDatabase.getInstance().getReference().child("allreviews").child(getRef(position).getKey()).removeValue();
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(holder.review_product_review.getContext(), "Cancelled", Toast.LENGTH_SHORT).show();
                    }
                });

                builder.show();
                return false;
            }
        });
    }
    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_item, parent, false);
        return new myViewHolder(view);
    }

    public class myViewHolder extends RecyclerView.ViewHolder {
        TextView review_product_name, review_product_review,username;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            review_product_name = itemView.findViewById(R.id.review_product_name);
            review_product_review = itemView.findViewById(R.id.review_product_review);
            username = itemView.findViewById(R.id.username);
        }
    }
}
