package com.example.twelve;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class menCategory extends AppCompatActivity {
    private Toolbar toolbar;
    private ImageView maccesories, mcoords, mactivewear, msweatshirts;
    private ImageView mjackets, mjeans, mjoggers, msleepwear;
    private ImageView msuits, munderwear, mtracksuit, mchinos;
    private LinearLayout mnative, mshorts, mshirts, mpolos;
    private ImageView mboots, mleathers, mslides, msneakers;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_men_category);

        toolbar = findViewById(R.id.myytoolbar3);
        maccesories = findViewById(R.id.maccesories);
        mcoords = findViewById(R.id.mcoords);
        mactivewear = findViewById(R.id.mactivewear);
        msweatshirts = findViewById(R.id.msweatshirts);
        mjackets = findViewById(R.id.mjackets);
        mjeans = findViewById(R.id.mjeans);
        mjoggers = findViewById(R.id.mjoggers);
        msleepwear = findViewById(R.id.msleepwear);
        mnative = findViewById(R.id.menlin9);
        mshorts = findViewById(R.id.menlin10);
        mshirts = findViewById(R.id.menlin11);
        mpolos = findViewById(R.id.menlin12);
        msuits = findViewById(R.id.msuits);
        munderwear = findViewById(R.id.munderwear);
        mtracksuit = findViewById(R.id.mtracksuit);
        mchinos = findViewById(R.id.mchinos);
        mboots = findViewById(R.id.mboots);
        mleathers = findViewById(R.id.mleathers);
        mslides = findViewById(R.id.mslides);
        msneakers = findViewById(R.id.msneakers);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        maccesories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(menCategory.this, newProduct.class);
                intent.putExtra("gender", "Male");
                intent.putExtra("categories", "Accesories");
                startActivity(intent);
            }
        });

        mcoords.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(menCategory.this, newProduct.class);
                intent.putExtra("gender", "Male");
                intent.putExtra("categories", "Co-ords");
                startActivity(intent);
            }
        });

        mactivewear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(menCategory.this, newProduct.class);
                intent.putExtra("gender", "Male");
                intent.putExtra("categories", "Activewear");
                startActivity(intent);
            }
        });

        msweatshirts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(menCategory.this, newProduct.class);
                intent.putExtra("gender", "Male");
                intent.putExtra("categories", "Sweatshirts");
                startActivity(intent);
            }
        });

        mjackets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(menCategory.this, newProduct.class);
                intent.putExtra("gender", "Male");
                intent.putExtra("categories", "Jackets");
                startActivity(intent);
            }
        });

        mjeans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(menCategory.this, newProduct.class);
                intent.putExtra("gender", "Male");
                intent.putExtra("categories", "Jeans");
                startActivity(intent);
            }
        });

        mjoggers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(menCategory.this, newProduct.class);
                intent.putExtra("gender", "Male");
                intent.putExtra("categories", "Joggers");
                startActivity(intent);
            }
        });

        msleepwear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(menCategory.this, newProduct.class);
                intent.putExtra("gender", "Male");
                intent.putExtra("categories", "Sleepwear");
                startActivity(intent);
            }
        });

        mnative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(menCategory.this, newProduct.class);
                intent.putExtra("gender", "Male");
                intent.putExtra("categories", "Nativewear");
                startActivity(intent);
            }
        });

        mshorts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(menCategory.this, newProduct.class);
                intent.putExtra("gender", "Male");
                intent.putExtra("categories", "Shorts");
                startActivity(intent);
            }
        });

        mshirts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(menCategory.this, newProduct.class);
                intent.putExtra("gender", "Male");
                intent.putExtra("categories", "Shirts");
                startActivity(intent);
            }
        });

        mpolos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(menCategory.this, newProduct.class);
                intent.putExtra("gender", "Male");
                intent.putExtra("categories", "Tees");
                startActivity(intent);
            }
        });



        msuits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(menCategory.this, newProduct.class);
                intent.putExtra("gender", "Male");
                intent.putExtra("categories", "Suits");
                startActivity(intent);
            }
        });
         munderwear.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Intent intent = new Intent(menCategory.this, newProduct.class);
                 intent.putExtra("gender", "Male");
                 intent.putExtra("categories", "Underwear");
                 startActivity(intent);
             }
         });

         mtracksuit.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Intent intent = new Intent(menCategory.this, newProduct.class);
                 intent.putExtra("gender", "Male");
                 intent.putExtra("categories", "Tracksuit");
                 startActivity(intent);
             }
         });
        mchinos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(menCategory.this, newProduct.class);
                intent.putExtra("gender", "Male");
                intent.putExtra("categories", "Chinos");
                startActivity(intent);
            }
        });

        mboots.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(menCategory.this, newProduct.class);
                intent.putExtra("gender", "Male");
                intent.putExtra("categories", "Boots");
                startActivity(intent);
            }
        });

        mleathers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(menCategory.this, newProduct.class);
                intent.putExtra("gender", "Male");
                intent.putExtra("categories", "Leathers");
                startActivity(intent);
            }
        });

        mslides.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(menCategory.this, newProduct.class);
                intent.putExtra("gender", "Male");
                intent.putExtra("categories", "Slides");
                startActivity(intent);
            }
        });

        msneakers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(menCategory.this, newProduct.class);
                intent.putExtra("gender", "Male");
                intent.putExtra("categories", "Sneakers");
                startActivity(intent);
            }
        });




    }
}