package com.example.stationaryshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.stationaryshop.databinding.ActivityAddProductBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class AddProduct extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    TextView painter;
    TextView student;
    TextView business;
    TextView architecture;
    TextView designer;
    TextView gift;
    TextView other;
    ProgressDialog selectCategoryDialog;
    ActivityAddProductBinding binding;


    //  EditText  pname;
    // EditText pimage;
    // EditText pprice;
    //Spinner pcategory;
    //   EditText pdescription;
    //  TextView add;
    // DatabaseReference stationarydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        binding = ActivityAddProductBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.appBar.backBtn.setOnClickListener(v -> getOnBackPressedDispatcher());
        sharedPreferences = this.getSharedPreferences("doctro", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        binding.appBar.title.setText("Add Product");
        binding.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertData();
            }
        });
        binding.cancel.setOnClickListener(view -> onBackPressed());
        binding.addProductCategory.setOnClickListener(view -> {
            selectCategoryDialog=new ProgressDialog(AddProduct.this);
            selectCategoryDialog.show();
            selectCategoryDialog.setCancelable(false);
            selectCategoryDialog.setContentView(R.layout.dialog_select_category);
            selectCategoryDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

            painter=selectCategoryDialog.findViewById(R.id.painter);
            student=selectCategoryDialog.findViewById(R.id.student);
            business=selectCategoryDialog.findViewById(R.id.business);
            architecture=selectCategoryDialog.findViewById(R.id.architecture);
            designer=selectCategoryDialog.findViewById(R.id.designer);
            gift=selectCategoryDialog.findViewById(R.id.gift);
            other=selectCategoryDialog.findViewById(R.id.other);

            painter.setOnClickListener(view1 -> {
                binding.addProductCategory.setText("painter");
                selectCategoryDialog.dismiss();
            });

            student.setOnClickListener(view1 -> {
                binding.addProductCategory.setText("student");
                selectCategoryDialog.dismiss();
            });

            business.setOnClickListener(view1 -> {
                binding.addProductCategory.setText("business");
                selectCategoryDialog.dismiss();
            });

            architecture.setOnClickListener(view1 -> {
                binding.addProductCategory.setText("architecture");
                selectCategoryDialog.dismiss();
            });

            designer.setOnClickListener(view1 -> {
                binding.addProductCategory.setText("designer");
                selectCategoryDialog.dismiss();
            });
            gift.setOnClickListener(view1 -> {
                binding.addProductCategory.setText("gift");
                selectCategoryDialog.dismiss();
            });
            other.setOnClickListener(view1 -> {
                binding.addProductCategory.setText("other");
                selectCategoryDialog.dismiss();
            });


        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }


    private void insertData() {
        Map<String, Object>map=new HashMap<>();
        if (binding.addProductName.getText().toString().equals("")){
            Toast.makeText(this,"Please Enter Name.",Toast.LENGTH_SHORT).show();
        }else if (binding.addProductImage.getText().toString().equals("")){
            Toast.makeText(this,"Please Enter Image.",Toast.LENGTH_SHORT).show();
        }else if (binding.addProductPrice.getText().toString().equals("")){
            Toast.makeText(this,"Please Enter Price.",Toast.LENGTH_SHORT).show();
        }else if (binding.addProductDescription.getText().toString().equals("")){
            Toast.makeText(this,"Please Enter Description.",Toast.LENGTH_SHORT).show();
        }else {
            map.put("pname",binding.addProductName.getText().toString());
            map.put("pimage",binding.addProductImage.getText().toString());
            map.put("pprice",binding.addProductPrice.getText().toString());
            map.put("pdescription",binding.addProductDescription.getText().toString());
            editor.putString("categoryname",binding.addProductCategory.getText().toString());
            editor.commit();
            editor.apply();
            FirebaseDatabase.getInstance().getReference().child("category").child(binding.addProductCategory.getText().toString()).push().setValue(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    FirebaseDatabase.getInstance().getReference().child("allproduct").push().setValue(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(AddProduct.this,"Data Insert Successfully",Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(AddProduct.this,"Error while insertation",Toast.LENGTH_SHORT).show();
                        }
                    });
                    Toast.makeText(AddProduct.this,"Data Insert Successfully.",Toast.LENGTH_SHORT).show();
                    finish();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(AddProduct.this,"Error while insertation",Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

}