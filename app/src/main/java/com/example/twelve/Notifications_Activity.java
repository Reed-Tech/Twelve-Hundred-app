package com.example.twelve;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.twelve.Adapters.not_Adapter;
import com.example.twelve.Model.Notifications;
import com.example.twelve.Prevalent.Prevalent;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Notifications_Activity extends AppCompatActivity {
    private RecyclerView not_rv;
    private not_Adapter adp;
    private BottomNavigationView not_btmnav_view;
    private TextView ychn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications_);

        this.getSupportActionBar().setTitle("Notifications");
        this.getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_arrow);
        this.getSupportActionBar().setDisplayShowHomeEnabled(true);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        not_rv = findViewById(R.id.not_rv);
        not_btmnav_view = findViewById(R.id.not_btmnavbar);
        ychn = findViewById(R.id.ychn_text);

        DatabaseReference notref = FirebaseDatabase.getInstance().getReference("Notifications");
        String path = Prevalent.currentOnlineUser.getEmail().replace(".", "");

        notref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child(Prevalent.currentOnlineUser.getEmail().replace(".", "")).exists()) {
                    List<Notifications> notlist = new ArrayList<>();
                    notlist.clear();
                    for (DataSnapshot dsd : snapshot.child(Prevalent.currentOnlineUser.getEmail().replace(".", "")).getChildren()) {
                        Notifications not = dsd.getValue(Notifications.class);
                        notlist.add(not);
                    }

                    if(notlist.size()==0){
                        ychn.setVisibility(View.VISIBLE);
                    }else{
                        LinearLayoutManager lim = new LinearLayoutManager(Notifications_Activity.this);
                        not_rv.setLayoutManager(lim);
                        not_rv.getLayoutManager().setMeasurementCacheEnabled(false);
                        adp = new not_Adapter(Notifications_Activity.this, notlist);
                        adp.notifyDataSetChanged();
                        not_rv.setAdapter(adp);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }
}