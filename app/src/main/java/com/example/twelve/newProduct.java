package com.example.twelve;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.twelve.Adapters.Edit_RVadapter;
import com.example.twelve.Prevalent.Prevalent;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.example.twelve.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class newProduct extends AppCompatActivity {
    private String categoryname, Pname, Pprice, Pdescription, savecurrentdate, savecurrenttime;
    private String productkey, downloadUrl, gendercategoryname, prev, checker, uscstring;
    private TextView upip, usc_text, fpn_text;
    private ImageView inputproductimg;
    private EditText inputproductname, inputproductdesc, inputproductprice;
    private Button addproductbtn;
    private static final int gallerypick = 1;
    private Uri imageuri, uscuri;
    private MultiAutoCompleteTextView colour1, size1, design1;
    private ProgressBar progressbar;
    private StorageReference Pimagesref;
    private DatabaseReference reff;
    private DatabaseReference creff;
    private RecyclerView newp_rv;
    GridLayoutManager lm1;
    Edit_RVadapter adapter;
    private List<String> imglist;
    private List<Uri> urilist;
    private DatabaseReference uref;
    String[] colo1 = {"Black", "White", "Red", "Brown", "Orange", "Yellow","Green", "Blue", "Purple"};
    String[] des1 = {"Plain", "Kampala", "Tiger skin", "Camoflauge", "Prints", "Floral","Stripes"};
    String[] siz2 = {"S", "M", "L", "XL", "XXL", "XXXL"};
    String[] siz3;

    private ArrayList<String> urllist;
    private int i, count;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_product);

        imglist = new ArrayList<>();
        urilist = new ArrayList<>();
        newp_rv = findViewById(R.id.newp_rv);
        colour1 = findViewById(R.id.col_mactv1);
        size1 = findViewById(R.id.size_mactv1);
        design1 = findViewById(R.id.design_mactv1);
        usc_text = findViewById(R.id.usc_text1);
        fpn_text = findViewById(R.id.fpn_text1);
        urllist = new ArrayList<>();
        prev = getIntent().getStringExtra("Prev");
        uref = FirebaseDatabase.getInstance().getReference("Stores");
        reff = FirebaseDatabase.getInstance().getReference("Products");
        creff = FirebaseDatabase.getInstance().getReference("HomeProducts");
        Pimagesref = FirebaseStorage.getInstance().getReference("Product Images");
        categoryname = getIntent().getExtras().get("categories").toString();
        gendercategoryname = getIntent().getExtras().get("gender").toString();
        upip = findViewById(R.id.upip);
        inputproductimg = findViewById(R.id.productimage);
        inputproductname = findViewById(R.id.productnametxt);
        inputproductdesc = findViewById(R.id.productdesctxt);
        inputproductprice = findViewById(R.id.productpricetxt);
        progressbar = findViewById(R.id.progressbarr);
        addproductbtn = findViewById(R.id.addproductbtn);
        siz3 = new String[16];

        this.getSupportActionBar().setTitle("New product");
        this.getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_arrow);
        this.getSupportActionBar().setDisplayShowHomeEnabled(true);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        int j = 31;
        for(int i =0; i<16; i++, j++){
            siz3[i] = String.valueOf(j);
        }

        inputproductimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checker = "upp";
                opengallery();

            }
        });

        usc_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(urllist.size()==0){
                    Toast.makeText(newProduct.this, "Please select product pictures first.", Toast.LENGTH_SHORT).show();
                }else{
                    checker = "usc";
                    opengallery();
                }

            }
        });

        addproductbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Validateproduct();
            }
        });

        ArrayAdapter<String> col = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, colo1);
        col.setDropDownViewResource(R.layout.spinner_dropdown);
        colour1.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
        colour1.setThreshold(1);
        colour1.setAdapter(col);

        ArrayAdapter<String> desg = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, des1);
        desg.setDropDownViewResource(R.layout.spinner_dropdown);
        design1.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
        design1.setThreshold(1);
        design1.setAdapter(desg);

        ArrayAdapter<String> sizz = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, siz3);
        sizz.setDropDownViewResource(R.layout.spinner_dropdown);
        ArrayAdapter<String> sizzz = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, siz2);
        sizzz.setDropDownViewResource(R.layout.spinner_dropdown);
        size1.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
        size1.setThreshold(1);
        if(categoryname.equalsIgnoreCase("Boots") || categoryname.equalsIgnoreCase("Sneakers") ||
                categoryname.equalsIgnoreCase("Leathers") || categoryname.equalsIgnoreCase("Slides")
                || categoryname.equalsIgnoreCase("Heels and Flats") || categoryname.equalsIgnoreCase("Sandals")){
            size1.setAdapter(sizz);
        }else{
            size1.setAdapter(sizzz);
        }
    }

    private void Validateproduct() {
        Pname = inputproductname.getText().toString();
        Pdescription = inputproductdesc.getText().toString();
        Pprice = inputproductprice.getText().toString();

        if (imageuri == null) {
            Toast.makeText(this, "One image must be selected at least", Toast.LENGTH_SHORT).show();
        } else {
            if (TextUtils.isEmpty(Pname) || TextUtils.isEmpty(Pdescription) || TextUtils.isEmpty(Pprice)) {
                Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT).show();
            } else {

                Saveproductinfo();
            }
        }
    }

    private void Saveproductinfo() {

        String pcl = colour1.getText().toString().trim();
        if(pcl.endsWith(",")){
            pcl = pcl.substring(0,pcl.length() - 1);
        }
        String psz = size1.getText().toString().trim();
        if(psz.endsWith(",")){
            psz = psz.substring(0,psz.length() - 1);
        }
        String pdsg = design1.getText().toString().trim();
        if(pdsg.endsWith(",")){
            pdsg = pdsg.substring(0,pdsg.length() - 1);
        }

        String[] single = pcl.split("\\s*,\\s*");
        List<String> single1 = Arrays.asList(single);

        String[] dsg = pdsg.split("\\s*,\\s*");
        List<String> dsg1 = Arrays.asList(dsg);

        String[] sz = psz.split("\\s*,\\s*");
        List<String> sz1 = Arrays.asList(sz);

        HashMap<String, Object> values = new HashMap<>();
        values.put("pid", productkey);
        values.put("gender", gendercategoryname);
        values.put("date", savecurrentdate);
        values.put("time", savecurrenttime);
        values.put("Colours", single1);
        values.put("Colour Text", pcl);
        values.put("Designs", dsg1);
        values.put("Design Text", pdsg);
        values.put("Sizes", sz1);
        values.put("Sizes Text", psz);
        values.put("description", Pdescription);
        values.put("Imageurllist", urllist);
        values.put("Category", categoryname);
        values.put("Price", "NGN " +Pprice);
        values.put("Pname", Pname);
        values.put("Store", Prevalent.currentOnlineStore.getStorename());


        if(!Prevalent.currentOnlineStore.getStoremail().replace(".", "").equals("")){
            String path = Prevalent.currentOnlineStore.getStoremail().replace(".", "");
            uref.child(path).child("Products").child(gendercategoryname).child(categoryname).child(Pname +" "+ productkey).setValue(values);
        }


        creff.child(Pname+" "+productkey).setValue(values);
        reff.child(gendercategoryname).child(categoryname).child(Pname +" "+ productkey).setValue(values).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    progressbar.setVisibility(View.INVISIBLE);
                    finish();
                    Toast.makeText(newProduct.this, "Product added successfully", Toast.LENGTH_SHORT).show();
                } else {
                    String message = task.getException().toString();
                    progressbar.setVisibility(View.INVISIBLE);
                    Toast.makeText(newProduct.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void opengallery() {
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
        if (requestCode == gallerypick && resultCode == RESULT_OK && (data.getData()!=null || data.getClipData()!= null)) {
            if(checker.equals("upp")){
                if (data.getClipData() != null) {
                    inputproductimg.setVisibility(View.GONE);
                    addproductbtn.setVisibility(View.INVISIBLE);
                    upip.setVisibility(View.VISIBLE);
                    Toast.makeText(this, "multiple images selected", Toast.LENGTH_SHORT).show();
                    Toast.makeText(this, "Images will take turns to save if multiple, Please wait...", Toast.LENGTH_SHORT).show();
                    count = data.getClipData().getItemCount();
                    Calendar calender = Calendar.getInstance();
                    SimpleDateFormat currentdate = new SimpleDateFormat("MMM dd, yyyy");
                    SimpleDateFormat currenttime = new SimpleDateFormat("HH:mm:ss a");
                    savecurrentdate = currentdate.format(calender.getTime());
                    savecurrenttime = currenttime.format(calender.getTime());
                    productkey = savecurrentdate + " " + savecurrenttime;

                    lm1 = new GridLayoutManager(newProduct.this, 3);
                    newp_rv.setLayoutManager(lm1);
                    newp_rv.getLayoutManager().setMeasurementCacheEnabled(false);

                    for (i = 0; i < count; i++) {
                        imageuri = data.getClipData().getItemAt(i).getUri();
                        urilist.add(imageuri);

                        final StorageReference filepath = Pimagesref.child(gendercategoryname).child(categoryname)
                                .child(Pname + " " + productkey)
                                .child(this.imageuri.getLastPathSegment() + gendercategoryname + categoryname + productkey + ".jpg");
                        final UploadTask uploadTask = filepath.putFile(imageuri);
                        filepath.putFile(imageuri).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            String message = e.toString();
                            progressbar.setVisibility(View.INVISIBLE);
                            Toast.makeText(newProduct.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                            }
                        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                progressbar.setVisibility(View.INVISIBLE);
                                Task<Uri> urltask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                                @Override
                                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                                        if (!task.isSuccessful()) {
                                            progressbar.setVisibility(View.INVISIBLE);
                                            throw task.getException();
                                        }
                                        downloadUrl = filepath.getDownloadUrl().toString();
                                        return filepath.getDownloadUrl();
                                }
                                }).addOnCompleteListener(new OnCompleteListener() {
                                    @Override
                                    public void onComplete(@NonNull Task task) {
                                        if (task.isSuccessful()) {
                                            downloadUrl = task.getResult().toString();
                                            urllist.add(downloadUrl);
                                            progressbar.setVisibility(View.INVISIBLE);
                                            if (urllist.size() == count) {
                                                upip.setVisibility(View.INVISIBLE);
                                                Toast.makeText(newProduct.this, "All done", Toast.LENGTH_SHORT).show();
                                                addproductbtn.setVisibility(View.VISIBLE);
                                            }

                                        }
                                    }
                                });
                            }
                        });

                    }
                    newp_rv.setVisibility(View.VISIBLE);
                    adapter = new Edit_RVadapter(imglist, urilist,newProduct.this);
                    adapter.notifyDataSetChanged();
                    newp_rv.setAdapter(adapter);

                } else {
                    if (data.getData() != null) {
                        Toast.makeText(this, "Single image selected", Toast.LENGTH_SHORT).show();
                        imageuri = data.getData();
                        addproductbtn.setVisibility(View.INVISIBLE);
                        upip.setVisibility(View.VISIBLE);
                        inputproductimg.setImageURI(imageuri);
                        Calendar calender = Calendar.getInstance();
                        SimpleDateFormat currentdate = new SimpleDateFormat("MMM dd, yyyy");
                        SimpleDateFormat currenttime = new SimpleDateFormat("HH:mm:ss a");
                        savecurrentdate = currentdate.format(calender.getTime());
                        savecurrenttime = currenttime.format(calender.getTime());
                        productkey = savecurrentdate + savecurrenttime;
                        final StorageReference filepath = Pimagesref.child(gendercategoryname).child(categoryname)
                                .child(Pname + " " + productkey)
                                .child(this.imageuri.getLastPathSegment() + gendercategoryname + categoryname + productkey + ".jpg");
                        final UploadTask uploadTask = filepath.putFile(imageuri);
                        filepath.putFile(imageuri).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                String message = e.toString();
                                progressbar.setVisibility(View.INVISIBLE);
                                Toast.makeText(newProduct.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                            }
                        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                progressbar.setVisibility(View.INVISIBLE);
                                Task<Uri> urltask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                                    @Override
                                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                                        if (!task.isSuccessful()) {
                                            progressbar.setVisibility(View.INVISIBLE);
                                            throw task.getException();
                                        }
                                        downloadUrl = filepath.getDownloadUrl().toString();
                                        return filepath.getDownloadUrl();
                                    }
                                }).addOnCompleteListener(new OnCompleteListener() {
                                    @Override
                                    public void onComplete(@NonNull Task task) {
                                        if (task.isSuccessful()) {
                                            downloadUrl = task.getResult().toString();
                                            urllist.add(downloadUrl);
                                            upip.setVisibility(View.INVISIBLE);
                                            Toast.makeText(newProduct.this, "All done", Toast.LENGTH_SHORT).show();
                                            progressbar.setVisibility(View.INVISIBLE);
                                            addproductbtn.setVisibility(View.VISIBLE);
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
                       final StorageReference filepath = Pimagesref.child(Pname + " " + productkey)
                               .child(data.getData().getLastPathSegment() + Pname + " " + productkey + ".jpg");
                       final UploadTask uploadTask = filepath.putFile(uscuri);
                       filepath.putFile(uscuri).addOnFailureListener(new OnFailureListener() {
                           @Override
                           public void onFailure(@NonNull Exception e) {
                               String message = e.toString();

                               Toast.makeText(newProduct .this, "Error: " + message, Toast.LENGTH_SHORT).show();
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

                                           Toast.makeText(newProduct.this, "All done", Toast.LENGTH_SHORT).show();

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
        return super.onOptionsItemSelected(item);
    }
}







