package com.example.org.cafe_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CafeListAdapter extends RecyclerView.Adapter<CafeListAdapter.ViewHolder>{
    private Context mContext;
    private ArrayList<CafeVO> cafe_list;

    public OnItemClickListner mOmItemClickListner = null;

    public interface OnItemClickListner{
        void onItemClick(View view, CafeVO cafeVO);
    }

    public void setOnItemClickListener(OnItemClickListner listener){
        mOmItemClickListner = listener;
    }

    public CafeListAdapter(Context mContext, ArrayList<CafeVO> cafe_list){
        this.mContext = mContext;
        this.cafe_list = cafe_list;
    }


    @Override
    public CafeListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View convertView = LayoutInflater.from(mContext).inflate(R.layout.cafe_item, parent, false);

        return new ViewHolder(convertView);
    }

    @Override
    public void onBindViewHolder(CafeListAdapter.ViewHolder holder, int position) {
        CafeVO cafeVO = cafe_list.get(position);
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
