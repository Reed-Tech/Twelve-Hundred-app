package com.example.twelve;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ForgotPassword extends AppCompatActivity {
    private EditText fpemail, fpusername;
    private Button pfsubmit;
    private TextView pbaf;
    private ProgressBar progresssbar;
    private DatabaseReference bref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        fpusername = findViewById(R.id.fpusername);
        fpemail = findViewById(R.id.fpemail);
        pfsubmit = findViewById(R.id.fpsubmit);
        pbaf = findViewById(R.id.pbaaf);
        progresssbar = findViewById(R.id.progresssbar);

       pfsubmit.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               validate();
           }
       });
    }

    private void validate() {
        String email = fpemail.getText().toString();
        String username = fpusername.getText().toString();
        if(TextUtils.isEmpty(email) || TextUtils.isEmpty(username)){
            pbaf.setVisibility(View.VISIBLE);
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    pbaf.setVisibility(View.INVISIBLE);
                }
            }, 3000);
        }else{
            passwordactivity(email, username);
        }
    }

    private void passwordactivity(final String email, final String username) {
        progresssbar.setVisibility(View.VISIBLE);
        bref = FirebaseDatabase.getInstance().getReference("Users");
        bref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.hasChild(email.replace(".", " "))){
                    if(snapshot.child(email.replace(".", " ")).child("username").getValue().toString().equals(username)){
                        progresssbar.setVisibility(View.INVISIBLE);
                        Intent intent = new Intent(ForgotPassword.this, ChangePassword.class);
                        intent.putExtra("e_mail", email);
                        startActivity(intent);

                    }else{
                        progresssbar.setVisibility(View.INVISIBLE);
                        Toast.makeText(ForgotPassword.this, "Invalid username", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    progresssbar.setVisibility(View.INVISIBLE);
                    Toast.makeText(ForgotPassword.this, "User account does not exist", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}