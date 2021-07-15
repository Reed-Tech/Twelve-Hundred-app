package com.example.twelve;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.twelve.fragments.Allitem_Frag;
import com.example.twelve.fragments.HomeFrag;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import io.paperdb.Paper;

public class Store_page extends AppCompatActivity {
    private TabLayout tab_layout1;
    private CircleImageView logo;
    private ViewPager fragvp;
    ViewPagerAdapter2 vpadapter;
    private Toolbar toolbar;
    private CollapsingToolbarLayout ctb;
    private String sname, smail, gender, category, pid;
    private TextView sname3, sloc, sdesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_page);

        tab_layout1 = findViewById(R.id.tab_layout1);
        sname3 = findViewById(R.id.sttore_namee);
        sdesc = findViewById(R.id.sttore_ddesc);
        sloc = findViewById(R.id.sttore_addresss);
        logo = findViewById(R.id.slogo);
        ctb = findViewById(R.id.ctb);
        sname = getIntent().getStringExtra("sname");
        gender = getIntent().getStringExtra("gender");
        category = getIntent().getStringExtra("category");
        pid = getIntent().getStringExtra("pid");
        toolbar = findViewById(R.id.sp_tb);
        fragvp = findViewById(R.id.view_pager1);
        vpadapter = new ViewPagerAdapter2(getSupportFragmentManager());

        ctb.setTitleEnabled(false);

        DatabaseReference refn= FirebaseDatabase.getInstance().getReference("Stores");
        refn.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren()){
                    if(sname.equals(ds.child("storename").getValue().toString())){
                       smail = ds.child("storemail").getValue().toString();
                       sloc.setText(smail);
                    }
                }

                String smail2 = smail.replace(".", "");
                sname3.setText(sname);
                if(!TextUtils.isEmpty(snapshot.child(smail2).child("Business Information").child("Business State").getValue().toString())){
                    sloc.setText(snapshot.child(smail2).child("Business Information").child("Business State").getValue().toString());
                }

                if(!TextUtils.isEmpty(snapshot.child(smail2).child("Business Information").child("Business Description").getValue().toString())){
                    sdesc.setText(snapshot.child(smail2).child("Business Information").child("Business Description").getValue().toString());
                }

                if( !TextUtils.isEmpty(snapshot.child(smail2).child("Business Information").child("Business Logo").getValue().toString())){
                    Picasso.get().load(snapshot.child(smail2).child("Business Information").child("Business Logo").getValue().toString()).into(logo);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_arrow);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        vpadapter.addfrag(new HomeFrag(), "Home");
        vpadapter.addfrag(new Allitem_Frag(), "All Items");

        fragvp.setAdapter(vpadapter);
        tab_layout1.setupWithViewPager(fragvp);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();

        }
        return true;
    }



}