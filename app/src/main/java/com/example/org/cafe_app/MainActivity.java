package com.example.org.cafe_app;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.InputDevice;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    EditText userID, userPW;
    Button register_btn, sign_in_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userID = (EditText)findViewById(R.id.et_id);
        userPW = (EditText)findViewById(R.id.et_pass);

        register_btn = (Button) findViewById(R.id.btn_register);
        register_btn.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent = new Intent( getApplicationContext(),
                        SignUpActivity.class);
                startActivity(intent);
            }
        });

        sign_in_btn = (Button) findViewById(R.id.btn_login);
        sign_in_btn.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {
                String id = userID.getText().toString();
                String pw = userPW.getText().toString();


                try {
                    CustomTask task = new CustomTask();
                    String result = task.execute(id, pw).get();

                    Log.i("result :: ",result);
                    result = result.trim();

                    if(result.equals("true")) {
                        Intent intent = new Intent(getApplicationContext(),
                                home.class);
                        intent.putExtra("user_id", id);
                        startActivity(intent);
                    }
                    else{
                        Log.i("결과 :: ","틀렸습니다.");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }

                Intent intent = new Intent(getApplicationContext(),
                        home.class);
                startActivity(intent);
            }
        });
    }

    class CustomTask extends AsyncTask<String,Void, String>{
        String sendMsg, receiveMsg;

        @Override
        protected String doInBackground(String... strings) {
            try {
                String str;

                URL url = new URL("http://192.168.1.37:8090/test_project/login.jsp");

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestMethod("POST");
                OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());

                sendMsg = "id=" + strings[0] + "&pw=" + strings[1];
                osw.write(sendMsg);
                osw.flush();

                if (conn.getResponseCode() == conn.HTTP_OK) {
                    InputStreamReader tmp = new InputStreamReader(conn.getInputStream());
                    BufferedReader reader = new BufferedReader(tmp);
                    StringBuffer buffer = new StringBuffer();

                    while ((str = reader.readLine()) != null) {
                        buffer.append(str);
                    }

                    receiveMsg = buffer.toString();
                } else {
                    Log.e("통신 결과", conn.getResponseCode() + "에러");
                }

            }catch(MalformedURLException e){
                e.printStackTrace();
            }catch (IOException e){
                e.printStackTrace();
            }
            return receiveMsg;
        }
    }
}
