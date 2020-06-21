package com.example.org.cafe_app;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.ViewHolder>{
    private Context mContext;
    private ArrayList<ItemVO> item_list;
    int item_layout;

    public ItemListAdapter(Context context, ArrayList<ItemVO> items, int item_layout){

        this.mContext = context;
        this.item_list = items;
        this.item_layout = item_layout;
    }

    public OnItemClickListener mOmItemClickListener = null;

    public interface OnItemClickListener{
        void onItemClick(View view, ItemVO itemVO);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mOmItemClickListener = listener;
    }

    public ItemListAdapter(Context mContext, ArrayList<ItemVO> item_list){
        this.mContext = mContext;
        this.item_list = item_list;
    }


    @Override
    public ItemListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View v = inflater.inflate(R.layout.item_layout, null);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ItemListAdapter.ViewHolder holder, int position) {
        ItemVO itemVO = item_list.get(position);

        ViewHolder viewHolder = (ViewHolder)holder;
        Drawable drawable = mContext.getResources().getDrawable(itemVO.getThumb_nail());
        viewHolder.img_thumb.setBackground(drawable);

        viewHolder.txt_item_name.setText(itemVO.getTitle());
        viewHolder.txt_price.setText(String.valueOf(itemVO.getPrice()));
    }

    @Override
    public int getItemCount() {
        return item_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private LinearLayout layout_item_panel;
        private ImageView img_thumb;
        private TextView txt_item_name;
        private TextView txt_price;

        public ViewHolder(View convertView){
            super(convertView);

            layout_item_panel = (LinearLayout)convertView.findViewById(R.id.item_layout);
            img_thumb = (ImageView)convertView.findViewById(R.id.item_img_thumb);
            txt_item_name = (TextView)convertView.findViewById(R.id.item_name);
            txt_price = (TextView)convertView.findViewById(R.id.item_price);
        }

    }
}


