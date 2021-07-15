package com.example.twelve;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.twelve.Adapters.RecyclerAdapter;
import com.example.twelve.Model.CartItems;
import com.example.twelve.Model.HomeProducts;
import com.example.twelve.Model.Users;
import com.example.twelve.Prevalent.Prevalent;
import com.google.android.material.navigation.NavigationView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import io.paperdb.Paper;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private Toolbar toolbar;
    private DrawerLayout drawerlay;
    private NavigationView navview;
    private ImageSlider imageSlider;
    private DatabaseReference productsref;
    private RecyclerView recyclerView;
    private LinearLayout cat_linear, not_linear, flash_linear, promo_linear;
    private TextView badge_counter, not_counter;
    MenuItem menuItem, menuItem2;
    private int cart_amount, not_amount;
    GridLayoutManager layoutManager;
    List<HomeProducts> listtems;
    RecyclerAdapter rcyAdapter;
    List<CartItems> lstcart = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        navview = findViewById(R.id.navview);
        cat_linear = findViewById(R.id.cat_linear);
        not_linear = findViewById(R.id.not_linear);
        flash_linear = findViewById(R.id.flash_linear);
        promo_linear = findViewById(R.id.promo_linear);
        drawerlay = findViewById(R.id.drawerlayout);
        toolbar = findViewById(R.id.myytoolbar);
        listtems = new ArrayList<>();
        not_amount = 16;



        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        imageSlider = findViewById(R.id.slider);
        recyclerView = findViewById(R.id.my_rv);
        productsref = FirebaseDatabase.getInstance().getReference("HomeProducts");
        Paper.init(this);

        cat_linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, userCategoryActivity.class));
            }
        });

        flash_linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, FlashDealActivity.class));
            }
        });

        promo_linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, HomeActivity2.class);
                startActivity(intent);
            }
        });

        List<SlideModel> slidemodel = new ArrayList<>();
        slidemodel.add(new SlideModel("https://images.unsplash.com/photo-1495121605193-b116b5b9c5fe?ixlib=rb-1.2.1&q=80&fm=jpg&crop=entropy&cs=tinysrgb&dl=alexandra-gorn-WF0LSThlRmw-unsplash.jpg", ScaleTypes.CENTER_CROP));
        slidemodel.add(new SlideModel("https://images.unsplash.com/photo-1512436991641-6745cdb1723f?ixlib=rb-1.2.1&q=80&fm=jpg&crop=entropy&cs=tinysrgb&dl=lauren-fleischmann-R2aodqJn3b8-unsplash.jpg", ScaleTypes.CENTER_CROP));
        slidemodel.add(new SlideModel("https://images.unsplash.com/photo-1595500037491-b81fa08512d7?ixlib=rb-1.2.1&q=80&fm=jpg&crop=entropy&cs=tinysrgb&dl=anton-jansson-NxMH9g_Wnpg-unsplash.jpg", ScaleTypes.CENTER_CROP));
        slidemodel.add(new SlideModel("https://images.unsplash.com/photo-1595593795628-5e32198b3ee4?ixlib=rb-1.2.1&q=80&fm=jpg&crop=entropy&cs=tinysrgb&dl=jake-goossen-juhD3QGCv20-unsplash.jpg", ScaleTypes.CENTER_CROP));
        slidemodel.add(new SlideModel("https://images.unsplash.com/photo-1595528983402-29309066e810?ixlib=rb-1.2.1&q=80&fm=jpg&crop=entropy&cs=tinysrgb&dl=jasmin-chew-VZv_aPFTDtg-unsplash.jpg", ScaleTypes.CENTER_CROP));
        imageSlider.setImageList(slidemodel, ScaleTypes.CENTER_CROP);

        navview.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerlay,toolbar,R.string.opennav,R.string.closenav);
        drawerlay.addDrawerListener(toggle);
        toggle.syncState();

        navview.setNavigationItemSelectedListener(this);

        View headerview = navview.getHeaderView(0);
        TextView pfullname = headerview.findViewById(R.id.profilefullname);
        TextView pusername = headerview.findViewById(R.id.header_username);
        CircleImageView userphoto = headerview.findViewById(R.id.profile_image);



        Users userdata = Paper.book().read("PrevalentUser");
        if(Prevalent.currentOnlineUser==null){
            Prevalent.currentOnlineUser = userdata;
        }
        pfullname.setText(Prevalent.currentOnlineUser.getName());
        pusername.setText(Prevalent.currentOnlineUser.getUsername());
        Picasso.get().load(Prevalent.currentOnlineUser.getProfile_image()).placeholder(R.drawable.profileicon).into(userphoto);


        layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.getLayoutManager().setMeasurementCacheEnabled(false);

        //Products are added in a list and passed to the adapter
        DatabaseReference pref = FirebaseDatabase.getInstance().getReference("HomeProducts");
        pref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    HomeProducts Hproducts = dataSnapshot.getValue(HomeProducts.class);
                    listtems.add(Hproducts);
                }
                Collections.reverse(listtems);
                rcyAdapter = new RecyclerAdapter(listtems, HomeActivity.this);
                rcyAdapter.notifyDataSetChanged();
                recyclerView.setAdapter(rcyAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        DatabaseReference cartref= FirebaseDatabase.getInstance().getReference("Cart List");
        cartref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.child("User View").hasChild(Prevalent.currentOnlineUser.getEmail().replace(".", ""))){
                    for(DataSnapshot ds : snapshot.child("User View")
                            .child(Prevalent.currentOnlineUser.getEmail().replace(".", "")).child("products").getChildren()){
                        CartItems cartItems = ds.getValue(CartItems.class);
                        lstcart.add(cartItems);

                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        if(drawerlay.isDrawerOpen(GravityCompat.START)){
            drawerlay.closeDrawer(GravityCompat.START);
        }else {
            this.moveTaskToBack(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu1, menu);
        menuItem = menu.findItem(R.id.toolbarcart);
        DatabaseReference cartref= FirebaseDatabase.getInstance().getReference("Cart List");
        cartref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.child("User View").hasChild(Prevalent.currentOnlineUser.getEmail().replace(".", ""))){
                    lstcart.clear();
                    for(DataSnapshot ds : snapshot.child("User View")
                            .child(Prevalent.currentOnlineUser.getEmail().replace(".", "")).child("products").getChildren()){
                        CartItems cartItems = ds.getValue(CartItems.class);
                        lstcart.add(cartItems);
                    }
                    cart_amount = lstcart.size();
                    if(cart_amount == 0){
                        menuItem.setActionView(null);
                    }else{
                        menuItem.setActionView(R.layout.badge_layout);
                        View view = menuItem.getActionView();
                        badge_counter =view.findViewById(R.id.badge_counter);
                        badge_counter.setText(String.valueOf(cart_amount));
                        view.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                startActivity(new Intent(HomeActivity.this, CartActivity.class));
                            }
                        });
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == (R.id.toolbarsearch)) {
            startActivity(new Intent(HomeActivity.this, SearchActivity.class));
        }
        if (id == (R.id.toolbarcart)) {
            startActivity(new Intent(HomeActivity.this, CartActivity.class));
        }
        return true;
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        if (id == R.id.drawersearch){
            startActivity(new Intent(HomeActivity.this, SearchActivity.class));
        }
        if (id == R.id.drawerlogout){
            Paper.book().destroy();
            startActivity(new Intent(HomeActivity.this, MainActivity2.class));
            finish();
        }
        if(id==R.id.drawerprofile){
            startActivity(new Intent(HomeActivity.this, Profile_Page_Activity.class));
        }

        drawerlay.closeDrawer(GravityCompat.START);
        return true;
    }
}