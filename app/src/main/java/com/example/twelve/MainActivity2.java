package com.example.twelve;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.twelve.Model.Users;
import com.example.twelve.Prevalent.Prevalent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class MainActivity2 extends AppCompatActivity {
    private Button login2, signup2;
    Animation btmAnim;
    private ProgressBar loadingbar;
    ProgressBar progressBar;
    private TextView number;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        btmAnim= AnimationUtils.loadAnimation(this, R.anim.bottom_animation);
        number = findViewById(R.id.number);

        login2 = findViewById(R.id.logbtn2);
        signup2 = findViewById(R.id.signupbtn2);
        progressBar = findViewById(R.id.prrogressbar);

        login2.setAnimation(btmAnim);
        signup2.setAnimation(btmAnim);
        login2.setVisibility(View.VISIBLE);
        signup2.setVisibility(View.VISIBLE);


        number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity2.this, Store_Login.class));

            }
        });

        signup2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity2.this, RegisterActivity.class));

            }
        });

        login2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity2.this, Login_Activity.class));

            }
        });
    }

    @Override
    public void onBackPressed() {
        this.moveTaskToBack(true);
    }
}