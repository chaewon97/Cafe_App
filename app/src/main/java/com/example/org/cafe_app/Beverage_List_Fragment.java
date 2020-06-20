package com.example.org.cafe_app;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.shape.CutCornerTreatment;

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

public class Beverage_List_Fragment extends Fragment {
    ViewGroup viewGroup;
    private RecyclerView rcc_item;
    private Context context;
    private String id;
    private String title;
    TextView cafe_title;


    public Beverage_List_Fragment(Context context){ this.context = context;}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        viewGroup = (ViewGroup) inflater.inflate(R.layout.beverage_list_fragment, container, false);

        cafe_title = (TextView) getView().findViewById(R.id.cafe_name);
        cafe_title.setText(title);

        RecyclerView recyclerView = (RecyclerView)viewGroup.findViewById(R.id.item_list);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(context, 2);
        recyclerView.setLayoutManager(layoutManager);

        Bundle args = getArguments();
        if(args != null){
            this.id = args.getString("cafe_id");
            this.title = args.getString("cafe_name");
        }

        try{
            CustomTask task = new CustomTask();
            String result = task.execute(this.id).get();

            JSONObject resultObject;
            JSONArray resultObjectArray = new JSONArray(result);

            ArrayList<ItemVO> items = new ArrayList<>();
            if(resultObjectArray.length() != 0){
                for(int i = 0 ; i < resultObjectArray.length() ; i++){
                    resultObject = (JSONObject)resultObjectArray.get(i);

                    String name = resultObject.getString("name");
                    String id = resultObject.getString("id");
                    int price = Integer.parseInt(resultObject.getString("price"));

                    ItemVO item = new ItemVO(R.drawable.logo, name, id, price);
                    items.add(item);
                }
            }
            recyclerView.setAdapter(new ItemListAdapter(getActivity(), items, R.layout.beverage_list_fragment));

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return viewGroup;
    }

    class CustomTask extends AsyncTask<String,Void, String> {
        String sendMsg, receiveMsg;

        @Override
        protected String doInBackground(String... strings) {
            try {
                String str;
                URL url = new URL("http://192.168.0.4:8090/test_project/food.jsp");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestMethod("POST");
                OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());

                sendMsg = "id="+strings[0];
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
