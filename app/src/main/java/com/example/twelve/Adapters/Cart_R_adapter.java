package com.example.twelve.Adapters;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.twelve.CartActivity;
import com.example.twelve.Model.CartItems;
import com.example.twelve.Prevalent.Prevalent;
import com.example.twelve.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Cart_R_adapter extends RecyclerView.Adapter<Cart_R_adapter.ViewHolder> {
    Context vContext;
    List<CartItems> Clist;

    public Cart_R_adapter(Context vContext, List<CartItems> clist) {
        this.vContext = vContext;
        Clist = clist;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(vContext).inflate(R.layout.cart_items, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.cart_pname.setText(Clist.get(position).getProductname());
        holder.cart_date.setText(Clist.get(position).getDate());
        holder.cart_pquantity.setText("Quantity: " + Clist.get(position).getQuantity());
        holder.cart_pprice.setText("Total price: " + Clist.get(position).getPrice() + " x "
                + Clist.get(position).getQuantity() + " = " + Clist.get(position).getTprice());
        Picasso.get().load(Clist.get(position).getImages().get(0)).placeholder(R.drawable.error_pic).into(holder.cart_image);

        holder.remove_item_ic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dic;
                dic = new Dialog(vContext);
                dic.setContentView(R.layout.confirmation_dialog);
                dic.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dic.setCancelable(false);
                final Button diayes = dic.findViewById(R.id.diayes);
                final Button diano = dic.findViewById(R.id.diano);
                final TextView psg = dic.findViewById(R.id.psg);
                final LinearLayout pwpb = dic.findViewById(R.id.lll1);
                final TextView close_cond = dic.findViewById(R.id.close_cond);
                close_cond.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dic.dismiss();
                    }
                });
                psg.setText("Do you want to remove item from cart?");
                dic.show();

                final Dialog did;
                did = new Dialog(vContext);
                did.setContentView(R.layout.completed_dialog);
                did.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                did.setCancelable(false);
                TextView close = did.findViewById(R.id.close_comd);
                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        did.dismiss();
                    }
                });

                diayes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View view) {
                        diayes.setEnabled(false);
                        diano.setEnabled(false);
                        close_cond.setEnabled(false);
                        pwpb.setVisibility(View.VISIBLE);
                        final DatabaseReference cref = FirebaseDatabase.getInstance().getReference("Cart List").child("User View").
                                child(Prevalent.currentOnlineUser.getEmail().replace(".", "")).child("products")
                                .child(Clist.get(position).getPid());

                        final DatabaseReference Aref = FirebaseDatabase.getInstance().getReference("Cart List").child("Admin View").
                                child(Prevalent.currentOnlineUser.getEmail().replace(".", "")).child("products")
                                .child(Clist.get(position).getPid());

                        cref.removeValue();

                        Aref.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    did.show();
                                    dic.dismiss();
                                    view.getContext().startActivity(new Intent(view.getContext(), CartActivity.class));
                                    ((Activity) view.getContext()).finish();
                                } else {
                                    dic.dismiss();
                                    Toast.makeText(vContext, "Product was not removed from cart", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                    }
                });

                diano.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dic.dismiss();
                    }
                });
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return Clist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView cart_image, remove_item_ic;
        private TextView cart_pname, cart_date, cart_pquantity, cart_pprice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cart_image = itemView.findViewById(R.id.cart_pimage);
            cart_pname = itemView.findViewById(R.id.cart_pname);
            cart_date = itemView.findViewById(R.id.cart_pdate);
            cart_pquantity = itemView.findViewById(R.id.cart_pquantity);
            cart_pprice = itemView.findViewById(R.id.cart_total_price);
            remove_item_ic = itemView.findViewById(R.id.remove_item_ic);
        }
    }
}
