package com.example.twelve;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

public class All_Items01 extends AppCompatActivity {

    private ListView allItems_lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all__items01);
        allItems_lv = findViewById(R.id.allitems_lv);

        All_Items01.this.getSupportActionBar().setTitle("Product Categories");
        All_Items01.this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        All_Items01.this.getSupportActionBar().setDisplayShowHomeEnabled(true);
        All_Items01.this.getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_arrow);

        final Dialog dialog = new Dialog(All_Items01.this);
        dialog.setContentView(R.layout.aepp_layout);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(true);
        final Button maledibtn = dialog.findViewById(R.id.male_dibtn);
        final Button femaledibtn = dialog.findViewById(R.id.female_dibtn);
        final TextView psg = dialog.findViewById(R.id.psg);
        dialog.show();

        final String[] malelist = {"Accesories", "Activewear", "Co-ords", "Jackets", "Jeans", "Joggers", "Nativewear", "Pants/Chinos", "Shirts",
                "Shorts", "Sleepwear", "Suits", "Sweatshirts", "T-shirts/Polo", "Tracksuits", "Underwear", "Boots", "Shoes", "Slides", "Sneakers"};

        final String[] femalelist = {"Accesories", "Activewear", "Co-ords", "Dresses", "Jackets", "Jeans", "Joggers", "Jumpsuits", "Kimonos", "Lingeries",
                "Nativewear", "Pants", "Shirts",
                "Shorts", "Skirts", "Sleepwear", "Suits", "Sweatshirts", "Tops/Polo", "Tracksuits", "Underwear", "Boots", "Heels", "Sandals", "Sneakers"};

        maledibtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(All_Items01.this, R.layout.spinner_dropdown, malelist);
                allItems_lv.setAdapter(adapter);
                adapter.notifyDataSetChanged();

                allItems_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        String catname = allItems_lv.getItemAtPosition(i).toString();
                        Intent intent = new Intent(All_Items01.this, Store_All_Items.class);
                        intent.putExtra("gender", "Male");
                        intent.putExtra("categories", catname);
                        startActivity(intent);
                    }
                });
            }
        });

        femaledibtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(All_Items01.this, R.layout.spinner_dropdown, femalelist);
                allItems_lv.setAdapter(adapter);
                adapter.notifyDataSetChanged();

                allItems_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        String catname = allItems_lv.getSelectedItem().toString();
                        Intent intent = new Intent(All_Items01.this, Store_All_Items.class);
                        intent.putExtra("gender", "Female");
                        intent.putExtra("categories", catname);
                        startActivity(intent);
                    }
                });
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}