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

import com.example.twelve.HomeActivity;
import com.example.twelve.Model.HomeProducts;
import com.example.twelve.Products_Page_Activity;
import com.example.twelve.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    List<HomeProducts> lstproducts;
    Context ncontext;

    public RecyclerAdapter(List<HomeProducts> lstproducts, Context ncontext) {
        this.lstproducts = lstproducts;
        this.ncontext = ncontext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(ncontext).inflate(R.layout.home_product_items, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.cardview_pname.setText(lstproducts.get(position).getPname());
        holder.cardview_category.setText(lstproducts.get(position).getCategory());
        holder.cardview_price.setText(lstproducts.get(position).getPrice());
        Picasso.get().load(lstproducts.get(position).getImageurllist().get(0)).into(holder.cardview_image);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), Products_Page_Activity.class);
                intent.putExtra("pid", lstproducts.get(position).getPname()+" "+lstproducts.get(position).getPid());
                intent.putExtra("gender", lstproducts.get(position).getGender());
                intent.putExtra("category", lstproducts.get(position).getCategory());
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return lstproducts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView cardview_image;
        public TextView cardview_pname, cardview_category, cardview_price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardview_image = itemView.findViewById(R.id.rec_imgview);
            cardview_pname = itemView.findViewById(R.id.rec_textviewname);
            cardview_category = itemView.findViewById(R.id.rec_textviewcategory);
            cardview_price = itemView.findViewById(R.id.rec_textprice);

        }
    }
}
