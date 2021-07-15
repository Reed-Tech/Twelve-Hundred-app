package com.example.twelve;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.stream.IntStream;

import de.hdodenhof.circleimageview.CircleImageView;


public class store_settings extends AppCompatActivity {
    private LinearLayout image_llayout;
    private CircleImageView logoimage;
    private EditText seller_firstname, seller_midname, seller_lastname, bus_name, bus_email, bus_phone;
    private EditText bus_address, bus_town, Description;
    private TextView idimage_name;
    private ProgressBar store_settingsProgressBar;
    private Spinner dob_day, dob_month, dob_year, exp_day, exp_month, exp_year;
    private Spinner Bus_type, ID_type, state;
    private String downloadUrl, idurl;
    private Button submit_btn, choose_photobtn;
    private String[] dateday;
    private String[] dateyear;
    private String[] expdateyear;
    private static final int gallerypick = 1;
    private Uri logouri, iduri;
    private int check1;
    private  String ste, path;
    private  String bustyp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_settings);
        image_llayout = findViewById(R.id.img_llayout);
        seller_firstname = findViewById(R.id.seller_firstname);
        seller_midname = findViewById(R.id.seller_midname);
        seller_lastname = findViewById(R.id.Seller_lastname);
        bus_name = findViewById(R.id.bus_name);
        bus_email = findViewById(R.id.bus_email);
        bus_phone = findViewById(R.id.bus_phone);
        bus_address = findViewById(R.id.bus_address);
        choose_photobtn = findViewById(R.id.choose_idphoto);
        submit_btn = findViewById(R.id.submit_btnn);
        store_settingsProgressBar = findViewById(R.id.store_setprgsbar);
        bus_town = findViewById(R.id.bus_town);
        Description = findViewById(R.id.busdesc_text);
        idimage_name = findViewById(R.id.idimage_name);
        logoimage = findViewById(R.id.logoimage);
        dob_day = findViewById(R.id.dob_day);
        dob_month = findViewById(R.id.dob_month);
        dob_year = findViewById(R.id.dob_year);
        exp_day = findViewById(R.id.idexp_day);
        exp_month = findViewById(R.id.idexp_month);
        exp_year = findViewById(R.id.idexp_year);
        Bus_type = findViewById(R.id.bus_type);
        ID_type = findViewById(R.id.id_type);
        state = findViewById(R.id.state_spinner);
        path = Prevalent.currentOnlineStore.getStoremail().replace(".", "");
        downloadUrl = ""; idurl = "";
        dateday = new String[32];
        dateyear = new String[53];
        expdateyear = new String[16];

        store_settings.this.getSupportActionBar().setTitle("Settings");
        store_settings.this.getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_arrow);
        store_settings.this.getSupportActionBar().setDisplayShowHomeEnabled(true);
        store_settings.this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        for(int i=0; i<=31; i++){
            if(i == 0){
                dateday[0] = "Day";
            }else{
                dateday[i] = String.valueOf(i);
            }
        }
        int m= 1950;
        for (int i=0; i<=52; i++,m++){
            if(i==0){
                dateyear[0] = "Year";
            }else{
                dateyear[i] = String.valueOf(m);
            }
        }
        int n=2020;
        for (int i=0; i<=14; i++,n++){
            if(i==0){
                expdateyear[0] = "Year";
            }else{
                expdateyear[i] = String.valueOf(n);
            }
        }

        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, R.layout.spinner_file, dateday);
        adapter1.setDropDownViewResource(R.layout.spinner_dropdown);
        dob_day.setAdapter(adapter1);

        String[] datemonth = {"Month","January", "February", "March", "April", "May", "June", "July", "August",
                "September", "October", "November", "December"};
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, R.layout.spinner_file, datemonth);
        adapter2.setDropDownViewResource(R.layout.spinner_dropdown);
        dob_month.setAdapter(adapter2);

        ArrayAdapter<String> adapter3 = new ArrayAdapter<>(this, R.layout.spinner_file, dateyear);
        adapter3.setDropDownViewResource(R.layout.spinner_dropdown);
        dob_year.setAdapter(adapter3);

        ArrayAdapter<String> adapter4 = new ArrayAdapter<>(this, R.layout.spinner_file, dateday);
        adapter4.setDropDownViewResource(R.layout.spinner_dropdown);
        exp_day.setAdapter(adapter4);

        ArrayAdapter<String> adapter5 = new ArrayAdapter<>(this, R.layout.spinner_file, datemonth);
        adapter5.setDropDownViewResource(R.layout.spinner_dropdown);
        exp_month.setAdapter(adapter5);

        ArrayAdapter<String> adapter6 = new ArrayAdapter<>(this, R.layout.spinner_file, expdateyear);
        adapter6.setDropDownViewResource(R.layout.spinner_dropdown);
        exp_year.setAdapter(adapter6);

        final String[] states = {"State", "Lagos", "Abuja", "Port-Harcout", "Cross-River"};
        ArrayAdapter<String> adapter7 = new ArrayAdapter<>(this, R.layout.spinner_file, states);
        adapter7.setDropDownViewResource(R.layout.spinner_dropdown);
        state.setAdapter(adapter7);


        final String[] bus_type = {"Choose Business type", "Brand Owner", "Brand Agent/Retailer", "Store Owner", "Others"};
        ArrayAdapter<String> adapter8 = new ArrayAdapter<>(this, R.layout.spinner_file, bus_type);
        adapter8.setDropDownViewResource(R.layout.spinner_dropdown);
        Bus_type.setAdapter(adapter8);


        final String[] idtype = {"Choose ID type", "National ID", "International Passport", "Driver's License", "Voter's Card"};
        ArrayAdapter<String> adapter9 = new ArrayAdapter<>(this, R.layout.spinner_file, idtype);
        adapter9.setDropDownViewResource(R.layout.spinner_dropdown);
        ID_type.setAdapter(adapter9);

        DatabaseReference store = FirebaseDatabase.getInstance().getReference("Stores");
        store.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.hasChild(Prevalent.currentOnlineStore.getStoremail().replace(".", ""))){
                    bus_name.setText(Prevalent.currentOnlineStore.getStorename());
                    bus_email.setText(Prevalent.currentOnlineStore.getStoremail());
                    bus_phone.setText(Prevalent.currentOnlineStore.getStorephone());
                }

                if(snapshot.child(Prevalent.currentOnlineStore.getStoremail().replace(".", "")).hasChild("Business Information")){
                    bus_address.setText(snapshot.child(Prevalent.currentOnlineStore.getStoremail().replace(".", ""))
                            .child("Business Information").child("Business Address").getValue().toString());
                    bus_town.setText(snapshot.child(Prevalent.currentOnlineStore.getStoremail().replace(".", ""))
                            .child("Business Information").child("Business Town").getValue().toString());
                    Description.setText(snapshot.child(Prevalent.currentOnlineStore.getStoremail().replace(".", ""))
                            .child("Business Information").child("Business Description").getValue().toString());
                    ste = snapshot.child(Prevalent.currentOnlineStore.getStoremail().replace(".", ""))
                            .child("Business Information").child("Business State").getValue().toString();
                    state.setSelection(getindex(state, ste));

                    bustyp = snapshot.child(Prevalent.currentOnlineStore.getStoremail().replace(".", ""))
                            .child("Business Information").child("Business Type").getValue().toString();
                    Bus_type.setSelection(getindex(Bus_type, bustyp));

                    if(snapshot.child(Prevalent.currentOnlineStore.getStoremail().replace(".", "")).
                            child("Business Information").hasChild("Business Logo")){
                        String url = snapshot.child(Prevalent.currentOnlineStore.getStoremail().replace(".", "")).
                                child("Business Information").child("Business Logo").getValue().toString();
                        Picasso.get().load(url).into(logoimage);
                    }

                }

                if(snapshot.child(Prevalent.currentOnlineStore.getStoremail().replace(".", "")).hasChild("Personnel")){
                    seller_firstname.setText(snapshot.child(Prevalent.currentOnlineStore.getStoremail().replace(".", ""))
                            .child("Personnel").child("First Name").getValue().toString());
                    seller_midname.setText(snapshot.child(Prevalent.currentOnlineStore.getStoremail().replace(".", ""))
                            .child("Personnel").child("Middle Name").getValue().toString());
                    seller_lastname.setText(snapshot.child(Prevalent.currentOnlineStore.getStoremail().replace(".", ""))
                            .child("Personnel").child("Last Name").getValue().toString());
                    String dobday = snapshot.child(Prevalent.currentOnlineStore.getStoremail().replace(".", ""))
                            .child("Personnel").child("DOB Day").getValue().toString();
                    dob_day.setSelection(getindex(dob_day, dobday));

                    String dobmonth = snapshot.child(Prevalent.currentOnlineStore.getStoremail().replace(".", ""))
                            .child("Personnel").child("DOB Month").getValue().toString();
                    dob_month.setSelection(getindex(dob_month, dobmonth));

                    String dobyear= snapshot.child(Prevalent.currentOnlineStore.getStoremail().replace(".", ""))
                            .child("Personnel").child("DOB Year").getValue().toString();
                    dob_year.setSelection(getindex(dob_year, dobyear));

                }

                if(snapshot.child(Prevalent.currentOnlineStore.getStoremail().replace(".", "")).hasChild("Identification")){
                    String expday = snapshot.child(Prevalent.currentOnlineStore.getStoremail().replace(".", "")).
                            child("Identification").child("ID Expiry Day").getValue().toString();
                    exp_day.setSelection(getindex(exp_day, expday));

                    String expmonth = snapshot.child(Prevalent.currentOnlineStore.getStoremail().replace(".", "")).
                            child("Identification").child("ID Expiry Month").getValue().toString();
                    exp_month.setSelection(getindex(exp_month, expmonth));

                    String expyear = snapshot.child(Prevalent.currentOnlineStore.getStoremail().replace(".", "")).
                            child("Identification").child("ID Expiry Year").getValue().toString();
                    exp_year.setSelection(getindex(exp_year, expyear));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        image_llayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check1=1;
                openGallery();
            }
        });

        choose_photobtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check1=2;
                openidgallery();
            }
        });

        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String firstname = seller_firstname.getText().toString();
                final String midname = seller_midname.getText().toString();
                final String lastname = seller_lastname.getText().toString();
                final String busname = bus_name.getText().toString();
                final String busemail = bus_email.getText().toString();
                final String busphone = bus_phone.getText().toString();
                final String busaddress = bus_address.getText().toString();
                final String bustown = bus_town.getText().toString();
                final String busdescription = Description.getText().toString();
                final String dobday = dob_day.getSelectedItem().toString();
                final String dobmonth = dob_month.getSelectedItem().toString();
                final String dobyear = dob_year.getSelectedItem().toString();
                final String chooseid = ID_type.getSelectedItem().toString();
                final String expday = exp_day.getSelectedItem().toString();
                final String expmonth = exp_month.getSelectedItem().toString();
                final String expyear = exp_year.getSelectedItem().toString();
                final String bustype = Bus_type.getSelectedItem().toString();
                final String statess = state.getSelectedItem().toString();

                final DatabaseReference sellerref = FirebaseDatabase.getInstance().getReference("Stores");
                sellerref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.hasChild(Prevalent.currentOnlineStore.getStoremail().replace(".", ""))){
                            if(!downloadUrl.equals("")) {
                                sellerref.child(path).child("Business Information").child("Business Logo").setValue(downloadUrl);
                            }
                            if(!idurl.equals("")){
                                sellerref.child(path).child("Identification").child("ID Photo").setValue(idurl);
                            }
                            HashMap<String, Object> sellermap = new HashMap<>();
                            sellermap.put("First Name", firstname);
                            sellermap.put("Middle Name", midname);
                            sellermap.put("Last Name", lastname);
                            sellermap.put("DOB Day", dobday);
                            sellermap.put("DOB Month", dobmonth);
                            sellermap.put("DOB Year", dobyear);
                            sellerref.child(Prevalent.currentOnlineStore.getStoremail().replace(".", "")).child("Personnel").updateChildren(sellermap);

                            HashMap<String, Object> busmap = new HashMap<>();
                            busmap.put("Business Name", busname);
                            busmap.put("Business E-mail", busemail);
                            busmap.put("Business Phone", busphone);
                            busmap.put("Business Address", busaddress);
                            busmap.put("Business Town", bustown);
                            busmap.put("Business Type", bustype);
                            busmap.put("Business Description", busdescription);
                            busmap.put("Business State", statess);
                            busmap.put("Business Full Address", busaddress+" "+bustown+" "+statess);

                            sellerref.child(Prevalent.currentOnlineStore.getStoremail().replace(".", "")).child("Business Information").updateChildren(busmap);

                            HashMap<String, Object> idmap = new HashMap<>();
                            idmap.put("ID Type", chooseid);

                            idmap.put("ID Expiry Day", expday);
                            idmap.put("ID Expiry Month", expmonth);
                            idmap.put("ID Expiry Year", expyear);
                            sellerref.child(Prevalent.currentOnlineStore.getStoremail().replace(".", "")).
                                    child("Identification").updateChildren(idmap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(store_settings.this, "Credentials has been saved", Toast.LENGTH_SHORT).show();
                                        store_settings.this.finish();
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
        });
    }

    private int getindex(Spinner spinner, String mystring) {
        for(int i=0; i<spinner.getCount(); i++ ){
            if(spinner.getItemAtPosition(i).toString().equalsIgnoreCase(mystring)){
                return i;
            }
        }
        return  0;
    }

    private void openidgallery() {
        Intent galleryintent = new Intent();
        galleryintent.setAction(Intent.ACTION_GET_CONTENT);
        galleryintent.setType("image/*");
        galleryintent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        galleryintent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        startActivityForResult(Intent.createChooser(galleryintent, "Select ID Photo"), gallerypick);
    }

    private void openGallery() {
        Intent galleryintent = new Intent();
        galleryintent.setAction(Intent.ACTION_GET_CONTENT);
        galleryintent.setType("image/*");
        galleryintent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        galleryintent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        startActivityForResult(Intent.createChooser(galleryintent, "Select Logo"), gallerypick);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == gallerypick && resultCode == RESULT_OK && data!=null){
            if(data.getData()!=null && check1==1){
                logouri=data.getData();
                logoimage.setImageURI(logouri);

                final StorageReference logoref = FirebaseStorage.getInstance().getReference("Logos").child(Prevalent.currentOnlineStore.getStoremail()).
                        child(Prevalent.currentOnlineStore.getStoremail()+" jpg");
                    final UploadTask uploadTask = logoref.putFile(logouri);
                    logoref.putFile(logouri).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            String message = e.toString();
                            store_settingsProgressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(store_settings.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            store_settingsProgressBar.setVisibility(View.INVISIBLE);
                            Task<Uri> urltask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                                @Override
                                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                                    if (!task.isSuccessful()) {
                                        store_settingsProgressBar.setVisibility(View.INVISIBLE);
                                        throw task.getException();
                                    }
                                    downloadUrl = logoref.getDownloadUrl().toString();
                                    return logoref.getDownloadUrl();
                                }
                            }).addOnCompleteListener(new OnCompleteListener() {
                                @Override
                                public void onComplete(@NonNull Task task) {
                                    if (task.isSuccessful()) {
                                        downloadUrl = task.getResult().toString();
                                        Toast.makeText(store_settings.this, "Logo uploaded", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    });


            } else if(check1==2 && data.getData()!=null){
                iduri=data.getData();
                idimage_name.setText(iduri.getLastPathSegment());
                idimage_name.setVisibility(View.VISIBLE);
                final StorageReference logoref = FirebaseStorage.getInstance().getReference("ID photos").child(Prevalent.currentOnlineStore.getStoremail()).
                        child(Prevalent.currentOnlineStore.getStoremail()+" jpg");
                final UploadTask uploadTask = logoref.putFile(iduri);
                logoref.putFile(iduri).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        String message = e.toString();
                        store_settingsProgressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(store_settings.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        store_settingsProgressBar.setVisibility(View.INVISIBLE);
                        Task<Uri> urltask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                            @Override
                            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                                if (!task.isSuccessful()) {
                                    store_settingsProgressBar.setVisibility(View.INVISIBLE);
                                    throw task.getException();
                                }
                                idurl = logoref.getDownloadUrl().toString();
                                return logoref.getDownloadUrl();
                            }
                        }).addOnCompleteListener(new OnCompleteListener() {
                            @Override
                            public void onComplete(@NonNull Task task) {
                                if (task.isSuccessful()) {
                                    idurl = task.getResult().toString();
                                    Toast.makeText(store_settings.this, "ID image uploaded", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                });

            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}