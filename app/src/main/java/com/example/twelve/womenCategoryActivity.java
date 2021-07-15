package com.example.twelve;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class womenCategoryActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private LinearLayout womenlin1, womenlin2, womenlin3, womenlin4, womenlin5, womenlin6, womenlin7, womenlin8;
    private LinearLayout womenlin9, womenlin10, womenlin11, womenlin12, womenlin13, womenlin14, womenlin15, womenlin16;
    private LinearLayout womenlin17, womenlin18, womenlin19, womenlin20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_women_category);

        toolbar = findViewById(R.id.myytoolbar4);
        womenlin1 = findViewById(R.id.womenlin1);
        womenlin2 = findViewById(R.id.womenlin2);
        womenlin3 = findViewById(R.id.womenlin3);
        womenlin4 = findViewById(R.id.womenlin4);
        womenlin5 = findViewById(R.id.womenlin5);
        womenlin6 = findViewById(R.id.womenlin6);
        womenlin7 = findViewById(R.id.womenlin7);
        womenlin8 = findViewById(R.id.womenlin8);
        womenlin9 = findViewById(R.id.womenlin9);
        womenlin10 = findViewById(R.id.womenlin10);
        womenlin11 = findViewById(R.id.womenlin11);
        womenlin12 = findViewById(R.id.womenlin12);
        womenlin13 = findViewById(R.id.womenlin13);
        womenlin14 = findViewById(R.id.womenlin14);
        womenlin15 = findViewById(R.id.womenlin15);
        womenlin16 = findViewById(R.id.womenlin16);
        womenlin17 = findViewById(R.id.womenlin17);
        womenlin18 = findViewById(R.id.womenlin18);
        womenlin19 = findViewById(R.id.womenlin19);
        womenlin20 = findViewById(R.id.womenlin20);


        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        womenlin1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(womenCategoryActivity.this, newProduct.class);
                intent.putExtra("categories", "Accesories");
                intent.putExtra("gender", "Female");
                startActivity(intent);

            }
        });

        womenlin2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(womenCategoryActivity.this, newProduct.class);
                intent.putExtra("categories", "Activewear");
                intent.putExtra("gender", "Female");
                startActivity(intent);

            }
        });

        womenlin3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(womenCategoryActivity.this, newProduct.class);
                intent.putExtra("categories", "Co-ords");
                intent.putExtra("gender", "Female");
                startActivity(intent);

            }
        });

        womenlin4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(womenCategoryActivity.this, newProduct.class);
                intent.putExtra("categories", "Sweatshirts");
                intent.putExtra("gender", "Female");
                startActivity(intent);

            }
        });

        womenlin5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(womenCategoryActivity.this, newProduct.class);
                intent.putExtra("categories", "Jackets");
                intent.putExtra("gender", "Female");
                startActivity(intent);

            }
        });

        womenlin6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(womenCategoryActivity.this, newProduct.class);
                intent.putExtra("categories", "Jeans");
                intent.putExtra("gender", "Female");
                startActivity(intent);

            }
        });

        womenlin7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(womenCategoryActivity.this, newProduct.class);
                intent.putExtra("categories", "Joggers");
                intent.putExtra("gender", "Female");
                startActivity(intent);

            }
        });

        womenlin8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(womenCategoryActivity.this, newProduct.class);
                intent.putExtra("categories", "Dresses");
                intent.putExtra("gender", "Female");
                startActivity(intent);

            }
        });

        womenlin9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(womenCategoryActivity.this, newProduct.class);
                intent.putExtra("categories", "Jumpsuits");
                intent.putExtra("gender", "Female");
                startActivity(intent);

            }
        });

        womenlin10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(womenCategoryActivity.this, newProduct.class);
                intent.putExtra("categories", "Shorts");
                intent.putExtra("gender", "Female");
                startActivity(intent);

            }
        });

        womenlin11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(womenCategoryActivity.this, newProduct.class);
                intent.putExtra("categories", "Shirts");
                intent.putExtra("gender", "Female");
                startActivity(intent);

            }
        });

        womenlin12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(womenCategoryActivity.this, newProduct.class);
                intent.putExtra("Categories", "Kimonos");
                intent.putExtra("gender", "Female");
                startActivity(intent);

            }
        });

        womenlin13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(womenCategoryActivity.this, newProduct.class);
                intent.putExtra("categories", "Lingeries");
                intent.putExtra("gender", "Female");
                startActivity(intent);

            }
        });

        womenlin14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(womenCategoryActivity.this, newProduct.class);
                intent.putExtra("categories", "Underwear");
                intent.putExtra("gender", "Female");
                startActivity(intent);

            }
        });

        womenlin15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(womenCategoryActivity.this, newProduct.class);
                intent.putExtra("categories", "Tracksuits");
                intent.putExtra("gender", "Female");
                startActivity(intent);

            }
        });

        womenlin16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(womenCategoryActivity.this, newProduct.class);
                intent.putExtra("categories", "Skirts");
                intent.putExtra("gender", "Female");
                startActivity(intent);

            }
        });

        womenlin17.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(womenCategoryActivity.this, newProduct.class);
                intent.putExtra("categories", "Boots");
                intent.putExtra("gender", "Female");
                startActivity(intent);

            }
        });

        womenlin18.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(womenCategoryActivity.this, newProduct.class);
                intent.putExtra("categories", "Heels and Flats");
                intent.putExtra("gender", "Female");
                startActivity(intent);

            }
        });

        womenlin19.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(womenCategoryActivity.this, newProduct.class);
                intent.putExtra("categories", "Sandals");
                intent.putExtra("gender", "Female");
                startActivity(intent);

            }
        });

        womenlin20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(womenCategoryActivity.this, newProduct.class);
                intent.putExtra("categories", "Sneakers");
                intent.putExtra("gender", "Female");
                startActivity(intent);

            }
        });


    }
}