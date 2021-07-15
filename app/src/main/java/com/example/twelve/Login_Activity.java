package com.example.twelve;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.twelve.Model.Users;
import com.example.twelve.Prevalent.Prevalent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class Login_Activity extends AppCompatActivity {
    private TextView logpagesignupbtn, adminlog, logotext, fptext;
    private EditText loginemail, loginpassword;
    private CheckBox rememberme;
    private Button loginpagebtn;
    private ProgressBar progressbar;
    private String dbName = "Users";
    private FirebaseAuth fAuth;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_);

        logpagesignupbtn = (TextView) findViewById(R.id.su);
        fptext = (TextView) findViewById(R.id.fptext);
        adminlog = (TextView) findViewById(R.id.admlog);
        loginemail = (EditText) findViewById(R.id.loginemail);
        loginpassword = (EditText) findViewById(R.id.loginepassword);
        rememberme = (CheckBox) findViewById(R.id.logincheck);
        loginpagebtn = (Button) findViewById(R.id.loginbtn);
        logotext = (TextView) findViewById(R.id.text_logo);
        progressbar = (ProgressBar) findViewById(R.id.pprrogressbar);

        Paper.init(this);

        fptext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login_Activity.this, ForgotPassword.class);
                startActivity(intent);
            }
        });

        logotext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adminlog.setVisibility(View.VISIBLE);
            }
        });

        adminlog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginpagebtn.setText("Login Admin");
                dbName = "Admins";
                adminlog.setVisibility(View.INVISIBLE);
            }
        });

        logpagesignupbtn.setEnabled(true);

        logpagesignupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login_Activity.this, RegisterActivity.class);
                startActivity(intent);
                Login_Activity.this.finish();
            }
        });

        loginpagebtn.setOnClickListener(new View.OnClickListener() {


            public void onClick(View view) {
                loginAccount();
            }
        });

    }

    private void loginAccount() {
        loginpagebtn.setEnabled(false);
        String email = loginemail.getText().toString();
        String password = loginpassword.getText().toString();
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT).show();
            loginpagebtn.setEnabled(true);

        } else {
            AllowAccess(email, password);
        }
    }

    private void AllowAccess(final String email, final String password) {
        if(rememberme.isChecked()){
            Paper.book().write(Prevalent.useremail, email);
            Paper.book().write(Prevalent.userpassword, password);
        }

        final DatabaseReference cref;
        logpagesignupbtn.setEnabled(false);
        progressbar.setVisibility(View.VISIBLE);
        cref = FirebaseDatabase.getInstance().getReference(dbName);
        cref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String email = loginemail.getText().toString();
                String password = loginpassword.getText().toString();


                if (snapshot.hasChild(email.replace(".", " "))) {
                    Users userdata = snapshot.child(email.replace(".", " ")).getValue(Users.class);
                    if (snapshot.child(email.replace(".", " ")).child("password").getValue().toString().equals(password)) {
                        if (dbName.equals("Admins")){
                            Toast.makeText(Login_Activity.this, "Successful", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(Login_Activity.this, AdminActivity.class));
                            finish();
                        }else{
                        progressbar.setVisibility(View.INVISIBLE);
                        Toast.makeText(Login_Activity.this, "Successful", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Login_Activity.this, HomeActivity2.class));
                        Prevalent.currentOnlineUser = userdata;
                        Paper.book().write("PrevalentUser", userdata);
                        finish();
                        }
                    } else {
                        progressbar.setVisibility(View.INVISIBLE);
                        loginpagebtn.setEnabled(true);
                        Toast.makeText(Login_Activity.this, "Incorrect Password, Try again", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    progressbar.setVisibility(View.INVISIBLE);
                    loginpagebtn.setEnabled(true);
                    Toast.makeText(Login_Activity.this, "User does not exist", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    @Override
    public void onBackPressed() {
        finish();
    }

}
