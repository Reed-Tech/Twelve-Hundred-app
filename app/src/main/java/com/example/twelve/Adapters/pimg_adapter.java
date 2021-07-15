package com.example.twelve.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.twelve.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class pimg_adapter extends RecyclerView.Adapter<pimg_adapter.pimg_VH> {
    Context cx;
    List<String> picslist;

    public pimg_adapter(Context cx, List<String> picslist) {
        this.cx = cx;
        this.picslist = picslist;
    }

    @NonNull
    @Override
    public pimg_VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new pimg_VH(LayoutInflater.from(cx).inflate(R.layout.pimg_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull pimg_VH holder, int position) {
        Picasso.get().load(picslist.get(position)).into(holder.iv);
        holder.txtv.setText((position+1) +"/"+ picslist.size());
    }

    @Override
    public int getItemCount() {
        return picslist.size();
    }

    public class pimg_VH extends RecyclerView.ViewHolder {
        private ImageView iv;
        private TextView txtv;
        public pimg_VH(@NonNull View itemView) {
            super(itemView);

            iv = itemView.findViewById(R.id.pimg_pic);
            txtv = itemView.findViewById(R.id.pos_num);
        }
    }
}
