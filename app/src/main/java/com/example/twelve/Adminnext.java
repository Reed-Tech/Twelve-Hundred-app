package com.example.twelve;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Adminnext extends AppCompatActivity {
    private String sname, gender, path, mode;
    private ListView next_lv;
    public static String item_string;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminnext);


        sname = getIntent().getStringExtra("sname");
        path = getIntent().getStringExtra("path");
        if (getIntent().getStringExtra("mode").equals(null) || !getIntent().getStringExtra("mode").equals("")) {
            mode = getIntent().getStringExtra("mode");
        } else {
            mode = "";
        }
        path = path.replace(".", "");
        gender = getIntent().getStringExtra("gender");
        next_lv = findViewById(R.id.next_lv);


        this.getSupportActionBar().setTitle("Categories" + "(" + sname + ")");
        this.getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_arrow);
        this.getSupportActionBar().setDisplayShowHomeEnabled(true);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String[] malelist = {"Accesories", "Activewear", "Co-ords", "Jackets", "Jeans", "Joggers", "Nativewear", "Pants/Chinos", "Shirts",
                "Shorts", "Sleepwear", "Suits", "Sweatshirts", "T-shirts and Polos", "Tracksuits", "Underwear", "Boots", "Shoes", "Slides", "Sneakers"};

        String[] femalelist = {"Accesories", "Activewear", "Co-ords", "Dresses", "Jackets", "Jeans", "Joggers", "Jumpsuits", "Kimonos", "Lingeries",
                "Nativewear", "Pants", "Shirts",
                "Shorts", "Skirts", "Sleepwear", "Suits", "Sweatshirts", "Tops/Polo", "Tracksuits", "Underwear", "Boots", "Heels", "Sandals", "Sneakers"};


        if (gender.equals("Male")) {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(Adminnext.this, R.layout.spinner_dropdown, malelist);

            next_lv.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        } else {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(Adminnext.this, R.layout.spinner_dropdown, femalelist);

            next_lv.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }

        next_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(mode.equals("user")){
                    String cat = next_lv.getItemAtPosition(i).toString();
                    item_string = cat;
                    Intent intent = new Intent(Adminnext.this, userstoreimglist.class);
                    intent.putExtra("path", path);
                    intent.putExtra("sname", sname);
                    intent.putExtra("gender", gender);
                    intent.putExtra("category", cat);
                    startActivity(intent);
                }else {
                    String cat = next_lv.getItemAtPosition(i).toString();
                    item_string = cat;
                    Intent intent = new Intent(Adminnext.this, Admineditproductlist.class);
                    intent.putExtra("path", path);
                    intent.putExtra("sname", sname);
                    intent.putExtra("gender", gender);
                    intent.putExtra("category", cat);
                    startActivity(intent);
                }

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