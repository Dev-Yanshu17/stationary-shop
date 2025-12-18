package com.example.adminAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.model.RatingModel;
import com.example.stationaryshop.R;

import java.util.List;

public class RatingAdapter extends RecyclerView.Adapter<RatingAdapter.ViewHolder> {
    private List<RatingModel> ratingList;

    public RatingAdapter(List<RatingModel> ratingList) {
        this.ratingList = ratingList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rate, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RatingModel rating = ratingList.get(position);
        holder.usernameTextView.setText(rating.getUsername());
        // Convert rating String to float before setting it to RatingBar
        holder.ratingBar.setRating(Float.parseFloat(rating.getRating()));
    }

    @Override
    public int getItemCount() {
        return ratingList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView usernameTextView;
        RatingBar ratingBar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            usernameTextView = itemView.findViewById(R.id.username);
            ratingBar = itemView.findViewById(R.id.rating_bar);
        }
    }
}
