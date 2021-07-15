package com.example.twelve;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.twelve.Adapters.flash_rva;
import com.example.twelve.Model.HomeProducts;
import com.example.twelve.Model.Promos;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import cn.iwgang.countdownview.CountdownView;

public class FlashDealActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private CountdownView countdownView;
    private List<Promos> listtems;
    flash_rva rcyAdapter;
    RecyclerView recyclerView;
    private TextView endsin, countdown_text;
    private boolean timerRunning;
    private CountDownTimer countDownTimer;
    GridLayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash_deal);

        toolbar = findViewById(R.id.flash_tb);
        listtems = new ArrayList<>();
        countdownView = findViewById(R.id.cndv);
        recyclerView = findViewById(R.id.flash_rv);
        countdown_text = findViewById(R.id.countdown_text);
        endsin = findViewById(R.id.ends_in);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Flash Deals");
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_arrow);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        SharedPreferences sharedPref = FlashDealActivity.this.getPreferences(Context.MODE_PRIVATE);
        String count = sharedPref.getString("sw_string", "26-11-2020 00:00:00");
        String min = sdf.format(calendar.getTime());



        Date date;
        Date date2;
        Date date3;
        try {
            date = sdf.parse(count);
            date2 = sdf.parse(min);
            long count2 = date.getTime();
            final long min2 = date2.getTime();
            if(min2>count2){
               Calendar cal = Calendar.getInstance();
               cal.setTime(date);
               cal.add(Calendar.DAY_OF_MONTH, 7);
               String minn = sdf.format(cal.getTime());
               SharedPreferences.Editor editor = sharedPref.edit();
               editor.putString("sw_string", minn);
               editor.apply();
               date3 = sdf.parse(minn);
               count2 = date3.getTime();
            }

            final long fin = count2 - min2;
            final long finalCount = count2;
            countDownTimer = new CountDownTimer(fin, 1000) {
                long fin = finalCount - min2;
                @Override
                public void onTick(long millisUntilFinished) {
                    fin = millisUntilFinished;

                    int day = (int) TimeUnit.MILLISECONDS.toDays(fin);
                    int hour = (int) (TimeUnit.MILLISECONDS.toHours(fin) - TimeUnit.DAYS.toHours(TimeUnit.MILLISECONDS.toDays(fin)));
                    int minute = (int) (TimeUnit.MILLISECONDS.toMinutes(fin) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(fin)));
                    int sec = (int) (TimeUnit.MILLISECONDS.toSeconds(fin) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(fin)));

                    String tlf = String.format(Locale.getDefault(), "%02d:%02d:%02d:%02d",day, hour, minute, sec);
                    countdown_text.setText(tlf);
                }

                @Override
                public void onFinish() {

                }
            }.start();
            timerRunning = true;
            countdownView.start(fin);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setFocusable(false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.getLayoutManager().setMeasurementCacheEnabled(false);

        DatabaseReference pref = FirebaseDatabase.getInstance().getReference("HomeProducts");
        pref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Promos Hproducts = dataSnapshot.getValue(Promos.class);
                    listtems.add(Hproducts);
                }
                Collections.reverse(listtems);
                rcyAdapter = new flash_rva( FlashDealActivity.this, listtems);
                rcyAdapter.notifyDataSetChanged();
                recyclerView.setAdapter(rcyAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id==android.R.id.home) {
            finish();
        }
        return true;
    }
}