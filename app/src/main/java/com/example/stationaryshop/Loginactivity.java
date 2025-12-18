package com.example.stationaryshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.model.UserProfile;
import com.example.stationaryshop.databinding.ActivityLoginactivityBinding;
import com.example.utils.JemsProvider;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Loginactivity extends AppCompatActivity {
    ProgressDialog progressDialog;
    ActivityLoginactivityBinding  binding;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    FirebaseAuth mauth;
    /*EditText etemail;
    EditText etpassword;
    Dialog loding;
    private FirebaseAuth mauth;*/



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


       // setContentView(R.layout.activity_loginactivity);
        binding=ActivityLoginactivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        progressDialog=new ProgressDialog(Loginactivity.this);
        FirebaseApp.initializeApp(this);
        mauth=FirebaseAuth.getInstance();
        binding.registertxt.setOnClickListener(view -> {
            startActivity(new Intent(Loginactivity.this, RegistrationActivity.class));
        });
        sharedPreferences=this.getSharedPreferences("doctro",MODE_PRIVATE);
        editor=sharedPreferences.edit();

        binding.signin.setOnClickListener(view -> {
            if (binding.email.getText().toString().equals("admin123@gmail.com")&&binding.pass.getText().toString().equals("admin123@")){
                editor.putString("email",binding.email.getText().toString());
                editor.commit();
                editor.apply();
                startActivity(new Intent(Loginactivity.this, AdminHomePage.class));
                finish();
            } else if (isVerify()) {
                progressDialog.show();
                progressDialog.setCancelable(false);
                progressDialog.setContentView(R.layout.activity_loadingview);
                progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                mauth.signInWithEmailAndPassword(binding.email.getText().toString().trim(),binding.pass.getText().toString().trim()).addOnCompleteListener(task -> {
                    progressDialog.dismiss();
                    if (task.isSuccessful()){
                        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("users").child(mauth.getCurrentUser().getUid());
                        userRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                                UserProfile userProfile=datasnapshot.getValue(UserProfile.class);
                                if (userProfile != null){
                                    editor.putString("username",userProfile.getUsername());
                                    editor.putBoolean("is_login",true);
                                    editor.putString("email",binding.email.getText().toString());
                                    editor.commit();
                                    editor.apply();
                                    startActivity(new Intent(Loginactivity.this, HomePage.class));
                                    finish();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                    }else {
                        Toast.makeText(Loginactivity.this,"Please check your login Credentials or register.",Toast.LENGTH_SHORT).show();
                    }
                });

            }else {

            }
        });

       /* mauth=FirebaseAuth.getInstance();
        etpassword=findViewById(R.id.pass);
        etemail=findViewById(R.id.email);
        if (mauth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), FragmentHome.class));
            finish();
        }

    }
    public void signin(View v){
        String email=etemail.getText().toString();
        String password=etpassword.getText().toString();

        if (email.isEmpty()) {
            etemail.setError("Enter valid Username");
        } else if (password.isEmpty()) {
            etpassword.setError("Enter valid Username");
        } else {
            if (JemsProvider.isInternetAvailable(this)) {
                showLoading(true);
                checkInDataBase(email, password, new LoadingCallBack() {
                    @Override
                    public void show(boolean needToShow) {
                        showLoading(needToShow);
                    }
                });
            }
        }*/
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public void call_backsignup(View v){
        Intent i = new Intent(getApplicationContext(), MainActivity2.class);
        startActivity(i);
    }

    boolean isVerify(){
        if (binding.email.getText().toString().trim().isEmpty()){
            binding.email.setError("Email is Empty");
            binding.email.requestFocus();
            return false;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(binding.email.getText().toString().trim()).matches()) {
            binding.email.setError("Enter the valid email");
            binding.email.requestFocus();
            return false;
        }
        if (binding.pass.getText().toString().trim().isEmpty()){
            binding.pass.setError("Password is Empty");
            binding.pass.requestFocus();
            return false;
        }
        if (binding.pass.getText().toString().trim().length()<6){
            binding.pass.setError("Length of Password is more then 6.");
            binding.pass.requestFocus();
            return false;
        }
        return true;
    }

   /* private void checkInDataBase(String email, String password, LoadingCallBack loadingCallBack) {
        FirebaseDatabase.getInstance().getReference().child("users").child(email).child("password").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String value=snapshot.getValue().toString();
                    Log.d("fatTAG","onDataChange:"+snapshot.getValue());
                    Log.d("fatTAG","onDataChange: value:" + value);
                    if (value.equals(password)){
                        checkAndRegisterUser(email,password,loadingCallBack);
                    }else {
                        loadingCallBack.show(false);
                        Toast.makeText(Loginactivity.this,"Wrong Password",Toast.LENGTH_SHORT).show();
                    }
                }else {
                    loadingCallBack.show(false);
                    Toast.makeText(Loginactivity.this,"User not Exist",Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                loadingCallBack.show(false);
                Toast.makeText(Loginactivity.this,"Failed to check user.",Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void checkAndRegisterUser(String email, String password, LoadingCallBack loadingCallBack) {
        if (!email.endsWith("@gmail.com")){
            email += "@gmail.com";
        }
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                loadingCallBack.show(false);
                if (task.isSuccessful()){
                    Toast.makeText(Loginactivity.this,"Log-in successfull",Toast.LENGTH_SHORT).show();
                    startThisActivity(FragmentHome.class);
                }else {
                    Toast.makeText(Loginactivity.this,"Log-in failed.Please Check Your Credentials.",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    interface LoadingCallBack{
        void show(boolean needToShow);
    }

    private void showLoading(boolean b) {
        if(loding==null){
            loding=new Dialog(this);
            loding.setContentView(LayoutInflater.from(this).inflate(R.layout.activity_loadingview,null));
            loding.setCancelable(false);
            loding.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }
        if (b){
            if (!loding.isShowing()) {
                loding.show();
            }
        }else {
            if (!loding.isShowing()) {
                loding.dismiss();
            }
        }
    }
    @SuppressLint("IntentWithNullActionLaunch")
    void startThisActivity(Class<?> class_){
        startActivity(new Intent(this,class_));
    }*/
    public void register(View v){
        Intent i=new Intent(getApplicationContext(), RegistrationActivity.class);
        startActivity(i);
    }
    public void forgtpassword(View v){
        Intent i=new Intent(getApplicationContext(), ForgetPasswordActivity.class);
        startActivity(i);
    }

}