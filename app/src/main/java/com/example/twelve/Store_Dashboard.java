package com.example.twelve;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.twelve.Model.HomeProducts;
import com.example.twelve.Model.Stores;
import com.example.twelve.Prevalent.Prevalent;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

public class Store_Dashboard extends AppCompatActivity {
    private Toolbar toolbar;
    private CardView CO_cardview, PO_cardview, WD_cardview, RI_cardview, AI_cardview, SN_cardview;
    private TextView storename, current_bal, store_address, store_phone, store_mail, store_desc;
    private TextView CO_number, PO_number, WD_number, RI_number, AI_number, SN_number;
    private ImageView logo;
    private ImageView cbpic;
    private Button store_logoutbtn;
    private int male, female;
    private static final int gallerypick = 1;
    private Uri cbp_uri;
    private String downloadUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store__dashboard);


        CO_number = findViewById(R.id.CO_number);
        PO_number = findViewById(R.id.PO_number);
        WD_number = findViewById(R.id.wd_number);
        RI_number = findViewById(R.id.RI_number);
        AI_number = findViewById(R.id.AI_number);
        SN_number = findViewById(R.id.not_number);
        toolbar = findViewById(R.id.Sdash_toolbar);
        CO_cardview = findViewById(R.id.CO_cardview);
        PO_cardview = findViewById(R.id.PO_cardview);
        WD_cardview = findViewById(R.id.WD_cardview);
        RI_cardview = findViewById(R.id.RI_cardview);
        AI_cardview = findViewById(R.id.AI_cardview);
        SN_cardview = findViewById(R.id.SN_cardview);
        storename = findViewById(R.id.store_namee);
        current_bal = findViewById(R.id.current_bal);
        store_address = findViewById(R.id.store_addresss);
        store_phone = findViewById(R.id.store_phonee);
        store_mail = findViewById(R.id.store_mail);
        store_desc = findViewById(R.id.store_description);
        logo = findViewById(R.id.Sdash_image);
        store_logoutbtn = findViewById(R.id.store_logoutbtn);
        cbpic = findViewById(R.id.cbpic);
        Paper.init(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Store Dashboard");

        Stores stores = Paper.book().read("PrevalentStore");
        if(Prevalent.currentOnlineStore==null){
            Prevalent.currentOnlineStore = stores;
        }

        final String path = Prevalent.currentOnlineStore.getStoremail().replace(".", "");

        DatabaseReference numref = FirebaseDatabase.getInstance().getReference("Stores").child(path).child("Products");
        numref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<HomeProducts> hp = new ArrayList<>();
                List<HomeProducts> hr = new ArrayList<>();
               if(snapshot.child("Female").hasChildren()){
                   for(DataSnapshot ds : snapshot.child("Female").getChildren()){
                       for(DataSnapshot ds2 : ds.getChildren()){
                           HomeProducts p = ds2.getValue(HomeProducts.class);
                           hp.add(p);
                           female = hp.size();

                       }
                   }
               }

                if(snapshot.child("Male").hasChildren()){
                    for(DataSnapshot ds : snapshot.child("Male").getChildren()){
                        for(DataSnapshot ds2 : ds.getChildren()){
                            HomeProducts p = ds2.getValue(HomeProducts.class);
                            hr.add(p);
                            male = hr.size();
                        }
                    }
                }
                int tmf = female + male;
                AI_number.setText(String.valueOf(tmf));


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        storename.setText(Prevalent.currentOnlineStore.getStorename());
        store_mail.setText(Prevalent.currentOnlineStore.getStoremail());
        store_phone.setText(Prevalent.currentOnlineStore.getStorephone());

        AI_cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Store_Dashboard.this, All_Items01.class);
                startActivity(intent);

            }
        });

        store_logoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent intent = new Intent(Store_Dashboard.this, MainActivity2.class);
                    startActivity(intent);
                    finish();
            }
        });

        cbpic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenGallery();
            }
        });

        DatabaseReference store = FirebaseDatabase.getInstance().getReference("Stores");
        store.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.child(path).hasChild("Banner") && !snapshot.child(path).child("Banner").getValue().toString().equals("")){
                    String bnr = snapshot.child(path).child("Banner").getValue().toString();
                    Picasso.get().load(bnr).into(cbpic);
                }

                if(snapshot.child(Prevalent.currentOnlineStore.getStoremail().replace(".", "")).hasChild("Business Information")){
                    store_address.setText(snapshot.child(Prevalent.currentOnlineStore.getStoremail().replace(".", ""))
                            .child("Business Information").child("Business State").getValue().toString());

                    store_desc.setText(snapshot.child(Prevalent.currentOnlineStore.getStoremail().replace(".", ""))
                            .child("Business Information").child("Business Description").getValue().toString());

                    if(snapshot.child(Prevalent.currentOnlineStore.getStoremail().replace(".", "")).child("Business Information").
                            hasChild("Business Logo") && snapshot.child(Prevalent.currentOnlineStore.getStoremail().replace(".", "")).
                            child("Business Information").
                            child("Business Logo").getValue().toString()!=""){
                        String url =snapshot.child(Prevalent.currentOnlineStore.getStoremail().replace(".", "")).child("Business Information").
                                child("Business Logo").getValue().toString();
                        Picasso.get().load(url).into(logo);
                    }
                }

                if(snapshot.child(Prevalent.currentOnlineStore.getStoremail().replace(".", "")).hasChild("Current Balance")){
                current_bal.setText(snapshot.child(Prevalent.currentOnlineStore.getStoremail().
                        replace(".", "")).child("Current Balance").getValue().toString());
                }




            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void OpenGallery() {
        Intent galleryintent = new Intent();
        galleryintent.setAction(Intent.ACTION_GET_CONTENT);
        galleryintent.setType("image/*");
        galleryintent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        galleryintent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        startActivityForResult(Intent.createChooser(galleryintent, "Select pictures"), gallerypick);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==gallerypick && resultCode==RESULT_OK && data!= null){
            if(data.getData()!=null){
                cbp_uri = data.getData();
                cbpic.setImageURI(cbp_uri);
                StorageReference filepath = FirebaseStorage.getInstance().getReference("Banner pictures");
                final String smail = Prevalent.currentOnlineStore.getStoremail().replace(",", "");
                final StorageReference cbppath = filepath.child(smail).child("banner" + this.cbp_uri.getLastPathSegment() + ".jpg");
                final UploadTask upt = cbppath.putFile(cbp_uri);
                cbppath.putFile(cbp_uri).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                      String msg = e.toString();
                        Toast.makeText(Store_Dashboard.this, "Error: " + msg, Toast.LENGTH_SHORT).show();
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                       Task<Uri> urltask = upt.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                           @Override
                           public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                               if (!task.isSuccessful()) {

                                   throw task.getException();
                               }
                               downloadUrl = cbppath.getDownloadUrl().toString();
                               return cbppath.getDownloadUrl();
                           }
                       }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                           @Override
                           public void onComplete(@NonNull Task<Uri> task) {
                             if(task.isSuccessful()){
                                 downloadUrl = task.getResult().toString();
                                 DatabaseReference dbr= FirebaseDatabase.getInstance().getReference("Stores");
                                 dbr.child(smail.replace(".", "")).child("Banner").setValue(downloadUrl);
                             }else{
                                 Toast.makeText(Store_Dashboard.this, "Banner was not uploaded", Toast.LENGTH_SHORT).show();
                             }
                           }
                       });
                    }
                });
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.store_dashboard, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId(); 
        if(id==R.id.settingg_sd){
            startActivity(new Intent(Store_Dashboard.this, store_settings.class));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        this.moveTaskToBack(true);
    }
}