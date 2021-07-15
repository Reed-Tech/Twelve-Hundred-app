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

public class userstoreAdapter extends RecyclerView.Adapter<userstoreAdapter.usa_ViewHolder> {
    List<HomeProducts> lei;
    Context cuo;

    public userstoreAdapter(List<HomeProducts> lei, Context cuo) {
        this.lei = lei;
        this.cuo = cuo;
    }

    @NonNull
    @Override
    public usa_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new usa_ViewHolder(LayoutInflater.from(cuo).inflate(R.layout.home_product_items, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull usa_ViewHolder holder, final int position) {
        holder.cardview_pname.setText(lei.get(position).getPname());
        holder.cardview_category.setText(lei.get(position).getCategory());
        holder.cardview_price.setText(lei.get(position).getPrice());
        Picasso.get().load(lei.get(position).getImageurllist().get(0)).into(holder.cardview_image);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), Products_Page_Activity.class);
                intent.putExtra("pid", lei.get(position).getPname()+" "+lei.get(position).getPid());
                intent.putExtra("gender", lei.get(position).getGender());
                intent.putExtra("category", lei.get(position).getCategory());
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return lei.size();
    }

    public class usa_ViewHolder extends RecyclerView.ViewHolder {
        public ImageView cardview_image;
        public TextView cardview_pname, cardview_category, cardview_price;
        public usa_ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardview_image = itemView.findViewById(R.id.rec_imgview);
            cardview_pname = itemView.findViewById(R.id.rec_textviewname);
            cardview_category = itemView.findViewById(R.id.rec_textviewcategory);
            cardview_price = itemView.findViewById(R.id.rec_textprice);
        }
    }
}
