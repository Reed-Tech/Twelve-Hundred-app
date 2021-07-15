package com.example.twelve;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.twelve.Adapters.RecyclerAdapter;
import com.example.twelve.Adapters.Store_All_items_Recycler_View;
import com.example.twelve.Model.HomeProducts;
import com.example.twelve.Prevalent.Prevalent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Store_All_Items extends AppCompatActivity {
    private RecyclerView store_item_rv;
    List<HomeProducts> leist;
    private String categories, gender;
    Store_All_items_Recycler_View rva;
    private TextView nitdy;
    private GridLayoutManager layoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store__all__items);
        leist = new ArrayList<>();
        store_item_rv = findViewById(R.id.store_Item_rv);
        nitdy = findViewById(R.id.nitdy_text);
        categories = getIntent().getStringExtra("categories").toString();
        gender = getIntent().getStringExtra("gender").toString();
        final String path = Prevalent.currentOnlineStore.getStoremail().replace(".", "");

        Store_All_Items.this.getSupportActionBar().setTitle(categories);
        Store_All_Items.this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Store_All_Items.this.getSupportActionBar().setDisplayShowHomeEnabled(true);
        Store_All_Items.this.getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_arrow);

        DatabaseReference dref = FirebaseDatabase.getInstance().getReference("Stores");
        dref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
               if(snapshot.child(path).child("Products").child(gender).child(categories).exists()){
                   for(DataSnapshot dos : snapshot.child(path).child("Products").child(gender).child(categories).getChildren()){
                       HomeProducts Hproducts = dos.getValue(HomeProducts.class);
                       leist.add(Hproducts);
                   }
                   Collections.reverse(leist);
                   rva = new Store_All_items_Recycler_View(Store_All_Items.this, leist);
                   rva.notifyDataSetChanged();
                   store_item_rv.setAdapter(rva);

               }else{
                   nitdy.setVisibility(View.VISIBLE);
                   store_item_rv.setVisibility(View.INVISIBLE);
               }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        layoutManager = new GridLayoutManager(this, 2);
        store_item_rv.setLayoutManager(layoutManager);
        store_item_rv.getLayoutManager().setMeasurementCacheEnabled(false);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_icon, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id==android.R.id.home){
            finish();
        }
        if(id==R.id.add_prdct){
            Intent intent = new Intent(Store_All_Items.this, newProduct.class);
            intent.putExtra("categories", categories);
            intent.putExtra("gender", gender);
            intent.putExtra("Prev", "Store");
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}