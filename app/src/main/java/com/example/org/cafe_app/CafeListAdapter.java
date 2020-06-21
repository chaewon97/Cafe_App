package com.example.org.cafe_app;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
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

public class CafeListAdapter extends RecyclerView.Adapter<CafeListAdapter.ViewHolder>{
    private Context mContext;
    private ArrayList<CafeVO> cafe_list;
    int item_layout;


    public CafeListAdapter(Context context, ArrayList<CafeVO> items, int item_layout){

        this.mContext = context;
        this.cafe_list = items;
        this.item_layout = item_layout;
    }

    public OnItemClickListener mOnItemClickListener = null;


    public interface OnItemClickListener{
        void onItemClick(View view, CafeVO cafeVO);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mOnItemClickListener = listener;
    }

    public CafeListAdapter(Context mContext, ArrayList<CafeVO> cafe_list){
        this.mContext = mContext;
        this.cafe_list = cafe_list;
    }


    @Override
    public CafeListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View v = inflater.inflate(R.layout.cafe_item, null);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(CafeListAdapter.ViewHolder holder, int position) {
        final CafeVO cafeVO = cafe_list.get(position);

        ViewHolder viewHolder = (ViewHolder)holder;
        Drawable drawable = mContext.getResources().getDrawable(cafeVO.getThumb_nail());
        viewHolder.img_thumb.setBackground(drawable);

        viewHolder.txt_cafe_name.setText(cafeVO.getTitle());
        viewHolder.layout_cafe_panel.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                mOnItemClickListener.onItemClick(view, cafeVO);
            }
        });
    }

    @Override
    public int getItemCount() {
        return cafe_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private LinearLayout layout_cafe_panel;
        private ImageView img_thumb;
        private TextView txt_cafe_name;

        public ViewHolder(View convertView){
            super(convertView);

            layout_cafe_panel = (LinearLayout)convertView.findViewById(R.id.cafe_item_layout);
            img_thumb = (ImageView)convertView.findViewById(R.id.img_thumb);
            txt_cafe_name = (TextView)convertView.findViewById(R.id.cafe_title);
        }
    }

}


