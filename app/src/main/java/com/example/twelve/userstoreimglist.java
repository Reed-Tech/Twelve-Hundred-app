package com.example.twelve;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.twelve.Adapters.aepl_adapter;
import com.example.twelve.Adapters.userstoreAdapter;
import com.example.twelve.Model.HomeProducts;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class userstoreimglist extends AppCompatActivity {
    private RecyclerView usil_rv;
    private TextView nitd;
    userstoreAdapter adapter;
    GridLayoutManager lm;
    private String sname, gender, path, category;
    private Toolbar usil_tb;
    List<HomeProducts> lee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userstoreimglist);

        usil_rv = findViewById(R.id.usil_rv);
        usil_tb = findViewById(R.id.usil_tb);
        sname = getIntent().getStringExtra("sname");
        gender = getIntent().getStringExtra("gender");
        category = getIntent().getStringExtra("category");
        nitd = findViewById(R.id.nitd);
        lee = new ArrayList<>();
        path = getIntent().getStringExtra("path");
        path = path.replace(".", "");

        setSupportActionBar(usil_tb);
        getSupportActionBar().setTitle(category);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_arrow);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        DatabaseReference stref = FirebaseDatabase.getInstance().getReference("Stores");

        lm = new GridLayoutManager(this, 2);
        usil_rv.setLayoutManager(lm);
        usil_rv.getLayoutManager().setMeasurementCacheEnabled(false);

        stref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child(path).child("Products").child(gender).child(category).exists() && snapshot.child(path).child("Products").child(gender).child(category).hasChildren()) {
                    for (DataSnapshot dss : snapshot.child(path).child("Products").child(gender).child(category).getChildren()) {
                        HomeProducts hp2 = dss.getValue(HomeProducts.class);
                        lee.add(hp2);
                    }

                    adapter = new userstoreAdapter(lee, userstoreimglist.this);
                    adapter.notifyDataSetChanged();
                    usil_rv.setAdapter(adapter);


                } else {
                    nitd.setVisibility(View.VISIBLE);
                    usil_rv.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return true;
    }
}