package com.example.twelve.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.twelve.Model.HomeProducts;
import com.example.twelve.Prevalent.Prevalent;
import com.example.twelve.Products_Page_Activity;
import com.example.twelve.R;
import com.example.twelve.Store_All_Items;
import com.example.twelve.Store_Dashboard;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class Store_All_items_Recycler_View extends RecyclerView.Adapter<Store_All_items_Recycler_View.ViewH>{
    private Context Ucontext;
    private List<HomeProducts> loist = new ArrayList<>();

    public Store_All_items_Recycler_View(Context ucontext, List<HomeProducts> loist) {
        Ucontext = ucontext;
        this.loist = loist;
    }

    @NonNull
    @Override
    public ViewH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(Ucontext).inflate(R.layout.store_all_items, parent, false);
        return new ViewH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewH holder, final int position) {

        holder.cardview_pname.setText(loist.get(position).getPname());
        holder.cardview_category.setText(loist.get(position).getCategory());
        holder.cardview_price.setText(loist.get(position).getPrice());
        holder.del_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                DatabaseReference sref = FirebaseDatabase.getInstance().getReference("Stores");
                DatabaseReference tref = FirebaseDatabase.getInstance().getReference("Products");
                DatabaseReference uref = FirebaseDatabase.getInstance().getReference("HomeProducts");
                final String pname = loist.get(position).getPname();
                final String pid = loist.get(position).getPid();
                final String gender = loist.get(position).getGender();
                final String category = loist.get(position).getCategory();
                String path = Prevalent.currentOnlineStore.getStoremail().replace(".", "");

                sref.child(path).child("Products").child(gender).child(category).child(pname+" "+pid).removeValue();
                tref.child(gender).child(category).child(pname+" "+pid).removeValue();
                //it switches just after this line with obviously no code for it to switch
                uref.child(pname+" "+pid).removeValue();

            }
        });
        Picasso.get().load(loist.get(position).getImageurllist().get(0)).into(holder.cardview_image);

        holder.edit_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String pname = loist.get(position).getPname();
                final String pid = loist.get(position).getPid();
                final String path2 = pname+" "+pid;
                final String  cat = loist.get(position).getCategory();
                final String gender = loist.get(position).getGender();


                Intent intent = new Intent(view.getContext(), Products_Page_Activity.class);
                intent.putExtra("pid", path2);
                intent.putExtra("prev_activity", "store");
                intent.putExtra("category", cat);
                intent.putExtra("gender", gender);
                view.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return loist.size();
    }

    public class ViewH extends RecyclerView.ViewHolder {
        public ImageView cardview_image;
        public TextView cardview_pname, cardview_category, cardview_price;
        private Button del_item, edit_item;

        public ViewH(@NonNull View itemView) {
            super(itemView);
            cardview_image = itemView.findViewById(R.id.rec_imgview);
            cardview_pname = itemView.findViewById(R.id.rec_textviewname);
            cardview_category = itemView.findViewById(R.id.rec_textviewcategory);
            cardview_price = itemView.findViewById(R.id.rec_textprice);
            del_item = itemView.findViewById(R.id.btn_del_item);
            edit_item = itemView.findViewById(R.id.btn_edit_item);
        }
    }

}


