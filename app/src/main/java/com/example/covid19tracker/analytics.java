package com.example.covid19tracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;


public class analytics extends AppCompatActivity{
    public static String country_selected;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_analytics);
        ChipNavigationBar bottomNavigationView = (ChipNavigationBar) findViewById(R.id.bottom_nav_bar);
        bottomNavigationView.setItemSelected(R.id.analytics,true);
        bottomNavigationView.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
           public void onItemSelected(int i) {
               switch (i){
                   case R.id.analytics:
                       break;
                   case R.id.home:
                       startActivity(new Intent(getApplicationContext(),MainActivity.class));
                       finish();
                       overridePendingTransition(0,0);
                       break;
                   case R.id.charts:
                       startActivity(new Intent(getApplicationContext(),Charts.class));
                       finish();
                       overridePendingTransition(0,0);
                       break;
                   case R.id.advices:
                       startActivity(new Intent(getApplicationContext(),advices.class));
                       finish();
                       overridePendingTransition(0,0);
                       break;
               }
            }
        }
        );
        // get updates text views
        final TextView current_country = findViewById(R.id.current_country);
        //spinner
        final ArrayList<String> countries = getCountries();
        countries.add("Select a country");
        final Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,R.layout.spinner_item,countries);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(arrayAdapter);
        spinner.setVisibility(View.GONE);
        current_country.setText("Current country : Morocco");
        // General Tab
        TabLayout tabLayout = findViewById(R.id.tabs);
        ViewPager viewPagerGeneral = findViewById(R.id.viewpager_general);
        final ViewPageAdapter adapterGeneral = new ViewPageAdapter(getSupportFragmentManager(),0);
        adapterGeneral.AddFragment(new myCountry(),"My Country");
        adapterGeneral.AddFragment(new Countries(),"Other Countries");
        viewPagerGeneral.setAdapter(adapterGeneral);
        tabLayout.setupWithViewPager(viewPagerGeneral);
        // spinner and tabs listeners
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getPosition()==0){
                    spinner.setVisibility(View.GONE);
                    current_country.setText("Current country : Morocco");
                }
                if (tab.getPosition()==1){
                    spinner.setVisibility(View.VISIBLE);
                    if (spinner.getSelectedItemPosition()==0){
                        current_country.setText("Current country : Morocco (default)");
                    }
                    else {
                        current_country.setText("Current country : "+spinner.getSelectedItem().toString());
                    }
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String s = spinner.getSelectedItem().toString();
                if(!s.equals("Select a country")){
                    country_selected = s.toLowerCase();
                    adapterGeneral.notifyDataSetChanged();
                }
                if (s.equals("Select a country")){
                    country_selected = "morocco";
                    adapterGeneral.notifyDataSetChanged();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private ArrayList<String> getCountries() {
        final ArrayList<String> list = new ArrayList<>();
        RequestQueue mQueue = Volley.newRequestQueue(this);
        String url = "https://corona.azure-api.net/country";
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for(int i=0;i<response.length();i++){
                                JSONObject object = (JSONObject) response.get(i);
                                list.add(object.getString("Country"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        mQueue.add(request);
        return list;
    }
}