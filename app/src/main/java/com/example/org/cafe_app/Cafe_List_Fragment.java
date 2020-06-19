package com.example.org.cafe_app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class Cafe_List_Fragment extends Fragment{
    ViewGroup viewGroup;
    private RecyclerView rcc_cafe;
    private CafeListAdapter mCafeAdapter;
    private Context context;

    public Cafe_List_Fragment(Context context){
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){

        viewGroup = (ViewGroup) inflater.inflate(R.layout.cafe_list_fragment, container, false);

        RecyclerView recyclerView = (RecyclerView)viewGroup.findViewById(R.id.cafe_list);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(context, 2);
        recyclerView.setLayoutManager(layoutManager);

        try {
            CustomTask task = new CustomTask();
            String result = task.execute().get();
            result = result.trim();

            Log.e("결과 ::", result);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        ArrayList<CafeVO> items = new ArrayList<>();
        CafeVO[] item = new CafeVO[5];

        item[0] = new CafeVO(R.drawable.logo,"starbucks");
        item[1] = new CafeVO(R.drawable.logo, "angel-in-us");
        item[2] = new CafeVO(R.drawable.logo, "hollys");
        item[3] = new CafeVO(R.drawable.logo, "#4");
        item[4] = new CafeVO(R.drawable.logo, "#5");


        for(int i = 0 ; i < 5 ; i++)
            items.add(item[i]);

        recyclerView.setAdapter(new CafeListAdapter(getActivity(), items, R.layout.cafe_list_fragment));

        return viewGroup;
    }

    class CustomTask extends AsyncTask<String,Void, String> {
        String sendMsg, receiveMsg;

        @Override
        protected String doInBackground(String... strings) {
            try {
                String str;
                URL url = new URL("http://172.20.10.2:8090/test_project/list.jsp");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestMethod("POST");
                OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());

                sendMsg = "";
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
