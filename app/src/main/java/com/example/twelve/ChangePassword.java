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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ChangePassword extends AppCompatActivity {
    private EditText cppass, cpcpass;
    private Button cpchange;
    private TextView cppbaaf;
    private DatabaseReference eref;
    private ProgressBar proogressbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        cppass = findViewById(R.id.cppass);
        cpcpass = findViewById(R.id.cpcpass);
        cpchange = findViewById(R.id.cpchange);
        cppbaaf = findViewById(R.id.cppbaaf);
        proogressbar = findViewById(R.id.proogressbar);

        cpchange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate();
            }
        });
    }

    private void validate() {
        String password = cppass.getText().toString();
        String conpaswword = cpcpass.getText().toString();

        if (TextUtils.isEmpty(password) || TextUtils.isEmpty(conpaswword)) {
            cppbaaf.setVisibility(View.VISIBLE);
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    cppbaaf.setVisibility(View.INVISIBLE);
                }
            }, 3000);

        } else if (password.length() < 6) {
            Toast.makeText(this, "Password must be up to six characters", Toast.LENGTH_SHORT).show();
        } else {
            passwordequality();
        }

    }


    private void passwordequality() {
        String password = cppass.getText().toString();
        String conpassword = cpcpass.getText().toString();
        if ((password.equals(conpassword))) {
           passwordchange();
        }else{
            Toast.makeText(this, "Passwords don't match", Toast.LENGTH_SHORT).show();
        }
    }

    private void passwordchange() {
        String password = cppass.getText().toString();
        String eemail;
        proogressbar.setVisibility(View.VISIBLE);
        eemail = getIntent().getStringExtra("e_mail");
        eref = FirebaseDatabase.getInstance().getReference("Users").child(eemail.replace(".", " ")).child("password");
        eref.setValue(password).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    proogressbar.setVisibility(View.INVISIBLE);
                    Toast.makeText(ChangePassword.this, "Successful", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(ChangePassword.this, Login_Activity.class));
                    finish();
                } else {
                    proogressbar.setVisibility(View.INVISIBLE);
                    Toast.makeText(ChangePassword.this, "Password was not updated", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


}



