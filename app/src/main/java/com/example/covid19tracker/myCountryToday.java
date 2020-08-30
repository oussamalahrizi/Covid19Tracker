package com.example.covid19tracker;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.util.Date;

public class myCountryToday extends Fragment {

    View view;
    TextView affected;
    TextView deaths;
    TextView recovered;
    TextView active;
    RequestQueue mQueue ;
    public myCountryToday() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mQueue = Volley.newRequestQueue(this.getContext());
        getData("morocco");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_my_country_today, container, false);
        affected = view.findViewById(R.id.affected_today_country);
        deaths = view.findViewById(R.id.deaths_today_country);
        recovered = view.findViewById(R.id.recovered_today_country);
        active = view.findViewById(R.id.active_today_country);
        return view;
    }

    public void getData(String country){
        String url = "https://corona.azure-api.net/country/"+country;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject object = response.getJSONObject("Summary");
                            affected.setText(String.valueOf(object.getInt("NewConfirmed")));
                            deaths.setText(String.valueOf(object.getInt("NewDeaths")));
                            recovered.setText(String.valueOf(object.getInt("NewRecovered")));
                            active.setText(String.valueOf(object.getInt("Active")));
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
    }

}