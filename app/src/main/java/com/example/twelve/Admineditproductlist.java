package com.example.twelve;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.twelve.Adapters.RecyclerAdapter;
import com.example.twelve.Adapters.aepl_adapter;
import com.example.twelve.Model.HomeProducts;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Admineditproductlist extends AppCompatActivity {
    private RecyclerView nextrv;
    private String sname, gender, category, path;
    private TextView nitd;
    aepl_adapter adapter;
    GridLayoutManager lm2;
    List<HomeProducts> lstp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admineditproductlist);

        nextrv = findViewById(R.id.nextrv);
        nitd = findViewById(R.id.nitd);
        sname = getIntent().getStringExtra("sname");
        gender = getIntent().getStringExtra("gender");
        category = getIntent().getStringExtra("category");
        path = getIntent().getStringExtra("path");
        lstp = new ArrayList<>();

        this.getSupportActionBar().setTitle(category + " " + "(" + sname + ")");
        this.getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_arrow);
        this.getSupportActionBar().setDisplayShowHomeEnabled(true);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        lm2 = new GridLayoutManager(this, 2);
        nextrv.setLayoutManager(lm2);
        nextrv.getLayoutManager().setMeasurementCacheEnabled(false);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Stores");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child(path).child("Products").child(gender).child(category).exists() && snapshot.child(path).child("Products").child(gender).child(category).hasChildren()) {
                    for (DataSnapshot dss : snapshot.child(path).child("Products").child(gender).child(category).getChildren()) {
                        HomeProducts hp2 = dss.getValue(HomeProducts.class);
                        lstp.add(hp2);
                    }

                    Collections.reverse(lstp);
                    adapter = new aepl_adapter(Admineditproductlist.this, lstp);
                    adapter.notifyDataSetChanged();
                    nextrv.setAdapter(adapter);


                } else {
                    nitd.setVisibility(View.VISIBLE);
                    nextrv.setVisibility(View.GONE);
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