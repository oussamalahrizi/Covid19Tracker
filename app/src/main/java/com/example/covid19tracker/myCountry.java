package com.example.covid19tracker;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;


public class myCountry extends Fragment {
    View view;
    TabLayout tabLayout;
    ViewPager viewPager;
    public myCountry() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_my_country, container, false);
        tabLayout = view.findViewById(R.id.tabs_days);
        viewPager = view.findViewById(R.id.viewpager_inner);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ViewPageAdapter adapter = new  ViewPageAdapter(getChildFragmentManager(),0);
        adapter.AddFragment(new myCountryToday(),"Today");
        adapter.AddFragment(new myCountryTotal(),"Total");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}