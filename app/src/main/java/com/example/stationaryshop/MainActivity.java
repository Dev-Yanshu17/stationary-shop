package com.example.stationaryshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent = new Intent(MainActivity.this,introduction1.class);
                startActivity(intent);
                finish();

            }
        },3000);
    }

    // @Override
    //protected void onStart() {
    // super.onStart();

    //FirebaseUser currentUser = firebaseAuth.getCurrentUser();

    //if (currentUser == null){
    //Toast.makeText(this, "user not found!", Toast.LENGTH_SHORT).show();
    // }else {
    //  FirebaseFirestore.getInstance().collection("USERS").document(currentUser.getUid()).update("Last seen", FieldValue.serverTimestamp())
    //   .addOnCompleteListener(new OnCompleteListener<Void>() {
    // @Override
    // public void onComplete(@NonNull Task<Void> task) {
    // if (task.isSuccessful()){

    // }else {
    // String error = task.getException().getMessage();
    //Toast.makeText(SplashActivity.this, error , Toast.LENGTH_SHORT).show();
    //  }
    //  }
    // });
    // }

    //  }

}
