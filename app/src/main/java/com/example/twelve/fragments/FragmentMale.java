package com.example.twelve.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.twelve.Model.Categories;
import com.example.twelve.R;
import com.example.twelve.Adapters.RecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class FragmentMale extends Fragment {
    View v;
    private RecyclerView myrv2;
    RecyclerViewAdapter adapter;
    private List<Categories> lstcats;

    public FragmentMale(){
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.male_fragment, container, false);
        myrv2 = v.findViewById(R.id.rv2);
        adapter = new RecyclerViewAdapter(lstcats, getContext());
        myrv2.setLayoutManager(new LinearLayoutManager(getContext()));
        myrv2.getLayoutManager().setMeasurementCacheEnabled(false);
        myrv2.setAdapter(adapter);
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lstcats = new ArrayList<>();
        lstcats.add(new Categories("Accesories", "Sunglasses, Ties.", "Male", "Accesories", R.drawable.men_accesories));
        lstcats.add(new Categories("Activewear", "suitable for sports and outdoor activities", "Male", "Activewear", R.drawable.men_sportswear));
        lstcats.add(new Categories("Co-ords", "Two-piece clothing.", "Male", "Co-ords", R.drawable.mencoords));
        lstcats.add(new Categories("Jackets", "Denim, Biker Jackets.", "Male", "Jackets", R.drawable.men_jackets));
        lstcats.add(new Categories("Jeans", "Rip jeans, Drawstring.", "Male", "Jeans", R.drawable.men_jeans1));
        lstcats.add(new Categories("Joggers", "Drawstring.", "Male", "Joggers", R.drawable.men_joggers));
        lstcats.add(new Categories("Nativewear", "Tailored multiple piece clothing.", "Male", "Nativewear", R.drawable.men_native2));
        lstcats.add(new Categories("Pants/Chinos", "Plaid, Checkered, Striped pants.", "Male", "Chinos", R.drawable.men_pants));
        lstcats.add(new Categories("Shirts", "Plaid, Patterned, Striped.", "Male", "Shirts", R.drawable.men_shirts));
        lstcats.add(new Categories("Shorts", "Drawstring, Combat.", "Male", "Shorts", R.drawable.men_shorts));
        lstcats.add(new Categories("Sleepwear", "Robes, Pyjamas", "Male", "Sleepwear", R.drawable.male_pyjamas));
        lstcats.add(new Categories("Suits", "Tuxedos, Blazers, Coats", "Male", "Suits", R.drawable.male_suits));
        lstcats.add(new Categories("Sweatshirts", "Hoodies, Sweaters.", "Male", "Sweatshirts", R.drawable.men_sweats));
        lstcats.add(new Categories("T-shirts/Polo", "Plain, Patterned,  Printed.", "Male", "Tees", R.drawable.men_tees));
        lstcats.add(new Categories("Tracksuits", "Hooded, Drawstring.", "Male", "Tracksuit", R.drawable.men_track));
        lstcats.add(new Categories("Underwear", "Briefs, Trunks, Thongs.", "Male", "Underwear", R.drawable.men_underwear));
        lstcats.add(new Categories("Boots", "Safety boots, Rain boots.", "Male", "Boots", R.drawable.men_boots));
        lstcats.add(new Categories("Shoes", "Brogues, Loafers.", "Male", "Leathers", R.drawable.men_shoes));
        lstcats.add(new Categories("Slippers", "Flip flops, Slides. ", "Male", "Slides", R.drawable.men_slides));
        lstcats.add(new Categories("Sneakers", "High-Tops, Chunky soles.", "Male", "Sneakers", R.drawable.men_sneakers));

    }
}
