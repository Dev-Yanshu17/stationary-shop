package com.example.stationaryshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.jar.JarException;

public class get_payment_amount extends AppCompatActivity implements PaymentResultListener {
    private Button paybtn;
    private EditText payamount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_payment_amount);

        payamount=findViewById(R.id.inputamount);
        paybtn=findViewById(R.id.paybutton);
        paybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String samount=payamount.getText().toString();
                int amount=Math.round(Float.parseFloat(samount)*100);

                Checkout checkout = new Checkout();
                checkout.setKeyID("rzp_test_izhabiTXDxMcWE");

                JSONObject object=new JSONObject();
                try {
                    object.put("name","developer");
                    object.put("currency","BDT");
                    object.put("amount",amount);
                    checkout.open(get_payment_amount.this,object);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    @Override
    public void onPaymentSuccess(String s) {
        Toast.makeText(this, "Payment Completed"+s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(this, "Payment Failed"+s, Toast.LENGTH_SHORT).show();
    }

    public void  cancel(View v){
        Intent i=new Intent(getApplicationContext(), cashonlivry.class);
        startActivity(i);
    }
}