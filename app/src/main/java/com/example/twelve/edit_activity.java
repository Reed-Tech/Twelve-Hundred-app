package com.example.twelve;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.collection.ArraySet;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;

import com.example.twelve.Adapters.Edit_RVadapter;
import com.example.twelve.Model.HomeProducts;
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

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class edit_activity extends AppCompatActivity {
    private RecyclerView edit_rv;
    private ImageView singleimg;
    private Uri imageuri, uscuri;
    private static final int gallerypick = 1;
    private EditText pname, pprice, pdesc;
    private MultiAutoCompleteTextView colour, size, design;
    private TextView upp_text;
    private TextView usc_text, fpn_text;
    private Button up_btn;
    private List<String> imglist;
    private List<Uri> urilist;
    private Edit_RVadapter adapter;
    private String checker, uscstring, path;
    private int count;
    GridLayoutManager lm;
    String category, pid, gender;
    String[] colo = {"Black", "White", "Red", "Brown", "Orange", "Yellow","Green", "Blue", "Purple"};
    String[] des = {"Plain", "Kampala", "Tiger skin", "Camoflauge", "Prints", "Floral","Stripes"};
    String[] siz1 = {"S", "M", "L", "XL", "XXL", "XXXL"};
    String[] siz;
    private List<String> urllist;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_activity);

        edit_activity.this.getSupportActionBar().setTitle("Edit Products");
        edit_activity.this.getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_arrow);
        edit_activity.this.getSupportActionBar().setDisplayShowHomeEnabled(true);
        edit_activity.this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        siz = new String[16];
        pname = findViewById(R.id.pname);
        pprice = findViewById(R.id.pprice);
        pdesc = findViewById(R.id.pdesc);
        colour = findViewById(R.id.col_mactv);
        imglist = new ArrayList<>();
        urilist = new ArrayList<>();
        urllist = new ArrayList<>();
        size = findViewById(R.id.size_mactv);
        up_btn = findViewById(R.id.up_btn);
        singleimg = findViewById(R.id.singleimg);
        design = findViewById(R.id.design_mactv);
        usc_text = findViewById(R.id.usc_text);
        upp_text = findViewById(R.id.upp_text);
        fpn_text = findViewById(R.id.fpn_text);
        edit_rv = findViewById(R.id.edit_rv);
        path = Prevalent.currentOnlineStore.getStoremail().replace(".", "");
        category = getIntent().getStringExtra("category");
        pid = getIntent().getStringExtra("pid");
        gender = getIntent().getStringExtra("gender");

        int j = 31;
        for(int i =0; i<16; i++, j++){
            siz[i] = String.valueOf(j);
        }

        lm = new GridLayoutManager(edit_activity.this, 3);
        edit_rv.setLayoutManager(lm);
        edit_rv.getLayoutManager().setMeasurementCacheEnabled(false);

        DatabaseReference dref = FirebaseDatabase.getInstance().getReference("Products");
        dref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.child(gender).child(category).hasChild(pid)){
                  if(snapshot.child(gender).child(category).child(pid).hasChild("Colour Text")){
                      colour.setText(snapshot.child(gender).child(category).child(pid).child("Colour Text").getValue().toString());
                  }
                  if(snapshot.child(gender).child(category).child(pid).hasChild("Design Text")){
                      design.setText(snapshot.child(gender).child(category).child(pid).child("Design Text").getValue().toString());
                    }
                  if(snapshot.child(gender).child(category).child(pid).hasChild("Sizes Text")){
                        size.setText(snapshot.child(gender).child(category).child(pid).child("Sizes Text").getValue().toString());
                    }

                  HomeProducts hp = snapshot.child(gender).child(category).child(pid).getValue(HomeProducts.class);
                  pname.setText(hp.getPname());
                  pprice.setText(hp.getPrice());
                  pdesc.setText(hp.getDescription());
                  imglist = hp.getImageurllist();
                }

                adapter = new Edit_RVadapter(imglist, urilist,edit_activity.this);
                adapter.notifyDataSetChanged();
                edit_rv.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        lm = new GridLayoutManager(this, 3);
        edit_rv.setLayoutManager(lm);
        edit_rv.getLayoutManager().setMeasurementCacheEnabled(false);
        adapter = new Edit_RVadapter(imglist, urilist,this);
        adapter.notifyDataSetChanged();
        edit_rv.setAdapter(adapter);

        ArrayAdapter<String> col = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, colo);
        col.setDropDownViewResource(R.layout.spinner_dropdown);
        colour.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
        colour.setThreshold(1);
        colour.setAdapter(col);

        ArrayAdapter<String> desg = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, des);
        desg.setDropDownViewResource(R.layout.spinner_dropdown);
        design.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
        design.setThreshold(1);
        design.setAdapter(desg);

        ArrayAdapter<String> sizz = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, siz);
        sizz.setDropDownViewResource(R.layout.spinner_dropdown);
        ArrayAdapter<String> siz2 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, siz1);
        siz2.setDropDownViewResource(R.layout.spinner_dropdown);
        size.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
        size.setThreshold(1);
        if(category.equalsIgnoreCase("Boots") || category.equalsIgnoreCase("Sneakers") ||
        category.equalsIgnoreCase("Leathers") || category.equalsIgnoreCase("Slides")
        || category.equalsIgnoreCase("Heels and Flats") || category.equalsIgnoreCase("Sandals")){
            size.setAdapter(sizz);
        }else{
            size.setAdapter(siz2);
        }

        upp_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(edit_activity.this, "Note that ALL existing images will be overidden", Toast.LENGTH_LONG).show();
                checker = "upp";
                openGallery();
            }
        });

        usc_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(edit_activity.this, "Note that any existing images will be overidden", Toast.LENGTH_SHORT).show();
                checker = "usc";
                openGallery();
            }
        });

        up_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String prname = pname.getText().toString();
                String prprice =pprice.getText().toString();
                String prdesc = pdesc.getText().toString();
                String prcolor = colour.getText().toString();
                String prsize = size.getText().toString();
                String prdesign = design.getText().toString();

                if(TextUtils.isEmpty(prname) || TextUtils.isEmpty(prprice) || TextUtils.isEmpty(prdesc) || TextUtils.isEmpty(prcolor)
                || TextUtils.isEmpty(prsize) || TextUtils.isEmpty(prdesign)){
                    Toast.makeText(edit_activity.this, "Please fill out all fields", Toast.LENGTH_SHORT).show();
                }else{
                   uploadcred(prname, prprice, prdesc, prcolor, prsize, prdesign);
                }
            }
        });



        DatabaseReference edit_ref = FirebaseDatabase.getInstance().getReference();
        edit_ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void uploadcred(String prname, String prprice, String prdesc, String prcolor, String prsize, String prdesign) {
        DatabaseReference editref = FirebaseDatabase.getInstance().getReference("HomeProducts");
        DatabaseReference editref2 = FirebaseDatabase.getInstance().getReference("Products");
        DatabaseReference editref3 = FirebaseDatabase.getInstance().getReference("Stores");
        String pcl = colour.getText().toString().trim();
        if(pcl.endsWith(",")){
            pcl = pcl.substring(0,pcl.length() - 1);
        }
        String psz = size.getText().toString().trim();
        if(psz.endsWith(",")){
            psz = psz.substring(0,psz.length() - 1);
        }
        String pdsg = design.getText().toString().trim();
        if(pdsg.endsWith(",")){
            pdsg = pdsg.substring(0,pdsg.length() - 1);
        }

        String[] single = pcl.split("\\s*,\\s*");
        List<String> single1 = Arrays.asList(single);

        String[] dsg = pdsg.split("\\s*,\\s*");
        List<String> dsg1 = Arrays.asList(dsg);

        String[] sz = psz.split("\\s*,\\s*");
        List<String> sz1 = Arrays.asList(sz);

        HashMap<String, Object> editmap = new HashMap<>();

        editmap.put("description", prdesc);
        if(urllist.size()!=0){
            editmap.put("Imageurllist", urllist);
        }
        editmap.put("Price", prprice);
        editmap.put("Colours", single1);
        editmap.put("Colour Text", pcl);
        editmap.put("Designs", dsg1);
        editmap.put("Design Text", pdsg);
        editmap.put("Sizes", sz1);
        editmap.put("Sizes Text", psz);
        editmap.put("Pname", prname);
        if(!TextUtils.isEmpty(uscstring)){
            editmap.put("Size Chart", uscstring);
        }

        editref2.child(gender).child(category).child(pid).updateChildren(editmap);

        editref3.child(path).child("Products").child(gender).child(category).child(pid).updateChildren(editmap);

        editref.child(pid).updateChildren(editmap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(edit_activity.this, "Product updated", Toast.LENGTH_SHORT).show();
                    finish();
                }else{
                    Toast.makeText(edit_activity.this, "Error updating product", Toast.LENGTH_SHORT).show();
                }
            }
        });
}

    private void openGallery() {
        Intent galleryintent = new Intent();
        galleryintent.setAction(Intent.ACTION_GET_CONTENT);
        galleryintent.setType("image/*");
        galleryintent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        galleryintent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        startActivityForResult(Intent.createChooser(galleryintent, "Select pictures"), gallerypick);
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == gallerypick && resultCode == RESULT_OK && (data.getData()!=null || data.getClipData()!= null)) {
            if(checker.equals("upp")) {
                if (data.getClipData() != null) {
                    Toast.makeText(this, "multiple images selected", Toast.LENGTH_SHORT).show();
                    Toast.makeText(this, "Images will take turns to save, Please wait...", Toast.LENGTH_SHORT).show();
                    count = data.getClipData().getItemCount();

                    lm = new GridLayoutManager(this, 3);
                    edit_rv.setLayoutManager(lm);
                    edit_rv.getLayoutManager().setMeasurementCacheEnabled(false);

                    for (int i = 0; i < count; i++) {
                        imageuri = data.getClipData().getItemAt(i).getUri();
                        urilist.add(data.getClipData().getItemAt(i).getUri());
                        StorageReference Pimagesref = FirebaseStorage.getInstance().getReference("Product Images");
                        final StorageReference filepath = Pimagesref.child(gender).child(category)
                                .child(pid)
                                .child(this.imageuri.getLastPathSegment() + gender + category + pid + ".jpg");
                        final UploadTask uploadTask = filepath.putFile(imageuri);
                        filepath.putFile(imageuri).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                String message = e.toString();

                                Toast.makeText(edit_activity.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                            }
                        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                Task<Uri> urltask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                                    @Override
                                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                                        if (!task.isSuccessful()) {

                                            throw task.getException();
                                        }
                                        String downloadUrl;
                                        downloadUrl = filepath.getDownloadUrl().toString();
                                        return filepath.getDownloadUrl();
                                    }
                                }).addOnCompleteListener(new OnCompleteListener() {
                                    @Override
                                    public void onComplete(@NonNull Task task) {
                                        if (task.isSuccessful()) {
                                            String downloadUrl;
                                            downloadUrl = task.getResult().toString();
                                            urllist.add(downloadUrl);

                                            if (urllist.size() == count) {

                                                Toast.makeText(edit_activity.this, "All done", Toast.LENGTH_SHORT).show();

                                            }

                                        }
                                    }
                                });
                            }
                        });

                    }

                    Edit_RVadapter edit_rVadapter = new Edit_RVadapter(imglist, urilist, edit_activity.this);
                    edit_rVadapter.notifyDataSetChanged();
                    edit_rv.setAdapter(edit_rVadapter);

                } else {
                    if (data.getData() != null) {
                        Toast.makeText(this, "Single image selected", Toast.LENGTH_SHORT).show();
                        imageuri = data.getData();
                        edit_rv.setVisibility(View.GONE);
                        singleimg.setVisibility(View.VISIBLE);
                        singleimg.setImageURI(imageuri);
                        StorageReference Pimagesref = FirebaseStorage.getInstance().getReference("Product Images");
                        final StorageReference filepath = Pimagesref.child(gender).child(category)
                                .child(pid)
                                .child(this.imageuri.getLastPathSegment() + gender + category + pid + ".jpg");
                        final UploadTask uploadTask = filepath.putFile(imageuri);
                        filepath.putFile(imageuri).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                String message = e.toString();

                                Toast.makeText(edit_activity.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                            }
                        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                Task<Uri> urltask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                                    @Override
                                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                                        if (!task.isSuccessful()) {

                                            throw task.getException();
                                        }
                                        String downloadUrl;
                                        downloadUrl = filepath.getDownloadUrl().toString();
                                        return filepath.getDownloadUrl();
                                    }
                                }).addOnCompleteListener(new OnCompleteListener() {
                                    @Override
                                    public void onComplete(@NonNull Task task) {
                                        if (task.isSuccessful()) {
                                            String downloadUrl;
                                            downloadUrl = task.getResult().toString();
                                            urllist.add(downloadUrl);

                                            Toast.makeText(edit_activity.this, "All done", Toast.LENGTH_SHORT).show();

                                        }
                                    }
                                });
                            }
                        });

                    }
                }
            }else {
                if(checker.equals("usc")){
                    if(data.getData()!=null){
                        uscuri=data.getData();
                        fpn_text.setVisibility(View.VISIBLE);
                        fpn_text.setText(uscuri.getLastPathSegment());
                        StorageReference Pimagesref = FirebaseStorage.getInstance().getReference("Size Charts");
                        final StorageReference filepath = Pimagesref.child(pid)
                                .child(data.getData().getLastPathSegment() + pid + ".jpg");
                        final UploadTask uploadTask = filepath.putFile(uscuri);
                        filepath.putFile(uscuri).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                String message = e.toString();

                                Toast.makeText(edit_activity.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                            }
                        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                Task<Uri> urltask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                                    @Override
                                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                                        if (!task.isSuccessful()) {

                                            throw task.getException();
                                        }
                                        String downloadUrl;
                                        downloadUrl = filepath.getDownloadUrl().toString();
                                        return filepath.getDownloadUrl();
                                    }
                                }).addOnCompleteListener(new OnCompleteListener() {
                                    @Override
                                    public void onComplete(@NonNull Task task) {
                                        if (task.isSuccessful()) {
                                            String downloadUrl;
                                            downloadUrl = task.getResult().toString();
                                            uscstring = downloadUrl;

                                            Toast.makeText(edit_activity.this, "All done", Toast.LENGTH_SHORT).show();

                                        }
                                    }
                                });
                            }
                        });
                    }
                }
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id==android.R.id.home){
            finish();
        }
        return true;
    }
}