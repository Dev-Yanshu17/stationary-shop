package com.example.stationaryshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adapter.OrderSummeryAdapter;
import com.example.model.AddToCart;
import com.example.model.OrderDetails;
import com.example.stationaryshop.R;
import com.example.stationaryshop.thankyou;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ShippingAddressPageActivity extends AppCompatActivity {
    private EditText nameEditText, addressEditText, cityEditText, postalCodeEditText, phoneEditText;
    private ImageView cashOnDeliveryImageView;
    private Button placeOrderButton;

    private DatabaseReference ordersRef;
    private RecyclerView recyclerView;
    private OrderSummeryAdapter adapter;
    // Variable to store the last clicked payment method ImageView
    private ImageView lastClickedPaymentMethod;
    ShippingAddressPageActivity binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipping_address_page);

        // Retrieve total price from intent
        double totalPrice = getIntent().getDoubleExtra("TOTAL_PRICE", 0.0);

        // Display total price in a TextView
        TextView totalPriceTextView = findViewById(R.id.total_price_text_view);
        totalPriceTextView.setText(String.valueOf(totalPrice));

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.ordersummeryrecyler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize Firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        ordersRef = database.getReference("Orders");

        // Initialize views
        nameEditText = findViewById(R.id.name);
        addressEditText = findViewById(R.id.address);
        cityEditText = findViewById(R.id.city);
        postalCodeEditText = findViewById(R.id.postalcode);
        phoneEditText = findViewById(R.id.phone);
        cashOnDeliveryImageView = findViewById(R.id.cashondelivery);
       // razorPayImageView = findViewById(R.id.razorpay);
        placeOrderButton = findViewById(R.id.btnorderplaced);

        // Inside onCreate() or wherever you initialize your views
        cashOnDeliveryImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Set Cash On Delivery as the payment method
                cashOnDeliveryImageView.setSelected(true);
               // razorPayImageView.setSelected(false); // Deselect RazorPay
                lastClickedPaymentMethod = cashOnDeliveryImageView;
                // Set tag for cashOnDeliveryImageView to indicate the payment method
                cashOnDeliveryImageView.setTag("Cash On Delivery");
                Toast.makeText(ShippingAddressPageActivity.this, "Payment method: Cash On Delivery", Toast.LENGTH_SHORT).show();
                // Update UI or perform other actions as needed
            }
        });
       /* razorPayImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Set RazorPay as the payment method
                razorPayImageView.setSelected(true);
                cashOnDeliveryImageView.setSelected(false); // Deselect Cash On Delivery
                lastClickedPaymentMethod = razorPayImageView;
                // Set tag for razorPayImageView to indicate the payment method
                razorPayImageView.setTag("RazorPay");
                Toast.makeText(ShippingAddressPageActivity.this, "Payment method: RazorPay", Toast.LENGTH_SHORT).show();
                // Update UI or perform other actions as needed
            }
        });*/
        // Query cart items for the current user
        queryCartItemsForCurrentUser();
        placeOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve data entered by the user
                String name = nameEditText.getText().toString();
                String address = addressEditText.getText().toString();
                String city = cityEditText.getText().toString();
                String postalCode = postalCodeEditText.getText().toString();
                String phone = phoneEditText.getText().toString();
                String paymentMethod = getPaymentMethod(); // Set payment method based on the selected ImageView

                // Check individual fields for empty values
                if (name.isEmpty()) {
                    nameEditText.setError("Please enter your name");
                    nameEditText.requestFocus();
                    return;
                }

                if (address.isEmpty()) {
                    addressEditText.setError("Please enter your address");
                    addressEditText.requestFocus();
                    return;
                }

                if (city.isEmpty()) {
                    cityEditText.setError("Please enter your city");
                    cityEditText.requestFocus();
                    return;
                }

                if (postalCode.isEmpty()) {
                    postalCodeEditText.setError("Please enter your postal code");
                    postalCodeEditText.requestFocus();
                    return;
                }

                if (phone.isEmpty()) {
                    phoneEditText.setError("Please enter your phone number");
                    phoneEditText.requestFocus();
                    return;
                }

                // Validate phone number starts with 7, 8, or 9
                if (!phone.matches("[7-9][0-9]{9}")) {
                    phoneEditText.setError("Please enter a valid mobile number starting with 7, 8, or 9");
                    phoneEditText.requestFocus();
                    return;
                }

                if (lastClickedPaymentMethod == null) {
                    Toast.makeText(ShippingAddressPageActivity.this, "Please select a payment method", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Check if either Cash on Delivery or Razor Pay is selected
                if (!(lastClickedPaymentMethod.getTag().equals("Cash On Delivery") || lastClickedPaymentMethod.getTag().equals("RazorPay"))) {
                    Toast.makeText(ShippingAddressPageActivity.this, "Please select a valid payment method", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Save the order details along with product details
                saveOrderDetails(name, address, city, postalCode, phone, paymentMethod, totalPrice);

            }
        });
    }

    private String getPaymentMethod() {
        if (lastClickedPaymentMethod != null) {
            return (String) lastClickedPaymentMethod.getTag();
        } else {
            // Return a default payment method or handle as per your requirement
            return "Default Payment Method";
        }
    }
    // Method to query cart items for the current user
    private void queryCartItemsForCurrentUser() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser == null) {
            // Handle the case where the user is not signed in
            return;
        }

        DatabaseReference cartRef = FirebaseDatabase.getInstance().getReference()
                .child("cart")
                .child(currentUser.getUid());

        cartRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<AddToCart> cartItems = new ArrayList<>();
                for (DataSnapshot cartSnapshot : dataSnapshot.getChildren()) {
                    AddToCart item = cartSnapshot.getValue(AddToCart.class);
                    if (item != null) {
                        cartItems.add(item);
                    }
                }
                // Set up RecyclerView adapter
                adapter = new OrderSummeryAdapter(ShippingAddressPageActivity.this, cartItems);
                recyclerView.setAdapter(adapter);
                // Save the order details along with product details
                //saveOrderDetails(name, address, city, postalCode, phone, paymentMethod, cartItems);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle database error
            }
        });
    }
    // Method to save order details along with product details
    private void saveOrderDetails(String name, String address, String city, String postalCode,
                                  String phone, String paymentMethod, double totalPrice) {

        // Create a unique order ID using the current datetime
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault());
        String orderId = dateFormat.format(new Date());
        // Get the current date and time in the desired format
        String orderdatetime = getCurrentDateTime();

        // Get the current user's ID
        String userId = getCurrentUserId();

        if (userId != null) {
            // Create a reference to the Orders node under the current user's ID
            DatabaseReference userOrdersRef = ordersRef.child(userId);

            // Create an instance of OrderDetails
            OrderDetails orderDetails = new OrderDetails();
            orderDetails.setOrderId(orderId);
            orderDetails.setUserName(name);
            orderDetails.setDateTime(new Date());
            orderDetails.setOrderdatetime(orderdatetime); // Set the order date and time
            orderDetails.setAddress(address);
            orderDetails.setCity(city);
            orderDetails.setPostalCode(postalCode);
            orderDetails.setPhone(phone);
            orderDetails.setPaymentMethod(paymentMethod);
            orderDetails.setTotalPrice(totalPrice);

            // Save the order details under the user's ID node with the unique order ID
            DatabaseReference orderRef = userOrdersRef.child(orderId);
            orderRef.setValue(orderDetails, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(@NonNull DatabaseError error, @NonNull DatabaseReference ref) {
                    if (error == null) {
                        // Order saved successfully
                        Toast.makeText(ShippingAddressPageActivity.this, "Order placed successfully", Toast.LENGTH_SHORT).show();
                        // Navigate to OrderPlacedActivity
                        Intent intent=new Intent(ShippingAddressPageActivity.this, thankyou.class);
                        startActivity(intent);
                    } else {
                        // Failed to save order
                        Toast.makeText(ShippingAddressPageActivity.this, "Failed to place order. Please try again.", Toast.LENGTH_SHORT).show();
                        Log.e("Firebase", "Error saving order: " + error.getMessage());
                    }
                }
            });
            // Save product details under the order node
            DatabaseReference productsRef = orderRef.child("products");
            for (AddToCart item : adapter.getItems()) {
                DatabaseReference productRef = productsRef.push();
                productRef.setValue(item);
            }
        } else {
            // Handle the case when the user is not logged in
            Toast.makeText(this, "User not logged in.", Toast.LENGTH_SHORT).show();
        }
    }
    private String getCurrentUserId() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            return currentUser.getUid();
        } else {
            // Handle the case when the current user is not logged in
            return null;
        }
    }
    private String getCurrentDateTime() {
        // Create a SimpleDateFormat object with the desired date and time format
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());

        // Get the current date and time
        Date currentDate = new Date();

        // Format the current date and time using the SimpleDateFormat object
        return dateFormat.format(currentDate);
    }



}