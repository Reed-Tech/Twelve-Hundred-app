package com.example.twelve;

import androidx.annotation.NonNull;

import com.example.twelve.Adapters.ReviewsAdapter;
import com.example.twelve.Adapters.SimpAdapter;
import com.example.twelve.Adapters.pimg_adapter;
import com.example.twelve.Adapters.ppadapter;
import com.example.twelve.Model.Reviews;
import com.example.twelve.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.twelve.Model.HomeProducts;
import com.example.twelve.Prevalent.Prevalent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import cn.iwgang.countdownview.CountdownView;
import io.paperdb.Paper;

public class Products_Page_Activity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private TextView product_page_price, product_page_pname, description_text, store_name, pdesc_text, pcat;
    private TextView storelnk, norev_text, lar_text, showall_text, ssc_text, cdv1, cdv2, promoprice;
    private ImageView sc_imgview;
    private Spinner color_sp, size_sp, design_sp;
    private LinearLayout sp_ll, gone_ll, ll_size, ll_quan, ll_des, ll_col;
    private ImageSlider imageSlider;
    private CountDownTimer cdt;
    private BottomNavigationView bottomNavigationView;
    private String productID = "", prevact, category, gender;
    private DatabaseReference databaseReference;
    private Button invs_editbtn;
    private Toolbar toolbar;
    private ImageView size_c;
    private RecyclerView pprv, rev_rv, simprv;
    List<Reviews> revlist;
    RecyclerView pimgrv;
    LinearLayoutManager lmmm;
    ppadapter ppa;
    private GenericTypeIndicator<List<String>> genericTypeIndicator;
    List<String> imglist;
    private String currentDate, currentTime, price_format, sname, smail, mode;
    private List<String> color_sp1, design, size;
    private ElegantNumberButton numberbutton;
    private ToggleButton tgbtn;
    private int starnum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products__page_);
        genericTypeIndicator = new GenericTypeIndicator<List<String>>() {
        };
        rev_rv = findViewById(R.id.rev_rv);
        lar_text = findViewById(R.id.lar_text);
        showall_text = findViewById(R.id.showall_text);
        norev_text = findViewById(R.id.norev_text);
        revlist = new ArrayList<>();
        pprv = findViewById(R.id.pprv1);
        simprv = findViewById(R.id.product_page_rv);
        pdesc_text = findViewById(R.id.pdesc_text);
        pcat = findViewById(R.id.productcat_pname);
        size_c = findViewById(R.id.size_c);
        product_page_price = findViewById(R.id.product_page_price);
        prevact = getIntent().getStringExtra("prev_activity");
        category = getIntent().getStringExtra("category");
        product_page_pname = findViewById(R.id.product_page_pname);
        description_text = findViewById(R.id.description_text);
        bottomNavigationView = findViewById(R.id.btm_navbar);
        productID = getIntent().getStringExtra("pid");
        mode = getIntent().getStringExtra("mode");
        store_name = findViewById(R.id.store_name);
        invs_editbtn = findViewById(R.id.invs_editbtn);
        pimgrv = findViewById(R.id.pimg_rv);
        toolbar = findViewById(R.id.product_page_toolbar);
        promoprice = findViewById(R.id.product_page_promo_price);


        sp_ll = findViewById(R.id.sp_ll);
        gone_ll = findViewById(R.id.gone_ll);
        ll_col = findViewById(R.id.ll_colour);
        ll_quan = findViewById(R.id.ll_quan);
        ll_des = findViewById(R.id.ll_des);
        numberbutton = findViewById(R.id.elegant_numbtn);
        gender = getIntent().getStringExtra("gender");
        storelnk = findViewById(R.id.visit_storelnk);
        color_sp = findViewById(R.id.color_sp);
        size_sp = findViewById(R.id.size_sp);
        design_sp = findViewById(R.id.design_sp);
        tgbtn = findViewById(R.id.tg_btn);
        ssc_text = findViewById(R.id.ssc_text);
        sc_imgview = findViewById(R.id.sc_imgview);
        cdv1 = findViewById(R.id.ppcd_text1);
        cdv2 = findViewById(R.id.ppcd_text2);
        imglist = null;
        Paper.init(this);


        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_arrow);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        SharedPreferences sharedPref = Products_Page_Activity.this.getPreferences(Context.MODE_PRIVATE);
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
            if (min2 > count2) {
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
            cdt = new CountDownTimer(fin, 1000) {
                long fin = finalCount - min2;

                @Override
                public void onTick(long millisUntilFinished) {
                    fin = millisUntilFinished;
                    int day = (int) TimeUnit.MILLISECONDS.toDays(fin);
                    int hour = (int) (TimeUnit.MILLISECONDS.toHours(fin) - TimeUnit.DAYS.toHours(TimeUnit.MILLISECONDS.toDays(fin)));
                    int minute = (int) (TimeUnit.MILLISECONDS.toMinutes(fin) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(fin)));
                    int sec = (int) (TimeUnit.MILLISECONDS.toSeconds(fin) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(fin)));

                    String tlf = String.format(Locale.getDefault(), "%02d:%02d:%02d:%02d", day, hour, minute, sec);

                    cdv1.setText(tlf);
                    cdv2.setText(tlf);
                }

                @Override
                public void onFinish() {

                }
            }.start();

        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (prevact != null) {
            invs_editbtn.setVisibility(View.VISIBLE);
            bottomNavigationView.setVisibility(View.INVISIBLE);
            sp_ll.setVisibility(View.GONE);
            ll_des.setVisibility(View.GONE);
            ll_quan.setVisibility(View.GONE);
            ll_col.setVisibility(View.GONE);
        }

        invs_editbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Products_Page_Activity.this, edit_activity.class);
                intent.putExtra("category", category);
                intent.putExtra("pid", productID);
                intent.putExtra("gender", gender);
                startActivity(intent);
            }
        });

        DatabaseReference dfer = FirebaseDatabase.getInstance().getReference("Products");
        dfer.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<HomeProducts> hpr2 = null;
                for (DataSnapshot dse : snapshot.child(gender).child(category).getChildren()) {
                    HomeProducts hpr = dse.getValue(HomeProducts.class);
                    hpr2 = new ArrayList<>();
                    hpr2.add(hpr);
                }
                RecyclerView.LayoutManager lr1 = new GridLayoutManager(Products_Page_Activity.this, 2);
                simprv.setFocusable(false);
                simprv.setLayoutManager(lr1);
                simprv.getLayoutManager().setMeasurementCacheEnabled(false);
                SimpAdapter srva = new SimpAdapter(Products_Page_Activity.this, hpr2);
                srva.notifyDataSetChanged();
                simprv.setAdapter(srva);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        ssc_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference dref = FirebaseDatabase.getInstance().getReference("Products");
                dref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (sc_imgview.getVisibility() == View.GONE) {
                            ssc_text.setText("Hide size chart");
                            if (snapshot.child(gender).child(category).child(productID).hasChild("Size Chart")
                                    && snapshot.child(gender).child(category).child(productID).child("Size Chart").getValue().toString() != "") {
                                String pic = snapshot.child(gender).child(category).child(productID).child("Size Chart").getValue().toString();
                                Picasso.get().load(pic).into(sc_imgview);
                                sc_imgview.setVisibility(View.VISIBLE);
                            } else {
                                ssc_text.setText("Show size chart");
                            }
                        } else {
                            sc_imgview.setVisibility(View.GONE);
                            ssc_text.setText("Show size chart");
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });


        storelnk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Paper.book().write("smail", smail);
                Paper.book().write("gender", gender);
                Paper.book().write("category", category);
                Paper.book().write("pid", productID);
                Intent intent = new Intent(Products_Page_Activity.this, Store_page.class);
                intent.putExtra("sname", sname);
                intent.putExtra("gender", gender);
                intent.putExtra("category", category);
                intent.putExtra("pid", productID);
                startActivity(intent);
            }
        });

        tgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tgbtn.isChecked()) {
                    gone_ll.setVisibility(View.VISIBLE);
                } else {
                    gone_ll.setVisibility(View.GONE);
                }
            }
        });


        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        lar_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dial = new Dialog(Products_Page_Activity.this);
                dial.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dial.setContentView(R.layout.rev_layout);
                dial.setCancelable(true);
                dial.show();

                final ImageView star1 = dial.findViewById(R.id.star_1);
                final ImageView star2 = dial.findViewById(R.id.star_2);
                final ImageView star3 = dial.findViewById(R.id.star_3);
                final ImageView star4 = dial.findViewById(R.id.star_4);
                final ImageView star5 = dial.findViewById(R.id.star_5);
                final TextView next = dial.findViewById(R.id.next_text);
                final TextView sas = dial.findViewById(R.id.sas_text);
                TextView close = dial.findViewById(R.id.close_man);
                final TextView numstars = dial.findViewById(R.id.numstars);

                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dial.dismiss();
                    }
                });


                star1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        starnum = 1;
                        sas.setVisibility(View.INVISIBLE);
                        numstars.setText(String.valueOf(starnum) + " star");
                        numstars.setVisibility(View.VISIBLE);
                        next.setVisibility(View.VISIBLE);
                        star1.setImageResource(R.drawable.ic_baseline_star_fill2_24);
                        star2.setImageResource(R.drawable.ic_baseline_star_border2_24);
                        star3.setImageResource(R.drawable.ic_baseline_star_border2_24);
                        star4.setImageResource(R.drawable.ic_baseline_star_border2_24);
                        star5.setImageResource(R.drawable.ic_baseline_star_border2_24);

                        next.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                final Dialog dial2 = new Dialog(Products_Page_Activity.this);
                                dial2.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                dial2.setContentView(R.layout.rev_layout2);
                                dial2.setCancelable(true);
                                dial2.show();

                                final EditText rev_et = dial2.findViewById(R.id.dia_revtext);
                                TextView postrev = dial2.findViewById(R.id.post_rev);
                                TextView close = dial2.findViewById(R.id.close_man);

                                close.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        dial2.dismiss();
                                    }
                                });

                                postrev.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        final String rev = rev_et.getText().toString();
                                        final DatabaseReference dref = FirebaseDatabase.getInstance().getReference("Products");
                                        DatabaseReference dref3 = FirebaseDatabase.getInstance().getReference("Stores");
                                        DatabaseReference dref4 = FirebaseDatabase.getInstance().getReference("HomeProducts");
                                        final HashMap<String, Object> busmap = new HashMap<>();
                                        DatabaseReference dref2 = FirebaseDatabase.getInstance().getReference("Users");
                                        dref2.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                String pimg = snapshot.child(Prevalent.currentOnlineUser.getEmail().
                                                        replace(".", " ")).child("profile_image").getValue().toString();

                                                if (!pimg.equals("") && pimg != null && !TextUtils.isEmpty(pimg)) {
                                                    busmap.put("pimg_url", pimg);
                                                } else {
                                                    busmap.put("pimg_url", "none");
                                                }

                                                busmap.put("username", Prevalent.currentOnlineUser.getUsername());
                                                busmap.put("rev_text", rev);
                                                busmap.put("userfullname", Prevalent.currentOnlineUser.getName());
                                                busmap.put("useremail", Prevalent.currentOnlineUser.getEmail());
                                                busmap.put("star", String.valueOf(starnum));

                                                dref.child(gender).child(category).child(productID).child("Reviews").
                                                        child(Prevalent.currentOnlineUser.getEmail().replace(".", ""))
                                                        .updateChildren(busmap).addOnCompleteListener(
                                                        new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if (task.isSuccessful()) {
                                                                    Toast.makeText(Products_Page_Activity.this, "Review posted successfully",
                                                                            Toast.LENGTH_SHORT).show();
                                                                    dial.dismiss();
                                                                    dial2.dismiss();
                                                                    Intent intent = new Intent(Products_Page_Activity.this, Products_Page_Activity.class);
                                                                    intent.putExtra("pid", productID);
                                                                    intent.putExtra("gender", gender);
                                                                    intent.putExtra("category", category);
                                                                    startActivity(intent);
                                                                    finish();
                                                                } else {
                                                                    Toast.makeText(Products_Page_Activity.this, "Review was not posted", Toast.LENGTH_SHORT).show();
                                                                }
                                                            }
                                                        }
                                                );

                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        });


                                    }
                                });
                            }
                        });
                    }
                });

                star2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        starnum = 2;
                        sas.setVisibility(View.INVISIBLE);
                        numstars.setText(starnum + " stars");
                        numstars.setVisibility(View.VISIBLE);
                        next.setVisibility(View.VISIBLE);
                        star1.setImageResource(R.drawable.ic_baseline_star_fill2_24);
                        star2.setImageResource(R.drawable.ic_baseline_star_fill2_24);
                        star3.setImageResource(R.drawable.ic_baseline_star_border2_24);
                        star4.setImageResource(R.drawable.ic_baseline_star_border2_24);
                        star5.setImageResource(R.drawable.ic_baseline_star_border2_24);

                        next.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                final Dialog dial2 = new Dialog(Products_Page_Activity.this);
                                dial2.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                dial2.setContentView(R.layout.rev_layout2);
                                dial2.setCancelable(true);
                                dial2.show();

                                final EditText rev_et = dial2.findViewById(R.id.dia_revtext);
                                TextView postrev = dial2.findViewById(R.id.post_rev);
                                TextView close = dial2.findViewById(R.id.close_man);

                                close.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        dial2.dismiss();
                                    }
                                });


                                postrev.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        final String rev = rev_et.getText().toString();
                                        final DatabaseReference dref = FirebaseDatabase.getInstance().getReference("Products");
                                        DatabaseReference dref3 = FirebaseDatabase.getInstance().getReference("Stores");
                                        DatabaseReference dref4 = FirebaseDatabase.getInstance().getReference("HomeProducts");

                                        final HashMap<String, Object> busmap = new HashMap<>();

                                        DatabaseReference dref2 = FirebaseDatabase.getInstance().getReference("Users");
                                        dref2.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                                final String rev = rev_et.getText().toString();
                                                String pimg = snapshot.child(Prevalent.currentOnlineUser.getEmail().replace(".", " "))
                                                        .child("profile_image").getValue().toString();

                                                busmap.put("pimg_url", pimg);
                                                busmap.put("username", Prevalent.currentOnlineUser.getUsername());
                                                busmap.put("rev_text", rev);
                                                busmap.put("userfullname", Prevalent.currentOnlineUser.getName());
                                                busmap.put("useremail", Prevalent.currentOnlineUser.getEmail());
                                                busmap.put("star", String.valueOf(starnum));

                                                dref.child(gender).child(category).child(productID).child("Reviews").
                                                        child(Prevalent.currentOnlineUser.getEmail().replace(".", "")).updateChildren(busmap).addOnCompleteListener(
                                                        new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if (task.isSuccessful()) {
                                                                    Toast.makeText(Products_Page_Activity.this, "Review posted successfully", Toast.LENGTH_SHORT).show();
                                                                    dial.dismiss();
                                                                    dial2.dismiss();
                                                                    Intent intent = new Intent(Products_Page_Activity.this, Products_Page_Activity.class);
                                                                    intent.putExtra("pid", productID);
                                                                    intent.putExtra("gender", gender);
                                                                    intent.putExtra("category", category);
                                                                    startActivity(intent);
                                                                    finish();
                                                                } else {
                                                                    Toast.makeText(Products_Page_Activity.this, "Review was not posted", Toast.LENGTH_SHORT).show();
                                                                }
                                                            }
                                                        }
                                                );
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        });


                                    }
                                });
                            }
                        });
                    }
                });
                star3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        starnum = 3;
                        sas.setVisibility(View.INVISIBLE);
                        numstars.setText(starnum + " stars");
                        numstars.setVisibility(View.VISIBLE);
                        next.setVisibility(View.VISIBLE);
                        star1.setImageResource(R.drawable.ic_baseline_star_fill2_24);
                        star2.setImageResource(R.drawable.ic_baseline_star_fill2_24);
                        star3.setImageResource(R.drawable.ic_baseline_star_fill2_24);
                        star4.setImageResource(R.drawable.ic_baseline_star_border2_24);
                        star5.setImageResource(R.drawable.ic_baseline_star_border2_24);

                        next.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                final Dialog dial2 = new Dialog(Products_Page_Activity.this);
                                dial2.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                dial2.setContentView(R.layout.rev_layout2);
                                dial2.setCancelable(true);
                                dial2.show();

                                final EditText rev_et = dial2.findViewById(R.id.dia_revtext);
                                TextView postrev = dial2.findViewById(R.id.post_rev);
                                TextView close = dial2.findViewById(R.id.close_man);

                                close.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        dial2.dismiss();
                                    }
                                });

                                postrev.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        final String rev = rev_et.getText().toString();
                                        final DatabaseReference dref = FirebaseDatabase.getInstance().getReference("Products");
                                        DatabaseReference dref3 = FirebaseDatabase.getInstance().getReference("Stores");
                                        DatabaseReference dref4 = FirebaseDatabase.getInstance().getReference("HomeProducts");
                                        final HashMap<String, Object> busmap = new HashMap<>();
                                        DatabaseReference dref2 = FirebaseDatabase.getInstance().getReference("Users");
                                        dref2.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                String pimg = snapshot.child(Prevalent.currentOnlineUser.getEmail().replace(".", " ")).child("profile_image").getValue().toString();
                                                busmap.put("pimg_url", pimg);
                                                busmap.put("username", Prevalent.currentOnlineUser.getUsername());
                                                busmap.put("rev_text", rev);
                                                busmap.put("userfullname", Prevalent.currentOnlineUser.getName());
                                                busmap.put("useremail", Prevalent.currentOnlineUser.getEmail());
                                                busmap.put("star", String.valueOf(starnum));

                                                dref.child(gender).child(category).child(productID).child("Reviews").
                                                        child(Prevalent.currentOnlineUser.getEmail().replace(".", "")).updateChildren(busmap).addOnCompleteListener(
                                                        new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if (task.isSuccessful()) {
                                                                    Toast.makeText(Products_Page_Activity.this, "Review posted successfully", Toast.LENGTH_SHORT).show();
                                                                    dial.dismiss();
                                                                    dial2.dismiss();
                                                                    Intent intent = new Intent(Products_Page_Activity.this, Products_Page_Activity.class);
                                                                    intent.putExtra("pid", productID);
                                                                    intent.putExtra("gender", gender);
                                                                    intent.putExtra("category", category);
                                                                    startActivity(intent);
                                                                    finish();
                                                                } else {
                                                                    Toast.makeText(Products_Page_Activity.this, "Review was not posted", Toast.LENGTH_SHORT).show();
                                                                }
                                                            }
                                                        }
                                                );
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        });

                                    }
                                });
                            }
                        });
                    }
                });
                star4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        starnum = 4;
                        sas.setVisibility(View.INVISIBLE);
                        numstars.setText(starnum + " stars");
                        numstars.setVisibility(View.VISIBLE);
                        next.setVisibility(View.VISIBLE);
                        star1.setImageResource(R.drawable.ic_baseline_star_fill2_24);
                        star2.setImageResource(R.drawable.ic_baseline_star_fill2_24);
                        star3.setImageResource(R.drawable.ic_baseline_star_fill2_24);
                        star4.setImageResource(R.drawable.ic_baseline_star_fill2_24);
                        star5.setImageResource(R.drawable.ic_baseline_star_border2_24);

                        next.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                final Dialog dial2 = new Dialog(Products_Page_Activity.this);
                                dial2.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                dial2.setContentView(R.layout.rev_layout2);
                                dial2.setCancelable(true);
                                dial2.show();

                                final EditText rev_et = dial2.findViewById(R.id.dia_revtext);
                                TextView postrev = dial2.findViewById(R.id.post_rev);
                                TextView close = dial2.findViewById(R.id.close_man);

                                close.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        dial2.dismiss();
                                    }
                                });


                                postrev.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        final String rev = rev_et.getText().toString();
                                        final DatabaseReference dref = FirebaseDatabase.getInstance().getReference("Products");
                                        DatabaseReference dref3 = FirebaseDatabase.getInstance().getReference("Stores");
                                        DatabaseReference dref4 = FirebaseDatabase.getInstance().getReference("HomeProducts");
                                        final HashMap<String, Object> busmap = new HashMap<>();
                                        DatabaseReference dref2 = FirebaseDatabase.getInstance().getReference("Users");
                                        dref2.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                String pimg = snapshot.child(Prevalent.currentOnlineUser.getEmail().replace(".", " ")).child("profile_image").getValue().toString();
                                                busmap.put("pimg_url", pimg);
                                                busmap.put("username", Prevalent.currentOnlineUser.getUsername());
                                                busmap.put("rev_text", rev);
                                                busmap.put("userfullname", Prevalent.currentOnlineUser.getName());
                                                busmap.put("useremail", Prevalent.currentOnlineUser.getEmail());
                                                busmap.put("star", String.valueOf(starnum));

                                                dref.child(gender).child(category).child(productID).child("Reviews").
                                                        child(Prevalent.currentOnlineUser.getEmail().replace(".", "")).updateChildren(busmap).addOnCompleteListener(
                                                        new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if (task.isSuccessful()) {
                                                                    Toast.makeText(Products_Page_Activity.this, "Review posted successfully", Toast.LENGTH_SHORT).show();
                                                                    dial.dismiss();
                                                                    dial2.dismiss();
                                                                    Intent intent = new Intent(Products_Page_Activity.this, Products_Page_Activity.class);
                                                                    intent.putExtra("pid", productID);
                                                                    intent.putExtra("gender", gender);
                                                                    intent.putExtra("category", category);
                                                                    startActivity(intent);
                                                                    finish();
                                                                } else {
                                                                    Toast.makeText(Products_Page_Activity.this, "Review was not posted", Toast.LENGTH_SHORT).show();
                                                                }
                                                            }
                                                        }
                                                );
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        });


                                    }
                                });
                            }
                        });
                    }
                });
                star5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        starnum = 5;
                        sas.setVisibility(View.INVISIBLE);
                        numstars.setText(starnum + " stars");
                        numstars.setVisibility(View.VISIBLE);
                        next.setVisibility(View.VISIBLE);
                        star1.setImageResource(R.drawable.ic_baseline_star_fill2_24);
                        star2.setImageResource(R.drawable.ic_baseline_star_fill2_24);
                        star3.setImageResource(R.drawable.ic_baseline_star_fill2_24);
                        star4.setImageResource(R.drawable.ic_baseline_star_fill2_24);
                        star5.setImageResource(R.drawable.ic_baseline_star_fill2_24);

                        next.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                final Dialog dial2 = new Dialog(Products_Page_Activity.this);
                                dial2.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                dial2.setContentView(R.layout.rev_layout2);
                                dial2.setCancelable(true);
                                dial2.show();

                                final EditText rev_et = dial2.findViewById(R.id.dia_revtext);
                                TextView postrev = dial2.findViewById(R.id.post_rev);
                                TextView close = dial2.findViewById(R.id.close_man);

                                close.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        dial2.dismiss();
                                    }
                                });

                                postrev.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        final String rev = rev_et.getText().toString();
                                        final DatabaseReference dref = FirebaseDatabase.getInstance().getReference("Products");
                                        DatabaseReference dref3 = FirebaseDatabase.getInstance().getReference("Stores");
                                        DatabaseReference dref4 = FirebaseDatabase.getInstance().getReference("HomeProducts");
                                        final HashMap<String, Object> busmap = new HashMap<>();
                                        DatabaseReference dref2 = FirebaseDatabase.getInstance().getReference("Users");
                                        dref2.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                String pimg = snapshot.child(Prevalent.currentOnlineUser.getEmail().replace(".", " ")).child("profile_image").getValue().toString();
                                                busmap.put("pimg_url", pimg);
                                                busmap.put("username", Prevalent.currentOnlineUser.getUsername());
                                                busmap.put("rev_text", rev);
                                                busmap.put("userfullname", Prevalent.currentOnlineUser.getName());
                                                busmap.put("useremail", Prevalent.currentOnlineUser.getEmail());
                                                busmap.put("star", String.valueOf(starnum));

                                                dref.child(gender).child(category).child(productID).child("Reviews").
                                                        child(Prevalent.currentOnlineUser.getEmail().replace(".", "")).updateChildren(busmap).addOnCompleteListener(
                                                        new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if (task.isSuccessful()) {
                                                                    Toast.makeText(Products_Page_Activity.this, "Review posted successfully", Toast.LENGTH_SHORT).show();
                                                                    dial.dismiss();
                                                                    dial2.dismiss();
                                                                    Intent intent = new Intent(Products_Page_Activity.this, Products_Page_Activity.class);
                                                                    intent.putExtra("pid", productID);
                                                                    intent.putExtra("gender", gender);
                                                                    intent.putExtra("category", category);
                                                                    startActivity(intent);
                                                                    finish();
                                                                } else {
                                                                    Toast.makeText(Products_Page_Activity.this, "Review was not posted", Toast.LENGTH_SHORT).show();
                                                                }
                                                            }
                                                        }
                                                );
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        });

                                    }
                                });
                            }
                        });
                    }
                });


            }
        });

        showall_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        databaseReference = FirebaseDatabase.getInstance().getReference("Products");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    HomeProducts homeProducts = snapshot.child(gender).child(category).child(productID).getValue(HomeProducts.class);
                    product_page_pname.setText(homeProducts.getPname());
                    pcat.setText(homeProducts.getCategory());

                    if (snapshot.child(gender).child(category).child(productID).child("Reviews").exists()
                            && snapshot.child(gender).child(category).child(productID).child("Reviews").hasChildren()) {
                        for (DataSnapshot dssa : snapshot.child(gender).child(category).child(productID).child("Reviews").getChildren()) {
                            Reviews rev = dssa.getValue(Reviews.class);
                            revlist.add(rev);
                        }
                    } else {
                        rev_rv.setVisibility(View.GONE);
                        norev_text.setVisibility(View.VISIBLE);
                    }

                    LinearLayoutManager llm2 = new LinearLayoutManager(Products_Page_Activity.this);
                    ReviewsAdapter revsa = new ReviewsAdapter(Products_Page_Activity.this, revlist);

                    rev_rv.setLayoutManager(llm2);
                    rev_rv.getLayoutManager().setMeasurementCacheEnabled(false);

                    revsa.notifyDataSetChanged();
                    rev_rv.setAdapter(revsa);

                    color_sp1 = snapshot.child(gender).child(category).child(productID).child("Colours").getValue(genericTypeIndicator);
                    ArrayAdapter<String> ar1 = new ArrayAdapter<>(Products_Page_Activity.this, R.layout.spinner_file, color_sp1);
                    ar1.setDropDownViewResource(R.layout.spinner_dropdown);
                    color_sp.setAdapter(ar1);

                    design = snapshot.child(gender).child(category).child(productID).child("Designs").getValue(genericTypeIndicator);
                    if (snapshot.child(gender).child(category).child(productID).child("Design Text").getValue().toString().equalsIgnoreCase("None")
                            || snapshot.child(gender).child(category).child(productID).child("Design Text").getValue().toString().equalsIgnoreCase("Nil")
                            || snapshot.child(gender).child(category).child(productID).child("Design Text").getValue().toString().equalsIgnoreCase("")
                            || snapshot.child(gender).child(category).child(productID).child("Design Text").getValue().toString().equalsIgnoreCase("As seen")) {
                        ll_des.setVisibility(View.GONE);
                    }
                    ArrayAdapter<String> ar2 = new ArrayAdapter<>(Products_Page_Activity.this, R.layout.spinner_file, design);
                    ar2.setDropDownViewResource(R.layout.spinner_dropdown);
                    design_sp.setAdapter(ar2);

                    size = snapshot.child(gender).child(category).child(productID).child("Sizes").getValue(genericTypeIndicator);
                    ArrayAdapter<String> ar3 = new ArrayAdapter<>(Products_Page_Activity.this, R.layout.spinner_file, size);
                    ar3.setDropDownViewResource(R.layout.spinner_dropdown);
                    size_sp.setAdapter(ar3);

                    product_page_price.setText(homeProducts.getPrice());
                    if (mode != null && mode.equalsIgnoreCase("promos")) {
                        if (snapshot.child(gender).child(category).child(productID).hasChild("Promo_price")
                                && snapshot.child(gender).child(category).child(productID).child("Promo_price").getValue().toString() != null
                                && !snapshot.child(gender).child(category).child(productID).child("Promo_price").getValue().toString().equalsIgnoreCase("")) {
                            String val = snapshot.child(gender).child(category).child(productID).child("Promo_price").getValue().toString();
                            product_page_price.setTextColor(getResources().getColor(R.color.colorred));
                            product_page_price.setPaintFlags(product_page_price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                            promoprice.setVisibility(View.VISIBLE);
                            if (pcat.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
                                ViewGroup.MarginLayoutParams P = (ViewGroup.MarginLayoutParams) pcat.getLayoutParams();
                                P.setMargins(50, -25, 0, 0);
                                pcat.requestLayout();
                            }
                            promoprice.setText(val);
                        }else{
                            product_page_price.setTextColor(getResources().getColor(R.color.colorred));
                            product_page_price.setPaintFlags(product_page_price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                            promoprice.setVisibility(View.VISIBLE);
                            if (pcat.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
                                ViewGroup.MarginLayoutParams P = (ViewGroup.MarginLayoutParams) pcat.getLayoutParams();
                                P.setMargins(15, -20, 0, 0);
                                pcat.requestLayout();
                            }
                            promoprice.setText("NGN 10,000");
                        }
                    }
                    description_text.setText(homeProducts.getDescription());
                    store_name.setText(snapshot.child(gender).child(category).child(productID).child("Store").getValue().toString());
                    sname = snapshot.child(gender).child(category).child(productID).child("Store").getValue().toString();

                    LinearLayoutManager llm1 = new LinearLayoutManager(Products_Page_Activity.this, LinearLayoutManager.HORIZONTAL, false);
                    pimgrv.setLayoutManager(llm1);
                    pimgrv.getLayoutManager().setMeasurementCacheEnabled(false);


                    DatabaseReference refn = FirebaseDatabase.getInstance().getReference("Stores");
                    refn.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot ds : snapshot.getChildren()) {
                                if (sname.equals(ds.child("storename").getValue().toString())) {
                                    smail = ds.child("storemail").getValue().toString();
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                    imglist = homeProducts.getImageurllist();
                    lmmm = new LinearLayoutManager(Products_Page_Activity.this);
                    pprv.setLayoutManager(lmmm);
                    pprv.getLayoutManager().setMeasurementCacheEnabled(false);
                    ppa = new ppadapter(Products_Page_Activity.this, imglist);
                    ppa.notifyDataSetChanged();
                    pprv.setAdapter(ppa);

                    Picasso.get().load(imglist.get(1)).into(size_c);

                    pimg_adapter pia = new pimg_adapter(Products_Page_Activity.this, imglist);
                    pia.notifyDataSetChanged();
                    pimgrv.setAdapter(pia);

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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.add_to_cart) {
            Toast.makeText(this, "Please wait", Toast.LENGTH_SHORT).show();
            addtocart();
        }
        if (id == R.id.buy_Now) {

            price_format = product_page_price.getText().toString().replaceAll("N", "");
            price_format = price_format.replace("G", "");
            price_format = price_format.replace(" ", "");
            price_format = price_format.replace(",", "");
            int subtotal = Integer.parseInt(price_format) * Integer.parseInt(numberbutton.getNumber());
            String subt = String.valueOf(subtotal);

            final HashMap<String, Object> cartmap = new HashMap<>();
            cartmap.put("pid", productID);
            cartmap.put("productname", product_page_pname.getText().toString());
            cartmap.put("price", product_page_price.getText().toString());
            cartmap.put("date", currentDate);
            cartmap.put("time", currentTime);
            cartmap.put("quantity", numberbutton.getNumber());
            cartmap.put("discount", "");
            cartmap.put("pricenum", price_format);
            cartmap.put("tprice", "NGN " + subt);
            cartmap.put("images", imglist);
            cartmap.put("colour", color_sp.getSelectedItem().toString());
            cartmap.put("size", size_sp.getSelectedItem().toString());
            if (design_sp.getVisibility() == View.VISIBLE) {
                cartmap.put("design", design_sp.getSelectedItem().toString());
            }

            Intent intent = new Intent(Products_Page_Activity.this, Checkout_Activity.class);
            intent.putExtra("subtotal", subt);
            startActivity(intent);
        }
        return true;
    }

    private void addtocart() {
        Calendar calender = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss a");

        currentDate = dateFormat.format(calender.getTime());
        currentTime = timeFormat.format(calender.getTime());
        String s2 = "NGN ,";

        price_format = product_page_price.getText().toString().replaceAll("N", "");
        price_format = price_format.replace("G", "");
        price_format = price_format.replace(" ", "");
        price_format = price_format.replace(",", "");

        long Totalprice = Integer.parseInt(numberbutton.getNumber()) * Integer.parseInt(price_format);
        String tprice = "NGN " + String.valueOf(Totalprice);


        final DatabaseReference cartref = FirebaseDatabase.getInstance().getReference().child("Cart List");

        final HashMap<String, Object> cartmap = new HashMap<>();
        cartmap.put("pid", productID);
        cartmap.put("productname", product_page_pname.getText().toString());
        cartmap.put("price", product_page_price.getText().toString());
        cartmap.put("date", currentDate);
        cartmap.put("time", currentTime);
        cartmap.put("quantity", numberbutton.getNumber());
        cartmap.put("discount", "");
        cartmap.put("pricenum", price_format);
        cartmap.put("tprice", tprice);
        cartmap.put("images", imglist);
        cartmap.put("colour", color_sp.getSelectedItem().toString());
        cartmap.put("size", size_sp.getSelectedItem().toString());
        if (design_sp.getVisibility() == View.VISIBLE) {
            cartmap.put("design", design_sp.getSelectedItem().toString());
        }


        cartref.child("Admin View").child(Prevalent.currentOnlineUser.getEmail().replace(".", "")).child("products")
                .child(productID).updateChildren(cartmap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    cartref.child("User View").child(Prevalent.currentOnlineUser.getEmail().replace(".", ""))
                            .child("products").child(productID).updateChildren(cartmap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(Products_Page_Activity.this, "Product has been added to cart", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(Products_Page_Activity.this, "Product was not added to cart", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
}