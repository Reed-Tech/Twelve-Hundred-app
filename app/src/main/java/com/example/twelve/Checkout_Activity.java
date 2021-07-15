package com.example.twelve;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

public class Checkout_Activity extends AppCompatActivity {
    Toolbar toolbar;
    private String subtotal, shipping, ordertotal;
    private TextView subt, ship, ordert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout_);

        subtotal = getIntent().getStringExtra("subtotal");
        shipping = "1000";
        ordertotal = getIntent().getStringExtra("ordertotal");
        toolbar = findViewById(R.id.checkout_toolbar);
        subt = (TextView) findViewById(R.id.subtotal_value);
        ship = (TextView) findViewById(R.id.shipping_value);
        ordert = (TextView) findViewById(R.id.ordertotal_value);

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_arrow);
        getSupportActionBar().setTitle("Order Summary");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        subt.setText("NGN "+subtotal);
        ship.setText("NGN "+ shipping);
        ordert.setText(String.valueOf(Integer.parseInt(shipping) + (Integer.parseInt(subtotal))));


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            onBackPressed();
        }
        return true;
    }
}