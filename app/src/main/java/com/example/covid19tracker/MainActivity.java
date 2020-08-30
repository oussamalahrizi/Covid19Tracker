package com.example.covid19tracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        ChipNavigationBar bottomNavigationView = (ChipNavigationBar) findViewById(R.id.bottom_nav_bar);
        bottomNavigationView.setItemSelected(R.id.home,true);
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
    }

    public void callNow(View view) {
        String yaqada = "tel:0801004747";
        Intent call_phone = new Intent(Intent.ACTION_DIAL);
        call_phone.setData(Uri.parse(yaqada));
        startActivity(call_phone);
    }

    public void open_faq_web(View view) {
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        builder = builder.setToolbarColor(Color.parseColor("#473F97"));
        CustomTabsIntent intent =  builder.build();
        intent.launchUrl(this, Uri.parse("http://www.covidmaroc.ma/Pages/conseilar.aspx"));
    }

    public void open_advice(View view){
        startActivity(new Intent(getApplicationContext(),advices.class));
        finish();
        overridePendingTransition(0,0);
    }

    public void call_emergency(View view) {
        String yaqada = "tel:141";
        Intent call_phone = new Intent(Intent.ACTION_DIAL);
        call_phone.setData(Uri.parse(yaqada));
        startActivity(call_phone);
    }
}