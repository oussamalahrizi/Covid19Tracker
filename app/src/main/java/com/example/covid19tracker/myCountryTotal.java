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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONException;
import org.json.JSONObject;

public class myCountryTotal extends Fragment {
    View view;
    TabLayout tabLayout;
    TextView affected;
    TextView deaths;
    TextView recovered;
    TextView active;
    RequestQueue mQueue ;

    public myCountryTotal() {
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
        view = inflater.inflate(R.layout.fragment_my_country_total, container, false);
        affected = view.findViewById(R.id.affected_total_country);
        deaths = view.findViewById(R.id.deaths_total_country);
        recovered = view.findViewById(R.id.recovered_total_country);
        active = view.findViewById(R.id.active_total_country);
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
                            affected.setText(String.valueOf(object.getInt("Confirmed")));
                            deaths.setText(String.valueOf(object.getInt("Deaths")));
                            recovered.setText(String.valueOf(object.getInt("Recovered")));
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