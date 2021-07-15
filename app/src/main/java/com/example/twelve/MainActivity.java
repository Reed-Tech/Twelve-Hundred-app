package com.example.twelve;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.twelve.Model.Users;
import com.example.twelve.Prevalent.Prevalent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;


public class MainActivity extends AppCompatActivity {
    private View decorView;
    Animation topAnim, btmAnim;
    TextView big12, word12, satext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Paper.init(this);

        final String useremail = Paper.book().read(Prevalent.useremail);
        final String userpassword = Paper.book().read(Prevalent.userpassword);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(useremail!=null && userpassword!=null){
                    if(!TextUtils.isEmpty(useremail) && !TextUtils.isEmpty(userpassword)){
                        Allowaccess(useremail, userpassword);
                    }else{
                        startActivity(new Intent(MainActivity.this, MainActivity2.class));
                        finish();
                    }
                }else{
                    startActivity(new Intent(MainActivity.this, MainActivity2.class));
                    finish();
                }
            }
        }, 7000);






        decorView = getWindow().getDecorView();
        decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                if (visibility == 0){
                    decorView.setSystemUiVisibility(hidebars());}
            }
        });

    }

    private void Allowaccess(final String useremail, final String userpassword) {
        final DatabaseReference cref;
        cref = FirebaseDatabase.getInstance().getReference("Users");
        cref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String email = useremail;
                String password = userpassword;

                if (snapshot.hasChild(email.replace(".", " "))) {
                    Users userdata = snapshot.child(email.replace(".", " ")).getValue(Users.class);
                    if (snapshot.child(email.replace(".", " ")).child("password").getValue().toString().equals(password)) {
                        Toast.makeText(MainActivity.this, "Already Logged in", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(MainActivity.this, HomeActivity2.class));
                        Prevalent.currentOnlineUser = userdata;
                        finish();
                    } else {
                        Toast.makeText(MainActivity.this, "Your password might have changed since your last login.", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(MainActivity.this, Login_Activity.class));
                        finish();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "User does not exist", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MainActivity.this, Login_Activity.class));
                    finish();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            decorView.setSystemUiVisibility(hidebars());
        }
    }

    public int hidebars() {
        return View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
    }
}
