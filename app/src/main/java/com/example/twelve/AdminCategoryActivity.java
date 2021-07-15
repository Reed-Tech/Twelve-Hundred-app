package com.example.twelve;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class AdminCategoryActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private ImageView imageview1, imageview2;
    private TextView textview1, textview2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        toolbar = findViewById(R.id.myytoolbar2);
        imageview1 = findViewById(R.id.menimgbtn);
        imageview2 = findViewById(R.id.womenimgbtn);
        textview1 = findViewById(R.id.mentxt);
        textview2 = findViewById(R.id.womentxt);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        imageview1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminCategoryActivity.this, menCategory.class));
            }
        });

        textview1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminCategoryActivity.this, menCategory.class));
            }
        });

        imageview2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminCategoryActivity.this, womenCategoryActivity.class));
            }
        });

        textview2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminCategoryActivity.this, womenCategoryActivity.class));
            }
        });

    }
}