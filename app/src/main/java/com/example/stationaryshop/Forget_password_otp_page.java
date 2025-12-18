package com.example.stationaryshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.stationaryshop.databinding.ActivityForgetPasswordOtpPageBinding;

public class Forget_password_otp_page extends AppCompatActivity {
    private EditText etCode1, etCode2, etCode3, etCode4;
    private Button btnContinue;
    ActivityForgetPasswordOtpPageBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_forget_password_otp_page);

        binding = ActivityForgetPasswordOtpPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        etCode1 = findViewById(R.id.etc1);
        etCode2 = findViewById(R.id.etc2);
        etCode3 = findViewById(R.id.etc3);
        etCode4 = findViewById(R.id.etc4);
        btnContinue = findViewById(R.id.btncontinuefc);

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve the verification code entered by the user
                String code1 = etCode1.getText().toString().trim();
                String code2 = etCode2.getText().toString().trim();
                String code3 = etCode3.getText().toString().trim();
                String code4 = etCode4.getText().toString().trim();

                // Check if all EditText fields are filled
                if (code1.isEmpty() || code2.isEmpty() || code3.isEmpty() || code4.isEmpty()) {
                    Toast.makeText(Forget_password_otp_page.this, "Please enter the complete verification code", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Concatenating the code to form the complete verification code
                String verificationCode = code1 + code2 + code3 + code4;

                // Retrieve the OTP copied to the clipboard
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                if (clipboard.hasPrimaryClip() && clipboard.getPrimaryClip().getItemCount() > 0) {
                    ClipData.Item item = clipboard.getPrimaryClip().getItemAt(0);
                    String copiedCode = item.getText().toString().trim();

                    // Compare the entered verification code with the copied code from the clipboard
                    if (verificationCode.equals(copiedCode)) {
                        // Verification code matches the code copied from the clipboard
                        // Proceed to the next activity for password reset
                        Intent intent = new Intent(Forget_password_otp_page.this, forgot_newpass.class);
                        startActivity(intent);
                    } else {
                        // Verification code does not match the code copied from the clipboard
                        Toast.makeText(Forget_password_otp_page.this, "Verification code does not match", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Clipboard is empty or does not contain valid text data
                    Toast.makeText(Forget_password_otp_page.this, "Clipboard is empty", Toast.LENGTH_SHORT).show();
                }
            }
        });


        // Distribute each digit of the OTP to the corresponding EditText fields when clicked
        View.OnClickListener codeClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText = (EditText) v;
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                if (clipboard.hasPrimaryClip() && clipboard.getPrimaryClip().getItemCount() > 0) {
                    ClipData.Item item = clipboard.getPrimaryClip().getItemAt(0);
                    String clipboardText = item.getText().toString().trim();
                    if (clipboardText.length() == 4) {
                        // Distribute the clipboard content into different EditText fields
                        etCode1.setText(String.valueOf(clipboardText.charAt(0)));
                        etCode2.setText(String.valueOf(clipboardText.charAt(1)));
                        etCode3.setText(String.valueOf(clipboardText.charAt(2)));
                        etCode4.setText(String.valueOf(clipboardText.charAt(3)));
                    }
                }
                //editText.setText(""); // Clear the EditText before entering the digit
                editText.requestFocus(); // Request focus to the clicked EditText
            }
        };
        etCode1.setOnClickListener(codeClickListener);
        etCode2.setOnClickListener(codeClickListener);
        etCode3.setOnClickListener(codeClickListener);
        etCode4.setOnClickListener(codeClickListener);

    }
}