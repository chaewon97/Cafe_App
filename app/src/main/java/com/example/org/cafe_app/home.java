package com.example.org.cafe_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

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
        beverage_list_fragment = new Beverage_List_Fragment(this.getApplicationContext());

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
                        /*
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.home_layout, beverage_list_fragment).commitAllowingStateLoss();
                         */
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.home_layout, cafe_list_fragment).commitAllowingStateLoss();
                        return true;
                    }

                    default:
                        return false;
                }
            }
        });
    }

    public void replaceFragment(Fragment fragment, String id, String name){
        Bundle args = new Bundle();
        args.putString("cafe_id", id);
        args.putString("cafe_name", name);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragment.setArguments(args); // 데이터 넘기기
        fragmentTransaction.replace(R.id.home_layout, fragment).commit();
    }
}
