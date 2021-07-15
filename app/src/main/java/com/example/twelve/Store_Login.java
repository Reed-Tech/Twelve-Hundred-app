package com.example.twelve;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.twelve.Model.Stores;
import com.example.twelve.Prevalent.Prevalent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class Store_Login extends AppCompatActivity {
    private EditText storemail, storepassword;
    private Button submit_btn;
    private TextView store_signup;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store__login);

        storemail = findViewById(R.id.login_storeemail);
        storepassword = findViewById(R.id.login_storepassword);
        submit_btn = findViewById(R.id.login_btn);
        store_signup = findViewById(R.id.Storesignup_logintext);
        progressBar = findViewById(R.id.Login_progressbar);
        Paper.init(this);

        store_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent intent = new Intent(Store_Login.this, Store_Signup.class);
               startActivity(intent);
            }
        });

        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String storemailtext = storemail.getText().toString();
                final String storepasswordtext = storepassword.getText().toString();


                if(TextUtils.isEmpty(storemailtext) ||TextUtils.isEmpty(storepasswordtext)) {
                    Toast.makeText(Store_Login.this, "Please fill out all fields", Toast.LENGTH_SHORT).show();
                }else{
                    progressBar.setVisibility(View.VISIBLE);
                    DatabaseReference storeq = FirebaseDatabase.getInstance().getReference("Stores");
                    storeq.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.child(storemailtext.replace(".", "")).exists()) {
                                Stores stores = snapshot.child(storemailtext.replace(".", "")).getValue(Stores.class);
                                if (stores.getStorepassword().equals(storepasswordtext)) {
                                    Paper.book().write("PrevalentStore", stores);
                                    Prevalent.currentOnlineStore = stores;
                                    progressBar.setVisibility(View.INVISIBLE);
                                    startActivity(new Intent(Store_Login.this, Store_Dashboard.class));
                                    finish();
                                }else{
                                    progressBar.setVisibility(View.INVISIBLE);
                                    Toast.makeText(Store_Login.this, "Incorrect Password", Toast.LENGTH_SHORT).show();
                                }
                            }else{
                                progressBar.setVisibility(View.INVISIBLE);
                                Toast.makeText(Store_Login.this, "E-mail does not exist", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}