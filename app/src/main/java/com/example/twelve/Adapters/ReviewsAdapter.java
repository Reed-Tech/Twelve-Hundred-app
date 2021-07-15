package com.example.twelve.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.twelve.Model.Reviews;
import com.example.twelve.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ReviewsViewholder> {

    Context conte;
    List<Reviews> rev_list;

    public ReviewsAdapter(Context conte, List<Reviews> rev_list) {
        this.conte = conte;
        this.rev_list = rev_list;
    }

    @NonNull
    @Override
    public ReviewsViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ReviewsViewholder(LayoutInflater.from(conte).inflate(R.layout.revs_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final ReviewsViewholder holder, final int position) {
        DatabaseReference eref = FirebaseDatabase.getInstance().getReference("Users");
        eref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!snapshot.child(rev_list.get(position).getUseremail().replace("."," ")).child("profile_image").getValue().toString().equalsIgnoreCase("default")){
                    Picasso.get().load(rev_list.get(position).getPimg_url()).into(holder.pimg);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        holder.userfullname.setText(rev_list.get(position).getUserfullname());
        holder.username.setText(rev_list.get(position).getUsername());
        holder.rev_body.setText(rev_list.get(position).getRev_text());

        if(rev_list.get(position).getStar().equals("1")){
            holder.star2.setImageResource(R.drawable.ic_baseline_star_border_24);
            holder.star3.setImageResource(R.drawable.ic_baseline_star_border_24);
            holder.star4.setImageResource(R.drawable.ic_baseline_star_border_24);
            holder.star5.setImageResource(R.drawable.ic_baseline_star_border_24);
        }
        if(rev_list.get(position).getStar().equals("2")){
            holder.star3.setImageResource(R.drawable.ic_baseline_star_border_24);
            holder.star4.setImageResource(R.drawable.ic_baseline_star_border_24);
            holder.star5.setImageResource(R.drawable.ic_baseline_star_border_24);
        }
        if(rev_list.get(position).getStar().equals("3")){
            holder.star4.setImageResource(R.drawable.ic_baseline_star_border_24);
            holder.star5.setImageResource(R.drawable.ic_baseline_star_border_24);
        }
        if(rev_list.get(position).getStar().equals("4")){
            holder.star5.setImageResource(R.drawable.ic_baseline_star_border_24);
        }
    }

    @Override
    public int getItemCount() {
        return rev_list.size();
    }

    public class ReviewsViewholder extends RecyclerView.ViewHolder {
        private CircleImageView pimg;
        private TextView userfullname, username, rev_body;
        private ImageView star1, star2, star3, star4, star5;
        public ReviewsViewholder(@NonNull View itemView) {
            super(itemView);
            pimg = itemView.findViewById(R.id.pimg);
            userfullname = itemView.findViewById(R.id.userfullname);
            username = itemView.findViewById(R.id.username);
            rev_body = itemView.findViewById(R.id.rev_body);
            star1 = itemView.findViewById(R.id.star_1);
            star2 = itemView.findViewById(R.id.star_2);
            star3 = itemView.findViewById(R.id.star_3);
            star4 = itemView.findViewById(R.id.star_4);
            star5 = itemView.findViewById(R.id.star_5);
        }
    }
}
