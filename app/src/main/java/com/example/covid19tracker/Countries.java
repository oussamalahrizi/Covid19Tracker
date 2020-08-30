package com.example.covid19tracker;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;


public class Countries extends Fragment {
    View view;
    TabLayout tabLayout;
    ViewPager viewPager;

    public Countries() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ViewPageAdapter adapter = new ViewPageAdapter(getChildFragmentManager(),0);
        adapter.AddFragment(new countriesToday(),"Today");
        adapter.AddFragment(new countriesTotal(),"Total");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_countries, container, false);
        tabLayout = view.findViewById(R.id.tabs_days_countries);
        viewPager = view.findViewById(R.id.viewpager_inner_countries);
        return view;
    }
}