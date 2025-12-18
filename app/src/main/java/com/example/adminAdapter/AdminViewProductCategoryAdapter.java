package com.example.adminAdapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.model.StationaryProduct;
import com.example.stationaryshop.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class AdminViewProductCategoryAdapter extends FirebaseRecyclerAdapter<StationaryProduct,AdminViewProductCategoryAdapter.myViewHolder> {
    Dialog dialog;
    SharedPreferences sharedPreferences;
    public AdminViewProductCategoryAdapter(@NonNull FirebaseRecyclerOptions<StationaryProduct> options){
        super(options);
    }
    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, @SuppressLint("RecyclerView")int position,@NonNull StationaryProduct model) {
        holder.stationary_product_name.setText(model.getPname());
        holder.stationary_price.setText("MRP : ₹ " + model.getPprice());
        Glide.with(holder.itemView.getContext()).load(model.getPimage()).into(holder.stationary_product_image);

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {
                sharedPreferences = v.getContext().getSharedPreferences("doctro", Context.MODE_PRIVATE);
                String categoryname = sharedPreferences.getString("categoryname", "");
                dialog = new Dialog(v.getContext());
                dialog.setContentView(R.layout.edit_option_dialog);
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                dialog.setCancelable(true);
                dialog.show();
                TextView edit_product = dialog.findViewById(R.id.edit_product1);
                TextView delete_product = dialog.findViewById(R.id.delete_product);

                edit_product.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        Dialog editproductdialog;
                        editproductdialog = new Dialog(v.getContext());
                        editproductdialog.setContentView(R.layout.edit_product_dialog);
                        editproductdialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        editproductdialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                        editproductdialog.setCancelable(true);
                        editproductdialog.show();

                        EditText edit_produt_name = editproductdialog.findViewById(R.id.edit_produt_name);
                        EditText edit_product_image = editproductdialog.findViewById(R.id.edit_product_image);
                        EditText edit_product_price = editproductdialog.findViewById(R.id.edit_product_price);
                        EditText edit_product_description = editproductdialog.findViewById(R.id.edit_product_description);

                        edit_produt_name.setText(model.getPname());
                        edit_product_image.setText(model.getPimage());
                        edit_product_price.setText(model.getPprice());
                        edit_product_description.setText(model.getPdescription());

                        TextView cancel = editproductdialog.findViewById(R.id.cancel);
                        TextView update = editproductdialog.findViewById(R.id.update);
                        cancel.setOnClickListener(v1 -> editproductdialog.dismiss());

                        update.setOnClickListener(v1 -> {
                            Map<String, Object> map = new HashMap<>();
                            map.put("pname", edit_produt_name.getText().toString());
                            map.put("pimage", edit_product_image.getText().toString());
                            map.put("pprice", edit_product_price.getText().toString());
                            map.put("pdescription", edit_product_description.getText().toString());

                            FirebaseDatabase.getInstance().getReference().child(categoryname)
                                    .child(getRef(position).getKey())
                                    .updateChildren(map)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Toast.makeText(holder.stationary_product_name.getContext(), "Update Successfully", Toast.LENGTH_SHORT).show();
                                            editproductdialog.dismiss();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(holder.stationary_product_name.getContext(), "Error While Updating", Toast.LENGTH_SHORT).show();
                                            editproductdialog.dismiss();
                                        }
                                    });

                        });
                    }

                });
                delete_product.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(holder.stationary_product_name.getContext());
                        builder.setTitle("Are You Sure?");
                        builder.setMessage("Deleted Dta Can't be undo.?");
                        builder.setPositiveButton("delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                FirebaseDatabase.getInstance().getReference().child(categoryname).child(getRef(position).getKey()).removeValue();

                            }
                        });
                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(holder.stationary_product_name.getContext(), "Cancelled", Toast.LENGTH_SHORT).show();
                            }
                        });
                        builder.show();
                        dialog.dismiss();
                    }
                });
                return false;
            }
        });
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent,int viewType){
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.stationary_product_item,parent,false);
        return new myViewHolder(view);
    }

    public class myViewHolder extends RecyclerView.ViewHolder{
        TextView stationary_product_name,stationary_price;
        ImageView stationary_product_image;
        public myViewHolder(@NonNull View itemView){
            super(itemView);
            stationary_product_name=itemView.findViewById(R.id.stationary_product_name);
            stationary_price=itemView.findViewById(R.id.stationary_price);
            stationary_product_image=itemView.findViewById(R.id.stationary_product_image);
        }
    }
}
