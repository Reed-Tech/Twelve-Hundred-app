package com.example.twelve;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.twelve.Adapters.Cart_R_adapter;
import com.example.twelve.Model.CartItems;
import com.example.twelve.Prevalent.Prevalent;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CartActivity extends AppCompatActivity {
    List<CartItems> lstcart;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView recyclerView;
    BottomNavigationView bottomNavigationView;
    Cart_R_adapter crt_adapter;
    private Button checkout;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        toolbar = findViewById(R.id.cart_toolbar);
        recyclerView = findViewById(R.id.cart_Rview);
        checkout = findViewById(R.id.checkout_btn);

        lstcart = new ArrayList<>();

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Cart");
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_arrow);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.getLayoutManager().setMeasurementCacheEnabled(false);

        DatabaseReference cartref= FirebaseDatabase.getInstance().getReference("Cart List");

        cartref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.child("User View").hasChild(Prevalent.currentOnlineUser.getEmail().replace(".", ""))){
                    int Tcheckout = 0;
                    for(DataSnapshot ds : snapshot.child("User View")
                            .child(Prevalent.currentOnlineUser.getEmail().replace(".", "")).child("products").getChildren()){
                        CartItems cartItems = ds.getValue(CartItems.class);
                        Tcheckout += (Integer.parseInt(cartItems.getPricenum()) * Integer.parseInt(cartItems.getQuantity())) ;
                        lstcart.add(cartItems);
                    }
                    checkout.setText("CHECKOUT NGN " + String.valueOf(Tcheckout));
                    Collections.reverse(lstcart);
                    crt_adapter = new Cart_R_adapter(CartActivity.this, lstcart);
                    crt_adapter.notifyDataSetChanged();
                    recyclerView.setAdapter(crt_adapter);
                    final int finalTcheckout = Tcheckout;
                    checkout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(CartActivity.this, Checkout_Activity.class);
                            intent.putExtra("subtotal", String.valueOf(finalTcheckout));
                            startActivity(intent);
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return true;
    }
}