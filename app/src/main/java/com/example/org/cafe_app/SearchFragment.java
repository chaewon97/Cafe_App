package com.example.org.cafe_app;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

public class SearchFragment extends Fragment {

    ViewGroup viewGroup;
    private RecyclerView rcc_item;
    private ItemListAdapter mItemListAdapter;
    private Context context;
    private String user_id;
    EditText up, down, drink_name;
    TextView popul;
    Button btn_search;

    public SearchFragment(Context context) {this.context = context;}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_search, container, false);

        RecyclerView recyclerView = (RecyclerView)viewGroup.findViewById(R.id.search_list);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(context, 2);
        recyclerView.setLayoutManager(layoutManager);

        Bundle args = getArguments();
        if(args != null){
            this.user_id = args.getString("user_id");
        }

        up = (EditText)viewGroup.findViewById(R.id.start_price);
        down = (EditText)viewGroup.findViewById(R.id.end_price);
        drink_name = (EditText)viewGroup.findViewById(R.id.drink_name);


        btn_search = viewGroup.findViewById(R.id.btn_search);
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String up_price = up.getText().toString();
                String down_price = down.getText().toString();
                String name = drink_name.getText().toString();


                try {
                    CustomTask task = new CustomTask();
                    String result = task.execute(up_price, down_price, name).get();

                    JSONObject resultObject;
                    JSONArray resultObjectArray = new JSONArray(result);

                    ArrayList<ItemVO> items = new ArrayList<>();
                    if(resultObjectArray.length() != 0){
                        for(int i = 0 ; i < resultObjectArray.length(); i++){
                            resultObject = (JSONObject)resultObjectArray.get(i);

                            String item_name = resultObject.getString("name");
                            String item_id = resultObject.getString("id");
                            int price = Integer.parseInt(resultObject.getString("price"));

                            ItemVO item;
                            item = new ItemVO(R.drawable.coffee, item_name, item_id, price, 1);

                            items.add(item);
                        }
                    }

                    mItemListAdapter = new ItemListAdapter(context, items, R.layout.fragment_search);
                    mItemListAdapter.setOnItemClickListener(new ItemListAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, ItemVO itemVO) {
                            Intent intent = new Intent(context, OrderActivity.class);
                            intent.putExtra("user_id", user_id);
                            intent.putExtra("beverage_id", itemVO.getId());
                            getActivity().startActivity(intent);
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        return viewGroup;
    }

    class CustomTask extends AsyncTask<String,Void, String> {
        String sendMsg, receiveMsg;

        @Override
        protected String doInBackground(String... strings) {
            try {
                String str;
                URL url = new URL("http://192.168.0.4:8090/test_project/search.jsp");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestMethod("POST");
                OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());

                sendMsg = "up_price="+strings[0]+"&down_price="+strings[1]+"&name="+strings[2];
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
