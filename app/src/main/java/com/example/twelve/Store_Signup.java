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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class Store_Signup extends AppCompatActivity {
    private EditText storename, storemail, storephone, storepassword;
    private TextView Store_login;
    private Button submit_Btn;
    private ProgressBar bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store__signup);

        storename = findViewById(R.id.storename);
        Store_login = findViewById(R.id.Storelogin_text);
        storemail = findViewById(R.id.storeemail);
        storephone = findViewById(R.id.storephone);
        storepassword = findViewById(R.id.storepassword);
        Store_login = findViewById(R.id.Storelogin_text);
        submit_Btn = findViewById(R.id.storesubmit_btn);
        bar = findViewById(R.id.Ssignup_progressbar);


        Store_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Store_Signup.this, Store_Login.class));
                finish();
            }
        });

        submit_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String storenametext = storename.getText().toString();
                final String storemailtext = storemail.getText().toString();
                final String storephonetext = storephone.getText().toString();
                final String storepasswordtext = storepassword.getText().toString();
                bar.setVisibility(View.VISIBLE);
                if(TextUtils.isEmpty(storenametext) || TextUtils.isEmpty(storephonetext) || TextUtils.isEmpty(storemailtext) || TextUtils.isEmpty(storepasswordtext)){
                    Toast.makeText(Store_Signup.this, "Please fill out all fields", Toast.LENGTH_SHORT).show();
                    bar.setVisibility(View.INVISIBLE);
                }else{
                    saveStore(storenametext, storemailtext, storephonetext, storepasswordtext);
                }
            }
        });
    }

    private void saveStore(final String storenametext, final String storemailtext, final String storephonetext, final String storepasswordtext) {
        DatabaseReference storeref = FirebaseDatabase.getInstance().getReference("Stores");
        storeref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.child(storemailtext.replace(".", "")).exists()){
                    Toast.makeText(Store_Signup.this, "Store email already exist", Toast.LENGTH_SHORT).show();
                    bar.setVisibility(View.INVISIBLE);
                }else{
                    DatabaseReference stores = FirebaseDatabase.getInstance().getReference("Stores");
                    HashMap<String, Object> storesave = new HashMap<>();
                    storesave.put("storename", storenametext);
                    storesave.put("storemail", storemailtext);
                    storesave.put("storephone",storephonetext);
                    storesave.put("storepassword", storepasswordtext);
                    storesave.put("Current Balance", "NGN 0");

                    stores.child(storemailtext.replace(".", "")).setValue(storesave).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(Store_Signup.this, "Registration successful", Toast.LENGTH_SHORT).show();
                                LoginStore(storemailtext, storepasswordtext);
                            }else{
                                bar.setVisibility(View.INVISIBLE);
                                Toast.makeText(Store_Signup.this, "Store was not created, Please try again", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void LoginStore(final String storemailtext, final String storepasswordtext) {
        if(!TextUtils.isEmpty(storemailtext) && !TextUtils.isEmpty(storepasswordtext)){

            DatabaseReference storeq = FirebaseDatabase.getInstance().getReference("Stores");
            storeq.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.child(storemailtext.replace(".", "")).exists()){
                        Stores stores = snapshot.child(storemailtext.replace(".", "")).getValue(Stores.class);
                        if(stores.getStorepassword().equals(storepasswordtext)) {
                                Prevalent.currentOnlineStore = stores;
                                bar.setVisibility(View.INVISIBLE);
                                startActivity(new Intent(Store_Signup.this, Store_Dashboard.class));
                                finish();
                        }else{
                            bar.setVisibility(View.INVISIBLE);
                            Toast.makeText(Store_Signup.this, "Incorrect Password", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        bar.setVisibility(View.INVISIBLE);
                        Toast.makeText(Store_Signup.this, "E-mail does not exist", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }
    }
}