package com.example.org.cafe_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.Console;

public class home extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    Beverage_List_Fragment beverage_list_fragment;
    Cafe_List_Fragment cafe_list_fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        //Create FragmentView
        cafe_list_fragment = new Cafe_List_Fragment(this.getApplicationContext());
        beverage_list_fragment = new Beverage_List_Fragment();

        //FirstView Fragment
        getSupportFragmentManager().beginTransaction().replace(R.id.home_layout, cafe_list_fragment).commitAllowingStateLoss();

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener(){

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {

                    case R.id.cafe_list_tab: {
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.home_layout, cafe_list_fragment).commitAllowingStateLoss();

                        return true;
                    }
                    case R.id.beverage_list_tab: {
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.home_layout, beverage_list_fragment).commitAllowingStateLoss();

                        return true;
                    }

                    default:
                        return false;
                }
            }
        });
    }
}
