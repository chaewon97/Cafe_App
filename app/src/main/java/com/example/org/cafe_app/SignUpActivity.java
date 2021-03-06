package com.example.org.cafe_app;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
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

public class SignUpActivity extends AppCompatActivity {

    EditText id, pw, phone, name;
    Button btn_ok;
    public static Activity signupActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        signupActivity = SignUpActivity.this;

        id = (EditText)findViewById(R.id.id);
        pw = (EditText)findViewById(R.id.password);
        phone = (EditText)findViewById(R.id.phoneNumber);
        name = (EditText)findViewById(R.id.name);

        btn_ok = (Button)findViewById(R.id.btn_ok);
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String userid = id.getText().toString();
                String userpw = pw.getText().toString();
                String phoneNumber = phone.getText().toString();
                String userName = name.getText().toString();

                try{
                 CustomTask task = new CustomTask();
                 Log.e("결과 :: ","들어옴");
                 String result = task.execute(userid, userpw, userName, phoneNumber).get();
                 Log.e("결과 :: ",result);

                 result = result.trim();

                 if(result.equals("true")){
                    signupActivity.finish();
                 }
                 else{
                    Log.e("결과 :: ","저장에 실패함");
                 }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    class CustomTask extends AsyncTask<String,Void, String> {
        String sendMsg, receiveMsg;

        @Override
        protected String doInBackground(String... strings) {
            try {
                String str;
                URL url = new URL("http://192.168.1.37:8090/test_project/signup.jsp");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestMethod("POST");
                OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());

                sendMsg = "id=" + strings[0] + "&pw=" + strings[1] + "&name=" + strings[2] + "&phonenumber=" + strings[3];
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

