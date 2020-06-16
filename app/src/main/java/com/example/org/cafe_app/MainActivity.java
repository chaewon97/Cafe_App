package com.example.org.cafe_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button register_btn = (Button) findViewById(R.id.btn_register);
        register_btn.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent = new Intent( getApplicationContext(),
                        SignUpActivity.class);
                startActivity(intent);
            }
        });

        Button sign_in_btn = (Button) findViewById(R.id.btn_login);
        sign_in_btn.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( getApplicationContext(),
                        home.class);
                startActivity(intent);
            }
        });
    }
}
