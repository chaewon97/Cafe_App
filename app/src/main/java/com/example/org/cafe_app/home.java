package com.example.org.cafe_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.Console;

public class home extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    Beverage_List_Fragment beverage_list_fragment;
    SearchFragment fragment_search;
    Cafe_List_Fragment cafe_list_fragment;
    String user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        //Create FragmentView
        cafe_list_fragment = new Cafe_List_Fragment(this.getApplicationContext());
        beverage_list_fragment = new Beverage_List_Fragment(this.getApplicationContext());
        fragment_search = new SearchFragment(this.getApplicationContext());

        Intent intent = getIntent();
        user_id = intent.getStringExtra("user_id");
        Log.e("로그인 아이디 :: ", user_id);

        //FirstView Fragment
        replaceFragment(cafe_list_fragment, user_id);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener(){

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {

                    case R.id.cafe_list_tab: {
                        replaceFragment(cafe_list_fragment, user_id);
                        return true;
                    }
                    case R.id.beverage_list_tab: {
                        replaceFragment(fragment_search, user_id);
                        return true;
                    }

                    default:
                        return false;
                }
            }
        });
    }

    public void replaceFragment(Fragment fragment, String id, String name, String userId){
        Bundle args = new Bundle();
        args.putString("cafe_id", id);
        args.putString("cafe_name", name);
        args.putString("user_id", userId);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragment.setArguments(args); // 데이터 넘기기
        fragmentTransaction.replace(R.id.home_layout, fragment).commit();
    }

    public void replaceFragment(Fragment fragment, String userId){
        Bundle args = new Bundle();
        args.putString("user_id", userId);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragment.setArguments(args); // 데이터 넘기기
        fragmentTransaction.replace(R.id.home_layout, fragment).commit();
    }

}
