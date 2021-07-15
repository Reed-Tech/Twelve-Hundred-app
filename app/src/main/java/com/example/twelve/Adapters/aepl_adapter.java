package com.example.twelve.Adapters;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.twelve.Admineditproductlist;
import com.example.twelve.CartActivity;
import com.example.twelve.DummyActivity;
import com.example.twelve.MainActivity;
import com.example.twelve.Model.HomeProducts;
import com.example.twelve.Prevalent.Prevalent;
import com.example.twelve.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.logging.LogRecord;

public class aepl_adapter extends RecyclerView.Adapter<aepl_adapter.aepl_VH> {
    private Context cont;
    private List<HomeProducts> pro;

    public aepl_adapter(Context cont, List<HomeProducts> pro) {
        this.cont = cont;
        this.pro = pro;
    }

    @NonNull
    @Override
    public aepl_VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(cont).inflate(R.layout.aeplproduct_imglayout, parent, false);
        return new aepl_VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull aepl_VH holder, final int position) {
        holder.name.setText(pro.get(position).getPname());
        holder.catname.setText(pro.get(position).getCategory());
        holder.price.setText(pro.get(position).getPrice());
        Picasso.get().load(pro.get(position).getImageurllist().get(1)).into(holder.img);


        holder.man_pr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dia;
                dia = new Dialog(cont);
                dia.setContentView(R.layout.manpro_dia);
                dia.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dia.setCancelable(false);
                TextView close_man = dia.findViewById(R.id.close_man);
                close_man.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dia.dismiss();
                    }
                });
                dia.show();

                final Dialog dib;
                dib = new Dialog(cont);
                dib.setContentView(R.layout.promoprice_dialog);
                dib.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dib.setCancelable(true);


                final TextView apfd = dia.findViewById(R.id.apfd);
                final TextView aptp = dia.findViewById(R.id.aptp);
                final TextView aptss = dia.findViewById(R.id.aptss);
                final TextView editp = dia.findViewById(R.id.editp);
                final TextView delp = dia.findViewById(R.id.delp);

                DatabaseReference dtaref = FirebaseDatabase.getInstance().getReference("Products");
                dtaref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        final HashMap<String, Object> values = new HashMap<>();
                        final String sname = snapshot.child(pro.get(position).getGender()).child(pro.get(position).getCategory()).
                                child(pro.get(position).getPname() + " " + pro.get(position).getPid()).child("Store").getValue().toString();

                        GenericTypeIndicator<List<String>> genericTypeIndicator = new GenericTypeIndicator<List<String>>() {
                        };
                        List<String> col = snapshot.child(pro.get(position).getGender()).child(pro.get(position).getCategory()).
                                child(pro.get(position).getPname() + " " + pro.get(position).getPid()).child("Colours").getValue(genericTypeIndicator);
                        List<String> des = snapshot.child(pro.get(position).getGender()).child(pro.get(position).getCategory()).
                                child(pro.get(position).getPname() + " " + pro.get(position).getPid()).child("Designs").getValue(genericTypeIndicator);
                        List<String> siz = snapshot.child(pro.get(position).getGender()).child(pro.get(position).getCategory()).
                                child(pro.get(position).getPname() + " " + pro.get(position).getPid()).child("Sizes").getValue(genericTypeIndicator);

                        values.put("pid", pro.get(position).getPid());
                        values.put("gender", pro.get(position).getGender());
                        values.put("date", snapshot.child(pro.get(position).getGender()).child(pro.get(position).getCategory()).
                                child(pro.get(position).getPname() + " " + pro.get(position).getPid()).child("date").getValue().toString());
                        values.put("time", snapshot.child(pro.get(position).getGender()).child(pro.get(position).getCategory()).
                                child(pro.get(position).getPname() + " " + pro.get(position).getPid()).child("time").getValue().toString());
                        values.put("Colours", col);
                        values.put("Colour Text", snapshot.child(pro.get(position).getGender()).child(pro.get(position).getCategory()).
                                child(pro.get(position).getPname() + " " + pro.get(position).getPid()).child("Colour Text").getValue().toString());
                        values.put("Designs", des);
                        values.put("Design Text", snapshot.child(pro.get(position).getGender()).child(pro.get(position).getCategory()).
                                child(pro.get(position).getPname() + " " + pro.get(position).getPid()).child("Design Text").getValue().toString());
                        values.put("Sizes", siz);
                        values.put("Sizes Text", snapshot.child(pro.get(position).getGender()).child(pro.get(position).getCategory()).
                                child(pro.get(position).getPname() + " " + pro.get(position).getPid()).child("Sizes Text").getValue().toString());
                        values.put("description", pro.get(position).getDescription());
                        values.put("Imageurllist", pro.get(position).getImageurllist());
                        values.put("Category", pro.get(position).getCategory());
                        values.put("Price", pro.get(position).getPrice());
                        values.put("Pname", pro.get(position).getPname());
                        values.put("Store", sname);

                        final Dialog dic;
                        dic = new Dialog(cont);
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

                        final Dialog did;
                        did = new Dialog(cont);
                        did.setContentView(R.layout.completed_dialog);
                        did.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        did.setCancelable(false);

                        apfd.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dib.show();
                                Button diadone = dib.findViewById(R.id.diadone);
                                Button diacancel = dib.findViewById(R.id.diacancel);
                                final EditText promoprice = dib.findViewById(R.id.promoprice);

                                diadone.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        if (TextUtils.isEmpty(promoprice.getText().toString())) {
                                            Toast.makeText(cont, "Please Input Promo Price", Toast.LENGTH_SHORT).show();
                                        } else {
                                            psg.setText("Are you sure you want to add this product to flash deals?");
                                            dic.show();
                                            diayes.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    close_cond.setEnabled(false);
                                                    diayes.setEnabled(false);
                                                    diano.setEnabled(false);
                                                    pwpb.setVisibility(View.VISIBLE);
                                                    values.put("Promo price", "NGN " + promoprice.getText().toString());
                                                    DatabaseReference dataref = FirebaseDatabase.getInstance().getReference("Flash Deals");
                                                    dataref.child(pro.get(position).getPname() + " " + pro.get(position).getPid()).updateChildren(values).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if (task.isSuccessful()) {
                                                                did.show();
                                                                dia.dismiss();
                                                                dib.dismiss();
                                                                dic.dismiss();
                                                                TextView close = did.findViewById(R.id.close_comd);
                                                                close.setOnClickListener(new View.OnClickListener() {
                                                                    @Override
                                                                    public void onClick(View view) {
                                                                        did.dismiss();
                                                                    }
                                                                });
                                                            } else {
                                                                dic.dismiss();
                                                                dib.dismiss();
                                                                Toast.makeText(cont, "Product was not added", Toast.LENGTH_SHORT).show();
                                                            }
                                                        }
                                                    });

                                                }
                                            });

                                            diano.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    dic.dismiss();
                                                    dib.dismiss();
                                                }
                                            });
                                        }


                                    }
                                });

                                diacancel.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        dib.dismiss();
                                    }
                                });
                            }
                        });

                        aptp.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                psg.setText("Are you sure you want to add this product to Promotion deals?");
                                dib.show();
                                Button diadone = dib.findViewById(R.id.diadone);
                                Button diacancel = dib.findViewById(R.id.diacancel);
                                final EditText promoprice = dib.findViewById(R.id.promoprice);

                                diadone.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        if (TextUtils.isEmpty(promoprice.getText().toString())) {
                                            Toast.makeText(cont, "Please Input Promo Price", Toast.LENGTH_SHORT).show();
                                        } else {
                                            dic.show();
                                            diayes.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    close_cond.setEnabled(false);
                                                    diayes.setEnabled(false);
                                                    diano.setEnabled(false);
                                                    pwpb.setVisibility(View.VISIBLE);
                                                    values.put("Promo price", "NGN " + promoprice.getText().toString());
                                                    DatabaseReference dataref = FirebaseDatabase.getInstance().getReference("Promotions");
                                                    dataref.child(pro.get(position).getPname() + " " + pro.get(position).getPid()).updateChildren(values).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if (task.isSuccessful()) {
                                                                did.show();
                                                                dia.dismiss();
                                                                dib.dismiss();
                                                                dic.dismiss();
                                                                TextView close = did.findViewById(R.id.close_comd);
                                                                close.setOnClickListener(new View.OnClickListener() {
                                                                    @Override
                                                                    public void onClick(View view) {
                                                                        did.dismiss();
                                                                    }
                                                                });
                                                            } else {
                                                                dic.dismiss();
                                                                dib.dismiss();
                                                                Toast.makeText(cont, "Product was not added", Toast.LENGTH_SHORT).show();
                                                            }
                                                        }
                                                    });

                                                }
                                            });

                                            diano.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    dic.dismiss();
                                                    dib.dismiss();
                                                }
                                            });
                                        }

                                    }
                                });

                                diacancel.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        dib.dismiss();
                                    }
                                });
                            }
                        });

                        aptss.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dib.show();
                                final Button diadone = dib.findViewById(R.id.diadone);
                                Button diacancel = dib.findViewById(R.id.diacancel);
                                final EditText promoprice = dib.findViewById(R.id.promoprice);

                                diadone.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        if (TextUtils.isEmpty(promoprice.getText().toString())) {
                                            Toast.makeText(cont, "Please Input Promo Price", Toast.LENGTH_SHORT).show();
                                        } else {
                                            psg.setText("Are you sure you want to add product to Seasonal deals?");
                                            dic.show();
                                            diayes.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    close_cond.setEnabled(false);
                                                    diayes.setEnabled(false);
                                                    diano.setEnabled(false);
                                                    pwpb.setVisibility(View.VISIBLE);
                                                    if (TextUtils.isEmpty(promoprice.getText().toString())) {
                                                        Toast.makeText(cont, "Please Input Promo Price", Toast.LENGTH_SHORT).show();
                                                    } else {
                                                        values.put("Promo price", "NGN " + promoprice.getText().toString());
                                                        DatabaseReference dataref = FirebaseDatabase.getInstance().getReference("Seasonal Deals");
                                                        dataref.child(pro.get(position).getPname() + " " + pro.get(position).getPid()).updateChildren(values).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if (task.isSuccessful()) {
                                                                    did.show();
                                                                    dia.dismiss();
                                                                    dib.dismiss();
                                                                    dic.dismiss();
                                                                    TextView close = did.findViewById(R.id.close_comd);
                                                                    close.setOnClickListener(new View.OnClickListener() {
                                                                        @Override
                                                                        public void onClick(View view) {
                                                                            did.dismiss();
                                                                        }
                                                                    });
                                                                } else {
                                                                    dic.dismiss();
                                                                    dib.dismiss();
                                                                    Toast.makeText(cont, "Product was not added", Toast.LENGTH_SHORT).show();
                                                                }
                                                            }
                                                        });
                                                    }
                                                }
                                            });

                                            diano.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    dic.dismiss();
                                                    dib.dismiss();
                                                }
                                            });
                                        }

                                    }
                                });

                                diacancel.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        dib.dismiss();
                                    }
                                });
                            }
                        });

                        delp.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                psg.setText("Are you sure you want to delete this product");
                                dic.show();
                                diayes.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(final View view) {
                                        pwpb.setVisibility(View.VISIBLE);
                                        Toast.makeText(cont, "Product will be removed from all promo deals (if existing) and product lists", Toast.LENGTH_LONG).show();
                                        DatabaseReference dtref = FirebaseDatabase.getInstance().getReference("Seasonal Deals");
                                        dtref.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                if (snapshot.hasChild(pro.get(position).getPname() + " " + pro.get(position).getPid())) {
                                                    DatabaseReference dataref = FirebaseDatabase.getInstance().getReference("Seasonal Deals");
                                                    dataref.child(pro.get(position).getPname() + " " + pro.get(position).getPid()).removeValue();
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        });

                                        DatabaseReference dtref1 = FirebaseDatabase.getInstance().getReference("Flash Deals");
                                        dtref1.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                if (snapshot.hasChild(pro.get(position).getPname() + " " + pro.get(position).getPid())) {
                                                    DatabaseReference dataref1 = FirebaseDatabase.getInstance().getReference("Flash Deals");
                                                    dataref1.child(pro.get(position).getPname() + " " + pro.get(position).getPid()).removeValue();
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        });

                                        DatabaseReference dtref2 = FirebaseDatabase.getInstance().getReference("Promotions");
                                        dtref2.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                if (snapshot.hasChild(pro.get(position).getPname() + " " + pro.get(position).getPid())) {
                                                    DatabaseReference dataref2 = FirebaseDatabase.getInstance().getReference("Promotions");
                                                    dataref2.child(pro.get(position).getPname() + " " + pro.get(position).getPid()).removeValue();
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        });

                                        DatabaseReference dtref3 = FirebaseDatabase.getInstance().getReference("Cart Items");
                                        dtref3.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                if (snapshot.hasChild(pro.get(position).getPname() + " " + pro.get(position).getPid())) {
                                                    DatabaseReference dataref3 = FirebaseDatabase.getInstance().getReference("Cart Items");
                                                    dataref3.child(pro.get(position).getPname() + " " + pro.get(position).getPid()).removeValue();
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        });

                                        DatabaseReference dtref4 = FirebaseDatabase.getInstance().getReference("HomeProducts");
                                        dtref4.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                if (snapshot.hasChild(pro.get(position).getPname() + " " + pro.get(position).getPid())) {
                                                    DatabaseReference dataref4 = FirebaseDatabase.getInstance().getReference("HomeProducts");
                                                    dataref4.child(pro.get(position).getPname() + " " + pro.get(position).getPid()).removeValue();
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        });

                                        DatabaseReference dtref5 = FirebaseDatabase.getInstance().getReference("Products");
                                        dtref5.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                if (snapshot.child(pro.get(position).getGender()).child(pro.get(position).getCategory()).
                                                        hasChild(pro.get(position).getPname() + " " + pro.get(position).getPid())) {
                                                    DatabaseReference dataref5 = FirebaseDatabase.getInstance().getReference("Products");
                                                    dataref5.child(pro.get(position).getGender()).child(pro.get(position).getCategory()).
                                                            child(pro.get(position).getPname() + " " + pro.get(position).getPid()).removeValue();
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        });

                                        DatabaseReference otref = FirebaseDatabase.getInstance().getReference("Stores");
                                        otref.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                for (DataSnapshot dsa : snapshot.getChildren()) {
                                                    if (dsa.child("storename").getValue().toString().equals(sname)) {
                                                        String path = dsa.child("storemail").getValue().toString().replace(".", "");
                                                        if (snapshot.child(path).child("Products").child(pro.get(position).getGender()).child(pro.get(position).getCategory()).
                                                                hasChild(pro.get(position).getPname() + " " + pro.get(position).getPid())) {
                                                            DatabaseReference dataref6 = FirebaseDatabase.getInstance().getReference("Stores");
                                                            dataref6.child(path).child("Products").child(pro.get(position).getGender()).child(pro.get(position).getCategory()).
                                                                    child(pro.get(position).getPname() + " " + pro.get(position).getPid()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                    if (task.isSuccessful()) {
                                                                        did.show();
                                                                        dia.dismiss();
                                                                        dib.dismiss();
                                                                        dic.dismiss();
                                                                        TextView close = did.findViewById(R.id.close_comd);
                                                                        close.setOnClickListener(new View.OnClickListener() {
                                                                            @Override
                                                                            public void onClick(View view) {
                                                                                did.dismiss();
                                                                            }
                                                                        });
                                                                    } else {
                                                                        dic.dismiss();
                                                                        Toast.makeText(cont, "Product was not deleted successfully", Toast.LENGTH_SHORT).show();
                                                                    }
                                                                }
                                                            });
                                                        }
                                                    }
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        });
                                    }
                                });

                                diano.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        dic.dismiss();
                                        dib.dismiss();
                                    }
                                });
                            }
                        });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return pro.size();
    }

    public class aepl_VH extends RecyclerView.ViewHolder {
        private Button man_pr;
        private ImageView img;
        private TextView name, catname, price;

        public aepl_VH(@NonNull View itemView) {
            super(itemView);
            man_pr = itemView.findViewById(R.id.aeplbtn_man_item);
            img = itemView.findViewById(R.id.aepl_imgview);
            name = itemView.findViewById(R.id.aeplrec_prdtname);
            catname = itemView.findViewById(R.id.aeplcategory);
            price = itemView.findViewById(R.id.aepl_price);
        }
    }
}
