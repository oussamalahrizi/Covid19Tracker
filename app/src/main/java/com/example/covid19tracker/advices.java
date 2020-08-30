package com.example.covid19tracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

public class advices extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_advices);
        ChipNavigationBar bottomNavigationView = (ChipNavigationBar) findViewById(R.id.bottom_nav_bar);
        bottomNavigationView.setItemSelected(R.id.advices,true);
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
                           startActivity(new Intent(getApplicationContext(),Charts.class));
                           finish();
                           overridePendingTransition(0,0);
                           break;
                       case R.id.advices:

                           break;
                   }
               }
           }
        );
    }
}