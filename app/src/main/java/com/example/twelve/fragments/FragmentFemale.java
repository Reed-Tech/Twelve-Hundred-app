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

public class FragmentFemale extends Fragment {
    View v;
    private RecyclerView myrv2;
    RecyclerViewAdapter adapter;
    private List<Categories> lstcats;

    public FragmentFemale(){
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.female_fragment, container, false);
        myrv2 = v.findViewById(R.id.femalefragrv);
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
        lstcats.add(new Categories("Accesories", "Hats, Scarves, Bags.", "Female", "Accesories", R.drawable.female_accesories));
        lstcats.add(new Categories("Activewear", "suitable for sports and outdoor activities.", "Female", "Activewear", R.drawable.female_sportswear));
        lstcats.add(new Categories("Co-ords", "Two-piece clothing.", "Female", "Co-ords", R.drawable.female_coords));
        lstcats.add(new Categories("Dresses", "Gowns, Native dresses, Corperate dresses.", "Female", "Dresses", R.drawable.female_dresses));
        lstcats.add(new Categories("Jackets", "Denim, Biker Jackets.", "Female", "Jackets", R.drawable.female_jackets));
        lstcats.add(new Categories("Jeans", "Rip jeans, Drawstring.", "Female", "Jeans", R.drawable.female_jeans));
        lstcats.add(new Categories("Joggers", "Drawstring.", "Female", "Joggers", R.drawable.female_joggers));
        lstcats.add(new Categories("Jumpsuits", "Pallazos, Short. ", "Female", "Jumpsuits", R.drawable.female_jumpsuits));
        lstcats.add(new Categories("Kimonos", "Plaid, Florals.", "Female", "Kimonos", R.drawable.female_kimonos));
        lstcats.add(new Categories("Lingeries", "Netted, Stockings.", "Female", "Lingeries", R.drawable.female_lingeries));
        lstcats.add(new Categories("Pants", "Plaid, Striped.", "Female", "Pants", R.drawable.female_lingeries));
        lstcats.add(new Categories("Shirts", "Plaid, Patterned, Striped.", "Female", "Shirts", R.drawable.female_shirts));
        lstcats.add(new Categories("Shorts", "Drawstring, Combat.", "Female", "Shorts", R.drawable.female_shorts));
        lstcats.add(new Categories("Skirts", "Denim, Florals.", "Female", "Skirts", R.drawable.female_skirts));
        lstcats.add(new Categories("Sweatshirts", "Hoodies, Sweaters.", "Female", "Sweatshirts", R.drawable.female_sweats1));
        lstcats.add(new Categories("Tops/Polo", "Plain, Patterned, Printed.", "Female", "Tees", R.drawable.female_tees));
        lstcats.add(new Categories("Tracksuits", "Hooded, Drawstring.", "Female", "Tracksuits", R.drawable.female_tracksuits));
        lstcats.add(new Categories("Underwear", "Panties, Bras.", "Female", "Underwear", R.drawable.female_underwear));
        lstcats.add(new Categories("Boots", "Safety boots, Rain boots.", "Female", "Boots", R.drawable.female_boots));
        lstcats.add(new Categories("Heels", "Wedges.", "Female", "Heels and Flats", R.drawable.female_heelsss));
        lstcats.add(new Categories("Sandals", "Buckle, Leather. ", "Female", "Sandals", R.drawable.female_sandals));
        lstcats.add(new Categories("Sneakers", "High-Tops, Chunky soles.", "Female", "Sneakers", R.drawable.men_sneakers));
    }
}
