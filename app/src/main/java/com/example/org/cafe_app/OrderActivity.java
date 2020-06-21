package com.example.org.cafe_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class OrderActivity extends AppCompatActivity{

    Button btn_order, btn_order_list;
    ListView order_list;
    String user_id, beverage_id;
    private OrderListAdapter orderListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        Intent intent = getIntent();
        user_id = intent.getExtras().getString("user_id");
        beverage_id = intent.getExtras().getString("beverage_id");

        order_list = (ListView)findViewById(R.id.order_list);
        btn_order = (Button)findViewById(R.id.btn_order);
        btn_order.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    InsertTask task = new InsertTask();
                    String result = task.execute(user_id).get();

                    if(result.equals("true")){
                        Log.e("OrderResult :: ", "success");
                    }
                    else{
                        Log.e("OrderResult :: ", "fail");
                    }
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        btn_order_list = (Button)findViewById(R.id.btn_order_list);
        btn_order_list.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View view) {

                try{
                    ReadListTask task = new ReadListTask();
                    String result = task.execute(user_id, beverage_id).get();

                    JSONObject resultObject;
                    JSONArray resultObjectArray = new JSONArray(result);

                    ArrayList<OrderVO> items = new ArrayList<>();
                    if(resultObjectArray.length() != 0){

                        for(int i = 0 ; i < resultObjectArray.length() ; i++){
                            resultObject = (JSONObject)resultObjectArray.get(i);

                            String user_id = resultObject.getString("user_id");
                            String drink_id = resultObject.getString("drink_id");
                            String drink_price = resultObject.getString("drink_price");
                            String drink_name = resultObject.getString("drink_name");
                            final String order_id = resultObject.getString("order_id");

                            OrderVO order_Item = new OrderVO(drink_name, drink_price, drink_id, order_id, user_id);
                            order_Item.setOnClickListener(new View.OnClickListener(){

                                @Override
                                public void onClick(View view) {
                                    try{
                                        DeleteTask task = new DeleteTask();
                                        String result = task.execute(order_id).get();

                                        if(result.equals("true")){
                                            Log.e("DeleteResult :: ", "success");
                                        }
                                        else{
                                            Log.e("DeleteResult :: ", "fail");
                                        }
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    } catch (ExecutionException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                            items.add(order_Item);
                        }
                        orderListAdapter = new OrderListAdapter(items, R.layout.activity_order);
                        order_list.setAdapter(orderListAdapter);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });


    }

    class ReadListTask extends AsyncTask<String,Void, String> {
        String sendMsg, receiveMsg;

        @Override
        protected String doInBackground(String... strings) {
            try {
                String str;
                URL url = new URL("http://192.168.1.37:8090/test_project/orderlist.jsp");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestMethod("POST");
                OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());

                sendMsg = "user_id"+strings[0];
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

    class InsertTask extends AsyncTask<String,Void, String> {
        String sendMsg, receiveMsg;

        @Override
        protected String doInBackground(String... strings) {
            try {
                String str;
                URL url = new URL("http://192.168.1.37:8090/test_project/insertorder.jsp");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestMethod("POST");
                OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());

                sendMsg = "user_id="+strings[0]+"&drink_id="+strings[1];
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

    class DeleteTask extends AsyncTask<String,Void, String> {
        String sendMsg, receiveMsg;

        @Override
        protected String doInBackground(String... strings) {
            try {
                String str;
                URL url = new URL("http://192.168.1.37:8090/test_project/deleteorder.jsp");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestMethod("POST");
                OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());

                sendMsg = "order_id"+strings[0];
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
