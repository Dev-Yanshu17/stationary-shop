package com.example.adminAdapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.model.LiveUser;
import com.example.stationaryshop.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class AdminViewUserAdapter extends FirebaseRecyclerAdapter<LiveUser,AdminViewUserAdapter.myViewHolder> {

    public AdminViewUserAdapter(@NonNull FirebaseRecyclerOptions<LiveUser> options){
        super(options);
    }
    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, @SuppressLint("RecyclrView")int position,@NonNull LiveUser model){
        holder.username.setText(model.getUsername());
        holder.useremail.setText(model.getEmail());
        holder.count.setText(String.valueOf(position + 1));
    }
    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent,int viewtype){
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.all_user_item,parent,false);
        return new myViewHolder(view);
    }
    public class myViewHolder extends RecyclerView.ViewHolder{
        TextView useremail,username,count;
        public myViewHolder(@NonNull View itemView){
            super(itemView);
            useremail = itemView.findViewById(R.id.useremail);
            username = itemView.findViewById(R.id.username);
            count = itemView.findViewById(R.id.count);

        }
    }


}
