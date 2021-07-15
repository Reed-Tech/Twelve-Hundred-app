package com.example.twelve;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AdminEditproductPanel extends AppCompatActivity {
    private List<String> list;
    private String lv_itemname, path, mode;
    Dialog dialog;


    private ListView st_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_editproduct_panel);

        st_name = findViewById(R.id.st_name);

        if (getIntent().getStringExtra("mode")!=null) {
            mode = getIntent().getStringExtra("mode");
        } else {
            mode = "admin";
        }

        list = new ArrayList<>();

        dialog = new Dialog(AdminEditproductPanel.this);
        dialog.setContentView(R.layout.aepp_layout);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(true);
        final Button maledibtn = dialog.findViewById(R.id.male_dibtn);
        final Button femaledibtn = dialog.findViewById(R.id.female_dibtn);
        final TextView psg = dialog.findViewById(R.id.psg);

        if (mode!=null && mode.equalsIgnoreCase("user")) {
            this.getSupportActionBar().setTitle("Store Names");
        } else {
            this.getSupportActionBar().setTitle("Edit Products (Admin)");
        }
        this.getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_arrow);
        this.getSupportActionBar().setDisplayShowHomeEnabled(true);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(AdminEditproductPanel.this, R.layout.spinner_dropdown, list);

        st_name.setAdapter(adapter);

        DatabaseReference fer = FirebaseDatabase.getInstance().getReference("Stores");
        fer.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dss : snapshot.getChildren()) {
                    String name = dss.child("storename").getValue().toString();

                    list.add(name);
                    adapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        st_name.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                dialog.show();
                lv_itemname = st_name.getItemAtPosition(i).toString();
                DatabaseReference otref = FirebaseDatabase.getInstance().getReference("Stores");
                otref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dsa : snapshot.getChildren()) {
                            if (dsa.child("storename").getValue().toString().equals(lv_itemname)) {
                                path = dsa.child("storemail").getValue().toString();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                maledibtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent = new Intent(AdminEditproductPanel.this, Adminnext.class);
                        intent.putExtra("sname", lv_itemname);
                        intent.putExtra("mode", mode);
                        intent.putExtra("gender", "Male");
                        intent.putExtra("path", path);
                        dialog.dismiss();
                        startActivity(intent);

                    }
                });

                femaledibtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent = new Intent(AdminEditproductPanel.this, Adminnext.class);
                        intent.putExtra("sname", lv_itemname);
                        intent.putExtra("gender", "Female");
                        intent.putExtra("mode", mode);
                        intent.putExtra("path", path);
                        dialog.dismiss();
                        startActivity(intent);


                    }
                });
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