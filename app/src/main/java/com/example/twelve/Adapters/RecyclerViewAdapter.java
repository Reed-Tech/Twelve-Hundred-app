package com.example.twelve.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.twelve.Category_Item_List;
import com.example.twelve.Model.Categories;
import com.example.twelve.R;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.myViewHolder> {
    List<Categories> lstcategories;
    Context mContext;

    public RecyclerViewAdapter(List<Categories> lstcategories, Context mContext) {
        this.lstcategories = lstcategories;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(mContext).inflate(R.layout.item_category,parent, false);
        myViewHolder vholder = new myViewHolder(v);
        return vholder;
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, final int position) {
        holder.cat_description.setText(lstcategories.get(position).getDescription());
        holder.cat_textview.setText(lstcategories.get(position).getName());
        holder.cat_imgview.setImageResource(lstcategories.get(position).getPhoto());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), Category_Item_List.class);
                intent.putExtra("gender", lstcategories.get(position).getGender());
                intent.putExtra("category", lstcategories.get(position).getCategory());
                view.getContext().startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return lstcategories.size();
    }

    public static class myViewHolder extends RecyclerView.ViewHolder{
        private ImageView cat_imgview;
        private TextView cat_textview, cat_description;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            cat_imgview = itemView.findViewById(R.id.cat_imgview);
            cat_textview = itemView.findViewById(R.id.cat_textview);
            cat_description = itemView.findViewById(R.id.cat_description);

        }
    }
}
