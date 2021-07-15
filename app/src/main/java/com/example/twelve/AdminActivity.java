package com.example.twelve;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class AdminActivity extends AppCompatActivity {
    private LinearLayout pending_orders, done_deal, manage_item, log_out;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        pending_orders = findViewById(R.id.llpending);
        done_deal = findViewById(R.id.llcomplete);
        manage_item = findViewById(R.id.lladditems);
        log_out = findViewById(R.id.lllogout);

        manage_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminActivity.this, AdminEditproductPanel.class));

            }
        });

        log_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminActivity.this, Login_Activity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    @Override
    public void onBackPressed() {
        this.moveTaskToBack(true);
    }
}