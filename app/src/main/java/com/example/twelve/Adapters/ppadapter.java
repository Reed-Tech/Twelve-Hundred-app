package com.example.twelve.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.twelve.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ppadapter extends RecyclerView.Adapter<ppadapter.ppviewholder> {
    Context ctt;
    List<String> piclist;

    public ppadapter(Context ctt, List<String> piclist) {
        this.ctt = ctt;
        this.piclist = piclist;
    }

    @NonNull
    @Override
    public ppviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ppviewholder(LayoutInflater.from(ctt).inflate(R.layout.pp_piclist, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ppviewholder holder, int position) {
        Picasso.get().load(piclist.get(position)).into(holder.picholder);
    }

    @Override
    public int getItemCount() {
        return piclist.size();
    }

    public class ppviewholder extends  RecyclerView.ViewHolder {
        private ImageView picholder;
        public ppviewholder(@NonNull View itemView) {
            super(itemView);
            picholder = itemView.findViewById(R.id.picholder);
        }
    }
}
