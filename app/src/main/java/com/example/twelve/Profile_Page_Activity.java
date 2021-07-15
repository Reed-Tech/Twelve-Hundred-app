package com.example.twelve;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.twelve.Model.Users;
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
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class Profile_Page_Activity extends AppCompatActivity {
    private CircleImageView circleImageView;
    private LinearLayout pll;
    private EditText fullname, username, email, del_phone, address, town_et;
    private Button submit_btn;
    private Uri imageuri;
    private Spinner state;
    private Toolbar toolbar;
    private static final int gallerypick = 1;
    private String path, downloadUrl;
    StorageReference profile_imagesRef;
    String checker = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile__page_);

        toolbar = findViewById(R.id.profile_toolbar);
        circleImageView = findViewById(R.id.civ);
        state = findViewById(R.id.state_spinner2);
        town_et = findViewById(R.id.town2);
        fullname = findViewById(R.id.profile_fullname);
        username = findViewById(R.id.profile_username);
        email = findViewById(R.id.profile_email);
        del_phone = findViewById(R.id.delievery_no);
        address = findViewById(R.id.delievery_address);
        submit_btn = findViewById(R.id.profile_submit_button);
        pll = findViewById(R.id.profile_image2);
        path = Prevalent.currentOnlineUser.getEmail().replace(".", " ");
        profile_imagesRef = FirebaseStorage.getInstance().getReference();

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Profile Settings");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_arrow);

        final String[] states = {"State", "Lagos", "Abuja", "Port-Harcout", "Cross-River"};
        ArrayAdapter<String> adapter7 = new ArrayAdapter<>(this, R.layout.spinner_file, states);
        adapter7.setDropDownViewResource(R.layout.spinner_dropdown);
        state.setAdapter(adapter7);

        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Profile_Page_Activity.this, "The e-mail text field cannot be edited", Toast.LENGTH_SHORT).show();
            }
        });

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Users users = snapshot.child(Prevalent.currentOnlineUser.getEmail().replace(".", " ")).getValue(Users.class);
                String path = Prevalent.currentOnlineUser.getEmail().replace(".", " ");
                DataSnapshot dataSnapshot = snapshot.child(Prevalent.currentOnlineUser.getEmail().replace(".", " "));
                if (dataSnapshot.exists()) {

                    if (dataSnapshot.child("State").exists()) {
                        String ste = dataSnapshot.child("State").getValue().toString();
                        state.setSelection(getindex(state, ste));
                    }

                    if (dataSnapshot.child("Town").exists()) {
                        town_et.setText(dataSnapshot.child("Town").getValue().toString());
                    }

                    if (!Prevalent.currentOnlineUser.getProfile_image().equals("default")) {
                        Picasso.get().load(Prevalent.currentOnlineUser.getProfile_image()).placeholder(R.drawable.profileicon).into(circleImageView);
                    } else {
                        circleImageView.setImageResource(R.drawable.profileicon);
                    }
                    if (!Prevalent.currentOnlineUser.getName().equals("")) {
                        fullname.setText(Prevalent.currentOnlineUser.getName());
                    } else {
                        fullname.setText("");
                    }
                    if (!Prevalent.currentOnlineUser.getUsername().equals("")) {
                        username.setText(Prevalent.currentOnlineUser.getUsername());
                    } else {
                        username.setText("");
                    }
                    if (!Prevalent.currentOnlineUser.getDelieveryphone().equals("")) {
                        del_phone.setText(Prevalent.currentOnlineUser.getDelieveryphone().replace("+234", ""));
                    } else {
                        del_phone.setText("");
                    }
                    if (!Prevalent.currentOnlineUser.getAddress().equals("default")) {
                        address.setText(Prevalent.currentOnlineUser.getAddress());
                    } else {
                        address.setText("");
                    }
                    if (!Prevalent.currentOnlineUser.getEmail().equals("")) {
                        email.setText(Prevalent.currentOnlineUser.getEmail());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                opengallery();
            }
        });

        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checker.equals("clicked")) {
                    validateupdate();
                } else {
                    validateupdate2();
                }
            }
        });


    }

    private int getindex(Spinner spinner, String mystring) {
        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(mystring)) {
                return i;
            }
        }
        return 0;
    }

    private void validateupdate2() {
        final String textfullname = fullname.getText().toString();
        final String textUsername = username.getText().toString();
        final String textemail = email.getText().toString();
        final String text_del_phone = del_phone.getText().toString();
        final String textAddress = address.getText().toString();
        final String stateAddress = state.getSelectedItem().toString();
        final String townAddress = town_et.getText().toString();

        if (TextUtils.isEmpty(textfullname) || TextUtils.isEmpty(textUsername) || TextUtils.isEmpty(textemail) || TextUtils.isEmpty(text_del_phone) || TextUtils.isEmpty(textAddress)) {
            Toast.makeText(Profile_Page_Activity.this, "Please fill out all fields", Toast.LENGTH_SHORT).show();
        } else {
            DatabaseReference cred_check = FirebaseDatabase.getInstance().getReference("Usernames");
            cred_check.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot suu : snapshot.getChildren()){
                        if(suu.getValue().toString().equalsIgnoreCase(Prevalent.currentOnlineUser.getUsername())){
                            continue;
                        }
                        if(suu.getValue().toString().equalsIgnoreCase(textUsername)){
                            Toast.makeText(Profile_Page_Activity.this, "Username is taken, please provide another one", Toast.LENGTH_SHORT).show();
                        }else{
                            Updatecredentials2(textfullname, textUsername, textemail, text_del_phone, textAddress, stateAddress, townAddress);
                            break;
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }

    private void validateupdate() {
        final String textfullname = fullname.getText().toString();
        final String textUsername = username.getText().toString();
        final String textemail = email.getText().toString();
        final String text_del_phone = del_phone.getText().toString();
        final String textAddress = address.getText().toString();
        final String stateAddress = state.getSelectedItem().toString();
        final String townAddress = town_et.getText().toString();

        if (TextUtils.isEmpty(textfullname) || TextUtils.isEmpty(textUsername)
                || TextUtils.isEmpty(textemail) || TextUtils.isEmpty(text_del_phone) || TextUtils.isEmpty(textAddress) || TextUtils.isEmpty(townAddress)) {
            Toast.makeText(Profile_Page_Activity.this, "Please fill out all fields", Toast.LENGTH_SHORT).show();
        } else {
            uploadprofileimage();
        }
    }

    private void uploadprofileimage() {
        final String textfullname = fullname.getText().toString();
        final String textUsername = username.getText().toString();
        final String textemail = email.getText().toString();
        final String text_del_phone = del_phone.getText().toString();
        final String textAddress = address.getText().toString();
        final String stateAddress = state.getSelectedItem().toString();
        final String townAddress = town_et.getText().toString();

        final StorageReference filepath = profile_imagesRef.child("Profile pictures").child(path).child(path + ".jpg");
        if (imageuri != null) {
            filepath.putFile(imageuri).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    String msg = e.toString();
                    Toast.makeText(Profile_Page_Activity.this, "Error: " + msg, Toast.LENGTH_SHORT).show();
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    Task<Uri> urltask = filepath.putFile(imageuri).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                        @Override
                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                            if (!task.isSuccessful()) {
                                throw task.getException();
                            }
                            downloadUrl = filepath.getDownloadUrl().toString();
                            return filepath.getDownloadUrl();

                        }
                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if (task.isSuccessful()) {
                                downloadUrl = task.getResult().toString();
                                Updatecredentials(textfullname, textUsername, textemail, text_del_phone, textAddress, downloadUrl, stateAddress, townAddress);
                            } else {
                                Toast.makeText(Profile_Page_Activity.this, "Error uploading image", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            });
        } else {
            Toast.makeText(this, "No image was selected", Toast.LENGTH_SHORT).show();
        }
    }

    private void Updatecredentials2(String textfullname, String textUsername, String textemail, String text_del_phone, String textAddress, String stateAddress, String townAddress) {

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        HashMap<String, Object> userdata = new HashMap<>();
        userdata.put("email", textemail);
        userdata.put("name", textfullname);
        userdata.put("username", textUsername);
        userdata.put("delieveryphone", "+234" + text_del_phone);
        userdata.put("address", textAddress);
        userdata.put("State", stateAddress);
        userdata.put("Town", townAddress);

        databaseReference.child(path).updateChildren(userdata).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(Profile_Page_Activity.this, "Credentials updated successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Profile_Page_Activity.this, Profile_Page_Activity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(Profile_Page_Activity.this, "Credentials update was unsuccessful", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    private void Updatecredentials(String textfullname, String textUsername, String textemail, String text_del_phone, String textAddress, String downloadUrl, String stateAddress, String townAddress) {


        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        HashMap<String, Object> userdata = new HashMap<>();
        userdata.put("email", textemail);
        userdata.put("name", textfullname);
        userdata.put("username", textUsername);
        userdata.put("delieveryphone", "+234" + text_del_phone);
        userdata.put("address", textAddress);
        userdata.put("profile_image", downloadUrl);
        userdata.put("State", stateAddress);
        userdata.put("Town", townAddress);

        databaseReference.child(path).updateChildren(userdata).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(Profile_Page_Activity.this, "Credentials updated successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Profile_Page_Activity.this, Profile_Page_Activity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(Profile_Page_Activity.this, "Credentials update was unsuccessful", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void opengallery() {
        CropImage.activity(imageuri)
                .setAspectRatio(1, 1)
                .start(Profile_Page_Activity.this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            checker = "clicked";
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            imageuri = result.getUri();
            circleImageView.setImageURI(imageuri);
        } else {
            Toast.makeText(this, "No file was chosen", Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}