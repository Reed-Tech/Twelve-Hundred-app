package com.example.twelve.Adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.twelve.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Edit_RVadapter extends RecyclerView.Adapter<Edit_RVadapter.Edit_VH> {
    List<String> imglist;
    List<Uri> uriList;
    Context cn;


    public Edit_RVadapter(List<String> imglist, List<Uri> uriList, Context cn) {
        this.imglist = imglist;
        this.uriList = uriList;
        this.cn = cn;
    }

    @NonNull
    @Override
    public Edit_VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(cn).inflate(R.layout.product_imglayout, parent, false);
        return new Edit_VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Edit_VH holder, int position) {
        if(uriList.size()==0) {
            Picasso.get().load(imglist.get(position)).into(holder.image);
        }else{
            holder.image.setImageURI(uriList.get(position));
        }
    }

    @Override
    public int getItemCount() {
        if(uriList.size()==0) {
            return imglist.size();
        }else{
            return uriList.size();
        }
    }

    public class Edit_VH extends RecyclerView.ViewHolder {
        private ImageView image;
        public Edit_VH(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
        }
    }
}
