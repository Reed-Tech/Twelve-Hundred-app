package com.example.twelve;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.text.TextUtils;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {
    private EditText regusername, regemail, regphone, regpassword, regfullname;
    private Button submit;
    private TextView regpagelogbtn, pfal;
    private ProgressBar progressbar;
    private FirebaseAuth fAuth;
    private DatabaseReference rootref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        fAuth = FirebaseAuth.getInstance();

        regfullname = findViewById(R.id.fullname);
        regusername = (EditText) findViewById(R.id.username);
        regemail = (EditText) findViewById(R.id.email);
        regphone = (EditText) findViewById(R.id.phone);
        regpassword = (EditText) findViewById(R.id.pwsd);
        submit = (Button) findViewById(R.id.submit_btn);
        regpagelogbtn = (TextView) findViewById(R.id.siv);
        pfal = (TextView) findViewById(R.id.pfal);
        progressbar = (ProgressBar) findViewById(R.id.progressbar);


        regpagelogbtn.setEnabled(true);

        regpagelogbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, Login_Activity.class);
                startActivity(intent);
                RegisterActivity.this.finish();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createAccount();
            }
        });

    }

    private void createAccount() {
        final String username = regusername.getText().toString();
        String email = regemail.getText().toString();
        String phone = regphone.getText().toString();
        String password = regpassword.getText().toString();
        String regBname = regfullname.getText().toString();

        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(email) || TextUtils.isEmpty(phone)
                || TextUtils.isEmpty(password) || TextUtils.isEmpty(regBname)) {
            pfal.setText("Please fill out all fields ");
            pfal.setVisibility(View.VISIBLE);
            Handler handler= new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    pfal.setVisibility(View.INVISIBLE);
                }
            }, 2000);
        }else {
            if (password.length()<6) {
                Toast.makeText(RegisterActivity.this, "Password must be up to six characters", Toast.LENGTH_SHORT).show();
            } else {
                Validatecredentials();

            }
        }
    }

    private void Validatecredentials() {
        final DatabaseReference cref;
        progressbar.setVisibility(View.VISIBLE);
        cref = FirebaseDatabase.getInstance().getReference();
        cref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                final String username = regusername.getText().toString();
                String email = regemail.getText().toString();
                String phone = regphone.getText().toString();
                String password = regpassword.getText().toString();
                String regBname = regfullname.getText().toString();

                if (snapshot.child("Users").child(email.replace(".", " ")).equals(email)){
                    progressbar.setVisibility(View.INVISIBLE);
                    Toast.makeText(RegisterActivity.this, "Email already exists", Toast.LENGTH_SHORT).show();
                }else{
                    if (snapshot.child("Usernames").hasChild(username)){
                        progressbar.setVisibility(View.INVISIBLE);
                        Toast.makeText(RegisterActivity.this, "Username is taken", Toast.LENGTH_SHORT).show();
                    }else {
                        if (snapshot.child("Phones").hasChild(phone)){
                            progressbar.setVisibility(View.INVISIBLE);
                            Toast.makeText(RegisterActivity.this, "This Phone number has already been registered", Toast.LENGTH_SHORT).show();
                        }else{
                            validateAccount(username, email, phone, password, regBname);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
    }

    private void validateAccount(final String username, final String email, final String phone, final String password, final String regBname) {

                fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        regpagelogbtn.setEnabled(false);
                        submit.setEnabled(false);
                        progressbar.setVisibility(View.VISIBLE);
                        String userID = fAuth.getCurrentUser().getUid().toString();
                        if(task.isSuccessful()){
                            rootref = FirebaseDatabase.getInstance().getReference();
                            HashMap<String, Object> userdata = new HashMap<>();
                            userdata.put("email", email);
                            userdata.put("Userid", userID);
                            userdata.put("username", username);
                            userdata.put("phone", phone);
                            userdata.put("password", password);
                            userdata.put("profile_image", "default");
                            userdata.put("address", "default");
                            userdata.put("delieveryphone", phone);
                            userdata.put("name", regBname);
                            rootref.child("Users").child(email.replace(".", " ")).setValue(userdata).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        HashMap<String, Object> usernames = new HashMap<>();
                                        usernames.put(username, username);
                                        HashMap<String, Object> phones = new HashMap<>();
                                        phones.put(phone, phone);
                                        rootref.child("Usernames").updateChildren(usernames);
                                        rootref.child("Phones").updateChildren(phones);
                                        Toast.makeText(RegisterActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                                        progressbar.setVisibility(View.INVISIBLE);
                                        Intent intent = new Intent(RegisterActivity.this, Login_Activity.class);
                                        startActivity(intent);
                                        finish();
                                    }else {
                                        progressbar.setVisibility(View.INVISIBLE);
                                        regpagelogbtn.setEnabled(true);
                                        submit.setEnabled(true);
                                        Toast.makeText(RegisterActivity.this, "Registration Unsuccessful", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }else{
                            Toast.makeText(RegisterActivity.this, "Authentication unsuccessful", Toast.LENGTH_SHORT).show();
                            regpagelogbtn.setEnabled(true);
                            submit.setEnabled(true);
                            progressbar.setVisibility(View.INVISIBLE);
                        }
                    }
                });


    }
    @Override
    public void onBackPressed() {
        finish();
    }
}







