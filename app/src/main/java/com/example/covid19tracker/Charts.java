package com.example.covid19tracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.DefaultAxisValueFormatter;
import com.github.mikephil.charting.formatter.DefaultValueFormatter;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Charts extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.charts);
        ChipNavigationBar bottomNavigationView = (ChipNavigationBar) findViewById(R.id.bottom_nav_bar);
        bottomNavigationView.setItemSelected(R.id.charts,true);
        bottomNavigationView.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
           @Override
           public void onItemSelected(int i) {
               switch (i){
                   case R.id.analytics:
                       startActivity(new Intent(getApplicationContext(),analytics.class));
                       finish();
                       overridePendingTransition(0,0);
                       break;
                   case R.id.home:
                       startActivity(new Intent(getApplicationContext(),MainActivity.class));
                       finish();
                       overridePendingTransition(0,0);
                       break;
                   case R.id.charts:

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
        // getting countries for spinner
        final Spinner spinner = findViewById(R.id.spinner_charts);
        ArrayList<String> countries = getCountries();
        countries.add("Select a country");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, R.layout.spinner_item,countries);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(arrayAdapter);
        final TextView current_country = findViewById(R.id.selected_country);
        current_country.setText("Selected country : Morocco (default)");
        // getting barcharts ids :
        final BarChart confirmedBarChart = findViewById(R.id.confirmed_bar);
        final BarChart recoveredBarChart = findViewById(R.id.recovered_bar);
        final BarChart deathsBarChart = findViewById(R.id.deaths_bar);
        // spinner item selected listener:
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (spinner.getSelectedItemPosition()!=0) {
                    current_country.setText("Selected country : "+spinner.getSelectedItem());
                    getValues(spinner.getSelectedItem().toString().toLowerCase(),confirmedBarChart,"Confirmed",
                            "#FFB259");
                    getValues(spinner.getSelectedItem().toString().toLowerCase(),recoveredBarChart,"Recovered",
                            "#4CD97B");
                    getValues(spinner.getSelectedItem().toString().toLowerCase(),deathsBarChart,"Deaths",
                            "#FF4C58");
                }
                else
                    current_country.setText("Selected country : Morocco (default)");
                getValues("morocco",confirmedBarChart,"Confirmed",
                        "#FFB259");
                getValues("morocco",recoveredBarChart,"Recovered",
                        "#4CD97B");
                getValues("morocco",deathsBarChart,"Deaths",
                        "#FF4C58");
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    private void getValues(String country, final BarChart barChart, final String status, final String color){
        RequestQueue mQueue = Volley.newRequestQueue(this);
        String url = "https://corona.azure-api.net/timeline/"+country;
        final ArrayList<BarEntry> barEntries = new ArrayList<>();
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            String[] labels = new String[response.length()];
                            for (int i =response.length()-1; i > response.length()-8;i--){
                                JSONObject object = (JSONObject) response.get(i);
                                String date = object.getString("Date");
                                SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
                                Date d = sdf.parse(date);
                                sdf.applyPattern("MMM dd");
                                String date1 = sdf.format(d);
                                int value =  object.getInt(status);
                                barEntries.add(new BarEntry(i,value));
                                labels[i] = date1;
                            }
                            BarDataSet barDataSet = new BarDataSet(barEntries,status);
                            barDataSet.setColors(ColorTemplate.rgb(color));
                            barDataSet.setDrawValues(false);
                            barDataSet.setValueTextSize(8f);
                            BarData barData = new BarData(barDataSet);
                            barData.setBarWidth(0.2f);
                            barData.setDrawValues(true);
                            barChart.setData(barData);
                            barChart.getDescription().setEnabled(false);
                            XAxis xAxis = barChart.getXAxis();
                            xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
                            xAxis.setLabelCount(7);
                            xAxis.setPosition(XAxis.XAxisPosition.TOP);
                            xAxis.setGranularity(1);
                            xAxis.setDrawAxisLine(false);
                            xAxis.setDrawGridLines(false);
                            barChart.getAxisRight().setDrawLabels(false);
                            barChart.animateY(1500);
                            barChart.setScaleEnabled(false);
                            barChart.invalidate();

                        } catch (Exception e) {
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

    private ArrayList<String> getCountries() {
        RequestQueue mQueue = Volley.newRequestQueue(this);
        final ArrayList<String> list = new ArrayList<>();
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