package com.example.twelve;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.twelve.Adapters.RecyclerAdapter;
import com.example.twelve.Model.HomeProducts;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Category_Item_List extends AppCompatActivity {
    private Toolbar toolbar;
    private RecyclerView Rview;
    private DatabaseReference eachproductsref;
    private ImageView collapsing_image;
    GridLayoutManager layoutManager;
    String gender, categoryname;
    List<HomeProducts> lstitms;
    RecyclerAdapter rcyAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category__item__list);

        gender = getIntent().getStringExtra("gender");
        categoryname = getIntent().getStringExtra("category");
        toolbar = findViewById(R.id.categorylist_toolbar);
        collapsing_image = findViewById(R.id.collapsing_image);
        Rview = findViewById(R.id.category_itemList_rv);
        lstitms = new ArrayList<>();
        eachproductsref = FirebaseDatabase.getInstance().getReference("Products").child(gender).child(categoryname);

        if(gender.equals("Male") && categoryname.equals("Accesories")){collapsing_image.setImageResource(R.drawable.men_accesories);}
        if(gender.equals("Male") && categoryname.equals("Activewear")){collapsing_image.setImageResource(R.drawable.men_sportswear);}
        if(gender.equals("Male") && categoryname.equals("Co-ords")){collapsing_image.setImageResource(R.drawable.mencoords);}
        if(gender.equals("Male") && categoryname.equals("Jackets")){collapsing_image.setImageResource(R.drawable.men_jackets);}
        if(gender.equals("Male") && categoryname.equals("Jeans")){collapsing_image.setImageResource(R.drawable.men_jeans1);}
        if(gender.equals("Male") && categoryname.equals("Joggers")){collapsing_image.setImageResource(R.drawable.men_joggers);}
        if(gender.equals("Male") && categoryname.equals("Nativewear")){collapsing_image.setImageResource(R.drawable.men_native2);}
        if(gender.equals("Male") && categoryname.equals("Chinos")){collapsing_image.setImageResource(R.drawable.men_pants);}
        if(gender.equals("Male") && categoryname.equals("Shirts")){collapsing_image.setImageResource(R.drawable.men_shirts);}
        if(gender.equals("Male") && categoryname.equals("Shorts")){collapsing_image.setImageResource(R.drawable.men_shorts);}
        if(gender.equals("Male") && categoryname.equals("Sleepwear")){collapsing_image.setImageResource(R.drawable.male_pyjamas);}
        if(gender.equals("Male") && categoryname.equals("Suits")){collapsing_image.setImageResource(R.drawable.male_suits);}
        if(gender.equals("Male") && categoryname.equals("Sweatshirts")){collapsing_image.setImageResource(R.drawable.men_sweats);}
        if(gender.equals("Male") && categoryname.equals("Tees")){collapsing_image.setImageResource(R.drawable.men_tees);}
        if(gender.equals("Male") && categoryname.equals("Tracksuit")){collapsing_image.setImageResource(R.drawable.men_track);}
        if(gender.equals("Male") && categoryname.equals("Underwear")){collapsing_image.setImageResource(R.drawable.men_underwear);}
        if(gender.equals("Male") && categoryname.equals("Boots")){collapsing_image.setImageResource(R.drawable.men_boots);}
        if(gender.equals("Male") && categoryname.equals("Leathers")){collapsing_image.setImageResource(R.drawable.men_shoes);}
        if(gender.equals("Male") && categoryname.equals("Slides")){collapsing_image.setImageResource(R.drawable.men_slides);}
        if(gender.equals("Male") && categoryname.equals("Sneakers")){collapsing_image.setImageResource(R.drawable.men_sneakers);}

        if(gender.equals("Female") && categoryname.equals("Accesories")){collapsing_image.setImageResource(R.drawable.female_accesories);}
        if(gender.equals("Female") && categoryname.equals("Activewear")){collapsing_image.setImageResource(R.drawable.female_sportswear);}
        if(gender.equals("Female") && categoryname.equals("Co-ords")){collapsing_image.setImageResource(R.drawable.female_coords);}
        if(gender.equals("Female") && categoryname.equals("Dresses")){collapsing_image.setImageResource(R.drawable.female_dresses);}
        if(gender.equals("Female") && categoryname.equals("Jackets")){collapsing_image.setImageResource(R.drawable.female_jackets);}
        if(gender.equals("Female") && categoryname.equals("Jeans")){collapsing_image.setImageResource(R.drawable.female_jeans);}
        if(gender.equals("Female") && categoryname.equals("Joggers")){collapsing_image.setImageResource(R.drawable.female_joggers);}
        if(gender.equals("Female") && categoryname.equals("Jumpsuits")){collapsing_image.setImageResource(R.drawable.female_jumpsuits);}
        if(gender.equals("Female") && categoryname.equals("Kimonos")){collapsing_image.setImageResource(R.drawable.female_kimonos);}
        if(gender.equals("Female") && categoryname.equals("Lingeries")){collapsing_image.setImageResource(R.drawable.female_lingeries);}
        if(gender.equals("Female") && categoryname.equals("Pants")){collapsing_image.setImageResource(R.drawable.female_lingeries);}
        if(gender.equals("Female") && categoryname.equals("Shirts")){collapsing_image.setImageResource(R.drawable.female_shirts);}
        if(gender.equals("Female") && categoryname.equals("Shorts")){collapsing_image.setImageResource(R.drawable.female_shorts);}
        if(gender.equals("Female") && categoryname.equals("Skirts")){collapsing_image.setImageResource(R.drawable.female_skirts);}
        if(gender.equals("Female") && categoryname.equals("Sweatshirts")){collapsing_image.setImageResource(R.drawable.female_sweats1);}
        if(gender.equals("Female") && categoryname.equals("Tees")){collapsing_image.setImageResource(R.drawable.female_tees);}
        if(gender.equals("Female") && categoryname.equals("Tracksuits")){collapsing_image.setImageResource(R.drawable.female_tracksuits);}
        if(gender.equals("Female") && categoryname.equals("Underwear")){collapsing_image.setImageResource(R.drawable.female_underwear);}
        if(gender.equals("Female") && categoryname.equals("Boots")){collapsing_image.setImageResource(R.drawable.female_boots);}
        if(gender.equals("Female") && categoryname.equals("Heels and Flats")){collapsing_image.setImageResource(R.drawable.female_heelsss);}
        if(gender.equals("Female") && categoryname.equals("Sandals")){collapsing_image.setImageResource(R.drawable.female_sandals);}
        if(gender.equals("Female") && categoryname.equals("Sneakers")){collapsing_image.setImageResource(R.drawable.men_sneakers);}

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(categoryname);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_arrow);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        layoutManager = new GridLayoutManager(this, 2);
        Rview.setLayoutManager(layoutManager);
        Rview.getLayoutManager().setMeasurementCacheEnabled(false);


       eachproductsref.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {
               for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                   HomeProducts Hproducts = dataSnapshot.getValue(HomeProducts.class);
                   lstitms.add(Hproducts);
               }
               Collections.reverse(lstitms);
               rcyAdapter = new RecyclerAdapter(lstitms, Category_Item_List.this);
               rcyAdapter.notifyDataSetChanged();
               Rview.setAdapter(rcyAdapter);
           }

           @Override
           public void onCancelled(@NonNull DatabaseError error) {
               Toast.makeText(Category_Item_List.this, "Something went wrong", Toast.LENGTH_SHORT).show();
           }
       });


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            onBackPressed();
        }
        return true;
    }
}