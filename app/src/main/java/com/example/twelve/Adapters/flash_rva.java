package com.example.twelve.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.twelve.Model.HomeProducts;
import com.example.twelve.Model.Promos;
import com.example.twelve.Products_Page_Activity;
import com.example.twelve.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class flash_rva extends RecyclerView.Adapter<flash_rva.flash_viewHolder> {
    Context con;
    List<Promos> flash;

    public flash_rva(Context con, List<Promos> flash) {
        this.con = con;
        this.flash = flash;
    }

    @NonNull
    @Override
    public flash_viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vi;
        vi = LayoutInflater.from(con).inflate(R.layout.flash_layout, parent, false);
        return new flash_viewHolder(vi);
    }

    @Override
    public void onBindViewHolder(@NonNull flash_viewHolder holder, final int position) {
        holder.cardview_pname.setText(flash.get(position).getPname());
        holder.cardview_category.setText(flash.get(position).getCategory());
        holder.normprice.setText(flash.get(position).getPrice());
        if(flash.get(position).getPromo_price()!=null){
            holder.flash_price.setText(flash.get(position).getPromo_price());
        }
        holder.normprice.setPaintFlags(holder.normprice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);;
        Picasso.get().load(flash.get(position).getImageurllist().get(0)).into(holder.cardview_image);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), Products_Page_Activity.class);
                intent.putExtra("pid", flash.get(position).getPname()+" "+flash.get(position).getPid());
                intent.putExtra("gender", flash.get(position).getGender());
                intent.putExtra("category", flash.get(position).getCategory());
                intent.putExtra("mode", "promos");
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return flash.size();
    }

    public class flash_viewHolder extends RecyclerView.ViewHolder {
        public ImageView cardview_image;
        public TextView cardview_pname, cardview_category, normprice, flash_price;
        public flash_viewHolder(@NonNull View itemView) {
            super(itemView);
            cardview_image = itemView.findViewById(R.id.rec_imgview);
            cardview_pname = itemView.findViewById(R.id.rec_textviewname);
            cardview_category = itemView.findViewById(R.id.rec_textviewcategory);
            normprice = itemView.findViewById(R.id.norm_price);
            flash_price = itemView.findViewById(R.id.flash_price);
        }
    }
}
