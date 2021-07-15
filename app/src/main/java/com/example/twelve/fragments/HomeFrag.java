package com.example.twelve.fragments;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.twelve.Adapters.RecyclerAdapter;
import com.example.twelve.Model.HomeProducts;
import com.example.twelve.R;
import com.example.twelve.Store_page;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import io.paperdb.Paper;

public class HomeFrag extends Fragment {
    private ImageView banner1;

    private List<HomeProducts> listp;
    private RecyclerView srv1, srv2;
    private String smail, smail1, gender, category, pid;
    private DatabaseReference r;
    private LinearLayoutManager lm;
    private GridLayoutManager glm;
    private RecyclerAdapter rad;



    View v;
    public HomeFrag(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.home_frag, container, false);
        listp = new ArrayList<>();
        Paper.init(getContext());
        srv1 = v.findViewById(R.id.srv1);
        srv2 = v.findViewById(R.id.srv2);
        Bundle bd = getArguments();
        if(Paper.book().read("smail")!=""){
            smail = Paper.book().read("smail");
        }
        smail1 = smail.replace(".", "");
        gender = Paper.book().read("gender");
        category = Paper.book().read("category");
        pid = Paper.book().read("pid");

        banner1 = v.findViewById(R.id.banner);

        glm = new GridLayoutManager(getContext(), 2);
        srv2.setLayoutManager(glm);
        srv2.getLayoutManager().setMeasurementCacheEnabled(false);
        lm = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        srv1.setLayoutManager(lm);
        srv1.getLayoutManager().setMeasurementCacheEnabled(false);

        r = FirebaseDatabase.getInstance().getReference("Stores");
        r.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.child(smail1).hasChild("Banner") && !snapshot.child(smail1).child("Banner").getValue().toString().equals("")){
                    String bnr = snapshot.child(smail1).child("Banner").getValue().toString();
                    Picasso.get().load(bnr).into(banner1);
                }
                for(DataSnapshot ds : snapshot.child(smail1).child("Products").child(gender).getChildren()){
                    for(DataSnapshot ps : ds.getChildren()){
                        HomeProducts sp = ps.getValue(HomeProducts.class);
                        listp.add(sp);
                    }
                }
                rad = new RecyclerAdapter(listp, getContext());
                rad.notifyDataSetChanged();
                srv1.setAdapter(rad);

                srv2.setAdapter(rad);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return v;
    }

}
