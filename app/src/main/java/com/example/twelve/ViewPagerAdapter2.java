package com.example.twelve;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter2 extends ViewPagerAdapter {
    private List<Fragment> lstfrag = new ArrayList<>();
    private List<String> lsttitle = new ArrayList<>();

    @Override
    public int getCount() {
        return lsttitle.size();
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return lstfrag.get(position);
    }

    public ViewPagerAdapter2(FragmentManager fm) {
        super(fm);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return lsttitle.get(position);
    }

    public void addfrag (Fragment fg, String title){
        lstfrag.add(fg);
        lsttitle.add(title);
    }
}
