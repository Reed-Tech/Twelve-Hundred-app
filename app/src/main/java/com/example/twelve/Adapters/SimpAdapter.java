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

import com.example.twelve.Model.HomeProducts;
import com.example.twelve.Products_Page_Activity;
import com.example.twelve.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SimpAdapter extends RecyclerView.Adapter<SimpAdapter.SimpViewHolder> {
    private Context cc;
    private List<HomeProducts> hpr;

    public SimpAdapter(Context cc, List<HomeProducts> hpr) {
        this.cc = cc;
        this.hpr = hpr;
    }

    @NonNull
    @Override
    public SimpViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(cc).inflate(R.layout.home_product_items, parent, false);
        return new SimpViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SimpViewHolder holder, final int position) {
        holder.cardview_pname.setText(hpr.get(position).getPname());
        holder.cardview_category.setText(hpr.get(position).getCategory());
        holder.cardview_price.setText(hpr.get(position).getPrice());
        Picasso.get().load(hpr.get(position).getImageurllist().get(0)).into(holder.cardview_image);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), Products_Page_Activity.class);
                intent.putExtra("pid", hpr.get(position).getPname()+" "+hpr.get(position).getPid());
                intent.putExtra("gender", hpr.get(position).getGender());
                intent.putExtra("category", hpr.get(position).getCategory());
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return hpr.size();
    }

    public class SimpViewHolder extends RecyclerView.ViewHolder{
        public ImageView cardview_image;
        public TextView cardview_pname, cardview_category, cardview_price;
        public SimpViewHolder(@NonNull View itemView) {
            super(itemView);
            cardview_image = itemView.findViewById(R.id.rec_imgview);
            cardview_pname = itemView.findViewById(R.id.rec_textviewname);
            cardview_category = itemView.findViewById(R.id.rec_textviewcategory);
            cardview_price = itemView.findViewById(R.id.rec_textprice);
        }
    }
}
